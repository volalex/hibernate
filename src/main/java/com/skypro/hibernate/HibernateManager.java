package com.skypro.hibernate;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateManager {

  private SessionFactory sessionFactory;

  public HibernateManager() {
    Configuration configuration = new Configuration().configure();
    configuration.addAnnotatedClass(Book.class);
    configuration.addAnnotatedClass(Author.class);
    this.sessionFactory = configuration.buildSessionFactory();
  }

  public void withEntityManager(Consumer<EntityManager> function) {
    try (Session session = this.sessionFactory.openSession()) {
      session.beginTransaction();
      function.accept(session);
      session.getTransaction().commit();
    }
  }

  public <T> T withEntityManager(Function<EntityManager, T> function) {
    try (Session session = this.sessionFactory.openSession()) {
      session.beginTransaction();
      T result = function.apply(session);
      session.getTransaction().commit();
      return result;
    }
  }
}
