package gude.filmdiary.controllers;

import gude.filmdiary.dao.UserDAO;
import gude.filmdiary.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserController {
    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        // Validate user input and perform necessary checks
        // ...

        userDAO.createUser(user);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        User user = userDAO.getUserById(id);

        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User updatedUser) {
        // Validate updated user input and perform necessary checks
        // ...

        User user = userDAO.getUserById(id);

        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());

            // Update the user in the database
            // ...

            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        User user = userDAO.getUserById(id);

        if (user != null) {
            // Delete the user from the database
            // ...

            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return Response.ok(users).build();
    }
}
