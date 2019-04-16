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
    public ArrayList<Program> getAllPrograms() {
        return persistenceService.getPrograms();
    }
}
