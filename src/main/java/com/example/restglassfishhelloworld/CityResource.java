package com.example.restglassfishhelloworld;

import com.example.restglassfishhelloworld.entities.City;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/cities")
public class CityResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> getAllCities() throws SQLException {
        List<City> citiesList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.city");
        while (resultSet.next()) {
            citiesList.add(new City(
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("CountryCode"),
                    resultSet.getString("District")
            ));
        }
        statement.close();
        connection.close();
        return citiesList;
    }

    @GET()
    @Path("/GET/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public City getCityById(@PathParam("id") Integer id) throws SQLException {
        City city = new City();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.city WHERE ID =" + id);
        if (resultSet.first()) {
            city.setId(resultSet.getInt("ID"));
            city.setName(resultSet.getString("Name"));
            city.setCountryCode(resultSet.getString("CountryCode"));
            city.setDistrict(resultSet.getString("District"));
        } else {
            statement.close();
            connection.close();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        statement.close();
        connection.close();
        return city;
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCity(City city) throws SQLException {
        if (city == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No City Sent").build();
        }
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("INSERT INTO `shop_online`.`city`\n" +
                "(`Name`, `CountryCode`, `District`)\n" +
                "VALUES\n" +
                "(\"" + city.getName() + "\",\n\"" +
                city.getCountryCode() + "\",\n\"" +
                city.getDistrict() + "\");\n");
        statement.close();
        connection.close();

        return Response.status(Response.Status.CREATED)
                .entity("City Created, Name : " + city.getName()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCity(City city) throws SQLException {
        if (city == null)
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No City Sent").build();
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String updateQuery = "UPDATE `shop_online`.`city` " +
                "SET " +
                "`Name` = ?, " +
                "`CountryCode` = ?, " +
                "`District` = ? " +
                "WHERE `ID` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("City Updated, Name : " + city.getName()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("City not found for ID : " + city.getId()).build();
            }
        } finally {
            connection.close();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCity(@PathParam("id") Integer id) throws SQLException {
        if (id == null)
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No City Sent").build();

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("DELETE FROM `shop_online`.`city`\n" +
                "WHERE ID=" + id + ";\n");
        statement.close();
        connection.close();

        return Response.status(Response.Status.OK)
                .entity("City Deleted, ID : " + id).build();
    }
}
