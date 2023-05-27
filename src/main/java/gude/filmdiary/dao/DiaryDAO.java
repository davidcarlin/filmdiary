package gude.filmdiary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gude.filmdiary.models.Diary;
import gude.filmdiary.models.Film;
import gude.filmdiary.models.User;

import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {
    private Connection connection;

    public DiaryDAO() {
        // Initialize the database connection
        String url = "jdbc:mysql://localhost:3306/filmdiary";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDiaryEntry(User user, Film film, int rating) {
        String query = "INSERT INTO diary_entries (user_id, film_id, rating) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.setLong(2, film.getId());
            statement.setInt(3, rating);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDiaryEntryRating(Long diaryEntryId, int newRating) {
        String query = "UPDATE diary_entries SET rating = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newRating);
            statement.setLong(2, diaryEntryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDiaryEntry(Long diaryEntryId) {
        String query = "DELETE FROM diary_entries WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, diaryEntryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Diary> getDiaryEntriesForUser(User user) {
        String query = "SELECT * FROM diary_entries WHERE user_id = ?";
        List<Diary> diaryEntries = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Diary diaryEntry = createDiaryEntryFromResultSet(resultSet);
                diaryEntries.add(diaryEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diaryEntries;
    }

    private Diary createDiaryEntryFromResultSet(ResultSet resultSet) throws SQLException {
        Diary diaryEntry = new Diary();
        diaryEntry.setId(resultSet.getLong("id"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        diaryEntry.setUser(user);
        Film film = new Film();
        film.setId(resultSet.getLong("film_id"));
        diaryEntry.setFilm(film);
        diaryEntry.setRating(resultSet.getInt("rating"));
        return diaryEntry;
    }
}
