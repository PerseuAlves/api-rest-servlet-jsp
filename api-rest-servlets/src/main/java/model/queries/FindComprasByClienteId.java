package model.queries;

import java.util.Objects;

public class FindComprasByClienteId {

	private Integer compraId;
	private String nomeCliente;
	private String nomeProduto;
	private Float valorUnidade;
	private Integer qtd;
	private Double valorTotal;
	private String dtCompra;
	
	public FindComprasByClienteId() {
		
	}

	public FindComprasByClienteId(Integer compraId, String nomeCliente, 
			String nomeProduto, Float valorUnidade, 
			Integer qtd, Double valorTotal, String dtCompra) {
		this.compraId = compraId;
		this.nomeCliente = nomeCliente;
		this.nomeProduto = nomeProduto;
		this.valorUnidade = valorUnidade;
		this.qtd = qtd;
		this.valorTotal = valorTotal;
		this.dtCompra = dtCompra;
	}

	public Integer getCompraId() {
		return compraId;
	}

	public void setCompraId(Integer compraId) {
		this.compraId = compraId;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Float getValorUnidade() {
		return valorUnidade;
	}

	public void setValorUnidade(Float valorUnidade) {
		this.valorUnidade = valorUnidade;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getDtCompra() {
		return dtCompra;
	}

	public void setDtCompra(String dtCompra) {
		this.dtCompra = dtCompra;
	}

	@Override
	public String toString() {
		return "FindComprasByClienteId [compraId=" + compraId + ", nomeCliente=" + nomeCliente + ", nomeProduto="
				+ nomeProduto + ", valorUnidade=" + valorUnidade + ", qtd=" + qtd + ", valorTotal=" + valorTotal
				+ ", dtCompra=" + dtCompra + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(compraId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FindComprasByClienteId other = (FindComprasByClienteId) obj;
		return Objects.equals(compraId, other.compraId);
	}
}
