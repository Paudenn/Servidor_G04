package edu.upc.dsa.models;

public class Items {


    private String name;
    private String description;
    private int id;

    public Items(){}

    public Items(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }
    public Items(String name, String description, int id) {
        this();
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {return this.name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}