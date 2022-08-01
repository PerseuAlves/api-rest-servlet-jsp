package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Compra;
import model.queries.FindComprasByClienteId;
import service.ICompraDao;

public class CompraDao implements ICompraDao{

	private Connection c;

	public CompraDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}

	@Override
	public List<Compra> findAll() throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT id, dt_compra, qtd, idCliente, idProduto FROM compra";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Compra> listCompra = new ArrayList<Compra>();
		
		while (rs.next()) {
			Compra compra = new Compra();
			
			compra.setId(rs.getInt("id"));
			compra.setDtCompra(rs.getString("dt_compra"));
			compra.setQtd(rs.getInt("qtd"));
			compra.setIdCliente(rs.getInt("idCliente"));
			compra.setIdProduto(rs.getInt("idProduto"));
			
			listCompra.add(compra);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return listCompra;
	}

	@Override
	public Compra findById(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT id, dt_compra, qtd, idCliente, idProduto FROM compra WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		Compra compra = new Compra();
		
		if (rs.next()) {			
			compra.setId(rs.getInt("id"));
			compra.setDtCompra(rs.getString("dt_compra"));
			compra.setQtd(rs.getInt("qtd"));
			compra.setIdCliente(rs.getInt("idCliente"));
			compra.setIdProduto(rs.getInt("idProduto"));
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return compra;
	}

	@Override
	public Compra save(Compra compra) throws SQLException {
		if (this.findById(compra.getId()).getId() != null) {
			c = checkConnectionStatus(c);
			
			String sql = "UPDATE compra SET dt_compra = ?, qtd = ?, idCliente = ?, idProduto = ? WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			
			ps.setString(1, compra.getDtCompra());
			ps.setInt(2, compra.getQtd());
			ps.setInt(3, compra.getIdCliente());
			ps.setInt(4, compra.getIdProduto());
			ps.setInt(5, compra.getId());
			
			ps.execute();
			ps.close();
			
			c.close();
			
			return compra;
		} else {
			c = checkConnectionStatus(c);
			
			String sql = "INSERT INTO compra VALUES (?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			
			ps.setInt(1, compra.getId());
			ps.setString(2, compra.getDtCompra());
			ps.setInt(3, compra.getQtd());
			ps.setInt(4, compra.getIdCliente());
			ps.setInt(5, compra.getIdProduto());
			
			ps.execute();
			ps.close();
			
			c.close();
			
			return compra;
		}
	}

	@Override
	public void delete(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "DELETE compra WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ps.execute();
		ps.close();
		
		c.close();
	}
	
	@Override
	public List<FindComprasByClienteId> FindComprasByClienteId(Integer id) throws SQLException {
		c = checkConnectionStatus(c);
		
		String sql = "SELECT comp.id AS compra_id, \n"
				+ "        cli.nome AS nome_cliente, \n"
				+ "        pro.nome AS nome_produto, \n"
				+ "        pro.valor AS valor_unidade, \n"
				+ "        comp.qtd, (comp.qtd * pro.valor) AS valor_total, \n"
				+ "        comp.dt_compra \n"
				+ "FROM cliente cli, compra comp, produto pro \n"
				+ "WHERE cli.id = comp.idCliente AND comp.idProduto = pro.id \n"
				+ "AND cli.id = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		List<FindComprasByClienteId> listCompraByClienteId = new ArrayList<FindComprasByClienteId>();
		
		while (rs.next()) {
			FindComprasByClienteId findComprasByClienteId = new FindComprasByClienteId();
			
			findComprasByClienteId.setCompraId(rs.getInt("compra_id"));
			findComprasByClienteId.setNomeCliente(rs.getString("nome_cliente"));
			findComprasByClienteId.setNomeCliente(rs.getString("nome_produto"));
			findComprasByClienteId.setValorUnidade(rs.getFloat("valor_unidade"));
			findComprasByClienteId.setQtd(rs.getInt("qtd"));
			findComprasByClienteId.setValorTotal(rs.getDouble("valor_total"));
			findComprasByClienteId.setDtCompra(rs.getString("dt_compra"));			
			
			listCompraByClienteId.add(findComprasByClienteId);
		}
		
		rs.close();
		ps.close();
		
		c.close();
		
		return listCompraByClienteId;
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
