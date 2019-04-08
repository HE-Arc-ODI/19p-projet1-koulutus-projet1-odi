package ch.hearc.odi.koulutus.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;

public class PersistenceServiceTest {

  private PersistenceService persistenceService;
  private EntityManagerFactory entityManagerFactory;

  @Before
  public void setUp() throws Exception {
    persistenceService = new PersistenceService();
    entityManagerFactory = Persistence.createEntityManagerFactory("ch.hearc.odi.koulutus.jpa");
  }

  @Test
  public void createAndPersistAPojo() {
    String testString = "hello world!";
    Pojo pojo = persistenceService.createAndPersistAPojo("hello world!");

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedPojoID = pojo.getId();
    Pojo actualPojo = entityManager.find(Pojo.class, expectedPojoID);

    assertEquals(pojo, actualPojo);
  }
}