package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Session;
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

public class SessionRestTest extends JerseyTest {

  @Inject
  PersistenceService PersistenceService;

  @Test
  public void createCourseReturnsExpectedCode() {
    // Arrange
    Integer expectedId = 2020;
    String expectedStartDateTime = "20.10.2018 17:00";
    String expectedEndDateTime = "20.10.2018 19:00";
    Double expectedPrice= 45.00;
    String expectedRoom = "B14";
    int expectedStatus = 200;



    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("StartDateTime", expectedStartDateTime);
    formData.add("EndDateTime", expectedEndDateTime);
    formData.add("Price", Double.toString(expectedPrice));
    formData.add("Room", expectedRoom);
    final Response response = target("session").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Test
  public void createSessioneReturnsExpectedObject() {
    // Arrange
    Integer expectedId = 2021;
    String expectedStartDateTime = "20.10.2018 17:00";
    String expectedEndDateTime = "20.10.2018 19:00";
    Double expectedPrice= 45.00;
    String expectedRoom = "B14";

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("StartDateTime", expectedStartDateTime);
    formData.add("EndDateTime", expectedEndDateTime);
    formData.add("Price", Double.toString(expectedPrice));
    formData.add("Room", expectedRoom);
    final Response response = target("session").request().post(Entity.form(formData));
    Session actualSession = response.readEntity(Session.class);
    String actualStartDateTime = actualSession.getStartDateTime();
    Integer actualId = actualSession.getId();
    String actualEndDateTime = actualSession.getEndDateTime();
    Double actualPrice = actualSession.getPrice();
    String actualRoom = actualSession.getRoom();

    //Assert
    assertEquals(expectedId, actualId);
    assertEquals(expectedEndDateTime, actualEndDateTime);
    assertEquals(expectedPrice, actualPrice);
    assertEquals(expectedRoom, actualRoom);
    assertEquals(expectedStartDateTime, actualStartDateTime);
  }

  @Test
  public void createIncompleteSessionReturnsErrorCode() {
    // Arrange
    Integer expectedId = 2021;
    String expectedStartDateTime = "20.10.2018 17:00";
    String expectedEndDateTime = "20.10.2018 19:00";
    Double expectedPrice= 45.00;
    String expectedRoom = "B14";
    int expectedStatus = 400;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("StartDateTime ", expectedStartDateTime);
    formData.add("EndDateTime", expectedEndDateTime);
    formData.add("Price", Double.toString(expectedPrice));
    formData.add("Room", expectedRoom);
    final Response response = target("session").request().post(Entity.form(formData));
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