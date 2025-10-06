package service;

import dao.BookDAO;
import model.Book;
import java.util.List;

public class BookService {
    private BookDAO bookDAO = new BookDAO();

    public void addBook(Book book) { bookDAO.addBook(book); }
    public List<Book> getAllBooks() { return bookDAO.getAllBooks(); }
    public Book getBookById(int id) { return bookDAO.getBookById(id); }
    public void updateBookQuantity(int id, int newQty) { bookDAO.updateBookQuantity(id, newQty); }
}
