package gude.filmdiary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gude.filmdiary.models.Film;
import gude.filmdiary.models.User;

    public class WatchlistDAO {
        private Connection connection;

        public WatchlistDAO() {
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

        public void addToWatchlist(User user, Film film) {
            String query = "INSERT INTO watchlist_entries (user_id, film_id) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, user.getId());
                statement.setLong(2, film.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void removeFromWatchlist(User user, Film film) {
            String query = "DELETE FROM watchlist_entries WHERE user_id = ? AND film_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, user.getId());
                statement.setLong(2, film.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Film> getWatchlistForUser(User user) {
            String query = "SELECT films.* FROM films " +
                    "INNER JOIN watchlist_entries ON films.id = watchlist_entries.film_id " +
                    "WHERE watchlist_entries.user_id = ?";
            List<Film> watchlist = new ArrayList<>();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, user.getId());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Film film = createFilmFromResultSet(resultSet);
                    watchlist.add(film);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return watchlist;
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

