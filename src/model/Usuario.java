package model;

public class Usuario {
	
	public Usuario(int id) {
		setId(id);
		setLogin("");
		setNome("");
		setGenero(null);
		setTelefone("");
		setAdministrador(false);
		setEndereco(null);
	}
	
	private static final String NOME_BD = "gestaolocadora";
	private static final String NOME_TABELA = "usuario";
	public static final String TABELA_USUARIO = NOME_BD + "." + NOME_TABELA;
	
	public static final String CAMPO_ID = NOME_TABELA + ".id";
	private int id;
	
	public static final String CAMPO_LOGIN = NOME_TABELA + ".login";
	private String login;
	
	public static final String CAMPO_SENHA = NOME_TABELA + ".senha";
    private String senha;
    
    public static final String CAMPO_NOME = NOME_TABELA + ".nome";
    private String nome;
    
    public static final String CAMPO_GENERO = NOME_TABELA + ".genero";
    private GeneroUsuario genero;
    
    public static final String CAMPO_TELEFONE = NOME_TABELA + ".telefone";
    private String telefone;
    
    public static final String CAMPO_ADMINISTRADOR = NOME_TABELA + ".administrador";
	private boolean administrador;
    
    public static final String CAMPO_IDENDERECO = NOME_TABELA + ".idendereco";
    private Endereco endereco;
    
    public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public GeneroUsuario getGenero() {
		return genero;
	}
	
	public void setGenero(GeneroUsuario genero) {
		this.genero = genero;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public boolean isAdministrador() {
		return administrador;
	}
    
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
