package be.Jadoulle.DAO;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {
	protected Connection connection;

	public DAO(Connection conn) {
		this.connection = conn;
	}

	public abstract T find(int id);
	public abstract ArrayList<T> findAll();
	public abstract boolean create(T obj);
	public abstract boolean update(T obj);
	public abstract boolean delete(T obj);
}
