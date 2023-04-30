package model;

import java.sql.Date;

public class Manutencao {

	public Manutencao(int id) {
		setId(id);
		setVeiculo(null);
		setDescricao("");
		setStatus(null);
		setDtInicio(null);
		setDtPrevFim(null);
		setDtFim(null);
	}
	
	private static final String NOME_BD = "gestaolocadora";
	private static final String NOME_TABELA = "manutencao";
	public static final String TABELA_MANUTENCAO = NOME_BD + "." + NOME_TABELA;
	
	public static final String CAMPO_ID = NOME_TABELA + ".id";
	private int id;
	
	public static final String CAMPO_IDVEICULO = NOME_TABELA + ".idveiculo";
	private Veiculo veiculo;
	
	public static final String CAMPO_DESCRICAO = NOME_TABELA + ".descricao";
	private String descricao;
	
	public static final String CAMPO_STATUS = NOME_TABELA + ".status";
	private StatusManutencao status;
	
	public static final String CAMPO_DTINICIO = NOME_TABELA + ".dtinicio";
	private Date dtInicio;
	
	public static final String CAMPO_DTPREVFIM = NOME_TABELA + ".dtprevfim";
	private Date dtPrevFim;
	
	public static final String CAMPO_DTFIM = NOME_TABELA + ".dtfim";
	private Date dtFim;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public StatusManutencao getStatus() {
		return status;
	}
	
	public void setStatus(StatusManutencao status) {
		this.status = status;
	}
	
	public Date getDtInicio() {
		return dtInicio;
	}
	
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	
	public Date getDtPrevFim() {
		return dtPrevFim;
	}
	
	public void setDtPrevFim(Date dtPrevFim) {
		this.dtPrevFim = dtPrevFim;
	}
	
	public Date getDtFim() {
		return dtFim;
	}
	
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	
}
