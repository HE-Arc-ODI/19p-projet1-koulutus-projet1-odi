package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import ch.hearc.ig.odi.business.Marathon;
import ch.hearc.ig.odi.injection.ServiceBinder;
import ch.hearc.ig.odi.service.RestService;
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
  RestService service;

  @Test
  public void createMarathonReturnsExpectedCode() {
    // Arrange
    long expectedId = 9999L;
    String expectedName = "Test Marathon";
    String expectedCity = "New Jersey";
    int expectedStatus = 200;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Long.toString(expectedId));
    formData.add("name", expectedName);
    formData.add("city", expectedCity);
    final Response response = target("marathon").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Test
  public void createMarathonReturnsExpectedObject() {
    // Arrange
    Long expectedId = 9999L;
    String expectedName = "Test Marathon";
    String expectedCity = "New Jersey";

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", expectedId.toString());
    formData.add("name", expectedName);
    formData.add("city", expectedCity);
    final Response response = target("marathon").request().post(Entity.form(formData));
    Marathon actualMarathon = response.readEntity(Marathon.class);
    String actualName = actualMarathon.getName();
    Long actualId = actualMarathon.getId();
    String actualCity = actualMarathon.getCity();

    //Assert
    assertEquals(expectedId, actualId);
    assertEquals(expectedName, actualName);
    assertEquals(expectedCity, actualCity);
  }

  @Test
  public void createIncompleteMarathonReturnsErrorCode() {
    // Arrange
    String expectedName = "Test Marathon";
    String expectedCity = "New Jersey";
    int expectedStatus = 400;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("name", expectedName);
    formData.add("city", expectedCity);
    final Response response = target("marathon").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Override
  protected Application configure() {
    return new ResourceConfig() {
      {
        register(new ServiceBinder());
        register(MarathonRest.class);
      }
    };
  }
}