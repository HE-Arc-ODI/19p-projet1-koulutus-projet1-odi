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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PersistenceService {

  private EntityManagerFactory entityManagerFactory;


  public PersistenceService() {
    //  an EntityManagerFactory is set up once for an application
    //  IMPORTANT: the name here matches the name of persistence-unit in persistence.xml
    entityManagerFactory = Persistence.createEntityManagerFactory("ch.hearc.odi.koulutus.jpa");
  }

  /**
   * Return all existing program
   *
   * @return a list
   */
  public ArrayList<Program> getPrograms() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Program> programs = entityManager.createQuery("from Program", Program.class)
            .getResultList();
    /*entityManager.getTransaction().commit();
    entityManager.close();*/
    return (ArrayList<Program>) programs;
  }

  /**
   * Return program by ID
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
   * Create a new Program and persist
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

  public Program createAndPersistProgram(Program program) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(program);
    entityManager.getTransaction().commit();
    entityManager.close();
    return program;
  }

  /**
   * Delete a program
   *
   * @param programId of the objet to delete
   * @throws ProgramException if the id does not match any existing product
   */
  public void deleteProgram(Integer programId) throws ProgramException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);

    if (program == null){
      throw  new ProgramException("Program with id "+ programId +" not found");
    }
    entityManager.remove(program);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  /**
   * Update a Program
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
    if (program == null){
      throw new ProgramException("Program with id "+ programId +" not found");
    }
    program.update(newProgram);
    entityManager.getTransaction().commit();
    return program;
  }

  /**
   * Return all existing courses for a given program id *
   *
   * @return a list
   */
  public ArrayList<Course> getCoursesByProgramId(Integer programId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Course> query = entityManager
            .createQuery("SELECT c from Course c where c.program.id = :programId", Course.class);

    List<Course> courses = query.setParameter("programId", programId).getResultList();

    if (courses == null) {
      throw new ProgramException("Program " + programId + " was not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();

    return (ArrayList<Course>) courses;
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
            .setParameter("programId", courseId)
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
  public Course createAndPersistCourse(Integer programId, Integer quarter, Integer year,
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
  }

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
  public Participant createAndPersistParticipant(String firstName, String lastName, String birthdate) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = new Participant(firstName, lastName, birthdate);
    entityManager.persist(participant);

    entityManager.getTransaction().commit();
    entityManager.close();

    return participant;
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
  public Session createAndPersistSession(Integer programId, Integer courseId, Date startDateTime, Date endDateTime,
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

public void registerParticipantToCourse(Integer programId, Integer courseId, Integer participantId) throws ProgramException, ParticipantException {
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

  @Override
  public void finalize() throws Throwable {
    entityManagerFactory.close();
    super.finalize();
  }



}





