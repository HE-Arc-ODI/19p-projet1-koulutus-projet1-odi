package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
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

    @GET
    @Path("{participantId}")
    public Participant getParticipantById(@PathParam("participantId") Integer participantId){
        try{
            return persistenceService.getParticipantByID(participantId);
        }catch (ParticipantException ex){
            ex.printStackTrace();
            throw new WebApplicationException("Participant not found");
        }
    }

    @POST
    @Path("{programId}/course/{courseId}/participant/{participantId}")
    public void registerParticipantToCourse(@PathParam("programId") Integer programId,
                                            @PathParam("courseId") Integer courseId,
                                            @PathParam("participantId") Integer participantId){
        try{
            persistenceService.registerParticipantToCourse(programId,courseId,participantId);
        } catch (ProgramException ex){
            ex.printStackTrace();
            throw new WebApplicationException("Error with Program while registering");
        }catch (ParticipantException ex){
            ex.printStackTrace();
            throw new WebApplicationException("Error with Participant while registering");
        }
    }
    /*
  DELET : DELET PARTICIPANT FROM MARATHON
   */
    @DELETE
    @Path("{programId}/course/{courseId}/participant/{participantId}")
    @Consumes(MediaType.APPLICATION_JSON)



}
