package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import service.IClienteDao;

public class ClienteDao implements IClienteDao {
	
	private Connection c;

	public ClienteDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}

	@Override
	public List<Cliente> findAll() throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT id, nome, telefone FROM cliente";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Cliente> listCliente = new ArrayList<Cliente>();
		
		while (rs.next()) {
			Cliente cliente = new Cliente();
			
			cliente.setId(rs.getInt("id"));
			cliente.setNome(rs.getString("nome"));
			cliente.setTelefone(rs.getString("telefone"));
			
			listCliente.add(cliente);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return listCliente;
	}

	@Override
	public Cliente findById(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT id, nome, telefone FROM cliente WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		Cliente cliente = new Cliente();
		
		if (rs.next()) {			
			cliente.setId(rs.getInt("id"));
			cliente.setNome(rs.getString("nome"));
			cliente.setTelefone(rs.getString("telefone"));
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return cliente;
	}

	@Override
	public Cliente save(Cliente cliente) throws SQLException {
		if (this.findById(cliente.getId()).getId() != null) {
			c = checkConnectionStatus(c);
			
			String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getTelefone());
			ps.setInt(3, cliente.getId());
			
			ps.execute();
			ps.close();
			
			c.close();
			
			return cliente;
		} else {
			c = checkConnectionStatus(c);
			
			String sql = "INSERT INTO cliente VALUES (?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			
			ps.setInt(1, cliente.getId());
			ps.setString(2, cliente.getNome());
			ps.setString(3, cliente.getTelefone());
			
			ps.execute();
			ps.close();
			
			c.close();
			
			return cliente;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "DELETE cliente WHERE id = ?";
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
