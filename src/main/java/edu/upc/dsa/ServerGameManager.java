package edu.upc.dsa;

import edu.upc.dsa.models.Items;
import edu.upc.dsa.models.User;

import java.util.List;

public interface ServerGameManager {


    public User addUser(User user);
    public User addUser(String name, String password, String email);
    public void loginUser(String name, String password);
    public List<User> getUserList();
    public User getUser(String name);
    public void deleteUser(String name, String password, String email);
    public void logOutUser(String name);
    public Items addItem (Items items);
    public Items addItem (String name, String desription);

    public void deleteItems (String name);
    public List<Items> getItemList();
    public Items getItem(String name);

    public int size();

    void clear();
}
