package com.robin.demojavafx;

import com.robin.demojavafx.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    public static void insertExpense(Expense expense) {
        String sql = "INSERT INTO expense (date, housing, food, goingOut, transportation, travel, tax, other) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, expense.getDate());
            pstmt.setDouble(2, expense.getHousing());
            pstmt.setDouble(3, expense.getFood());
            pstmt.setDouble(4, expense.getGoingOut());
            pstmt.setDouble(5, expense.getTransportation());
            pstmt.setDouble(6, expense.getTravel());
            pstmt.setDouble(7, expense.getTax());
            pstmt.setDouble(8, expense.getOther());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT date, housing, food, goingOut, transportation, travel, tax, other FROM expense";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getString("date"),
                        rs.getDouble("housing"),
                        rs.getDouble("food"),
                        rs.getDouble("goingOut"),
                        rs.getDouble("transportation"),
                        rs.getDouble("travel"),
                        rs.getDouble("tax"),
                        rs.getDouble("other")
                );
                expenses.add(expense);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }
}