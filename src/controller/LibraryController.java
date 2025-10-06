package controller;

import model.Book;
import model.Member;
import model.Transaction;
import service.BookService;
import service.MemberService;
import service.TransactionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibraryController {
    private BookService bookService = new BookService();
    private MemberService memberService = new MemberService();
    private TransactionService transactionService = new TransactionService();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        int choice = -1;
        do {
            System.out.println("\n--- Library Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add Member");
            System.out.println("4. View Members");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = scanner.nextInt(); scanner.nextLine();

            switch(choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> addMember();
                case 4 -> viewMembers();
                case 5 -> issueBook();
                case 6 -> returnBook();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while(choice != 0);
    }

    private void addBook() {
        System.out.print("Title: "); String title = scanner.nextLine();
        System.out.print("Author: "); String author = scanner.nextLine();
        System.out.print("Publisher: "); String publisher = scanner.nextLine();
        System.out.print("Quantity: "); int qty = scanner.nextInt();
        bookService.addBook(new Book(0, title, author, publisher, qty));
        System.out.println("Book added successfully!");
    }

    private void viewBooks() {
        List<Book> books = bookService.getAllBooks();
        System.out.println("--- Books List ---");
        for(Book b : books) {
            System.out.println(b.getId() + ". " + b.getTitle() + " | " + b.getAuthor() + " | Qty: " + b.getQuantity());
        }
    }

    private void addMember() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Phone: "); String phone = scanner.nextLine();
        memberService.addMember(new Member(0, name, email, phone));
        System.out.println("Member added successfully!");
    }

    private void viewMembers() {
        List<Member> members = memberService.getAllMembers();
        System.out.println("--- Members List ---");
        for(Member m : members) {
            System.out.println(m.getId() + ". " + m.getName() + " | " + m.getEmail());
        }
    }

    private void issueBook() {
        System.out.print("Member ID: "); int memberId = scanner.nextInt();
        System.out.print("Book ID: "); int bookId = scanner.nextInt();
        Book book = bookService.getBookById(bookId);
        if(book == null || book.getQuantity() <= 0) {
            System.out.println("Book not available!");
            return;
        }
        transactionService.issueBook(bookId, memberId);
        bookService.updateBookQuantity(bookId, book.getQuantity() - 1);
        System.out.println("Book issued successfully!");
    }

    private void returnBook() {
        System.out.print("Transaction ID: "); int transId = scanner.nextInt();
        Transaction t = transactionService.getTransactionById(transId);
        if(t == null || t.getReturnDate() != null) {
            System.out.println("Invalid transaction!");
            return;
        }
        t.setReturnDate(new Date());
        transactionService.updateTransaction(t);
        Book book = bookService.getBookById(t.getBookId());
        bookService.updateBookQuantity(book.getId(), book.getQuantity() + 1);
        System.out.println("Book returned successfully!");
    }
}
