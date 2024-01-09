package com.example.restglassfishhelloworld;

import com.example.restglassfishhelloworld.entities.Product;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/products")
public class ProductResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.products");

        while (resultSet.next()) {
            productList.add(new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getString("dossier"),
                    resultSet.getInt("category_id"),
                    resultSet.getInt("in_stock"),
                    resultSet.getDouble("price"),
                    resultSet.getString("brand"),
                    resultSet.getInt("nbr_image"),
                    resultSet.getTimestamp("date_added")
            ));
        }

        statement.close();
        connection.close();
        return productList;
    }

    @GET
    @Path("GET/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") Integer id) throws SQLException {
        Product product = new Product();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.products WHERE product_id =" + id);

        if (resultSet.first()) {
            product.setProductId(resultSet.getInt("product_id"));
            product.setProductName(resultSet.getString("product_name"));
            product.setProductDescription(resultSet.getString("product_description"));
            product.setDossier(resultSet.getString("dossier"));
            product.setCategoryId(resultSet.getInt("category_id"));
            product.setInStock(resultSet.getInt("in_stock"));
            product.setPrice(resultSet.getDouble("price"));
            product.setBrand(resultSet.getString("brand"));
            product.setNbrImage(resultSet.getInt("nbr_image"));
            product.setDateAdded(resultSet.getTimestamp("date_added"));
        } else {
            statement.close();
            connection.close();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        statement.close();
        connection.close();
        return product;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product) throws SQLException {
        if (product == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Product Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("INSERT INTO `shop_online`.`products`" +
                "(`product_name`, `product_description`, `dossier`, `category_id`, `in_stock`, `price`, `brand`, `nbr_image`, `date_added`)" +
                "VALUES (" +
                "\"" + product.getProductName() + "\", " +
                "\"" + product.getProductDescription() + "\", " +
                "\"" + product.getDossier() + "\", " +
                "\"" + product.getCategoryId() + "\", " +
                "\"" + product.getInStock() + "\", " +
                "\"" + product.getPrice() + "\", " +
                "\"" + product.getBrand() + "\", " +
                "\"" + product.getNbrImage() + "\", " +
                "\"" + product.getDateAdded() + "\"" +
                ");");
        statement.close();
        connection.close();

        return Response.status(Response.Status.CREATED)
                .entity("Product Created, Name : " + product.getProductName()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product product) throws SQLException {
        if (product == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Product Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String updateQuery = "UPDATE `shop_online`.`products` " +
                "SET " +
                "`product_name` = ?, " +
                "`product_description` = ?, " +
                "`dossier` = ?, " +
                "`category_id` = ?, " +
                "`in_stock` = ?, " +
                "`price` = ?, " +
                "`brand` = ?, " +
                "`nbr_image` = ?, " +
                "`date_added` = ? " +
                "WHERE `product_id` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getProductDescription());
            preparedStatement.setString(3, product.getDossier());
            preparedStatement.setInt(4, product.getCategoryId());
            preparedStatement.setInt(5, product.getInStock());
            preparedStatement.setDouble(6, product.getPrice());
            preparedStatement.setString(7, product.getBrand());
            preparedStatement.setInt(8, product.getNbrImage());
            preparedStatement.setTimestamp(9, product.getDateAdded());
            preparedStatement.setInt(10, product.getProductId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Product Updated, Name : " + product.getProductName()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Product not found for ID : " + product.getProductId()).build();
            }
        } finally {
            connection.close();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteProduct(@PathParam("id") Integer id) throws SQLException {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Product ID Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String deleteQuery = "DELETE FROM `shop_online`.`products` WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Product Deleted, ID : " + id).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Product not found for ID : " + id).build();
            }
        } finally {
            connection.close();
        }
    }
}