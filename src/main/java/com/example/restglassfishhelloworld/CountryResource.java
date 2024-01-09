package com.example.restglassfishhelloworld;

import com.example.restglassfishhelloworld.entities.Country;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/countries")
public class CountryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> getAllCountries() throws SQLException {
        List<Country> countriesList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.country");

        while (resultSet.next()) {
            countriesList.add(new Country(
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("continent"),
                    resultSet.getString("region"),
                    resultSet.getString("localName"),
                    resultSet.getInt("capital"),
                    resultSet.getString("code2")
            ));
        }

        statement.close();
        connection.close();
        return countriesList;
    }

    @GET
    @Path("GET/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Country getCountryByCode(@PathParam("code") String code) throws SQLException {
        Country country = new Country();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.country WHERE code ='" + code + "'");

        if (resultSet.first()) {
            country.setCode(resultSet.getString("code"));
            country.setName(resultSet.getString("name"));
            country.setContinent(resultSet.getString("continent"));
            country.setRegion(resultSet.getString("region"));
            country.setLocalName(resultSet.getString("localName"));
            country.setCapital(resultSet.getInt("capital"));
            country.setCode2(resultSet.getString("code2"));
        } else {
            statement.close();
            connection.close();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        statement.close();
        connection.close();
        return country;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCountry(Country country) throws SQLException {
        if (country == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Country Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String insertQuery = "INSERT INTO `shop_online`.`country`" +
                "(`code`, `name`, `continent`, `region`, `localName`, `capital`, `code2`)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, country.getCode());
            preparedStatement.setString(2, country.getName());
            preparedStatement.setString(3, country.getContinent());
            preparedStatement.setString(4, country.getRegion());
            preparedStatement.setString(5, country.getLocalName());
            preparedStatement.setInt(6, country.getCapital());
            preparedStatement.setString(7, country.getCode2());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.CREATED).entity(country).build();
            } else {
                throw new WebApplicationException("Failed to create country.", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } finally {
            connection.close();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCountry(Country country) throws SQLException {
        if (country == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Country Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String updateQuery = "UPDATE `shop_online`.`country` " +
                "SET " +
                "`name` = ?, " +
                "`continent` = ?, " +
                "`region` = ?, " +
                "`localName` = ?, " +
                "`capital` = ?, " +
                "`code2` = ? " +
                "WHERE `code` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, country.getName());
            preparedStatement.setString(2, country.getContinent());
            preparedStatement.setString(3, country.getRegion());
            preparedStatement.setString(4, country.getLocalName());
            preparedStatement.setInt(5, country.getCapital());
            preparedStatement.setString(6, country.getCode2());
            preparedStatement.setString(7, country.getCode());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Country Updated, Name : " + country.getName()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Country not found for Code : " + country.getCode()).build();
            }
        } finally {
            connection.close();
        }
    }

    @DELETE
    @Path("/delete/{code}")
    public Response deleteCountry(@PathParam("code") String code) throws SQLException {
        if (code == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Country Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate("DELETE FROM `shop_online`.`country`\n" +
                "WHERE code='" + code + "';\n");

        statement.close();
        connection.close();

        if (rowsAffected > 0) {
            return Response.status(Response.Status.OK)
                    .entity("Country Deleted, Code : " + code).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Country not found for Code : " + code).build();
        }
    }
}
