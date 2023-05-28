package gude.filmdiary.controllers;

import gude.filmdiary.dao.DiaryDAO;
import gude.filmdiary.models.Diary;
import gude.filmdiary.models.Film;
import gude.filmdiary.models.User;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/diary")
public class DiaryController {
    private DiaryDAO diaryDAO;

    public DiaryController() {
        this.diaryDAO = new DiaryDAO();
    }

    @POST
    @Path("/{userId}/{filmId}/{rating}")
    public Response addDiaryEntry(
            @PathParam("userId") Long userId,
            @PathParam("filmId") Long filmId,
            @PathParam("rating") int rating) {

        User user = new User(); // Retrieve user from the database based on the userId
        Film film = new Film(); // Retrieve film from the database based on the filmId

        // Check if the user and film exist in the database
        if (user != null && film != null) {
            diaryDAO.addDiaryEntry(user, film, rating);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{diaryEntryId}/{newRating}")
    public Response updateDiaryEntryRating(
            @PathParam("diaryEntryId") Long diaryEntryId,
            @PathParam("newRating") int newRating) {

        diaryDAO.updateDiaryEntryRating(diaryEntryId, newRating);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{diaryEntryId}")
    public Response deleteDiaryEntry(@PathParam("diaryEntryId") Long diaryEntryId) {
        diaryDAO.deleteDiaryEntry(diaryEntryId);
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiaryEntriesForUser(@PathParam("userId") Long userId) {
        User user = new User(); // Retrieve user from the database based on the userId

        List<Diary> diaryEntries = diaryDAO.getDiaryEntriesForUser(user);
        return Response.ok(diaryEntries).build();
    }
}
