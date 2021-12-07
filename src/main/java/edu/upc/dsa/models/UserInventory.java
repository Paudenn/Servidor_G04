package edu.upc.dsa.models;

public class UserInventory {

    private int id_u;
    private int id_i;

    public UserInventory(){}

    public UserInventory(int id_u, int id_i) {
        this.id_i = id_i;
        this.id_u = id_u;
    }

    public int getId_i() {
        return id_i;
    }

    public void setId_i(int id_i) {
        this.id_i = id_i;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }
}
