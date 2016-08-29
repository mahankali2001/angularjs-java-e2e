package ngdemo.business;

import java.util.ArrayList;
import java.util.List;

import ngdemo.entity.User;
import ngdemo.entity.UserDTO;
import ngdemo.rest.UserRequest;
import ngdemo.rest.UserResponse;
import ngdemo.persistence.UserRepository;

public class UserBusiness {

	UserRepository ur = new UserRepository();
	//private List<User> ul = null; 
	private List<UserDTO> ulDTO = null; 
			
    public UserResponse getUser(int uid) {
    	return MapUDTOToUserResponse(ur.getUser(uid));    	
    }
    
    public List<UserResponse> getUsers() {
    	List<UserDTO> uDTOList = ur.getUsers();
    	List<UserResponse> urList = new ArrayList<UserResponse>();
    	for(UserDTO uDTO : uDTOList)
    	{
    		urList.add(MapUDTOToUserResponse(uDTO));
    	}
    	
    	return urList;
    }
    
    public UserResponse saveUser(UserRequest input) {
    	UserDTO udto = MapURToUDTO(input);
    	ValidateUser(udto);
    	if(udto.getErrorCode() == null || udto.getErrorCode().trim() == "")
    	{
	    	// map dto to db object model1 and call save
	    	User u = MapUDTOToUser(udto);
	    	ur.saveUser(u);
	    	udto.setUid(u.getUid());
	    	//if(udto.getUid() == null || udto.getUid().trim() == "" || udto.getUid().trim() == "0")	    		
	    	//	udto.setUid(u.getUid());
	    	// map dto to db object model2 and call save ... etc
    	}
    	
        return MapUDTOToUserResponse(udto);
    	//return MapUReqToUserRes(input);
    }
    
    private UserResponse MapUReqToUserRes(UserRequest input)
    {
    	UserResponse ur = new UserResponse();
    	ur.setUid(input.getUid());
    	ur.setFirstName(input.getFirstName());
    	ur.setLastName(input.getLastName());
    	return ur;
    }
    
    private void ValidateUser(UserDTO udto)
    {
    	if(udto != null)
    	{
    		if(udto.getFirstName() == null || udto.getFirstName().trim() == "")
    			udto.setErrorCode("Message_MissingFirstName");
    		if(udto.getLastName() == null || udto.getLastName().trim() == "")
    			udto.setErrorCode("Message_MissingLastName"); 
    	}
    }
    
    private UserDTO MapURToUDTO(UserRequest input)
    {
    	UserDTO udto = new UserDTO();
    	udto.setUid(input.getUid());
    	udto.setFirstName(input.getFirstName());
    	udto.setLastName(input.getLastName());    	
    	return udto;
    }
    
    private User MapUDTOToUser(UserDTO udto)
    {
    	User u = new User();
    	u.setUid(udto.getUid());
    	u.setFirstName(udto.getFirstName());
    	u.setLastName(udto.getLastName());    	
    	return u;
    }
    
    public UserResponse MapUDTOToUserResponse(UserDTO u)
    {
    	UserResponse ur = new UserResponse();
    	ur.setUid(u.getUid());
    	ur.setFirstName(u.getFirstName());
    	ur.setLastName(u.getLastName());
    	ur.setErrorCode(u.getErrorCode());
    	return ur;
    }
    
    public void deleteUser(int uid)
    {
    	ur.deleteUser(uid);
    }
    
    public void copyUser(int uid)
    {
    	ur.copyUser(uid);
    }
}
