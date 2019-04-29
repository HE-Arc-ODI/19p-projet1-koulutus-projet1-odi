package ch.hearc.odi.koulutus.business;


import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Participant")
@XmlRootElement(name = "Participant")
public class Participant {

  private Integer id;
  private String firstName;
  private String lastName;
  private Date birthdate;
  private List<Course> courses;

  public Participant() {
    courses = new ArrayList<>();
  }

  public Participant(String firstName, String lastName, Date birthdate) {
    this();
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
  }

  public Participant(String firstName, String lastName, String birthdate) throws Exception {
    this();
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = new SimpleDateFormat("dd.MM.yyyy").parse(birthdate);
  }

  public Participant(Integer id, String firstName, String lastName, Date birthdate) {
    this(firstName, lastName, birthdate);
    this.id = id;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public List<Course> courses() {
    return courses;
  }

  public void addCourses(Course course) throws ProgramException {
    if (!courses.contains(course)) {
      courses.add(course);
    }
  }

  public Course getCourses(Integer id) throws ParticipantException {
    for (Course course : this.courses) {
      if (course.getId() == (id.longValue())) {
        return course;
      }
    }
    throw new ParticipantException("Course not found: " + id);
  }

  public void removeFromCourse(Integer idCourse) throws ProgramException {
    this.courses.remove(this.getIndex(idCourse));
  }

  public int getIndex(Integer id) throws ProgramException {
    int i;
    for (i = 0; i < courses.size(); i++) {
      Course course = courses.get(i);
      if (course.getId() == (id.longValue())) {
        return i;
      }
    }
    throw new ProgramException("Index not found");
  }
  
}
