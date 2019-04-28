package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.injection.ServiceBinder;
import ch.hearc.odi.koulutus.rest.ParticipantResource;
import ch.hearc.odi.koulutus.rest.ProgramResource;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class ParticipantRestTest extends JerseyTest {

  @Inject
  PersistenceService PersistenceService;

  @Test
  public void createParticipantReturnsExpectedCode() {
    // Arrange
    Integer expectedId = 3010;
    String expectedFirstName = "Sabrina";
    String expectedLastName = "Hillow";
    String expectedBirthdate= "15.03.1997";
    int expectedStatus = 200;



    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("FirstName", expectedFirstName);
    formData.add("LastName", expectedLastName);
    formData.add("Birthdate",expectedBirthdate);
    final Response response = target("participant").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Test
  public void createSessioneReturnsExpectedObject() {
    // Arrange
    Integer expectedId = 3011;
    String expectedFirstName = "Sabrina";
    String expectedLastName = "Hillow";
    String expectedBirthdate= "15.03.1997";

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("FirstName", expectedFirstName);
    formData.add("LastName", expectedLastName);
    formData.add("Birthdate",expectedBirthdate);
    final Response response = target("participant").request().post(Entity.form(formData));
    Participant actualParticipant= response.readEntity(Participant.class);
    String actualFirstName = actualParticipant.getFirstName();
    Integer actualId = actualParticipant.getId();
    String actualLastName= actualParticipant.getLastName();
    String actualBirthday = actualParticipant.getBirthdate();

    //Assert
    assertEquals(expectedId, actualId);
    assertEquals(expectedFirstName, actualFirstName);
    assertEquals(expectedLastName, actualLastName);
    assertEquals(expectedBirthdate, actualBirthday);
  }

  @Test
  public void createIncompleteParticipantReturnsErrorCode() {
    // Arrange
    Integer expectedId = 3011;
    String expectedFirstName = "Sabrina";
    String expectedLastName = "Hillow";
    String expectedBirthdate= "15.03.1997";

    int expectedStatus = 400;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("FirstName", expectedFirstName);
    formData.add("LastName", expectedLastName);
    formData.add("Birthdate",expectedBirthdate);
    final Response response = target("participant").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Override
  protected Application configure() {
    return new ResourceConfig() {
      {
        register(new ServiceBinder());
        register(ParticipantResource.class);
      }
    };
  }
}