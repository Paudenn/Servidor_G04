package edu.upc.dsa.services;


import edu.upc.dsa.ServerGameManager;
import edu.upc.dsa.ServerGameManagerImpl;
import edu.upc.dsa.models.*;
import edu.upc.dsa.models.AUXmodels.GameAUX;
import edu.upc.dsa.models.AUXmodels.ItemsAUX;
import edu.upc.dsa.models.AUXmodels.LoginAUX;
import edu.upc.dsa.models.AUXmodels.UserAUX;
import edu.upc.dsa.models.Items;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/GameServer", description = "Endpoint to GameServer Service")
@Path("/GameServer")
public class GameServerService {

    private ServerGameManager gsm;

    public GameServerService() {
        this.gsm = ServerGameManagerImpl.getInstance();
        if (gsm.size()==0) {
            this.gsm.addUser("Pau","123","pau.ruiz.blanco@upc.edu");
            this.gsm.addUser("Laura","321","laura.vazquez.husillos@upc.edu");
            this.gsm.addUser("Alba","231","alba.munoz.gil@upc.edu");
            this.gsm.addItem("Pistola","Descativa");
            this.gsm.addItem("Taladro","Atraviesa");

        }
    }
    @POST
    @ApiOperation(value = "Register a user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UserAUX.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/register/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(UserAUX user) {
        if (user.getName()==null || user.getPassword()==null || user.getMail()==null)  return Response.status(500).entity(user).build();
        this.gsm.addUser(user.getName(),user.getPassword(),user.getMail());
        return Response.status(201).entity(user).build();

    }

    @POST
    @ApiOperation(value = "create a new Item", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= ItemsAUX.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addItems/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(ItemsAUX items) {

        if (items.getName()==null || items.getDescription()==null)  return Response.status(500).entity(items).build();
        this.gsm.addItem(items.getName(), items.getDescription());
        return Response.status(201).entity(items).build();
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/userList/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {

        List<User> users = this.gsm.getUserList();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build() ;

    }
    @GET
    @ApiOperation(value = "get all Items", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Items.class, responseContainer="List"),
    })
    @Path("/itemList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemList() {

        List<Items> items = this.gsm.getItemList();

        GenericEntity<List<Items>> entity = new GenericEntity<List<Items>>(items) {};
        return Response.status(201).entity(entity).build()  ;

    }
    @GET
    @ApiOperation(value = "get all Items by user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Items.class, responseContainer="List"),
    })
    @Path("/userItemList/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserItemList(@PathParam("id") int id) {

        List<Items> items = this.gsm.getUserItemList(id);

        GenericEntity<List<Items>> entity = new GenericEntity<List<Items>>(items) {};
        return Response.status(201).entity(entity).build()  ;

    }
    @GET
    @ApiOperation(value = "get User info", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/userInfo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User user = this.gsm.getUser(id);
        if (user == null) return Response.status(404).build();
        else  return Response.status(201).entity(user).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Item", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/deleteItem/{id}")
    public Response deleteItems(@PathParam("id") int id) {

        if (gsm.getItem(id)==null) return Response.status(404).build();
        else gsm.deleteItems(id);
        return Response.status(201).build();
    }
    @DELETE
    @ApiOperation(value = "delete a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/deleteUser/{id}")
    public Response deleteUser(@PathParam("id") int id) {

        if (gsm.getUser(id)==null)
            return Response.status(404).build();
        else gsm.deleteUser(id);
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "Login", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK",response= LoginAUX.class),
            @ApiResponse(code = 404, message = "Not found (user or pass not match)")

    })

    @Path("/login/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginAUX login) {
        User u = gsm.loginUser(login.getName(),login.getPassword());
        if (u.getName()==null || u.getPassword()==null)  return Response.status(404).build();
        else {
            this.gsm.updateScore(u.getId(),"active",1);
            return Response.status(201).entity(login).build();
        }
    }
    @GET
    @ApiOperation(value = "Logout", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK"),
            @ApiResponse(code = 404, message = "Not found (user not found)")
    })
    @Path("/logout/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logoutUser(@PathParam("id") int id) {
        if (gsm.getUser(id)== null) return Response.status(404).build();
        else this.gsm.updateScore(gsm.getUser(id).getId(),"active",0);
        return Response.status(201).build();
    }
    @PUT
    @ApiOperation(value = "update a User HighScore (by id)", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/updatePlayerScore")
    public Response updateScore(User user) {

        this.gsm.updateScore(user.getId(),"highScore",user.getHighScore());
        return Response.status(201).build();
    }
    @PUT
    @ApiOperation(value = "update a User HighScore (by id)", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/updateActive")
    public Response updateActive(User user) {

        this.gsm.updateScore(user.getId(),"active",user.getActive());
        return Response.status(201).build();
    }
    @POST
    @ApiOperation(value = "add item to invetory by userId", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UserInventory.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addToinventory/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToInventory(UserInventory userinv) {

        if (userinv.getId_u()==0 || userinv.getId_i()==0)  return Response.status(500).entity(userinv).build();
        User err = this.gsm.addToInventory(userinv.getId_u(),userinv.getId_i());
        if (err == null)
            return Response.status(404).build();
        else
        return Response.status(201).entity(userinv).build();
    }

    @POST
    @ApiOperation(value = "Start a game", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= GameAUX.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/startGame/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGame(GameAUX game) {
        if (game.getLevel() <= 0 || game.getScore() <= 0 || game.getPlayerID() <= 0)  return Response.status(500).entity(game).build();
        this.gsm.addGame(game.getLevel(),game.getScore(),game.getPlayerID());
        return Response.status(201).entity(game).build();

    }
    @GET
    @ApiOperation(value = "Restart game (playerID)", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/restartGame/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response restartGame(@PathParam("id") int id) {
        Game game = this.gsm.restartGame(id);
        if (game == null) return Response.status(404).build();
        else return Response.status(201).entity(game).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Game by player ID", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/deleteGame/{id}")
    public Response deleteGame(@PathParam("id") int id) {

        if (gsm.restartGame(id)==null)
            return Response.status(404).build();
        else gsm.deleteGame(id);
        return Response.status(201).build();
    }





}
