package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.ProductService;
import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api("Engineering Academy Dropwizard Product API")
@Path("/api")
public class ProductController {
    private ProductService productService = new ProductService();

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(productService.getAllProducts())
                    .build();
        } catch (FailedToGetProductsException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") int id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(productService.getProductById(id))
                    .build();
        } catch (ProductDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (FailedToGetProductException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(ProductRequest product) {
        try {
            return Response
                    .ok(productService.createProduct(product))
                    .build();
        } catch (FailedToCreateProductException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();

        } catch (InvalidProductException e) {
            System.err.println(e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @PUT
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id, ProductRequest product) {
        try {
            productService.updateProduct(id, product);
            return Response
                    .ok()
                    .build();
        } catch (FailedToCreateProductException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        } catch (InvalidProductException | ProductDoesNotExistException e ) {
            System.err.println(e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @DELETE
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") int id) {
        try {
            productService.deleteProduct(id);
            return Response
                    .ok()
                    .build();
        } catch (ProductDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (GenericSQLException e) {
            System.err.println(e.getMessage());
            return Response
                    .serverError()
                    .build();
        }
    }
}
