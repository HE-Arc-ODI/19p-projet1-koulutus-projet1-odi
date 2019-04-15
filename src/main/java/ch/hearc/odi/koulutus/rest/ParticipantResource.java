package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("participant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ParticipantResource {

  @Inject
  private PersistenceService persistenceService;

  @GET
  public List<Participant> personGet() {
    return persistenceService.getParticipant();
  }

}
