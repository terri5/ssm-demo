package com.terri.model;

import java.io.Serializable;

public class User  implements Serializable{
    private int id;
    private String userName;
    private int userAge;
    private String userAddress;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getUserAge() {
        return userAge;
    }
    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
	public String getName() {
		// TODO Auto-generated method stub
		return getUserName();
	}
	public int getAge() {
		return getUserAge();
	}
	public void setName(String name) {
		setUserName(name);
		
	}
	public void setAge(int age) {
		setUserAge(age);
		
	}

}