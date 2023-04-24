package model;

public class Endereco {

	public Endereco (int id) {
		setId(id);
	}
	
	private static final String NOME_BD = "gestaolocadora";
	private static final String NOME_TABELA = "endereco";
	public static final String TABELA_ENDERECO = NOME_BD + "." + NOME_TABELA;
	
	public static final String CAMPO_ID = NOME_TABELA + ".id";
	private int id;
	
	public static final String CAMPO_CEP = NOME_TABELA + ".cep";
	private int cep;
	
	public static final String CAMPO_LOGRADOURO = NOME_TABELA + ".logradouro";
	private String logradouro;
	
	public static final String CAMPO_NUMERO = NOME_TABELA + ".numero";
	private int numero;
	
	public static final String CAMPO_COMPLEMENTO = NOME_TABELA + ".complemento";
	private String complemento;
	
	public static final String CAMPO_BAIRRO = NOME_TABELA + ".bairro";
	private String bairro;
	
	public static final String CAMPO_CIDADE = NOME_TABELA + ".cidade";
	private String cidade;
	
	public static final String CAMPO_UF = NOME_TABELA + ".uf";
	private String uf;
	
	public static final String CAMPO_PAIS = NOME_TABELA + ".pais";
	private String pais;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCep() {
		return cep;
	}
	
	public void setCep(int cep) {
		this.cep = cep;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
}
