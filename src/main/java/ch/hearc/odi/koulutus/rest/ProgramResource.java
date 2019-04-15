package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("program")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ProgramResource {

  @Inject
  private PersistenceService persistenceService;

  /*
  Get all programs
   */
  @GET
  public List<Program> programGet() {
    return persistenceService.getPrograms();
  }

  /*
  Get program By Id
   */
  @GET
  @Path("{programId}")
  public Program getProgramById(@PathParam("programId") Integer programId) {
    return persistenceService.getProgramById(programId);
  }



}
