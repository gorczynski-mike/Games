package com.gorczynskimike.warmercolder;

import java.sql.*;

public class DatabaseH2Service implements DatabaseService {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:./test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public DatabaseH2Service() {
        try( Connection conn = getDBConnection() ) {
            Statement stmnt = conn.createStatement();
            stmnt.execute("CREATE TABLE IF NOT EXISTS`scores` (" +
                    "`ScoreID` int PRIMARY KEY AUTO_INCREMENT," +
                    "`PlayerName` varchar (255)," +
                    "`PlayerScore` int);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(int i=0; i<10; i++) {
//            addScore("test"+i, 20*i);
//        }
//        getTop10Scores();
    }

    @Override
    public void addScore(String user, int score) {
        try( Connection conn = getDBConnection() ) {
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO scores (PlayerName, PlayerScore) VALUES (?,?);");
            stmnt.setString(1,user);
            stmnt.setInt(2,score);
            int result = stmnt.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTop10Scores() {
        StringBuilder sb = new StringBuilder();
        try( Connection conn = getDBConnection() ) {
            Statement stmnt = conn.createStatement();
            stmnt.execute("SELECT s.PlayerName, s.PlayerScore FROM scores s ORDER BY PlayerScore LIMIT 10;");
            ResultSet resultSet = stmnt.getResultSet();
            System.out.println(resultSet == null);
            while(resultSet.next()) {
                String playerName = resultSet.getString(1);
                int playerScore = resultSet.getInt(2);
                sb.append(playerName + "&&" + playerScore + System.lineSeparator());
                System.out.println(playerName + " : " + playerScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("error1: " + e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println("Error2: " + e.getMessage());
        }
        return dbConnection;
    }
}

