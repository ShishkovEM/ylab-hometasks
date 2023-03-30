package io.ylab.intensive.lesson04.movie;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
  private DataSource dataSource;

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void loadData(File file) {
    String csvSplitter = ";";

    try (
            Connection connection = dataSource.getConnection();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr)

    ) {
      MovieDAO movieDAO = new MovieDAO(connection);

      br.readLine();
      br.readLine();
      String line = br.readLine();

      while (line != null) {
        String[] tmp = line.split(csvSplitter);
        if (tmp.length != 10) {
          throw new RuntimeException("incorrect file structure");
        }

        movieDAO.createMovie(parseLine(tmp));

        line = br.readLine();
      }

    } catch (SQLException e) {
      throw new RuntimeException("can't obtain connection for MovieDAO: " + e);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("file source not found: " + e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Movie parseLine(String[] tmp) {
    Movie movie = new Movie();
    movie.setYear(Objects.equals(tmp[0], "") ? null : Integer.parseInt(tmp[0]));
    movie.setLength(Objects.equals(tmp[1], "") ? null : Integer.parseInt(tmp[1]));
    movie.setTitle(Objects.equals(tmp[2], "") ? null : tmp[2]);
    movie.setSubject(Objects.equals(tmp[3], "") ? null : tmp[3]);
    movie.setActors(Objects.equals(tmp[4], "") ? null : tmp[4]);
    movie.setActress(Objects.equals(tmp[5], "") ? null : tmp[5]);
    movie.setDirector(Objects.equals(tmp[6], "") ? null : tmp[6]);
    movie.setPopularity(Objects.equals(tmp[7], "") ? null : Integer.parseInt(tmp[7]));
    movie.setAwards(Objects.equals(tmp[8], "Yes") ? Boolean.TRUE : Objects.equals(tmp[8], "No") ? Boolean.FALSE : null);

    return movie;
  }
}