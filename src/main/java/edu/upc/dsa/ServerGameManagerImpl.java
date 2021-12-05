package edu.upc.dsa;

import java.util.*;

import edu.upc.dsa.database.FactorySession;
import edu.upc.dsa.database.Session;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Items;
import org.apache.log4j.Logger;


public class ServerGameManagerImpl implements ServerGameManager {
    private static ServerGameManager instance;
    private HashMap<String, User> users;
    protected List<Items> items;
    final static Logger logger = Logger.getLogger(ServerGameManagerImpl.class);
    private Session session = null;

    private ServerGameManagerImpl() {

        this.users = new HashMap<>();
        this.items = new ArrayList<>();
    }
    public static ServerGameManager getInstance() {
        if (instance==null) instance = new ServerGameManagerImpl();
        return instance;
    }
    @Override
    public User addUser(User user) {
        logger.info("new User " + user);
        if(users.containsKey(user.getName())){
            logger.info("Username already exists!");
            return null;
        }
        else if(users.containsKey(user.getMail()))
        {
            logger.info("This mail is already registered!");
            return null;
        }
        this.users.put(user.getName(), user);
        logger.info("new user added!");
        return user;
    }

    @Override
    public User addUser(String name, String password, String email) {

        try {
            session = FactorySession.openSession();
            User user=new User(name,password,email);
           session.save(user);
            logger.info("name: "+ name +"password: "+ "email: "+email);
        }
        catch (Exception e)
        {
            logger.info("Error al insertar usuario");
        }
        finally {
            session.close();
        }

        return this.addUser(new User(name,password,email));

    }

    @Override
    public void loginUser(String name, String password) {

        if(users.containsKey(name))
        {
            if(users.get(name).getPassword().equals(password))
            {
                logger.info("Login successful!");
               // users.get(name).setActive(true);
            }
            else logger.info("Wrong Password");
        }
        else logger.info("This username does not exist");

    }

    @Override
    public void deleteUser(String name, String password, String email) {
        User u = users.get(name);
        if (u==null) {
            logger.warn("not found " + u);
        }
        else if(users.get(name).getPassword().equals(password) && users.get(name).getMail().equals(email)){
            logger.info(u + " deleted ");
        }

        this.users.remove(u);
    }

    @Override
    public void logOutUser(String name) {
        if (users.containsKey(name))
        {
            //users.get(name).setActive(false);
        }
        else logger.info("Wrong name");

    }

    @Override
    public List<User> getUserList() {
        logger.info("List of all registered users");
        List<User> userList = Arrays.asList(users.values().stream().toArray(User[]::new));
        return userList;
    }

    @Override
    public User getUser(String name) {

        if(users.containsKey(name)){
            logger.info(name+" found");
            return users.get(name);
        }
        else {
            logger.info(name + " not found");
            return null;
            }
    }
    @Override
    public Items addItem(Items items) {
        logger.info("new Object " + items.getName() +": " + items.getDescription());
        this.items.add(items);
        logger.info("new Object added");
        return items;
    }
    @Override
    public Items addItem(String name, String descritpion) {
        logger.info("new Object " + name +": " + descritpion);

        logger.info("new Object added");
        return this.addItem(new Items(name,descritpion));
    }

    @Override
    public void deleteItems(String name) {
        String items = (name);
        if (items==null) {
            logger.warn("not found " + items);
        }
        else logger.info(items+" deleted ");

        this.items.remove(items);

    }
    public Items getItem(String name) {
        logger.info("getObject("+name+")");

        for (Items items: this.items) {
            if (items.equals(name)) {
                logger.info("getObject("+name+"): "+items);

                return items;
            }
        }
        logger.warn("not found " + name);
        return null;
    }

    @Override
    public List<Items> getItemList() {
        return this.items;
    }

    public static void delete(){
        instance = null;
        logger.info("Instance GameManagerImpl deleted");
    }

    public void clear(){
        users.clear();
        items.clear();
        logger.info("Instance GameManagerImpl clear");
    }



    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    /*
    @Override
    public Track updateTrack(Track p) {
        Track t = this.getTrack(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setSinger(p.getSinger());
            t.setTitle(p.getTitle());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }

     */
}