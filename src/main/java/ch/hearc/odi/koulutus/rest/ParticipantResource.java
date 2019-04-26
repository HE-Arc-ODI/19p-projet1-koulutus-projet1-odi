package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
    public void  deleteParticipantFromCourse(@PathParam("programId") Integer programId,
        @PathParam("courseId") Integer courseId, @PathParam("participantId") Integer participantId) {
        try {
            persistenceService.unregisterParticipantToCourse(programId, courseId, participantId);
        } catch (ProgramException | ParticipantException e) {
            e.printStackTrace();
            throw new NotFoundException("The program does not exist");
        }

    }

    @PUT
    public Participant updateParticipant(@FormParam("participantId") Integer participantId,
        @FormParam("firstName") String firstName,
        @FormParam("lastName") String lastName,
        @FormParam("birtday") String birthday) {
        try {
            return persistenceService.updateParticipant(participantId, firstName, lastName,birthday);
        } catch (ParticipantException e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

    }

    @Path("{participantId}/summary")
    @GET
    public void summaryParticipant(@PathParam("participantId") Integer participantId) {
        try {
             persistenceService.getParticipantByID(participantId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }


}
