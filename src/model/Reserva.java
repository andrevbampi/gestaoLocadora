package model;

import java.sql.Date;

public class Reserva {

	public Reserva(int id) {
		setId(id);
		setCliente(null);
		setVeiculo(null);
		setDtContratacao(null);
		setDtPrevDevolucao(null);
		setDtDevolucao(null);
		setStatus(null);
	}
	
	private static final String NOME_BD = "gestaolocadora";
	private static final String NOME_TABELA = "reserva";
	public static final String TABELA_RESERVA = NOME_BD + "." + NOME_TABELA;
	
	public static final String CAMPO_ID = NOME_TABELA + ".id";
	private int id;
	
	public static final String CAMPO_IDCLIENTE = NOME_TABELA + ".idcliente";
	private Usuario cliente;
	
	public static final String CAMPO_IDVEICULO = NOME_TABELA + ".idveiculo";
	private Veiculo veiculo;
	
	public static final String CAMPO_DTCONTRATACAO = NOME_TABELA + ".dtcontratacao";
	private Date dtContratacao;
	
	public static final String CAMPO_DTPREVDEVOLUCAO = NOME_TABELA + ".dtprevdevolucao";
	private Date dtPrevDevolucao;
	
	public static final String CAMPO_DTDEVOLUCAO = NOME_TABELA + ".dtdevolucao";
	private Date dtDevolucao;
	
	public static final String CAMPO_STATUS = NOME_TABELA + ".status";
	private StatusReserva status;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Usuario getCliente() {
		return cliente;
	}
	
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public Date getDtContratacao() {
		return dtContratacao;
	}
	
	public void setDtContratacao(Date dtContratacao) {
		this.dtContratacao = dtContratacao;
	}
	
	public Date getDtPrevDevolucao() {
		return dtPrevDevolucao;
	}
	
	public void setDtPrevDevolucao(Date dtPrevDevolucao) {
		this.dtPrevDevolucao = dtPrevDevolucao;
	}
	
	public Date getDtDevolucao() {
		return dtDevolucao;
	}
	
	public void setDtDevolucao(Date dtDevolucao) {
		this.dtDevolucao = dtDevolucao;
	}
	
	public StatusReserva getStatus() {
		return status;
	}
	
	public void setStatus(StatusReserva status) {
		this.status = status;
	}
	
}
