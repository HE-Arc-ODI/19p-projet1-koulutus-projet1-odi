package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.services.PersistenceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("participant")
@Produces(MediaType.APPLICATION_JSON)
public class ParticipantResource {
    @Inject
    PersistenceService persistenceService;

    @GET
    public ArrayList<Participant> getAllParticipant(){
        return persistenceService.getParticipant();
    }
}
