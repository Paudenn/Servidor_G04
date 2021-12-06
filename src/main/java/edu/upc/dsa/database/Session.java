package edu.upc.dsa.database;

import edu.upc.dsa.models.User;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {
    int save(Object entity);
    void close();
    HashMap get(Class theClass, String attr, String value);
    int update(Object object);
    int update(Class theClass, int id, String attribute, Object value);
    int delete(Class theClass, String attribute, Object value);
    User getBy(Class theClass, String attr, Object value);


    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}
