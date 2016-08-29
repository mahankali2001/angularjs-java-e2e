package ngdemo.rest;

import java.util.List;

import ngdemo.business.UserBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/app")
public class UserRestService {

	// Rest (req/response objects) <-> Business (req->dto->model/dto->response) <-> persistenace (model or entity->dto) <-> DB
	UserBusiness ub = new UserBusiness();
	
	@GET
	@Path("/users/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getUserInJSON(@PathParam("uid") String uid) {		
		return ub.getUser(Integer.parseInt(uid));
    }
    
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserResponse> getUsersInJSON() {
        return ub.getUsers();
    }
    
 
 	@Path("/users/input")
 	@POST
 	@Produces({MediaType.APPLICATION_JSON})
 	@Consumes({MediaType.APPLICATION_JSON})
 	public UserResponse saveUser(@Context UriInfo uriInfo,UserRequest input) { 		
 		return ub.saveUser(input);
 		//return null;
 	}
 	
 	@Path("/users/delete/{uid}")
 	@GET 	 	
 	public void deleteUser(@PathParam("uid") String uid) { 		
 		ub.deleteUser(Integer.parseInt(uid)); 		
 	}
 	
 	@Path("/users/copy/{uid}")
 	@GET 	 	
 	public void copyUser(@PathParam("uid") String uid) { 		
 		ub.copyUser(Integer.parseInt(uid)); 		
 	}
}
