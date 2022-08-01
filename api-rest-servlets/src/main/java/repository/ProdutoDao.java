package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Produto;
import service.IProdutoDao;

public class ProdutoDao implements IProdutoDao{

	private Connection c;

	public ProdutoDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}

	@Override
	public List<Produto> findAll() throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT id, nome, valor FROM produto";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Produto> listProduto = new ArrayList<Produto>();
		
		while (rs.next()) {
			Produto produto = new Produto();
			
			produto.setId(rs.getInt("id"));
			produto.setNome(rs.getString("nome"));
			produto.setValor(rs.getFloat("valor"));
			
			listProduto.add(produto);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return listProduto;
	}

	@Override
	public Produto findById(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT id, nome, valor FROM produto WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		Produto produto = new Produto();
		
		if (rs.next()) {			
			produto.setId(rs.getInt("id"));
			produto.setNome(rs.getString("nome"));
			produto.setValor(rs.getFloat("valor"));
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return produto;
	}

	@Override
	public Produto save(Produto produto) throws SQLException {
		if (this.findById(produto.getId()).getId() != null) {
			c = checkConnectionStatus(c);
			
			String sql = "UPDATE produto SET nome = ?, valor = ? WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			
			ps.setString(1, produto.getNome());
			ps.setFloat(2, produto.getValor());
			ps.setInt(3, produto.getId());
			
			ps.execute();
			ps.close();
			
			c.close();
			
			return produto;
		} else {
			c = checkConnectionStatus(c);
			
			String sql = "INSERT INTO produto VALUES (?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			
			ps.setInt(1, produto.getId());
			ps.setString(2, produto.getNome());
			ps.setFloat(3, produto.getValor());
			
			ps.execute();
			ps.close();
			
			c.close();
			
			return produto;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "DELETE produto WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ps.execute();
		ps.close();
		
		c.close();
	}
	
	/*
	 * check the status of the connection
	 * */
	private Connection checkConnectionStatus(Connection c) {
		try {
			if (c.isClosed()) {
				GenericDao gDao = new GenericDao();
				c = gDao.getConnection();
			}
			
			return c;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return c;
		}
	}
}
