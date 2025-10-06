package service;

import dao.TransactionDAO;
import model.Transaction;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void issueBook(int bookId, int memberId) {
        Transaction transaction = new Transaction();
        transaction.setBookId(bookId);
        transaction.setMemberId(memberId);
        transaction.setIssueDate(new Date());
        transactionDAO.addTransaction(transaction);
    }

    public Transaction getTransactionById(int id) { return transactionDAO.getTransactionById(id); }
    public void updateTransaction(Transaction transaction) { transactionDAO.updateTransaction(transaction); }
    public List<Transaction> getAllTransactions() { return transactionDAO.getAllTransactions(); }
}
