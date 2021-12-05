package edu.upc.dsa.services;


import edu.upc.dsa.ServerGameManager;
import edu.upc.dsa.ServerGameManagerImpl;
import edu.upc.dsa.models.*;
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
            @ApiResponse(code = 201, message = "Successful", response=UserAUX.class),
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
            @ApiResponse(code = 201, message = "Successful", response= Items.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addItems/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(Items items) {

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


/*
    @GET
    @ApiOperation(value = "get all Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Track.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks() {

        List<Track> tracks = this.gsm.findAll();

        GenericEntity<List<Track>> entity = new GenericEntity<List<Track>>(tracks) {};
        return Response.status(201).entity(entity).build()  ;

    }

    */

    @GET
    @ApiOperation(value = "get User info", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/userInfo/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("name") String name) {
        User user = this.gsm.getUser(name);
        if (user == null) return Response.status(404).build();
        else  return Response.status(201).entity(user).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/deleteUser/{name}/{password}/{email}")
    public Response deleteUser(@PathParam("name") String name,@PathParam("password")String password,@PathParam("email") String email) {

        if (gsm.getUser(name)==null) return Response.status(404).build();
        else gsm.deleteUser(name,password,email);
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
        gsm.loginUser(login.getName(),login.getPassword());
        if (login.getName()==null || login.getPassword()==null)  return Response.status(404).build();
        return Response.status(201).entity(login).build();
    }
    @GET
    @ApiOperation(value = "Logout", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK"),
            @ApiResponse(code = 404, message = "Not found (user not found)")
    })
    @Path("/logout/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logoutUser(@PathParam("name") String name) {
        if (gsm.getUser(name)== null) return Response.status(404).build();
        else gsm.logOutUser(name);
        return Response.status(201).build();
    }
    /* @PUT
    @ApiOperation(value = "update a Track", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/")
    public Response updateTrack(Track track) {

        Track t = this.gsm.updateTrack(track);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }

     */





}