package service;

import java.sql.SQLException;
import java.util.List;

import model.Compra;
import model.queries.FindComprasByClienteId;

public interface ICompraDao {

	 public List<Compra> findAll() throws SQLException;
	 public Compra findById(Integer id) throws SQLException;
	 public Compra save(Compra compra) throws SQLException;
	 public void delete(Integer id) throws SQLException;
	 public List<FindComprasByClienteId> FindComprasByClienteId(Integer id) throws SQLException;
}
