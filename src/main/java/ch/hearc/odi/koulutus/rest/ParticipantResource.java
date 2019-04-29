package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("participant")
@Produces(MediaType.APPLICATION_JSON)
public class ParticipantResource {

    @Inject
    PersistenceService persistenceService;

    @GET
    public ArrayList<Participant> getAllParticipant() {
        return persistenceService.getParticipant();
    }

    @GET
    @Path("{participantId}")
    public Participant getParticipantById(@PathParam("participantId") Integer participantId) {
        try {
            return persistenceService.getParticipantByID(participantId);
        } catch (ParticipantException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Participant not found");
        }
    }


    /*
    *//*
  DELET : DELET PARTICIPANT FROM MARATHON
   *//*



    @PUT
    public Participant updateParticipant(@FormParam("participantId") Integer participantId,
        @FormParam("firstName") String firstName,
        @FormParam("lastName") String lastName,
        @FormParam("birtday") String birthday) throws Exception {
        try {
            return persistenceService
                .updateParticipant(participantId, firstName, lastName, birthday);
        } catch (ParticipantException e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (ParseException e){
            e.printStackTrace();
            throw new Exception("error in date format");
        }

    }

    @Path("{participantId}/summary")
    @GET
    public Participant summaryParticipant(@PathParam("participantId") Integer participantId) {
        try {
            return persistenceService.getParticipantByID(participantId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }


    */
}
