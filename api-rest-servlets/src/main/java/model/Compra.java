package model;

import java.util.Objects;

public class Compra {

	private Integer id;
	private String dtCompra;
	private Integer qtd;
	private Integer idCliente;
	private Integer idProduto;
	
	public Compra() {
		
	}

	public Compra(Integer id, String dtCompra, Integer qtd, Integer idCliente, Integer idProduto) {
		this.id = id;
		this.dtCompra = dtCompra;
		this.qtd = qtd;
		this.idCliente = idCliente;
		this.idProduto = idProduto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDtCompra() {
		return dtCompra;
	}

	public void setDtCompra(String dtCompra) {
		this.dtCompra = dtCompra;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", dtCompra=" + dtCompra + ", qtd=" + qtd + ", idCliente=" + idCliente
				+ ", idProduto=" + idProduto + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		return Objects.equals(id, other.id);
	}
}
