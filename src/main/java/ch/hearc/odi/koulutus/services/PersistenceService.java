/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.services;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ProgramException;
import com.sun.tools.sjavac.ProblemException;
import java.util.ArrayList;
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
    entityManager.getTransaction().commit();
    entityManager.close();
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
  public Program createAndPersistProgram(String name, String richDescription, String field, Integer price) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = new Program(name, richDescription, field, price);
    entityManager.persist(program);
    entityManager.getTransaction().commit();
    entityManager.close();
    return program;
  }

  /**
   * Return all existing courses for a given program id
   *    *
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
  @Override
  public void finalize() throws Throwable {
    entityManagerFactory.close();
    super.finalize();
  }



}





