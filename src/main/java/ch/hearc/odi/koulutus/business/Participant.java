package ch.hearc.odi.koulutus.business;


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
}
