package ngdemo.entity;

import javax.xml.bind.annotation.XmlRootElement;

public class UserDTO {

	private int uid;
    private String firstName;
    private String lastName;
    private String errorCode;

    public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		if(this.errorCode == null || this.errorCode == "")
			this.errorCode = errorCode;
		else
			this.errorCode = this.errorCode +","+ errorCode;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

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
}
