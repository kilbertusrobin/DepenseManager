package com.robin.demojavafx.db;

import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Location of database
     */
    private static final String location = getDatabaseLocation();

    /**
     * Currently only table needed
     */
    private static final String requiredTable = "Expense";

    public static boolean isOK() {
        if (!checkDrivers()) return false; //driver errors

        if (!checkConnection()) return false; //can't connect to db

        return createTableIfNotExists(); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createTables =
                """
                        CREATE TABLE IF NOT EXISTS expense(
                             date TEXT NOT NULL,
                             housing REAL NOT NULL,
                             food REAL NOT NULL,
                             goingOut REAL NOT NULL,
                             transportation REAL NOT NULL,
                             travel REAL NOT NULL,
                             tax REAL NOT NULL,
                             other REAL NOT NULL
                     );
                   """;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(createTables);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not find tables in database");
            return false;
        }
    }

    public static Connection connect() {
        createDatabaseIfNotExists();  // Ensure database is created

        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }

    private static void createDatabaseIfNotExists() {
        File dbFile = new File(location);
        File parentDir = dbFile.getParentFile();

        // Vérifier si le répertoire existe, sinon le créer
        if (!parentDir.exists()) {
            boolean dirsCreated = parentDir.mkdirs();
            if (dirsCreated) {
                Logger.getAnonymousLogger().log(Level.INFO, LocalDateTime.now() + ": Created directory at " + parentDir.getAbsolutePath());
            } else {
                Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Failed to create directory at " + parentDir.getAbsolutePath());
                return;
            }
        }

        // Vérifier si le fichier de base de données existe
        if (!dbFile.exists()) {
            try {
                boolean created = dbFile.createNewFile();
                if (created) {
                    Logger.getAnonymousLogger().log(Level.INFO, LocalDateTime.now() + ": Database file created at " + location);
                }
            } catch (Exception e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Failed to create database file at " + location);
            }
        }
    }


    private static String getDatabaseLocation() {
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();

        // Assuming you're on Windows, and creating in AppData
        return System.getenv("APPDATA") + "\\DepenseManager\\database.db";
    }

}
