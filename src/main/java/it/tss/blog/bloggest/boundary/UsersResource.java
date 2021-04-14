/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.bloggest.boundary;


import it.tss.blog.bloggest.boundary.dto.UserCreate;
import it.tss.blog.bloggest.control.UserStore;
import it.tss.blog.bloggest.entity.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

/**
 *
 * @author utente
 */
@DenyAll
@Path("/users")
public class UsersResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserStore userStore;

    @Context
    private ResourceContext resource;

    @Context
    SecurityContext securityCtx;

    @Inject
    JsonWebToken jwt;


    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath()+"\n");
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid UserCreate u) {
        User saved = userStore.create(new User(u));
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> searchAll() {
        return userStore.searchAll();
    }

    @SecurityRequirement(name = "jwt")
    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("{userId}")
    public UserResource find(@PathParam("userId") Long id) {
        boolean isUserRole = securityCtx.isUserInRole(User.Role.USER.name());
        if (isUserRole && (jwt == null || jwt.getSubject() == null || Long.parseLong(jwt.getSubject()) != id)) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Access forbidden: role not allowed").build());
        }
        UserResource sub = resource.getResource(UserResource.class);
        sub.setUserId(id);
        return sub;
    }

}
