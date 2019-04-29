/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.services;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

public class PersistenceService {

  private EntityManagerFactory entityManagerFactory;

  public PersistenceService() {
    //  an EntityManagerFactory is set up once for an application
    //  IMPORTANT: the name here matches the name of persistence-unit in persistence.xml
    entityManagerFactory = Persistence.createEntityManagerFactory("ch.hearc.odi.koulutus.jpa");
  }

  /**
   * Return all existing program Swagger : List all training programs   *
   *
   * @return a list
   */
  public ArrayList<Program> getPrograms() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Program> programs = entityManager.createQuery("from Program", Program.class)
        .getResultList();
    return (ArrayList<Program>) programs;
  }



  /**
   * Create a new Program and persist Swagger : Create a new training program
   *
   * @return the program object created
   */
  public Program createAndPersistProgram(String name, String richDescription, String field,
      Integer price) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = new Program(name, richDescription, field, price);
    entityManager.persist(program);
    entityManager.getTransaction().commit();
    entityManager.close();
    return program;
  }



  /**
   * Return program by ID Swagger : View the details of a training program
   *
   * @return a program
   */
  public Program getProgramById(Integer programId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);
    entityManager.getTransaction().commit();
    entityManager.close();
    return program;
  }

  /**
   * Delete a program Swagger : Delete an existing training program
   *
   * @param programId of the objet to delete
   * @throws ProgramException if the id does not match any existing product
   */
  public void deleteProgram(Integer programId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);

    if (program == null) {
      throw new ProgramException("Program with id " + programId + " not found");
    }
    entityManager.remove(program);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  /**
   * Update a Program Swagger : update an existing training program. Returns the updated training
   * program.
   *
   * @param programId specifies which object to update
   * @param newProgram new data
   * @return the program updated
   * @throws ProgramException if the id does not match any existing customer
   */
  public Program updateProgram(Integer programId, Program newProgram) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);
    if (program == null) {
      throw new ProgramException("Program with id " + programId + " not found");
    }
    program.update(newProgram);
    entityManager.getTransaction().commit();
    return program;
  }

  /**
   * Return all existing courses for a given program id * Swagger : Get all course for a given
   * program
   *
   * @return a list
   */

  public List<Course> getCoursesByProgramId(Integer programId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programId);
    if (program == null){
      throw new ProgramException("Program " + programId + " was not found");
    }
    entityManager.close();
    return program.getCourses();
  }

  /**
   * Return course by ID and program id
   *
   * @return a course
   */
  public Course getCourseByIdProgramId(Integer programId, Integer courseId)
      throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Course> query = entityManager.createQuery(
            "SELECT c from Course c where c.program.id = :programId and c.id = :courseId",
            Course.class);

    Course courses = query.setParameter("programId", programId)
        .setParameter("courseId", courseId)
        .getSingleResult();

    if (courses == null) {
      throw new ProgramException("Program or course not found");
    }

    entityManager.getTransaction().commit();
    entityManager.close();

    return courses;
  }

  /**
   * Create a new Course and persist
   *
   * @return the course object created
   */
  public Course createAndPersistCourse(Integer programId, Course courseToAdd) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = courseToAdd;
    Program program = getProgramById(programId);

    if(program != null){
      program.addCourse(course);
      entityManager.persist(course);
      entityManager.merge(program);
      entityManager.getTransaction().commit();
      entityManager.close();
    }else{
      throw new ProgramException("Program " + programId + " was not found");
    }

    return course;
  }
  /*public Course createAndPersistCourse(Integer programId, Integer quarter, Integer year,
      Integer maxNumberOfParticipants) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course courses = new Course(quarter, year, maxNumberOfParticipants);
    Program program = getProgramById(programId);

    if (program != null) {
      program.addCourse(courses);
      entityManager.persist(courses);
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      throw new ProgramException("Program " + programId + " was not found");
    }

    return courses;
  }*/

  /**
   * Return all existing participants
   *
   * @return a list
   */
  public ArrayList<Participant> getParticipant() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Participant> participant = entityManager.createQuery("from Participant", Participant.class)
        .getResultList();

    entityManager.getTransaction().commit();
    entityManager.close();

    return (ArrayList<Participant>) participant;
  }

  /**
   * Create a new Participant and persist
   *
   * @return the course object created
   */
  public Participant createAndPersistParticipant(String firstName, String lastName,
      String birthdate) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    try {
        Participant participant = new Participant(firstName, lastName, birthdate);
        entityManager.persist(participant);

        entityManager.getTransaction().commit();
        entityManager.close();

        return participant;
    } catch (Exception e){
        throw new ParticipantException("An error occured while creating the participant");
    }
  }

  /**
   * Find a participant by his id
   *
   * @param participantid : specifies which customer to return
   * @return an objet customer
   */
  public Participant getParticipantByID(Integer participantid) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Participant actualParticipant = entityManager.find(Participant.class, participantid);
    if (actualParticipant != null) {
      return actualParticipant;
    } else {
      throw new ParticipantException("Participant with id " + participantid + " not found");
    }
  }

  /**
   * Create a new Session and persist
   *
   * @return the session object created
   */
  public Session createAndPersistSession(Integer programId, Integer courseId, Date startDateTime,
      Date endDateTime,
      Double price, String room) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course courseFromBD = this.getCourseByIdProgramId(programId, courseId);

    Session session = new Session(startDateTime, endDateTime, price, room);

    if (courseFromBD != null) {
      courseFromBD.addSessions(session);
      entityManager.persist(session);
      entityManager.merge(courseFromBD);
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      throw new ProgramException("Program or course not found");
    }

    return session;
  }

  public void registerParticipantToCourse(Integer programId, Integer courseId,
      Integer participantId) throws ProgramException, ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    Course courseFromDB = this.getCourseByIdProgramId(programId, courseId);
    Participant participantFromDB = this.getParticipantByID(participantId);

    if (courseFromDB != null && participantFromDB != null) {
      participantFromDB.addCourses(courseFromDB);
      entityManager.merge(participantFromDB);
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      throw new ProgramException("Course or participant not found");
    }
  }

  /**
   * unregister a participant to a course
   */
  public void unregisterParticipantToCourse(Integer programId, Integer courseId,
      Integer participantId)
      throws ProgramException, ParticipantException {

    Course course = getCourseByIdProgramId(programId, courseId);

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    if (course == null) {
      throw new ProgramException(
          "Course with id " + courseId + " in Program with id " + programId + "not found ");
    }

    entityManager.remove(participantId);
    entityManager.merge(course);

    entityManager.getTransaction().commit();
    entityManager.close();

  }

  /**
   * Delete a course Swagger : delete a course for a given program
   */
  public Course deleteCourseFromProgram(Integer courseId, Integer programId)
      throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = entityManager.find(Course.class, courseId);

    if (course == null) {
      throw new ProgramException("Program with id " + courseId + " not found");
    }
    entityManager.remove(course);
    entityManager.getTransaction().commit();
    entityManager.close();
    return course;
  }




  @Override
  public void finalize() throws Throwable {
    entityManagerFactory.close();
    super.finalize();
  }


  public Program createAndPersistProgram(Program program) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(program);
    entityManager.getTransaction().commit();
    entityManager.close();
    return program;
  }

  public Course updateCourse(Integer programId, Integer courseId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = entityManager.find(Course.class, courseId);

    course.update(course);
    entityManager.getTransaction().commit();
    return course;
  }

  public Participant getParticipantFromGivenCourse(Integer participantId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Participant> query = entityManager
        .createQuery("SELECT p from Participant p where p.course.id = :participantId", Participant.class);

    List<Participant> participants = query.setParameter("participantId", participantId).getResultList();

    if (participants == null) {
      throw new ProgramException("Participant " + participantId + " was not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();

    return (Participant) participants;

  }

  public ArrayList<Session> getSessionByCourseAndProgramId(Integer programId, Integer courseId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Session> query = entityManager
        .createQuery("SELECT s from Session s where s.course.id = :courseId", Session.class);

    List<Session> sessions = query.setParameter("courseId", courseId).getResultList();

    if (sessions == null) {
      try {
        throw new ProgramException("Course " + courseId + " was not found");
      } catch (ProgramException e) {
        e.printStackTrace();
      }
    }
    entityManager.getTransaction().commit();
    entityManager.close();

    return (ArrayList<Session>) sessions;
  }

  public Session getSessionIdByCourseIdAndProgramId(Integer programId, Integer courseId, Integer sessionId) {

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    TypedQuery<Session> query = entityManager.createQuery(
        "SELECT s from Session s where s.course.id = :courseId and s.id = :programId",
        Session.class);

    Session session = query.setParameter("programId", programId)
        .setParameter("courseId", courseId)
        .setParameter("sessionId", sessionId)
        .getSingleResult();

    if (session == null) {
      try {
        throw new ProgramException("Program or course not found");
      } catch (ProgramException e) {e.printStackTrace();

      }
    }

    entityManager.getTransaction().commit();
    entityManager.close();

    return session;

  }

  public Session updateSession(Integer programId, Integer courseId, Integer sessionId,
      Date startDateTime, Date endDateTime, Double price,String room) {
    Program program = getProgramById(programId);
    Course c = (Course) program.getCourses();
    Session s = (Session) c.getSessions();
    s.setEndDateTime(endDateTime);
    s.setStartDateTime(startDateTime);
    s.setPrice(price);
    s.setRoom(room);
    return s;
  }

  public Participant updateParticipant(Integer participantId, String firstName, String lastName, String birthday)
          throws ParticipantException, ParseException {
    Participant p = this.getParticipantByID(participantId);
    if (p != null) {
      p.setFirstName(firstName);
      p.setLastName(lastName);
      p.setBirthdate(new SimpleDateFormat("dd.mm.yyyy").parse(birthday));
      return p;
    } else {
      throw new ParticipantException("unknown Participant: " + participantId);
    }
  }

  public Course addParticipantToCourse(Integer participantId, Integer courseId, Integer programId)
      throws ParticipantException {
    Program program = this.getProgramById(programId);
    Course course = (Course) program.getCourses();
    Participant participant = this.getParticipantByID(participantId);

    return null;
  }
}





