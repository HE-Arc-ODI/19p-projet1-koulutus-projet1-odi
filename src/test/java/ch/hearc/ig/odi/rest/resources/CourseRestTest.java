package ch.hearc.ig.odi.rest.resources;

import static org.junit.Assert.assertEquals;

import ch.hearc.odi.koulutus.business.Course;
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

public class CourseRestTest extends JerseyTest {

  @Inject
  PersistenceService PersistenceService;

  @Test
  public void createCourseReturnsExpectedCode() {
    // Arrange
    Integer expectedId = 1004;
    Integer expectedQuarter = 2;
    Integer expectedYear = 2018;
    Integer expectedMaxNumberOfParticipants= 5;

    String expectedStatus = "OPEN";


    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("Quarter", Integer.toString(expectedQuarter));
    formData.add("Year", Integer.toString(expectedYear));
    formData.add("MaxNumberOfParticipants", Integer.toString(expectedMaxNumberOfParticipants));
    final Response response = target("course").request().post(Entity.form(formData));
    int responseStatus = response.getStatus();

    //Assert
    assertEquals(expectedStatus, responseStatus);
  }

  @Test
  public void createCourseReturnsExpectedObject() {
    // Arrange
    Integer expectedId = 1005;
    Integer expectedQuarter = 2;
    Integer expectedYear = 2018;
    Integer expectedMaxNumberOfParticipants= 5;

    String expectedStatus = "OPEN";

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("Quarter", Integer.toString(expectedQuarter));
    formData.add("Year", Integer.toString(expectedYear));
    formData.add("MaxNumberOfParticipants", Integer.toString(expectedMaxNumberOfParticipants));
    final Response response = target("course").request().post(Entity.form(formData));
    Course actualCourse = response.readEntity(Course.class);
    Integer actualQuarter = actualCourse.getQuarter();
    Integer actualId = actualCourse.getId();
    Integer actualYear = actualCourse.getYear();
    Integer actualMaxNumberOfParticipants = actualCourse.getMaxNumberOfParticipants();

    //Assert
    assertEquals(expectedId, actualId);
    assertEquals(expectedQuarter, actualQuarter);
    assertEquals(expectedMaxNumberOfParticipants, actualMaxNumberOfParticipants);
    assertEquals(expectedYear, actualYear);
  }

  @Test
  public void createIncompleteCourseReturnsErrorCode() {
    // Arrange
    Integer expectedId = 1006;
    Integer expectedQuarter = 2;
    Integer expectedYear = 2018;
    Integer expectedMaxNumberOfParticipants= 5;
    int expectedStatus = 400;

    // Act
    MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
    formData.add("id", Integer.toString(expectedId));
    formData.add("Quarter", Integer.toString(expectedQuarter));
    formData.add("Year", Integer.toString(expectedYear));
    formData.add("MaxNumberOfParticipants", Integer.toString(expectedMaxNumberOfParticipants));
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