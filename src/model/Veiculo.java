package model;

public class Veiculo {
	
	public Veiculo(int id) {
		setId(id);
		setMarca("");
		setModelo("");
		setAno(0);
		setCor("");
		setStatus(null);
		setCategoria(null);
		setPlaca("");
		setAtivo(true);
	}
	
	private static final String NOME_BD = "gestaolocadora";
	private static final String NOME_TABELA = "veiculo";
	public static final String TABELA_VEICULO = NOME_BD + "." + NOME_TABELA;
	
	public static final String CAMPO_ID = NOME_TABELA + ".id";
	private int id;
	
	public static final String CAMPO_MARCA = NOME_TABELA + ".marca";
	private String marca;
	
	public static final String CAMPO_MODELO = NOME_TABELA + ".modelo";
	private String modelo;
	
	public static final String CAMPO_ANO = NOME_TABELA + ".ano";
	private int ano;
	
	public static final String CAMPO_COR = NOME_TABELA + ".cor";
	private String cor;
	
	public static final String FUNCAO_GETSTATUS = NOME_BD + ".getstatusveiculo";
	private StatusVeiculo status;
	
	public static final String CAMPO_CATEGORIA = NOME_TABELA + ".categoria";
	private CategoriaVeiculo categoria;
	
	public static final String CAMPO_PLACA = NOME_TABELA + ".placa";
	private String placa;
	
	public static final String CAMPO_ATIVO = NOME_TABELA + ".ativo";
	private boolean ativo;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public StatusVeiculo getStatus() {
		return status;
	}
	
	public void setStatus(StatusVeiculo status) {
		this.status = status;
	}
	
	public CategoriaVeiculo getCategoria() {
		return categoria;
	}
	
	public void setCategoria(CategoriaVeiculo categoria) {
		this.categoria = categoria;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
    
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
