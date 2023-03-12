package com.skypro.hibernate;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    HibernateManager hibernateManager = new HibernateManager();
    hibernateManager.withEntityManager(em -> {
      Author author = new Author();
      author.setFirstName("Lev");
      author.setLastName("Tolstoy");
      Book book = new Book();
      book.setTitle("War and Peace");
      book.setAuthor(author);
      book.setISBN("978-3-16-148410-0");
      book.setPublicationYear(1959);
      author.getBooks().add(book);
      em.persist(author);
    });

    hibernateManager.withEntityManager(em -> {
      Book book = new Book();
      book.setTitle("War and Peace");
      book.setISBN("978-3-16-148410-1");
      Author author = em.getReference(Author.class, 1);
      book.setAuthor(author);
      em.persist(book);
    });

    Book book = hibernateManager.withEntityManager(em -> {
      return em.find(Book.class, "978-3-16-148410-1");
    });
//
//    hibernateManager.withEntityManager(em -> {
//      Book bookFromDatabase = em.find(Book.class, "978-3-16-148410-0");
//      System.out.println(bookFromDatabase.getISBN());
//      System.out.println(bookFromDatabase.getPublicationYear());
//      System.out.println(bookFromDatabase.getAuthor().fullName());
//    });
//
//    hibernateManager.withEntityManager(em -> {
//      Book book = em.find(Book.class, "978-3-16-148410-0");
//      book.setPublicationYear(1590);
//    });
//
//    hibernateManager.withEntityManager(em -> {
//      Book book = em.find(Book.class, "978-3-16-148410-0");
//      System.out.println(book.getISBN());
//      System.out.println(book.getPublicationYear());
//    });
//
//    hibernateManager.withEntityManager(em -> {
//      List<Book> books = em.createQuery("SELECT e FROM Book e", Book.class).getResultList();
//      System.out.println(books);
//    });
//
//    hibernateManager.withEntityManager(em -> {
//      Author author = em.find(Author.class, 1);
//      em.createQuery("DELETE FROM Book where author=:author")
//          .setParameter("author", author)
//          .executeUpdate();
//    });

//    hibernateManager.withEntityManager(em -> {
//      Book book = em.find(Book.class, "978-3-16-148410-0");
//      em.remove(book);
//    });

    hibernateManager.withEntityManager(em -> {
      List<Book> books = em.createQuery("SELECT e FROM Book e", Book.class).getResultList();
      System.out.println(books);
    });
  }
}
