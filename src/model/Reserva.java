package model;

import java.sql.Date;

import controller.util.ControllerUtils;

public class Reserva {

	public Reserva(int id) {
		setId(id);
		setCliente(null);
		setVeiculo(null);
		setDtContratacao(null);
		setDtPrevDevolucao(null);
		setDtDevolucao(null);
		setStatus(null);
		setValorDiaria(0);
		setPercMultaDiaria(0);
		setMultaTotal(0);
		setValorTotalPrev(0);
		setValorTotal(0);
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
	
	public static final String CAMPO_VALORDIARIA = NOME_TABELA + ".valordiaria";
	private double valorDiaria;
	
	public static final String CAMPO_PERCMULTADIARIA = NOME_TABELA + ".percmultadiaria";
	private double percMultaDiaria;
	
	public static final String CAMPO_MULTATOTAL = NOME_TABELA + ".multatotal";
	private double multaTotal;
	
	public static final String CAMPO_VALORTOTALPREV = NOME_TABELA + ".valortotalprev";
	private double valorTotalPrev;
	
	public static final String CAMPO_VALORTOTAL = NOME_TABELA + ".valortotal";
	private double valorTotal;
	
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

	public double getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public double getPercMultaDiaria() {
		return percMultaDiaria;
	}

	public void setPercMultaDiaria(double percMultaDiaria) {
		this.percMultaDiaria = percMultaDiaria;
	}

	public double getMultaTotal() {
		return multaTotal;
	}

	public void setMultaTotal(double multaTotal) {
		this.multaTotal = multaTotal;
	}

	public double getValorTotalPrev() {
		return valorTotalPrev;
	}

	public void setValorTotalPrev(double valorTotalPrev) {
		this.valorTotalPrev = valorTotalPrev;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public int getDiasPrev() {
		return ControllerUtils.contarDiasPrevistosReserva(this);
	}
	
	public int getTotalDias() {
		return ControllerUtils.contarTotalDiasReserva(this);
	}
		
	public int getDiasAtraso() {
		return ControllerUtils.contarDiasAtrasoReserva(this);
	}
	
}
