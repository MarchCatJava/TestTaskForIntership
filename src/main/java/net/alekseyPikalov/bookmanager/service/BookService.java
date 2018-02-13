package net.alekseyPikalov.bookmanager.service;

import net.alekseyPikalov.bookmanager.model.Book;
import net.alekseyPikalov.bookmanager.model.Book;

import java.util.List;

public interface BookService {
    public void addBook(Book book);

    public void updateBook(Book book);

    public void removeBook(int id);

    public Book getBookById(int id);

    public void readBook(int id);

    public List<Book> listBooks();
}
