package ch.hearc.odi.koulutus.business;


import ch.hearc.odi.koulutus.exception.ProgramException;
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
  private String birthdate;
  private List<Course> courses;

  public Participant() {
    courses = new ArrayList<>();
  }

  public Participant(String firstName, String lastName, String birthdate) {
    this();
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
  }

  public Participant(Integer id, String firstName, String lastName, String birthdate) {
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

  public String getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }

  public List<Course> courses() {
    return courses;
  }

  public void addCourses(Course course) throws ProgramException {
    courses.add(course);
  }
}
