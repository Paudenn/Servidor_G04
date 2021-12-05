package edu.upc.dsa.database;

import edu.upc.dsa.ServerGameManagerImpl;
import edu.upc.dsa.database.util.ObjectHelper;
import edu.upc.dsa.database.util.QueryHelper;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;


public class SessionImpl implements Session {
    private final Connection conn;

    final Logger log = Logger.getLogger(ServerGameManagerImpl.class);

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    public void save(Object entity) {

        String insertQuery = QueryHelper.createQueryINSERT(entity);
        log.info(insertQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            pstm.setObject(1, 0);
            int i = 2;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }
            log.info(pstm.toString());
            pstm.executeUpdate();
           // pstm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try{
            this.conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Object get(Object o, int ID) {
        Class theClass = o.getClass();
        String selectQuery = QueryHelper.createQuerySELECT(o);
        log.info(selectQuery);

        Object entity = null;

        PreparedStatement pstm = null;
        ResultSet result = null;

        try{
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, ID);
            log.info("Anem a executar la Query.");
            result = pstm.executeQuery();
            log.info("Query executada satisfactoriament.");

            while (result.next()){
                Field[] fields = theClass.getDeclaredFields();
                result.getString(1);
                for(int i = 0; i < fields.length; i ++){
                    ResultSetMetaData rsmd = result.getMetaData();
                    String name = rsmd.getColumnName(i+2);
                    log.info("The column name is: "+ name);
                    ObjectHelper.setter(o,name, result.getObject(i+2));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return o;
    }

    public void update(Object object) {

    }

    public void delete(Object object) {

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
