package edu.upc.dsa;

import edu.upc.dsa.models.Items;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserInventory;

import java.util.List;

public interface ServerGameManager {


    public User addUser(User user);
    public User addUser(String name, String password, String email);
    public void loginUser(String name, String password);
    public List<User> getUserList();
    public User getUser(int id);
    public void deleteUser(int id);
    public void logOutUser(int id);
    public void updateScore (String name,String attribute,Object value);
    public User addToInventory (int id_u,int id_i);


    public Items addItem (Items items);
    public Items addItem (String name, String desription);
    public void deleteItems (String name);
    public List<Items> getItemList();
    public Items getItem(String name);

    public int size();

    void clear();
}
