package edu.upc.dsa.database.util;

public class QueryHelper {

    public static String createQueryINSERT(Object object)
    {
        String query = "INSERT INTO " + object.getClass().getSimpleName() + " (";

        String[] fields = ObjectHelper.getFields(object);

        query = query + fields[0];

        for(int i = 1; i < fields.length; i++)
            query = query + "," + fields[i];


        query = query + ") VALUES (?";

        for(int i = 0; i < fields.length - 1; i++)
            query = query + ",?";

        query = query + ");";

        return query;
    }

    public static String createQueryDELETE(Object object)
    {
        String query = "DELETE FROM " + object.getClass().getSimpleName() + " WHERE name=?;";

        return query;
    }

    public static String createQuerySELECT(Class theClass, String field)
    {
        String query = "SELECT * from " + theClass.getSimpleName() + " WHERE " + field +
                "=?;";

        return query;
    }
    public static String createQuerySELECTAll(Class theClass)
    {
        return "SELECT * FROM " + theClass.getSimpleName() + ";";
    }

    public static String createQueryUPDATE(Object object)
    {
        String query = "UPDATE " + object.getClass().getSimpleName() + " SET ";
        String[] fields = ObjectHelper.getFields(object);

        for(String f : fields)
        {
            if(!f.equals("name"))
                query = query + f + "=?, ";
        }

        //erase the last ,
        query = query.substring(0,query.length()-2);

        query = query + " WHERE id=?;";

        return query;
    }

    public static String createQueryUPDATEAttribute(Class theClass, String attribute)
    {
        String query = "UPDATE " + theClass.getSimpleName() + " SET ";
        query = query + attribute + "=? WHERE id=?;";

        return query;
    }
    public static String createQueryINSERTItems(Class theClass, Object valueclass)
    {
        String query = "INSERT INTO " + theClass.getSimpleName() + " (";

        String[] fields = ObjectHelper.getFields(valueclass);

        query = query + fields[0];

        for(int i = 1; i < fields.length; i++)
            query = query + "," + fields[i];


        query = query + ") VALUES (?";

        for(int i = 0; i < fields.length - 1; i++)
            query = query + ",?";

        query = query + ");";


        return query;
    }

    public static String createQueryDELETEInventory(Class theClass)
    {
        String query = "DELETE FROM " + theClass.getSimpleName() +
                " WHERE id_u=?;";

        return query;
    }
    public static String createQueryDELETEInventoryItem(Class theClass)
    {
        String query = "DELETE FROM " + theClass.getSimpleName() +
                " WHERE id_i=?;";

        return query;
    }
    public static String createQueryDELETEGame(Class theClass)
    {
        String query = "DELETE FROM " + theClass.getSimpleName() +
                " WHERE playerID=?;";

        return query;
    }

    public static String createQueryDELETEWithCondition(Class theClass, String attribute)
    {
        String query = "DELETE FROM "+ theClass.getSimpleName() + " WHERE name=? AND " +
                attribute + "=?;";

        return query;
    }

}