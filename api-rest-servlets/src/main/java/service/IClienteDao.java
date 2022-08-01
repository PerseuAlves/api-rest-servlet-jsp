package service;

import java.sql.SQLException;
import java.util.List;

import model.Cliente;

public interface IClienteDao {

	 public List<Cliente> findAll() throws SQLException;
	 public Cliente findById(Integer id) throws SQLException;
	 public Cliente save(Cliente cliente) throws SQLException;
	 public void delete(Integer id) throws SQLException;
}
