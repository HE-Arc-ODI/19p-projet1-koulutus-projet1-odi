package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;

import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

  @Inject
  private PersistenceService persistenceService;

  /*********************Program****************************************************************/
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<Program> getAllPrograms() {
    return persistenceService.getPrograms();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Program createProgram(Program program) {
    return persistenceService.createAndPersistProgram(program);
  }

  @GET
  @Path("{programId}")
  public Program getProgramById(@PathParam("programId") Integer programId) {
    return persistenceService.getProgramById(programId);
  }

  @DELETE
  @Path("{programId}")
  public void deleteProgramById(@PathParam("programId") Integer programId) {
    try {
      persistenceService.deleteProgram(programId);
      CacheControl cacheControl = new CacheControl();
      cacheControl.setMaxAge(86400);
    } catch (ProgramException ex) {
      ex.printStackTrace();
      throw new WebApplicationException("Program not delete");
    }
  }

  @PUT
  @Path("{programId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Program updateProgram(@PathParam("programId") Integer programId,
      Program newProgram) {
    try {
      return persistenceService.updateProgram(programId, newProgram);
    } catch (ProgramException ex) {
      ex.printStackTrace();
      throw new WebApplicationException("Program not updated");
    }
  }

  /***********************COURSE****************************************************************/

  @GET
  @Path("{programId}/course")
  public List<Course> getAllCourseFromProgram(@PathParam("programId") Integer programId) {
    try {
      return persistenceService.getCoursesByProgramId(programId);
    } catch (ProgramException ex) {
      ex.printStackTrace();
      throw new WebApplicationException("Program " + programId + " not found");
    }
  }

  @POST
  @Path("{programId}/course")
  @Consumes(MediaType.APPLICATION_JSON)
  public Course addCourseToProgram(@PathParam("programId") Integer programId, Course courseToAdd){
      try {
          return persistenceService.createAndPersistCourse(programId, courseToAdd);
      } catch (ProgramException e) {
          e.printStackTrace();
          throw new NotFoundException("The course does not exist");
      }
  }

  @GET
  @Path("{programId}/course/{courseId}")
  public Course courseByIdFromGivenProgram(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId) {
    try {
      return persistenceService.getCourseByIdProgramId(programId, courseId);
    } catch (ProgramException e) {
      e.printStackTrace();
      throw new NotFoundException("the program does not exist");
    }
  }
  /*NOT WORKING*/
  @DELETE
  @Path("{programId}/course/{courseId}")
  public void deleteCourseFromGivenProgram(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId) {
    try {
      persistenceService.deleteCourseFromProgram(courseId, programId);
    } catch (ProgramException e) {
      e.printStackTrace();
      throw new NotFoundException("the course does not exist");
    }
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{programId}/course/{courseId}")
  public Course updateCourse(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId, Course courseUpdated) {
    return persistenceService.updateCourse(programId, courseId, courseUpdated);
  }
  /*NOT TESTED*/
  @GET
  @Path("{programId}/course/{courseId}/participant")
  public List<Participant> participantFromGivenCourse(@PathParam("programId") Integer programId,
                                                      @PathParam("courseId") Integer courseId) {
    try {
      return persistenceService.getParticipantsFromGivenCourse(programId,courseId);
    } catch (ProgramException e) {
        e.printStackTrace();
        throw new WebApplicationException("Program or Course ID not found");
    }
  }

  /*********************SESSION****************************************************************/
  /*NOT TESTED*/
  /*@GET
  @Path("{programId}/course/{courseId}/session")
  public ArrayList<Session> getAllSessionForGivenCourseAndProg(
      @PathParam("programId") Integer programId, @PathParam("courseId") Integer courseId) {
    return persistenceService.getSessionByCourseAndProgramId(programId, courseId);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("{programId}/course/{courseId}/session")
  public Session addSessionToCourseAndProg(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId,
      Session sessionToAdd) {
    try {
      return persistenceService
          .createAndPersistSession(programId, courseId, sessionToAdd);
    } catch (ProgramException e) {
      e.printStackTrace();
      throw new NotFoundException("The course does not exist");
    }
  }

  @GET
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public Session getSessionForGivenCourseAndProg(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId, @PathParam("sessionId") Integer sessionId) {
    return persistenceService.getSessionIdByCourseIdAndProgramId(programId, courseId, sessionId);
  }

  /**
   * delete a session by id for a given course and program
   */
  /*@DELETE
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public void deleteSessionByIdFromCourseIdAndProgId(@PathParam("programId") Integer programId,
                                                     @PathParam("courseId") Integer courseId,
      Integer sessionId)
      throws ProgramException {
    Program p = getProgramById(programId);
    if (p != null) {
      if (courseId != null) {
        Course c = p.getCourse(courseId);
        if (c != null) {
          if (sessionId != null) {
            c.removeSession(sessionId);
          } else {
            throw new ProgramException("Session doesn't exsist");
          }
        }
      }
    }
  }
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @PUT
  public Session updateSession(@PathParam("programId") Integer programId, @FormParam("courseId") Integer courseId,
      @FormParam("sessionId") Integer sessionId, Session sessionUpdated) {
      try {
          return persistenceService.updateSession(programId, courseId, sessionId, sessionUpdated);
      } catch (ProgramException e){
          e.printStackTrace();
          throw new WebApplicationException("Program, course or session ID not found");
      }
  }

    /*********************PARTICIPANT****************************************************************/

    /*@POST
    @Path("{programId}/course/{courseId}/participant/{participantId}")
    public void registerParticipantToCourse(@PathParam("programId") Integer programId,
                                            @PathParam("courseId") Integer courseId,
                                            @PathParam("participantId") Integer participantId) {
        try {
            persistenceService.registerParticipantToCourse(programId, courseId, participantId);
        } catch (ProgramException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Error with Program while registering");
        } catch (ParticipantException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Error with Participant while registering");
        }
    }

    @DELETE
    @Path("{programId}/course/{courseId}/participant/{participantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteParticipantFromCourse(@PathParam("programId") Integer programId,
                                            @PathParam("courseId") Integer courseId,
                                            @PathParam("participantId") Integer participantId) {
        try {
            persistenceService.unregisterParticipantToCourse(programId, courseId, participantId);
        } catch (ProgramException | ParticipantException e) {
            e.printStackTrace();
            throw new NotFoundException("The program does not exist");
        }

    }

    @Path("{programId}/course/{courseId}/participant/{participantId}")
    @POST
    public void addParticipantCourse(@PathParam("programId") Integer programId,
                                     @PathParam("courseId") Integer courseId,
                                     @PathParam("participantId") Integer participantId) {
        try {
            persistenceService.addParticipantToCourse(participantId, courseId, programId);
        } catch (ParticipantException e) {
            e.printStackTrace();
        }
    }*/
}
