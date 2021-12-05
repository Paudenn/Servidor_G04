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


    /*public static void setter(Object object, String property, Object value) throws NoSuchMethodException {
        // Method // invoke
        System.out.println("Entra a la funcio setter.");
        Object ret = null;
        Class theClass = object.getClass();

        //Method[] methodList = theClass.getMethods();
        String gMethod = "set"+ property.substring(0,1).toUpperCase()+property.substring(1);
        System.out.println("metode> " + gMethod);
        Method setter = null;
        if(value.getClass()==String.class){
            setter = theClass.getMethod(gMethod,String.class);
        } else if(value.getClass()==Double.class){
            setter = theClass.getMethod(gMethod,double.class);
        }

        try{
            setter.invoke(object, value);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static Object getter(Object object, String property) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // Method // invoke
        System.out.println("Entra a la funcio getter.");
        // Method // invoke
        Object ret = null;
        Class theClass = object.getClass();
        //object."get"+property();  // object.getName();  property = "name"
        //Method sMethod = "getName";  //"get"+property.substring(0,1).toUpperCase()+property.substring(1);
        //Method method = theClass.getMethod(property, null)  ;


        //Method[] methodList = theClass.getMethods();
        String sMethod = "get"+ property.substring(0,1).toUpperCase()+property.substring(1);

        System.out.println("metode> "+sMethod);


        Method getter = theClass.getMethod(sMethod,null);
        ret = getter.invoke(object);

        return ret;
    }

    /*public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String ret = (String)getter(new User("PAU", "RUBL","hola"), "password");

        System.out.println(ret);
    }

     */
}
