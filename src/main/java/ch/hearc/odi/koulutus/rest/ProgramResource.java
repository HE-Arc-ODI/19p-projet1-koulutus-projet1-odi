package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;

import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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
    public Program createProgram(Program program){
        return persistenceService.createAndPersistProgram(program);
    }

    @GET
    @Path("{programId}")
    public Program getProgramById(@PathParam("programId") Integer programId){
        return persistenceService.getProgramById(programId);
    }

    @DELETE
    @Path("{programId}")
    public void deleteProgramById(@PathParam("programId") Integer programId){
        try{
            persistenceService.deleteProgram(programId);
            CacheControl cacheControl = new CacheControl();
            cacheControl.setMaxAge(86400);
        }catch (ProgramException ex){
            ex.printStackTrace();
            throw new WebApplicationException("Program not delete");
        }
    }

    @PUT
    @Path("{programId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateProgram(@PathParam("programId") Integer programId,
                              Program newProgram){
        try{
            persistenceService.updateProgram(programId,newProgram);
        }catch (ProgramException ex){
            ex.printStackTrace();
            throw new WebApplicationException("Program not updated");
        }
    }
/***********************COURSE****************************************************************/

  @GET
  @Path("{programId}/course")
  public Course getAllCourseFromProgram(@PathParam("programId") Integer programId){
    
  }


  @POST
  @Path("{courseId}")
  public Course addCourseToProgram(@PathParam("courseId") Integer courseId,
      @FormParam("quarter") Integer quarter,
      @FormParam("year") Integer year,
      @FormParam("maxNumberOfParticipants") Integer maxNumberOfParticipants) {

    try {
      return persistenceService
          .createAndPersistCourse(courseId, quarter, year, maxNumberOfParticipants);
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


/*
    //A TESTER !!!!!!!!!!!!!!!!!!!!
    @GET
    @Path("{programId/course}")
    public ArrayList<Course> getCourseByProgramId(@PathParam("programId") Integer programId){
        try {
            return persistenceService.getCoursesByProgramId(programId);
        } catch (ProgramException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Program "+ programId +" not found");
        }
    }*/
}
