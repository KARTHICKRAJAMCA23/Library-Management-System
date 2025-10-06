package dao;

import model.Transaction;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions(book_id, member_id, issue_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, transaction.getBookId());
            ps.setInt(2, transaction.getMemberId());
            ps.setDate(3, new java.sql.Date(transaction.getIssueDate().getTime()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) transaction.setId(rs.getInt(1));

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Transaction getTransactionById(int id) {
        String sql = "SELECT * FROM transactions WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setBookId(rs.getInt("book_id"));
                t.setMemberId(rs.getInt("member_id"));
                t.setIssueDate(rs.getDate("issue_date"));
                t.setReturnDate(rs.getDate("return_date"));
                return t;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET return_date=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (transaction.getReturnDate() != null) {
                ps.setDate(1, new java.sql.Date(transaction.getReturnDate().getTime()));
            } else {
                ps.setNull(1, Types.DATE);
            }
            ps.setInt(2, transaction.getId());
            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setBookId(rs.getInt("book_id"));
                t.setMemberId(rs.getInt("member_id"));
                t.setIssueDate(rs.getDate("issue_date"));
                t.setReturnDate(rs.getDate("return_date"));
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
