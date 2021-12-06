package edu.upc.dsa.database;

import edu.upc.dsa.ServerGameManagerImpl;
import edu.upc.dsa.database.util.ObjectHelper;
import edu.upc.dsa.database.util.QueryHelper;
import edu.upc.dsa.models.Items;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownServiceException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;


public class SessionImpl implements Session {
    private final Connection conn;

    final Logger log = Logger.getLogger(ServerGameManagerImpl.class);

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    public int save(Object entity) {

        String insertQuery = QueryHelper.createQueryINSERT(entity);
        log.info(insertQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            int i = 1;

            for (String field: ObjectHelper.getFields(entity)) {
                if(field.equals("id")){
                    pstm.setNull(1,Types.NULL);

                }
                else {
                    java.lang.Object object = ObjectHelper.getValue(entity, field);
                    pstm.setObject(i,object);
                    i++;
                }
            }
            log.info(pstm.toString());
            pstm.executeQuery();


        } catch (SQLIntegrityConstraintViolationException e)
        {
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public void close() {
        try{
            this.conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int update(Object entity) {

        PreparedStatement preparedStatement = null;
        String query = QueryHelper.createQueryUPDATE(entity);
        ResultSet resultSet;

        try
        {
            preparedStatement = conn.prepareStatement(query);
            String[] fields = ObjectHelper.getFields(entity);

            int i = 1;

            for(String f : fields)
            {
                if(!f.equals("id")) {
                    preparedStatement.setObject(i, ObjectHelper.getValue(entity, f));
                    i++;
                }
            }
            preparedStatement.setObject(fields.length, ObjectHelper.getValue(entity, "id"));

            boolean error = preparedStatement.execute();
            if(!error)
                return 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public int update(Class theClass, int id, String attribute, Object value) {

        PreparedStatement preparedStatement = null;
        String query = QueryHelper.createQueryUPDATEAttribute(theClass, attribute);
        ResultSet resultSet;

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, id);

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    public int delete(Class theClass, String attribute, Object entity) {

        PreparedStatement preparedStatement = null;
        String query = QueryHelper.createQueryDELETEWithCondition(theClass, attribute);

        try {
            preparedStatement = conn.prepareStatement(query);

            for (String field : ObjectHelper.getFields(entity)) {
                if (field.equals("name")) {
                    java.lang.Object object = ObjectHelper.getValue(entity, field);
                    preparedStatement.setObject(1, object);

                } else if (field.equals(attribute)) {
                    java.lang.Object object = ObjectHelper.getValue(entity, field);
                    preparedStatement.setObject(2, object);
                }
            }
            preparedStatement.executeQuery();
        }
        catch(SQLException e){
                e.printStackTrace();
                return -2;
            }

            return 0;
        }


    public User getBy(Class theClass, String attr, Object value) {

        PreparedStatement preparedStatement = null;
        String query = QueryHelper.createQuerySELECT(theClass, attr);
        User attributes = new User();
        try
        {
            preparedStatement = conn.prepareStatement(query);
            for (String field : ObjectHelper.getFields(value)) {
                if (field.equals("name")) {
                    java.lang.Object object = ObjectHelper.getValue(value, field);
                    preparedStatement.setObject(1, object);
                    attributes.setName(object.toString());
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                attributes.setPassword(resultSet.getObject(2).toString());
                attributes.setMail(resultSet.getObject(3).toString());

                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {

                    //attributes.add(resultSetMetaData.getColumnName(1), resultSet.getObject(i + 1));
                }
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;
    }
    public Items getItems(Class theClass, String attr, Object value) {

        PreparedStatement preparedStatement = null;
        String query = QueryHelper.createQuerySELECT(theClass, attr);
        Items attributes = new Items();
        try
        {
            preparedStatement = conn.prepareStatement(query);
            for (String field : ObjectHelper.getFields(value)) {
                if (field.equals("name")) {
                    java.lang.Object object = ObjectHelper.getValue(value, field);
                    preparedStatement.setObject(1, object);
                    attributes.setName(object.toString());
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                attributes.setDescription(resultSet.getObject(2).toString());
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;
    }
    public HashMap get(Class theClass, String attr, String value) {
        PreparedStatement preparedStatement = null;
        String query = QueryHelper.createQuerySELECT(theClass, attr);
        HashMap<String, java.lang.Object> attributes = new HashMap<>();;

        try
        {
            preparedStatement = conn.prepareStatement(query);
            for (String field : ObjectHelper.getFields(value)) {
                if (field.equals("name")) {
                    java.lang.Object object = ObjectHelper.getValue(value, field);
                    preparedStatement.setObject(1, object);

                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.first()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    attributes.put(resultSetMetaData.getColumnName(i + 1), resultSet.getObject(i + 1));
                }
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;

    }

    public List<Object> findAll(Class theClass) {
        return null;
    }

    public List<Object> findAll(Class theClass, HashMap params) {
        return null;
    }

    public List<Object> query(String query, Class theClass, HashMap params) {
        return null;
    }
}
