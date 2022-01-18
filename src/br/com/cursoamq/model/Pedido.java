package br.com.cursoamq.model;

import java.math.BigDecimal;

public class Pedido {
	private Integer id;
	private String cpfCliente; 
	private Produto produto;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido(Integer id, String cpfCliente, Produto produto) {
		super();
		this.id = id;
		this.cpfCliente = cpfCliente;
		this.produto = produto;
	}
	
	public String toJSON() {
		String json = "{id:"+this.id+", produto:"+this.produto.getNome()+"}";
		
		return json;
	}
	
	public static Pedido builder(Integer idPedido) {
		Produto prod = new Produto(1, "cerveja pesco beer", 16.90);
		Pedido ped = new Pedido(idPedido, "05207250930", prod);
		return ped;
	}
	
}

class Produto {
	private Integer id;
	private String nome;
	private double preco;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public Produto(Integer id, String nome, double d) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = d;
	}
	
	
}
