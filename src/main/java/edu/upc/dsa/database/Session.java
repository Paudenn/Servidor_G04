package edu.upc.dsa.database;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.Items;
import edu.upc.dsa.models.User;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {
    int save(Object entity);
    void close();
    HashMap get(Class theClass, String attr, String value);
    int update(Object object);
    int update(Class theClass,int id,String attribute, Object value);
    int saveItem(Class theClass,int id_u,String attribute,Object id, Object valueclass);
    int delete(Class theClass, String attribute, Object value);
    int deleteInventory(Class theClass, String attribute, Object value);
    int deleteInventoryItems(Class theClass, String attribute, Object value);
    User getBy(Class theClass, String attr, Object value);
    Items getItems (Class theClass, String attr, Object value);
    Game getGame(Class theClass, String attr, Object value);
    int deleteGame(Class theClass, String attribute, Object entity);

    List<HashMap<String, Object>> getAllBy(Class theClass, String attr, Object value);
    List<HashMap<String, Object>> getAll(Class theClass);

    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}
