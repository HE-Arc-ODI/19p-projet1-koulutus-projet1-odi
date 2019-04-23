package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

/*
  @GET
  @Path("{id}")
  public Participant getParticipant(@Context HttpServletRequest servletRequest, @PathParam("id") Integer id) {
    try {
      return persistenceService.getP(id);
    } catch (PersonException e) {
      e.printStackTrace();
      throw new NotFoundException("the person does not exist.");
    }
  }*/

}
