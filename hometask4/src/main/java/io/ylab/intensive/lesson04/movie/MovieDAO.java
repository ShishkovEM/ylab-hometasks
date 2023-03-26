package io.ylab.intensive.lesson04.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class MovieDAO {
    private Connection connection;

    public MovieDAO(Connection connection) {
        this.connection = connection;
    }

    public void createMovie(Movie movie) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement(
                "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )) {
            if (Objects.equals(movie.getYear(), null)) {
                query.setNull(1, Types.INTEGER);
            } else {
                query.setInt(1, movie.getYear());
            }

            if (Objects.equals(movie.getLength(), null)) {
                query.setNull(2, Types.INTEGER);
            } else  {
                query.setInt(2, movie.getLength());
            }

            if (Objects.equals(movie.getTitle(), null)) {
                query.setNull(3, Types.VARCHAR);
            } else  {
                query.setString(3, movie.getTitle());
            }

            if (Objects.equals(movie.getSubject(), null)) {
                query.setNull(4, Types.VARCHAR);
            } else  {
                query.setString(4, movie.getSubject());
            }

            if (Objects.equals(movie.getActors(), null)) {
                query.setNull(5, Types.VARCHAR);
            } else  {
                query.setString(5, movie.getActors());
            }

            if (Objects.equals(movie.getActress(), null)) {
                query.setNull(6, Types.VARCHAR);
            } else  {
                query.setString(6, movie.getActress());
            }

            if (Objects.equals(movie.getDirector(), null)) {
                query.setNull(7, Types.VARCHAR);
            } else  {
                query.setString(7, movie.getDirector());
            }

            if (Objects.equals(movie.getPopularity(), null)) {
                query.setNull(8, Types.INTEGER);
            } else  {
                query.setInt(8, movie.getPopularity());
            }

            if (Objects.equals(movie.getAwards(), null)) {
                query.setNull(9, Types.BOOLEAN);
            } else  {
                query.setBoolean(9, movie.getAwards());
            }

            query.execute();
        }
    }
}
