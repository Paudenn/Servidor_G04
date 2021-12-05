package edu.upc.dsa.models;

public class User {

    private String name;
    private String password;
    private String mail;
    //private Boolean active;
    private int highScore;

    public User(){}

    public User(String name, String password, String mail) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        //this.active = false;
        this.highScore = 0;

    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

   // public Boolean getActive() {return active;}

    // public void setActive(Boolean active) {this.active = active;}

    public int getHighScore() {return highScore;}

    public void setHighScore(int highScore) {this.highScore = highScore;}
}
