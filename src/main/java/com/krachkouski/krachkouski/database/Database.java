package com.krachkouski.krachkouski.database;

import com.krachkouski.krachkouski.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {



    //Устанавливаем подключение к базе данных
    public static Connection connection() throws SQLException {
        String DB_URL = "jdbc:h2:~/Dune";
        String USER = "Krachkouski";
        String PASS = "7753191";
        return DriverManager.getConnection(DB_URL,USER,PASS);
    }



    //Создание таблицы терминов, если та не существует
    public static void createTableTerminology() {
        Connection conn;
        Statement stmt;
        try {
            conn = connection();

            stmt = conn.createStatement();
            String sql =  "CREATE TABLE IF NOT EXISTS TERMINOLOGY " +
                    "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    " WORD VARCHAR(255), " +
                    " DEFINITION CHARACTER LARGE OBJECT)";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }



    //Вставить значение в таблицу терминов
    public static void insertTableTerminology(String word, String definition) {
        Connection conn;
        Statement stmt;
        try {
            conn = connection();

            stmt = conn.createStatement();
            String sql =  "INSERT INTO TERMINOLOGY(WORD, DEFINITION) VALUES ('" + word.toUpperCase() + "', '" + definition + "')";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }



    //Показать все записи из таблицы терминов
    public static ObservableList<Word> selectTableTerminology(String searchPattern) {
        ObservableList<Word> wordsList = FXCollections.observableArrayList();
        Connection conn;
        Statement stmt;
        ResultSet rs;
        try {
            conn = connection();

            stmt = conn.createStatement();
            String sql =  "SELECT * FROM TERMINOLOGY WHERE WORD LIKE '%"+ searchPattern +"%' ORDER BY WORD";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                wordsList.add(new Word(rs.getInt("ID"), rs.getString("WORD"), rs.getString("DEFINITION")));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return wordsList;
    }



    //Удалить выбранную запись из таблицы терминов
    public static void deleteTableTerminology(int id) {
        Connection conn;
        Statement stmt;
        try {
            conn = connection();

            stmt = conn.createStatement();
            String sql =  "DELETE FROM TERMINOLOGY WHERE ID = " + id;
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }



    //Обновить выбранную запись из таблицы терминов
    public static void updateTableTerminology(int id, String word, String definition) {
        Connection conn;
        Statement stmt;
        try {
            conn = connection();

            stmt = conn.createStatement();
            String sql =  "UPDATE TERMINOLOGY SET WORD = '" + word.toUpperCase() + "', DEFINITION = '" + definition + "' WHERE ID = " + id;
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }



}
