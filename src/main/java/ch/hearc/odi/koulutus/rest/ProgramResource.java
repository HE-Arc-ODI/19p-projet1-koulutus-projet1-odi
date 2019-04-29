package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
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
  public Course deleteCourseFromGivenProgram(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId) {
    try {
      return persistenceService.deleteCourseFromProgram(courseId, programId);
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
  /*
  @GET
  @Path("{participantId}")
  public void participantFromGivenCourse(@PathParam("participantId") Integer participantId) {
    try {
      persistenceService.getParticipantFromGivenCourse(participantId);
    } catch (ProgramException e) {
      e.printStackTrace();
    }
  }*/

  /*********************SESSION****************************************************************/

  /*@GET
  @Path("{programId}/course/{courseId}/session")
  public ArrayList<Session> getAllSessionForGivenCourseAndProg(
      @PathParam("programId") Integer programId, @PathParam("courseId") Integer courseId) {
    return persistenceService.getSessionByCourseAndProgramId(programId, courseId);
  }

  @POST
  @Path("{programId}/course/{courseId}/session")
  public void addSessionToCourseAndProg(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId,
      @FormParam("startDateTime") Date startDateTime,
      @FormParam("endDateTime") Date endDateTime,
      @FormParam("price") Double price,
      @FormParam("room") String room) {
    try {
      persistenceService
          .createAndPersistSession(programId, courseId, startDateTime, endDateTime, price, room);
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
        Course c = (Course) p.getCourses(courseId);
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
  @PUT
  public Session updateSession(@PathParam("programId") Integer programId, @FormParam("courseId") Integer courseId,
      @FormParam("sessionId") Integer sessionId, @FormParam("startDateTime") Date startDateTime, @FormParam("endDateTime") Date endDateTime, @FormParam("price") Double price, @FormParam("room") String room) {
    return persistenceService.updateSession(programId, courseId, sessionId, startDateTime,endDateTime,price,room);

  }*/
}
