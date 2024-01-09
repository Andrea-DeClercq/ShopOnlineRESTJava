package com.example.restglassfishhelloworld;

import com.example.restglassfishhelloworld.entities.Users;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Users> getAllUsers() throws SQLException{
        List<Users> usersList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.users");
        while(resultSet.next()) {
            usersList.add(new Users(resultSet.getInt(1),
                    resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), resultSet.getString(7),
                    resultSet.getInt(8), resultSet.getString(9),
                    resultSet.getInt(10)));
        }
        statement.close();
        connection.close();
        return usersList;
    }

    @GET()
    @Path("GET/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Users getUserById(@PathParam("id") Integer id) throws SQLException{
        Users users = new Users();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.users WHERE user_id ="+id);
        if (resultSet.first()) {
            users.setId(resultSet.getInt(1));
            users.setUserName(resultSet.getString(2));
            users.setUserEmail(resultSet.getString(3));
            users.setUserPhone(resultSet.getString(4));
            users.setUserFname(resultSet.getString(5));
            users.setUserLname(resultSet.getString(6));
            users.setUserPassword(resultSet.getString(7));
            users.setUserCityId(resultSet.getInt(8));
            users.setUserAdress(resultSet.getString(9));
            users.setUserLoginStatus(resultSet.getInt(10));
        } else {
            statement.close();
            connection.close();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        statement.close();
        connection.close();
        return users;
    }
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Users user) throws SQLException{
        if(user==null){
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No User Sent").build();
        }
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement=connection.createStatement();
        int i = statement.executeUpdate("INSERT INTO `shop_online`.`users`\n" +
                "(`user_name`,\n" +
                "`user_email`,\n" +
                "`user_phone`,\n" +
                "`user_fname`,\n" +
                "`user_lname`,\n" +
                "`user_password`,\n" +
                "`user_city`,\n" +
                "`user_adress`,\n" +
                "`user_login_status`)\n" +
                "VALUES\n" +
                "(\""+user.getUserName()+"\",\n\"" +
                user.getUserEmail()+"\",\n\"" +
                user.getUserPhone()+"\",\n\"" +
                user.getUserFname()+"\",\n\"" +
                user.getUserLname()+"\",\n\"" +
                user.getUserPassword()+"\",\n" +
                user.getUserCityId()+",\n\"" +
                user.getUserAdress()+"\",\n" +
                user.getUserLoginStatus()+");\n");
        statement.close();
        connection.close();

        return Response.status(Response.Status.CREATED)
                .entity("User Created, Name : "+user.getUserName()).build();

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUSER(Users user) throws SQLException {
        if(user==null)
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No User Sent").build();
        Connection connection=DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/shop_online?useSSL=false","root","root");
        String updateQuery = "UPDATE `shop_online`.`users` " +
                "SET " +
                "`user_name` = ?, " +
                "`user_email` = ?, " +
                "`user_phone` = ?, " +
                "`user_fname` = ?, " +
                "`user_lname` = ?, " +
                "`user_password` = ?, " +
                "`user_city` = ?, " +
                "`user_adress` = ?, " +
                "`user_login_status` = ? " +
                "WHERE `user_id` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setString(3, user.getUserPhone());
            preparedStatement.setString(4, user.getUserFname());
            preparedStatement.setString(5, user.getUserLname());
            preparedStatement.setString(6, user.getUserPassword());
            preparedStatement.setInt(7, user.getUserCityId());
            preparedStatement.setString(8, user.getUserAdress());
            preparedStatement.setInt(9, user.getUserLoginStatus());
            preparedStatement.setInt(10, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("User Updated, Name : " + user.getUserName()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found for ID : " + user.getId()).build();
            }
        } finally {
            connection.close();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response updateUSER(@PathParam("id") Integer id) throws SQLException {
        if(id==null)
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No User Sent").build();

        Connection connection=DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/shop_online?useSSL=false","root","root");
        Statement statement=connection.createStatement();
        int i =statement.executeUpdate("DELETE FROM `shop_online`.`users`\n" +
                "WHERE user_id="+id+";\n");
        statement.close();
        connection.close();

        return Response.status(Response.Status.OK)
                .entity("User Deleted, id : "+id).build();

    }
}
