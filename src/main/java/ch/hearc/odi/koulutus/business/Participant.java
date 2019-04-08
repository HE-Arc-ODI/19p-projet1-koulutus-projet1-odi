package ch.hearc.odi.koulutus.business;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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

}
