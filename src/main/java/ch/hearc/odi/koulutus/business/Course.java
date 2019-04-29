package ch.hearc.odi.koulutus.business;

import ch.hearc.odi.koulutus.exception.ProgramException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Course")
@XmlRootElement(name = "Course")
public class Course extends Session implements Serializable {
    private Integer id;

    public enum QuarterEnum {
        num_1(Integer.valueOf(1)),
        num_2(Integer.valueOf(2)),
        num_3(Integer.valueOf(3)),
        num_4(Integer.valueOf(4));
        private Integer quarterEnum;

        QuarterEnum(Integer quarterEnum) {

            this.quarterEnum = quarterEnum;
        }

        public String toString() {
            return super.toString();
        }
    }

    @JsonProperty("quarter")
    private Integer quarter;
    private Integer year;
    private Integer maxNumberOfParticipants;
    private Program program;

    public enum StatusEnum {
        OPEN("OPEN"),
        CONFIRMED("CONFIRMED"),
        CANCELED("CANCELED");
        private String statusEnum;

        StatusEnum(String statusEnum) {
            this.statusEnum = statusEnum;
        }

        public String toString() {
            return super.toString();
        }
    }

    @JsonProperty("status")
    private String status;

    private List<Session> sessions;

    public Course() {
        this.status = StatusEnum.OPEN.toString();
        this.sessions = new ArrayList<>();
    }

    public Course(String quarter, Integer year, Integer maxNumberOfParticipants){
        this();
        this.quarter = Integer.valueOf(quarter);
        this.year = year;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    public Course(Integer id, String quarter, Integer year, Integer maxNumberOfParticipants,
      StatusEnum status) {
        this(quarter,year,maxNumberOfParticipants);
        this.status = status.toString();
        this.id = id;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public int getIndex(Integer id) throws ProgramException {
        int i;
        for (i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);
            if (session.getId() == id) {
                return i;
            }
        }
        throw new ProgramException("Index not found");
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuarter() {
        return this.quarter;
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

    @OneToMany(targetEntity = Session.class, fetch = FetchType.EAGER)
    //@JoinColumn(name = "sessions")
    //@OrderColumn(name = "order_session")
    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void addSessions(Session session) throws ProgramException {
        sessions.add(session);
    }

    public void removeSession(Integer idSession) throws ProgramException {
        this.sessions.remove(this.getIndex(idSession));
    }

    public Session getSession(Integer id){
        for (Session session : sessions){
            if(session.getId() == id){
                return session;
            }
        }
        return null;
    }

    @ManyToOne
    @JsonBackReference
    public Program getProgram(){
        return program;
    }

    public void setProgram(Program program){
        this.program = program;
    }

    public void update(Course newCourse) {
        this.setMaxNumberOfParticipants(newCourse.getMaxNumberOfParticipants());
        this.setQuarter(newCourse.getQuarter());
        //this.setSessions(newCourse.getSessions(sessionId));
        this.setYear(newCourse.getYear());
    }
}
