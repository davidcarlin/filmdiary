package gude.filmdiary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gude.filmdiary.models.Film;

public class FilmDAO {
    private Connection connection;

    public FilmDAO() {
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

    public void createFilm(Film film) {
        String query = "INSERT INTO films (name, year, director) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, film.getName());
            statement.setInt(2, film.getYear());
            statement.setString(3, film.getDirector());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Film getFilmById(Long id) {
        String query = "SELECT * FROM films WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createFilmFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Film> getAllFilms() {
        String query = "SELECT * FROM films";
        List<Film> films = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Film film = createFilmFromResultSet(resultSet);
                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return films;
    }

    private Film createFilmFromResultSet(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setName(resultSet.getString("name"));
        film.setYear(resultSet.getInt("year"));
        film.setDirector(resultSet.getString("director"));
        return film;
    }
}


