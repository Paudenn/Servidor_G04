package edu.upc.dsa.models;

public class Game {

    private int id;
    private int level;
    private int score;
    private int playerID;

    public Game(){}

    public Game(int level, int score, int playerID) {
        this.level = level;
        this.score = score;
        this.playerID = playerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
