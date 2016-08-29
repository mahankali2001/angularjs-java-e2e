package ngdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User")//, catalog = "mkyong", uniqueConstraints = {
//@UniqueConstraint(columnNames = "STOCK_NAME"),
//@UniqueConstraint(columnNames = "STOCK_CODE") })
public class User implements Cloneable { //http://stackoverflow.com/questions/9944882/hibernate-copy-object-values-into-new-object-with-new-generated-id

	@Id
	@Column(name="uid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int uid;
    private String firstName;
    private String lastName;
    
    public User clone() {

    	User obj = new User();
        obj.setFirstName(this.firstName);
        obj.setLastName(this.lastName);

        return obj;
    }

    public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	//@Column(name = "firstName", unique = true, nullable = false, length = 40)
	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
	public String toString(){
		return "uid="+uid+", firstName="+firstName+", lastName="+lastName;
	}
}
