package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;

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

    /*COURSE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{programId/course}")
    public Course createCourseByProgramId(@PathParam("programId") Integer programId,
                                          @PathParam("quarter") Integer quarter,
                                          @PathParam("year") Integer year,
                                          @PathParam("maxNumberOfParticipants") Integer maxNumberOfParticipants){
        try {
            return persistenceService.createAndPersistCourse(programId,quarter,year,maxNumberOfParticipants);
        } catch (ProgramException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Program "+ programId +" not found");
        }
    }

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
