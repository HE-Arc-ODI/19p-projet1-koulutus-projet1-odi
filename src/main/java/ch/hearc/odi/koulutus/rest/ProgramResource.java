package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.services.PersistenceService;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    public Program createProgram(@PathParam("name") String name,
                                 @PathParam("richDescription") String richDescription,
                                 @PathParam("field") String field,
                                 @PathParam("price") Integer price){
        return persistenceService.createAndPersistProgram(name,richDescription,field,price);
    }

    @GET
    @Path("{programId}")
    public Program getProgramById(@PathParam("programId") Integer programId){
        return persistenceService.getProgramById(programId);
    }
}
