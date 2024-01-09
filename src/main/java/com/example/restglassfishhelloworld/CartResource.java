package com.example.restglassfishhelloworld;

import com.example.restglassfishhelloworld.entities.Cart;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/carts")
public class CartResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cart> getAllCarts() throws SQLException {
        List<Cart> cartList = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.cart");

        while (resultSet.next()) {
            cartList.add(new Cart(
                    resultSet.getInt("id_cart"),
                    resultSet.getInt("id_user"),
                    resultSet.getInt("id_product"),
                    resultSet.getInt("qts"),
                    resultSet.getBoolean("payed"),
                    resultSet.getBoolean("confirmed")
            ));
        }

        statement.close();
        connection.close();
        return cartList;
    }

    @GET
    @Path("GET/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getCartById(@PathParam("id") Integer id) throws SQLException {
        Cart cart = new Cart();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop_online.cart WHERE id_cart =" + id);

        if (resultSet.first()) {
            cart.setIdCart(resultSet.getInt("id_cart"));
            cart.setIdUser(resultSet.getInt("id_user"));
            cart.setIdProduct(resultSet.getInt("id_product"));
            cart.setQuantity(resultSet.getInt("qts"));
            cart.setPayed(resultSet.getBoolean("payed"));
            cart.setConfirmed(resultSet.getBoolean("confirmed"));
        } else {
            statement.close();
            connection.close();
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        statement.close();
        connection.close();
        return cart;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCart(Cart cart) throws SQLException {
        if (cart == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Cart Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("INSERT INTO `shop_online`.`cart`" +
                "(`id_user`, `id_product`, `qts`, `payed`, `confirmed`)" +
                "VALUES (" +
                "\"" + cart.getIdUser() + "\", " +
                "\"" + cart.getIdProduct() + "\", " +
                "\"" + cart.getQuantity() + "\", " +
                (cart.isPayed() ? 1 : 0) + ", " +
                "\"" + (cart.isConfirmed() ? 1 : 0) + "\"" +
                ")");
        statement.close();
        connection.close();

        return Response.status(Response.Status.CREATED)
                .entity("Cart Created, ID : " + cart.getIdCart()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCart(Cart cart) throws SQLException {
        if (cart == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Cart Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String updateQuery = "UPDATE `shop_online`.`cart` " +
                "SET " +
                "`id_user` = ?, " +
                "`id_product` = ?, " +
                "`qts` = ?, " +
                "`payed` = ?, " +
                "`confirmed` = ? " +
                "WHERE `id_cart` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, cart.getIdUser());
            preparedStatement.setInt(2, cart.getIdProduct());
            preparedStatement.setInt(3, cart.getQuantity());
            preparedStatement.setBoolean(4, cart.isPayed());
            preparedStatement.setBoolean(5, cart.isConfirmed());
            preparedStatement.setInt(6, cart.getIdCart());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Cart Updated, ID : " + cart.getIdCart()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cart not found for ID : " + cart.getIdCart()).build();
            }
        } finally {
            connection.close();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCart(@PathParam("id") Integer id) throws SQLException {
        if (id == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No Cart ID Sent").build();
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shop_online?useSSL=false", "root", "root");
        String deleteQuery = "DELETE FROM `shop_online`.`cart` WHERE id_cart = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK)
                        .entity("Cart Deleted, ID : " + id).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cart not found for ID : " + id).build();
            }
        } finally {
            connection.close();
        }
    }
}
