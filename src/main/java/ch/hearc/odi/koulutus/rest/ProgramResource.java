package ch.hearc.odi.koulutus.rest;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

   /*
  GET :  Course BY ID FROM Program BY ID
 */

  @GET
  @Path("{programId}/course/{courseId}")
  public Course courseByIdFromGivenProgram(@PathParam("programId") Integer programId,
      @PathParam("courseId") Integer courseId) {
    try {
      return persistenceService.getCourseByIdProgramId(programId, courseId);
    } catch (ProgramException e) {
      e.printStackTrace();
      throw new NotFoundException("the program does not exist");
    }
  }

  /*
POST : ADD program
 */
  @POST
  public Program programPost(@FormParam("name") String name, @FormParam("richDesscription") String richDesscription,
      @FormParam("field") String field, @FormParam("price") Integer price)
      throws ParseException {
    return persistenceService.createAndPersistProgram(name, richDesscription,field,price);
  }



}
