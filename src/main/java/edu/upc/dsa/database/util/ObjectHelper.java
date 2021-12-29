package edu.upc.dsa.database.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectHelper {
    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();

        Field[] fields = theClass.getDeclaredFields();

        String[] sFields = new String[fields.length];
        int i=0;

        for (Field f: fields) {
            sFields[i++] = f.getName();
        }

        return sFields;

    }
    public static Object getValue (Object object, String property) {

        try {

            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            Object value = field.get(object);

            return value;
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }



}
