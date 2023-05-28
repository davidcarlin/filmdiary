package gude.filmdiary.controllers;

import java.util.List;

import gude.filmdiary.dao.WatchlistDAO;
import gude.filmdiary.models.Film;
import gude.filmdiary.models.User;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/watchlist")
public class WatchlistController {
    private WatchlistDAO watchlistDAO;

    public WatchlistController() {
        this.watchlistDAO = new WatchlistDAO();
    }

    @POST
    @Path("/{userId}/{filmId}")
    public Response addToWatchlist(@PathParam("userId") Long userId, @PathParam("filmId") Long filmId) {
        User user = new User(); // Retrieve user from the database based on the userId
        Film film = new Film(); // Retrieve film from the database based on the filmId

        // Check if the user and film exist in the database
        if (user != null && film != null) {
            watchlistDAO.addToWatchlist(user, film);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{userId}/{filmId}")
    public Response removeFromWatchlist(@PathParam("userId") Long userId, @PathParam("filmId") Long filmId) {
        User user = new User(); // Retrieve user from the database based on the userId
        Film film = new Film(); // Retrieve film from the database based on the filmId

        // Check if the user and film exist in the database
        if (user != null && film != null) {
            watchlistDAO.removeFromWatchlist(user, film);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWatchlistForUser(@PathParam("userId") Long userId) {
        User user = new User(); // Retrieve user from the database based on the userId

        List<Film> watchlist = watchlistDAO.getWatchlistForUser(user);
        return Response.ok(watchlist).build();
    }
}
