package edu.upc.dsa;

import java.util.*;

import edu.upc.dsa.database.FactorySession;
import edu.upc.dsa.database.Session;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Items;
import edu.upc.dsa.models.UserInventory;
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
        if (instance == null) instance = new ServerGameManagerImpl();
        return instance;
    }

    @Override
    public User addUser(User user) {
        logger.info("new User " + user);
        if (users.containsKey(user.getName())) {
            logger.info("Username already exists!");
            return null;
        } else if (users.containsKey(user.getMail())) {
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
            User user = new User(name, password, email);
            User attributes = session.getBy(user.getClass(), "name", user);
            if (attributes == null) {
                session.save(user);
                logger.info("name: " + name + "password: " + "email: " + email);
            }
        } catch (Exception e) {
            logger.info("Error al insertar usuario");
        } finally {
            session.close();
        }

        return this.addUser(new User(name, password, email));

    }

    @Override
    public User loginUser(String name, String password) {

        try {
            session = FactorySession.openSession();
            User u = new User(name, password, null);
            User nameCheck = session.getBy(User.class, "name", u);
            User passwordCheck = session.getBy(User.class, "password", u);
            if (nameCheck != null & passwordCheck != null) {
                logger.info("Login successful!");

                return nameCheck;
            } else if (nameCheck == null & passwordCheck != null) {
                logger.info("Nombre de usuario incorrecto");
                return null;
            } else if (passwordCheck == null & nameCheck != null) {
                logger.info("Password incorrecto");
                return null;
            } else {
                logger.info("Error al inserar usuario");
                return null;
            }
        } catch (Exception e) {
            logger.info("Error al encontrar usuario");
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void deleteUser(int id) {
        try {
            session = FactorySession.openSession();
            String atribute = "password";
            User finder = new User(null, null, null, id);
            User u = session.getBy(User.class, "id", finder);
            if (u == null) {
                logger.warn("not found " + u);
            } else {
                session.deleteInventory(UserInventory.class, "id", u);

                session.delete(u.getClass(), atribute, u);
                logger.info(u + " deleted ");
            }
        } catch (Exception e) {
            logger.info("Error al borrar usuario");
        } finally {
            session.close();
        }
    }

    @Override
    public void logOutUser(int id) {
        if (users.containsKey(id)) {
            //users.get(name).setActive(false);
        } else logger.info("Wrong name");

    }

    @Override
    public void updateScore(String name, String attribute, Object value) {
        try {
            session = FactorySession.openSession();
            User u = new User(name, null, null);
            User attributes = session.getBy(User.class, "name", u);
            if (attributes != null) {

                session.update(User.class, name, attribute, value);

            } else {
                logger.info(name + " not found");

            }
        } catch (Exception e) {
            logger.info("Error al visualizar un usuario");
        } finally {
            session.close();
        }


    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();

        try {
            session = FactorySession.openSession();
            List<HashMap<String, java.lang.Object>> list = session.getAll(User.class);
            if (list != null) {
                for (HashMap<String, java.lang.Object> h : list) {
                    userList.add(new User(
                            (String) h.get("name"),
                            (String) h.get("password"),
                            (String) h.get("mail"),
                            (int) h.get("ID")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        logger.info("List of all registered users");
        // = Arrays.asList(users.values().stream().toArray(User[]::new));
        return userList;
    }

    @Override
    public User getUser(int id) {
        try {
            session = FactorySession.openSession();
            User u = new User(null, null, null, id);
            User attributes = session.getBy(User.class, "id", u);
            if (attributes != null) {
                logger.info(attributes.getName() + " found");
                return attributes;

            } else {
                logger.info("User not found");
                return null;
            }
        } catch (Exception e) {
            logger.info("Error al visualizar un usuario");
        } finally {
            session.close();
        }
        return null;

    }

    public User addToInventory(int id_u, int id_i) {
        User u = null;
        try {
            session = FactorySession.openSession();
            User us = new User(null, null, null, id_u);
            User attributes = session.getBy(User.class, "id", us);
            if (attributes != null) {
                UserInventory uI = new UserInventory(id_u, id_i);
                int err = session.saveItem(UserInventory.class, id_u, "id_i", id_i, uI);
                if (err == -1) {
                    logger.info("Item no encontrado");
                    return null;
                } else {
                    logger.info("Objecto anadido de forma correcta");
                    u = attributes;
                }
            } else {
                logger.info("Usuario no encontrado");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return u;
    }

    @Override
    public List<Items> getUserItemList(int id) {
        List<Items> itemsList = new ArrayList<>();
        try {
            session = FactorySession.openSession();
            User us = new User(null, null, null, id);
            User attributes = session.getBy(User.class, "id", us);
            if (attributes != null) {
                List<HashMap<String, java.lang.Object>> list = session.getAllBy(UserInventory.class, "id_u", id);
                if (list != null) {
                    for (HashMap<String, java.lang.Object> i : list) {
                        Items finder = new Items(null, null, (int) i.get("id_i"));
                        Items items = session.getItems(Items.class, "id", finder);
                        if (items != null) {
                            itemsList.add(new Items(
                                    items.getName(),
                                    items.getDescription(),
                                    items.getId()
                            ));
                        }
                    }
                } else {
                    logger.info("Usuario no encontrado");
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return itemsList;
    }

    @Override
    public Items addItem(Items items) {

        logger.info("new Object " + items.getName() + ": " + items.getDescription());
        this.items.add(items);
        logger.info("new Object added");
        return items;
    }

    @Override
    public Items addItem(String name, String descritpion) {
        try {
            session = FactorySession.openSession();
            Items it = new Items(name, descritpion);
            Items attributes = session.getItems(it.getClass(), "name", it);
            if (attributes == null) {
                session.save(it);
                logger.info("new Object " + name + ": " + descritpion);

                logger.info("new Object added");
            }
        } catch (Exception e) {
            logger.info("Error al insertar objeto");
        } finally {
            session.close();
        }
        return this.addItem(new Items(name, descritpion));
    }

    @Override
    public void deleteItems(int id) {
        try {
            session = FactorySession.openSession();
            Items it = new Items(null, null, id);
            Items attributes = session.getItems(it.getClass(), "id", it);
            if (attributes != null) {
                session.deleteInventoryItems(UserInventory.class, "id", it);
                session.delete(it.getClass(), "description", attributes);
            }
            if (id == 0) {
                logger.warn("not found ");
            } else logger.info(items + " deleted ");

            this.items.remove(items);
        } catch (Exception e) {
            logger.info("Error al insertar objeto");
        } finally {
            session.close();
        }
    }

    public Items getItem(int id) {
        logger.info("getObject(" + id + ")");
        try {
            session = FactorySession.openSession();
            Items it = new Items(null, null, id);
            Items attributes = session.getItems(it.getClass(), "id", it);
            if (attributes != null) {
                logger.info("Item: " + attributes);
                return attributes;
            } else {
                logger.info("Item not found");
                return null;
            }
        } catch (Exception e) {
            logger.info("Error al insertar objeto");
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Game addGame(int level, int score, int playerID) {
        try {
            session = FactorySession.openSession();
            Game startGame = new Game(level, score, playerID);
            Game attributes = session.getGame(startGame.getClass(), "playerID", startGame);
            if (attributes == null) {
                session.save(startGame);
                logger.info("Partida comenzada correctamente");
            } else return null;
        } catch (Exception e) {
            logger.info("Error al comenzar juego");
        } finally {
            session.close();
        }
        return this.addGame(level, score, playerID);
    }

    @Override
    public Game restartGame(int playerID) {
        try {
            session = FactorySession.openSession();
            Game startGame = new Game(-1, -1, playerID);
            Game attributes = session.getGame(startGame.getClass(), "playerID", startGame);
            if (attributes == null) {
                logger.info("No hay una partida comenzada");
                return null;
            } else return attributes;
        } catch (Exception e) {
            logger.info("Error al comenzar juego");
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Game deleteGame(int playerID) {
        try {
            session = FactorySession.openSession();
            String atribute = "password";
            Game finder = new Game(-1, -1, playerID);
            Game u = session.getGame(Game.class, "playerID", finder);
            if (u == null) {
                logger.warn("not found ");
            } else {
                session.deleteGame(Game.class, "playerID", u);
                logger.info(u + " deleted ");
            }
        } catch (Exception e) {
            logger.info("Error al borrar");
        } finally {
            session.close();
        }
        return null;
    }




    @Override
    public List<Items> getItemList() {
        List<Items> itemsList = new ArrayList<>();

        try {
            session = FactorySession.openSession();
            List<HashMap<String, java.lang.Object>> list = session.getAll(Items.class);
            if(list != null) {
                for (HashMap<String, java.lang.Object> h : list) {
                    itemsList.add(new Items(
                            (String) h.get("name"),
                            (String) h.get("description"),
                            (int) h.get("ID")
                    ));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
        logger.info("List of all items");
        // = Arrays.asList(users.values().stream().toArray(User[]::new));
        return itemsList;
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


}
