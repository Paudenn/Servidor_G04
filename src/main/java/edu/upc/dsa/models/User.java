package edu.upc.dsa.models;

public class User {

    private String name;
    private String password;
    private String mail;
    private int highScore;
    private int id;
    private int active;


    public User(){}

    public User(String name, String password, String mail) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        //this.active = false;
        this.highScore = 0;

    }
    public User(String name, String password, String mail, int id) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.highScore = 0;
        this.id = id;
        this.active = 0;

    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

    public int getActive() {return active;}

    public void setActive(int active) {this.active = active;}

    public int getHighScore() {return highScore;}

    public void setHighScore(int highScore) {this.highScore = highScore;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
