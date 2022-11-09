package be.Jadoulle.POJO;

import java.io.Serializable;

import be.Jadoulle.DAO.AbstractDAOFactory;
import be.Jadoulle.DAO.DAO;
import be.Jadoulle.DAO.UserDAO;

public abstract class User implements Serializable {
	private static final long serialVersionUID = 1695476360458977633L;
	
	private int id;
	private String username;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//constructor
	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	//methods
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
	
	public static User login(String username, String password) {
		return UserDAO.authenticate(username, password);
	}
	
}
