package service;

import java.sql.SQLException;
import java.util.List;

import model.Produto;

public interface IProdutoDao {

	 public List<Produto> findAll() throws SQLException;
	 public Produto findById(Integer id) throws SQLException;
	 public Produto save(Produto produto) throws SQLException;
	 public void delete(Integer id) throws SQLException;
}
