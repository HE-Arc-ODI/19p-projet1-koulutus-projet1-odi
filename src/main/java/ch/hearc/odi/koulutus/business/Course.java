package ch.hearc.odi.koulutus.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
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


  public Course() {
    sessions = new ArrayList<>();
  }

  public Course(Integer quarter, Integer year, Integer maxNumberOfParticipants) {
    this();
    this.quarter = quarter;
    this.year = year;
    this.maxNumberOfParticipants = maxNumberOfParticipants;
  }

  public Course(Integer id, Integer quarter, Integer year, Integer maxNumberOfParticipants) {

    this(quarter, year, maxNumberOfParticipants);
    this.id = id;
  }

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  public Integer getId() {
    return id;
  }

  @OneToMany(targetEntity = Session.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "session")
  @OrderColumn(name = "order_session")
  public List<Session> getSessions() {
    return this.getSessions();
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getQuarter() {
    return quarter;
  }

  public void setQuarter(Integer quarter) {
    this.quarter = quarter;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMaxNumberOfParticipants() {
    return maxNumberOfParticipants;
  }

  public void setMaxNumberOfParticipants(Integer maxNumberOfParticipants) {
    this.maxNumberOfParticipants = maxNumberOfParticipants;
  }


}
