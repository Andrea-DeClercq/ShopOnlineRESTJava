package com.example.restglassfishhelloworld;

import com.example.restglassfishhelloworld.entities.Category;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/categories")
public class CategoryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.categories");

        while (resultSet.next()) {
            categoryList.add(new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("web_title"),
                    resultSet.getInt("parent"),
                    resultSet.getInt("leval")
            ));
        }

        statement.close();
        connection.close();
        return categoryList;
    }

    @GET
    @Path("GET/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategoryById(@PathParam("id") Integer id) throws SQLException {
        Category category = new Category();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.categories WHERE id =" + id);

        if (resultSet.first()) {
            category.setId(resultSet.getInt("id"));
            category.setTitle(resultSet.getString("title"));
            category.setWebTitle(resultSet.getString("web_title"));
            category.setParent(resultSet.getInt("parent"));
            category.setLevel(resultSet.getInt("leval"));
        } else {
            statement.close();
            connection.close();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        statement.close();
        connection.close();
        return category;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCategory(Category category) throws SQLException {
        if (category == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Category Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("INSERT INTO `shop_online`.`categories`" +
                "(`title`, `web_title`, `parent`, `leval`)" +
                "VALUES (" +
                "\"" + category.getTitle() + "\", " +
                "\"" + category.getWebTitle() + "\", " +
                "\"" + category.getParent() + "\", " +
                "\"" + category.getLevel() + "\"" +
                ")");
        statement.close();
        connection.close();

        return Response.status(Response.Status.CREATED)
                .entity("Category Created, Title : " + category.getTitle()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(Category category) throws SQLException {
        if (category == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Category Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String updateQuery = "UPDATE `shop_online`.`categories` " +
                "SET " +
                "`title` = ?, " +
                "`web_title` = ?, " +
                "`parent` = ?, " +
                "`leval` = ? " +
                "WHERE `id` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setString(2, category.getWebTitle());
            preparedStatement.setInt(3, category.getParent());
            preparedStatement.setInt(4, category.getLevel());
            preparedStatement.setInt(5, category.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Category Updated, Title : " + category.getTitle()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Category not found for ID : " + category.getId()).build();
            }
        } finally {
            connection.close();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCategory(@PathParam("id") Integer id) throws SQLException {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Category ID Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String deleteQuery = "DELETE FROM `shop_online`.`categories` WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Category Deleted, ID : " + id).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Category not found for ID : " + id).build();
            }
        } finally {
            connection.close();
        }
    }
}
