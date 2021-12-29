package edu.upc.dsa;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.Items;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserInventory;

import java.util.List;

public interface ServerGameManager {


    public User addUser(User user);
    public User addUser(String name, String password, String email);
    public int loginUser(String name, String password);
    public List<User> getUserList();
    public User getUser(int id);
    public void deleteUser(int id);
    public void logOutUser(int id);
    public void updateScore (String name,String attribute,Object value);
    public User addToInventory (int id_u,int id_i);
    public List<Items> getUserItemList(int id);


    public Items addItem (Items items);
    public Items addItem (String name, String description);
    public void deleteItems (int id);
    public List<Items> getItemList();
    public Items getItem(int id);


    public Game addGame (int level, int score, int playerID);
    public Game restartGame (int playerID);
    public Game deleteGame (int playerID);

    public int size();
    void clear();
}
