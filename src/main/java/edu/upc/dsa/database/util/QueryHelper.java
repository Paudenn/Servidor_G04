package edu.upc.dsa.database.util;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity);

        sb.append("ID");
        for (String field: fields) {
            sb.append(",").append(field);
        }

        sb.append(") VALUES (?");

        for (String field: fields) {
            sb.append(",?");
        }

        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String createQuerySELECT(Object entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(entity.getClass().getSimpleName());
        sb.append(" WHERE ID = ?");
        System.out.println(sb.toString());
        return sb.toString();
    }
    public static String createQueryDELETE(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE * FROM ").append(entity.getClass().getSimpleName());
        sb.append(" WHERE ID = ?");
        System.out.println(sb.toString());
        return  sb.toString();
    }

    public static String createQueryUPDATE(Object entity){
        /*StringBuffer sb = new StringBuffer("UPDATE ");
        sb.append(entity.getClass().getSimpleName());
        String [] fields = ObjectHelper.getFields(entity);
        sb.append(" SET (").append("ID");
        for (String field: fields) {
            sb.append(",").append(field);
        }

        sb.append(") WHERE ID = ?");
        System.out.println(sb.toString());
        return sb.toString();

         */
        StringBuffer sb = new StringBuffer("UPDATE ");
        sb.append(entity.getClass().getSimpleName());
        sb.append(" SET ").append("salary = ?");
        sb.append(" WHERE ID = ?");
        System.out.println(sb.toString());
        return sb.toString();
    }

}
