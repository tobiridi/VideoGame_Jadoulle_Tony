package be.Jadoulle.POJO;

import java.io.Serializable;
import java.util.Objects;

import be.Jadoulle.DAO.AbstractDAOFactory;
import be.Jadoulle.DAO.UserDAO;

public abstract class User implements Serializable {
	private static final long serialVersionUID = 1695476360458977633L;

	protected int id;
	protected String username;
	protected String password;

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
	public static User login(String username, String password) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		User userAuthenticate =  ((UserDAO) adf.getUserDao()).authenticate(username, password);
		return userAuthenticate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (this.getClass() != obj.getClass()))
			return false;
		User other = (User) obj;
		return this.id == other.id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}

}
