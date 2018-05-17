package com.gorczynskimike.warmercolder;

import java.sql.ResultSet;

public interface DatabaseService {
    void addScore(String user, int score);
    ResultSet getTop10Scores();
}
