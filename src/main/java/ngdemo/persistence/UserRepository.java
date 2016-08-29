package ngdemo.persistence;

import java.util.ArrayList;
import java.util.List;

import ngdemo.entity.User;
import ngdemo.entity.UserDTO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepository {

	/*private List<User> ul = null; 
			
    public UserDTO getUser(String uid) {
    	if(ul == null)
    		SetupUsers();    	
        for(User u : ul)
        {
        	if(u !=null && u.getUid().equals(uid))
        		return MapUserToUDTO(u);
        }
    	return null;
    }
    
    public List<UserDTO> getUsers() { // return List of db object model or dto
    	if(ul == null)
    		SetupUsers();
    	List<UserDTO> uDTOList = new ArrayList<UserDTO>();
    	for(User u : ul)
        {
    		uDTOList.add(MapUserToUDTO(u));
        }
    	return uDTOList;
    }
    
    public User saveUser(User u) {
    	//do hibernate/jdbc insert/save and return user object (with updated userid in case of insert)
    	u.setUid("3");
    	if(u.getUid() == null || u.getUid().trim() =="" || u.getUid().trim() == "0")
    	{
    		//insert and get the uid and set it to user object
    		u.setUid("3");
    	}	
    	else
    	{
    		//update the user object
    	}    	
        return u;
    }
    
    private void SetupUsers()
    {
    	ul = new ArrayList<User>();
    	
    	User user1 = new User();
    	user1.setUid("1");
    	user1.setFirstName("JonFromREST");
        user1.setLastName("DoeFromREST");
        ul.add(user1);
        User user2 = new User();
        user2.setUid("2");
        user2.setFirstName("V");
        user2.setLastName("M");
        ul.add(user2);        
    }
    
    private UserDTO MapUserToUDTO(User u)
    {
    	UserDTO uDTO = new UserDTO();
    	uDTO.setUid(u.getUid());
    	uDTO.setFirstName(u.getFirstName());
    	uDTO.setLastName(u.getLastName());    	
    	return uDTO;
    }*/
	
	Session session = null;
	/*private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}*/
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	//@Override
	public void saveUser(User u) {
		session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		if (u.getUid() > 0) {
			session.update(u);
		} else {
			
			session.persist(u);
		}
		session.getTransaction().commit();
		logger.info("User saved successfully, Contact Details="+u);
		//return MapUserToUDTO(u);
		//return null;
	}

	//@Override
	public void deleteUser(int uid) {
		//Session session = this.sessionFactory.getCurrentSession();
		session = HibernateUtil.getSessionFactory().openSession();
		User u = (User) session.load(User.class, new Integer(uid));
		if(null != u){
			session.getTransaction().begin();
			session.delete(u);
			session.getTransaction().commit();
		}		
		logger.info("User deleted successfully, contact details="+u);
	}

	//@Override
	public void copyUser(int uid) {
		//Session session = this.sessionFactory.getCurrentSession();
		session = HibernateUtil.getSessionFactory().openSession();
		User u = (User) session.load(User.class, new Integer(uid));
		User NewU = u.clone();
		if(NewU != null){			
			session.getTransaction().begin();
			session.persist(NewU);
			session.getTransaction().commit();
		}		
		logger.info("User copied successfully, contact details="+NewU);
	}
		
	//@Override
	public List<UserDTO> getUsers() {
		//Session session = this.sessionFactory.getCurrentSession();
		session = HibernateUtil.getSessionFactory().openSession();
		List<User> uList = session.createQuery("from User").list();
		for(User u : uList){
			logger.info("Contact List::"+u);
		}
		
		List<UserDTO> uDTOList = new ArrayList<UserDTO>();
    	for(User u : uList)
        {
    		uDTOList.add(MapUserToUDTO(u));
        }
    	return uDTOList;		
	}

	private UserDTO MapUserToUDTO(User u)
    {
    	UserDTO uDTO = new UserDTO();
    	uDTO.setUid(u.getUid());
    	uDTO.setFirstName(u.getFirstName());
    	uDTO.setLastName(u.getLastName());    	
    	return uDTO;
    }
	
	//@Override
	public UserDTO getUser(int uid) {
		//Session session = this.sessionFactory.getCurrentSession();		
		session = HibernateUtil.getSessionFactory().openSession();
		User u = (User) session.get(User.class, new Integer(uid));
		logger.info("Contact loaded successfully, Contact details="+u);
		return MapUserToUDTO(u);
	}
}
