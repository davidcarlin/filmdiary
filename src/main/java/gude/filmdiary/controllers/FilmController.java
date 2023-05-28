package gude.filmdiary.controllers;

import gude.filmdiary.dao.FilmDAO;
import gude.filmdiary.models.Film;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/films")
public class FilmController {
    private FilmDAO filmDAO;

    public FilmController() {
        this.filmDAO = new FilmDAO();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFilm(Film film) {
        // Validate film input and perform necessary checks
        // ...

        filmDAO.createFilm(film);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilm(@PathParam("id") Long id) {
        Film film = filmDAO.getFilmById(id);

        if (film != null) {
            return Response.ok(film).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

@PUT
@Path("/{id}")
@Consumes(MediaType.APPLICATION_JSON)
public Response updateFilm(@PathParam("id") Long id, Film updatedFilm) {
    // Validate updated film input and perform necessary checks
    // ...

    Film film = filmDAO.getFilmById(id);

    if (film != null) {
        film.setName(updatedFilm.getName());
        film.setName(updatedFilm.getName());
        // Update other properties of the film object as needed

        // Update the film in the database
        filmDAO.createFilm(film);

        return Response.ok().build();
    } else {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

    }

