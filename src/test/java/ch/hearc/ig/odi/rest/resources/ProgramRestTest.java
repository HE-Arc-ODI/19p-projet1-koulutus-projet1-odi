package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.injection.ServiceBinder;
import ch.hearc.odi.koulutus.rest.ProgramResource;
import ch.hearc.odi.koulutus.services.PersistenceService;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class ProgramRestTest extends JerseyTest {

  @Inject
  PersistenceService PersistenceService;

  @Test
  public void createProgramReturnsExpectedCode() {
    // Arrange
    Integer expectedId = 1004;
    String expectedName = "Cuisine coréenne";
    String expectedRichDescription = "Le Pays du matin calme se situe à la confluence entre la Chine et le Japon. Dans ce contexte spécifique, la cuisine coréenne, si elle semble plonger ses racines dans les traditions culinaires de ses voisins, se particularise par les divers ingrédients tels que l'ail, le piment rouge, la sauce de soja, le gingembre et les graines et huile de sésame.";
    String expectedField= "Gatronomie";
    Integer expectedPrice= 150;

    String expectedStatus = "200";


    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("name", expectedName);
    formData.add("RichDescription", expectedRichDescription);
    formData.add("Field", expectedField);
    formData.add("Price", Integer.toString(expectedPrice));
    final Response response = target("program").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Test
  public void createProgramReturnsExpectedObject() {
    // Arrange
    Integer expectedId = 1005;
    String expectedName = "Cuisine coréenne";
    String expectedRichDescription = "Le Pays du matin calme se situe à la confluence entre la Chine et le Japon. Dans ce contexte spécifique, la cuisine coréenne, si elle semble plonger ses racines dans les traditions culinaires de ses voisins, se particularise par les divers ingrédients tels que l'ail, le piment rouge, la sauce de soja, le gingembre et les graines et huile de sésame.";
    String expectedField= "Gatronomie";
    Integer expectedPrice= 150;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("name", expectedName);
    formData.add("RichDescription", expectedRichDescription);
    formData.add("Field", expectedField);
    formData.add("Price", Integer.toString(expectedPrice));
    final Response response = target("program").request().post(Entity.form(formData));
    Program actualProgram = response.readEntity(Program.class);
    String actualName = actualProgram.getName();
    Integer actualId = actualProgram.getId();
    String actualrichDescription = actualProgram.getRichDescription();
    String actuelField = actualProgram.getField();
    Integer actualPrice = actualProgram.getPrice();

    //Assert
    assertEquals(expectedId, actualId);
    assertEquals(expectedName, actualName);
    assertEquals(expectedField, actuelField);
    assertEquals(expectedPrice, actualPrice);
    assertEquals(expectedRichDescription, actualrichDescription);
  }

  @Test
  public void createIncompleteProgramReturnsErrorCode() {
    // Arrange
    String expectedName = "Cuisine coréenne";
    String expectedRichDescription = "Le Pays du matin calme se situe à la confluence entre la Chine et le Japon. Dans ce contexte spécifique, la cuisine coréenne, si elle semble plonger ses racines dans les traditions culinaires de ses voisins, se particularise par les divers ingrédients tels que l'ail, le piment rouge, la sauce de soja, le gingembre et les graines et huile de sésame.";
    String expectedField= "Gatronomie";
    Integer expectedPrice= 150;
    int expectedStatus = 400;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("name", expectedName);
    formData.add("RichDescription", expectedRichDescription);
    formData.add("Field", expectedField);
    formData.add("Price", Integer.toString(expectedPrice));
    final Response response = target("program").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Override
  protected Application configure() {
    return new ResourceConfig() {
      {
        register(new ServiceBinder());
        register(ProgramResource.class);
      }
    };
  }
}