package com.gorczynskimike.warmercolder;

public interface DatabaseService {
    void addScore(String user, int score);
    String getTop10Scores();
}
