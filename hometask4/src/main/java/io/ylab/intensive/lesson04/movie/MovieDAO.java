package io.ylab.intensive.lesson04.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class MovieDAO {
    private final Connection connection;

    public MovieDAO(Connection connection) {
        this.connection = connection;
    }

    public void createMovie(Movie movie) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement(
                "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )) {
            setOrInteger(movie.getYear(), query, 1);
            setOrInteger(movie.getLength(), query, 2);
            setOrVarchar(movie.getTitle(), query, 3);
            setOrVarchar(movie.getSubject(), query, 4);
            setOrVarchar(movie.getActors(), query, 5);
            setOrVarchar(movie.getActress(), query, 6);
            setOrVarchar(movie.getDirector(), query, 7);
            setOrInteger(movie.getPopularity(), query, 8);
            setOrBoolean(movie.getAwards(), query, 9);

            query.execute();
        }
    }

    public void setOrInteger(Integer integerValueToSet, PreparedStatement query, int parameterIndex) throws SQLException {
        if (Objects.equals(integerValueToSet, null)) {
            query.setNull(parameterIndex, Types.INTEGER);
        } else  {
            query.setInt(parameterIndex, integerValueToSet);
        }
    }

    public void setOrVarchar(String stringValueToSet, PreparedStatement query, int parameterIndex) throws SQLException {
        if (Objects.equals(stringValueToSet, null)) {
            query.setNull(parameterIndex, Types.INTEGER);
        } else  {
            query.setString(parameterIndex, stringValueToSet);
        }
    }

    public void setOrBoolean(Boolean booleanValueToSet, PreparedStatement query, int parameterIndex) throws SQLException {
        if (Objects.equals(booleanValueToSet, null)) {
            query.setNull(parameterIndex, Types.BOOLEAN);
        } else  {
            query.setBoolean(parameterIndex, booleanValueToSet);
        }
    }
}
