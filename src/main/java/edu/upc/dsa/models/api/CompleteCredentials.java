package edu.upc.dsa.models.api;

public class CompleteCredentials {
    private String name;
    private String password;
    private String mail;

    public CompleteCredentials(String name, String password, String mail)
    {
        this.name = name;
        this.password = password;
        this.mail = mail;
    }

    public CompleteCredentials(){}

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String email) {
        this.mail = email;
    }
}
