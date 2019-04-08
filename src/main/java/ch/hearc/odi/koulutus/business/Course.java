package ch.hearc.odi.koulutus.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Course")
@XmlRootElement(name = "Course")
public class Course implements Serializable {
  private Integer id;
  private Integer quarter;
  private Integer year;
  private Integer maxNumberOfParticipants;
  enum status {
    OPEN,
    CONFIRMED,
    CANCELLED
  }
  private List<Session> sessions;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  public Integer getId() {
    return id;
  }

  public Course() {
    sessions = new ArrayList<>();
  }
}
