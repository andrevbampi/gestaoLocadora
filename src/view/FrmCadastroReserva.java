package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ReservaController;
import controller.exception.BusinessRuleException;
import model.Reserva;
import model.StatusReserva;
import model.Usuario;
import model.Veiculo;
import view.report.RelatorioReserva;
import view.util.ViewUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FrmCadastroReserva extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtId;
	private JTextField edtIdCliente;
	private JTextField edtNomeCliente;
	private JTextField edtIdVeiculo;
	private JTextField edtMarcaModeloVeiculo;
	private final ButtonGroup rgpStatusReserva = new ButtonGroup();
	private Usuario usuarioSistema;
	private ReservaController reservaController;
	private JFormattedTextField edtDtContratacao;
	private JFormattedTextField edtDtPrevDevolucao;
	private JFormattedTextField edtDtDevolucao;
	private MaskFormatter mascaraData;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnRelatorio = new JButton("Relatório");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarReservas = new JButton("...");
	private JButton btnConsultarClientes = new JButton("...");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JRadioButton rbtStPago = new JRadioButton("Pago");
	private JRadioButton rbtStCan = new JRadioButton("Cancelado");
	private JRadioButton rbtStAnd = new JRadioButton("Em andamento");
	private JTextField edtValorDiaria;
	private JTextField edtPercMultaDiaria;
	private JTextField edtValorTotalPrev;
	private JTextField edtValorMultaTotal;
	private JTextField edtValorTotal;
	private JTextField edtTotalDias;
	private JTextField edtDiasPrev;
	private Usuario clienteSelecionado;
	private Veiculo veiculoSelecionado;
	private Reserva reservaSelecionada;
	private JTextField edtDiasAtraso;

	public FrmCadastroReserva(Usuario usuarioSistema) {
		setResizable(false);
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		clienteSelecionado = null;
		veiculoSelecionado = null;
		reservaSelecionada = null;
		this.usuarioSistema = usuarioSistema;
		reservaController = new ReservaController(usuarioSistema);
		setBounds(100, 100, 496, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(Color.GRAY);
		contentPane.add(pnlTop, BorderLayout.NORTH);
		pnlTop.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTitulo = new JPanel();
		pnlTitulo.setBackground(Color.GRAY);
		pnlTop.add(pnlTitulo, BorderLayout.WEST);
		
		JLabel lblTitulo = new JLabel("RESERVA DE VEÍCULO");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblTitulo);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.GRAY);
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);

		try {
			mascaraData = new MaskFormatter(ViewUtils.getMascaraCampoData());
		} catch (ParseException e1) {
			mascaraData = null;
		}
		
		edtDtContratacao = new JFormattedTextField(mascaraData);
		edtDtContratacao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lostFocusCamposData(edtDtContratacao);
			}
		});
		edtDtPrevDevolucao = new JFormattedTextField(mascaraData);
		edtDtPrevDevolucao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lostFocusCamposData(edtDtPrevDevolucao);
			}
		});
		edtDtDevolucao = new JFormattedTextField(mascaraData);
		edtDtDevolucao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lostFocusCamposData(edtDtDevolucao);
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
	
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregar();
			}
		});
		btnConsultarClientes.setFocusable(false);
		btnConsultarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaUsuarios();
			}
		});
		btnConsultarVeiculos.setFocusable(false);
		btnConsultarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaVeiculos();
			}
		});
		
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novo();
			}
		});
		
		pnlBotoes.add(btnNovo);
		pnlBotoes.add(btnSalvar);
		
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		pnlBotoes.add(btnLimpar);
		
		pnlBotoes.add(btnCarregar);
		
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarRelatorio();
			}
		});
		pnlBotoes.add(btnRelatorio);
		pnlBotoes.add(btnSair);
		
		JPanel pnlReservaVeiculo = new JPanel();
		pnlReservaVeiculo.setLayout(null);
		pnlReservaVeiculo.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlReservaVeiculo, BorderLayout.CENTER);
		
		rgpStatusReserva.add(rbtStPago);
		rbtStPago.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lostFocusCamposData(null);
			}
		});
		rbtStPago.setEnabled(false);
		rbtStPago.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStPago.setBackground(Color.LIGHT_GRAY);
		rbtStPago.setBounds(275, 170, 57, 23);
		pnlReservaVeiculo.add(rbtStPago);

		rgpStatusReserva.add(rbtStCan);
		rbtStCan.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lostFocusCamposData(null);
			}
		});
		rbtStCan.setEnabled(false);
		rbtStCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStCan.setBackground(Color.LIGHT_GRAY);
		rbtStCan.setBounds(195, 170, 79, 23);
		pnlReservaVeiculo.add(rbtStCan);

		rgpStatusReserva.add(rbtStAnd);
		rbtStAnd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lostFocusCamposData(null);
			}
		});
		rbtStAnd.setEnabled(false);
		rbtStAnd.setSelected(true);
		rbtStAnd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStAnd.setBackground(Color.LIGHT_GRAY);
		rbtStAnd.setBounds(94, 170, 100, 23);
		pnlReservaVeiculo.add(rbtStAnd);
		
		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblId.setBounds(80, 23, 19, 14);
		pnlReservaVeiculo.add(lblId);
		
		edtId = new JTextField();
		edtId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!edtId.getText().trim().equals("")) {
					carregar();
				}
			}
		});
		edtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
			}
		});
		edtId.setColumns(10);
		edtId.setBounds(94, 20, 225, 20);
		pnlReservaVeiculo.add(edtId);
		
		edtIdCliente = new JTextField();
		edtIdCliente.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				carregarClientePorId();
			}
		});
		edtIdCliente.setEnabled(false);
		edtIdCliente.setColumns(10);
		edtIdCliente.setBounds(94, 45, 35, 20);
		pnlReservaVeiculo.add(edtIdCliente);
		
		JLabel lblIdCliente = new JLabel("Cliente");
		lblIdCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIdCliente.setBounds(55, 48, 41, 14);
		pnlReservaVeiculo.add(lblIdCliente);
		
		JLabel lblDtContratacao = new JLabel("Contratação");
		lblDtContratacao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDtContratacao.setBounds(29, 98, 71, 14);
		pnlReservaVeiculo.add(lblDtContratacao);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setBounds(55, 175, 40, 14);
		pnlReservaVeiculo.add(lblStatus);
		btnConsultarReservas.setFocusable(false);
		btnConsultarReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaReservas();
			}
		});
		
		btnConsultarReservas.setBounds(324, 19, 25, 23);
		pnlReservaVeiculo.add(btnConsultarReservas);
		
		btnConsultarClientes.setEnabled(false);
		btnConsultarClientes.setBounds(324, 44, 25, 23);
		pnlReservaVeiculo.add(btnConsultarClientes);
		
		edtNomeCliente = new JTextField();
		edtNomeCliente.setFocusable(false);
		edtNomeCliente.setForeground(new Color(255, 255, 255));
		edtNomeCliente.setEditable(false);
		edtNomeCliente.setBackground(new Color(128, 128, 128));
		edtNomeCliente.setColumns(10);
		edtNomeCliente.setBounds(132, 45, 187, 20);
		pnlReservaVeiculo.add(edtNomeCliente);
		
		JLabel lblVeiculo = new JLabel("Veículo");
		lblVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVeiculo.setBounds(55, 73, 41, 14);
		pnlReservaVeiculo.add(lblVeiculo);
		
		edtIdVeiculo = new JTextField();
		edtIdVeiculo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				carregarVeiculoPorId();
			}
		});
		edtIdVeiculo.setEnabled(false);
		edtIdVeiculo.setColumns(10);
		edtIdVeiculo.setBounds(94, 70, 35, 20);
		pnlReservaVeiculo.add(edtIdVeiculo);
		
		btnConsultarVeiculos.setEnabled(false);
		btnConsultarVeiculos.setBounds(324, 69, 25, 23);
		pnlReservaVeiculo.add(btnConsultarVeiculos);
		
		edtMarcaModeloVeiculo = new JTextField();
		edtMarcaModeloVeiculo.setFocusable(false);
		edtMarcaModeloVeiculo.setForeground(Color.WHITE);
		edtMarcaModeloVeiculo.setEditable(false);
		edtMarcaModeloVeiculo.setColumns(10);
		edtMarcaModeloVeiculo.setBackground(Color.GRAY);
		edtMarcaModeloVeiculo.setBounds(132, 70, 187, 20);
		pnlReservaVeiculo.add(edtMarcaModeloVeiculo);
				
		edtDtContratacao.setEnabled(false);
		edtDtContratacao.setBounds(94, 95, 257, 20);
		pnlReservaVeiculo.add(edtDtContratacao);
		
		edtDtPrevDevolucao.setEnabled(false);
		edtDtPrevDevolucao.setBounds(94, 120, 257, 20);
		pnlReservaVeiculo.add(edtDtPrevDevolucao);
		
		JLabel lblDtPrevDevolucao = new JLabel("Previsão");
		lblDtPrevDevolucao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDtPrevDevolucao.setBounds(50, 123, 48, 14);
		pnlReservaVeiculo.add(lblDtPrevDevolucao);
		
		edtDtDevolucao.setEnabled(false);
		edtDtDevolucao.setBounds(94, 145, 257, 20);
		pnlReservaVeiculo.add(edtDtDevolucao);
		
		JLabel lblDtDevolucao = new JLabel("Devolução");
		lblDtDevolucao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDtDevolucao.setBounds(39, 148, 57, 14);
		pnlReservaVeiculo.add(lblDtDevolucao);
		
		edtValorDiaria = new JTextField();
		edtValorDiaria.setText("");
		edtValorDiaria.setForeground(Color.WHITE);
		edtValorDiaria.setFocusable(false);
		edtValorDiaria.setEditable(false);
		edtValorDiaria.setColumns(10);
		edtValorDiaria.setBackground(Color.GRAY);
		edtValorDiaria.setBounds(94, 195, 90, 20);
		pnlReservaVeiculo.add(edtValorDiaria);
		
		JLabel lblValorDiaria = new JLabel("Diária");
		lblValorDiaria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValorDiaria.setBounds(65, 198, 40, 14);
		pnlReservaVeiculo.add(lblValorDiaria);
		
		JLabel lblPercMultaDiaria = new JLabel("Multa diária");
		lblPercMultaDiaria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPercMultaDiaria.setBounds(202, 198, 79, 14);
		pnlReservaVeiculo.add(lblPercMultaDiaria);
		
		edtPercMultaDiaria = new JTextField();
		edtPercMultaDiaria.setText("");
		edtPercMultaDiaria.setForeground(Color.WHITE);
		edtPercMultaDiaria.setFocusable(false);
		edtPercMultaDiaria.setEditable(false);
		edtPercMultaDiaria.setColumns(10);
		edtPercMultaDiaria.setBackground(Color.GRAY);
		edtPercMultaDiaria.setBounds(261, 195, 90, 20);
		pnlReservaVeiculo.add(edtPercMultaDiaria);
		
		edtValorTotalPrev = new JTextField();
		edtValorTotalPrev.setText("");
		edtValorTotalPrev.setForeground(Color.WHITE);
		edtValorTotalPrev.setFocusable(false);
		edtValorTotalPrev.setEditable(false);
		edtValorTotalPrev.setColumns(10);
		edtValorTotalPrev.setBackground(Color.GRAY);
		edtValorTotalPrev.setBounds(94, 220, 90, 20);
		pnlReservaVeiculo.add(edtValorTotalPrev);
		
		edtValorMultaTotal = new JTextField();
		edtValorMultaTotal.setText("");
		edtValorMultaTotal.setForeground(Color.WHITE);
		edtValorMultaTotal.setFocusable(false);
		edtValorMultaTotal.setEditable(false);
		edtValorMultaTotal.setColumns(10);
		edtValorMultaTotal.setBackground(Color.GRAY);
		edtValorMultaTotal.setBounds(261, 245, 90, 20);
		pnlReservaVeiculo.add(edtValorMultaTotal);
		
		edtValorTotal = new JTextField();
		edtValorTotal.setText("");
		edtValorTotal.setForeground(Color.WHITE);
		edtValorTotal.setFocusable(false);
		edtValorTotal.setEditable(false);
		edtValorTotal.setColumns(10);
		edtValorTotal.setBackground(Color.GRAY);
		edtValorTotal.setBounds(261, 270, 90, 20);
		pnlReservaVeiculo.add(edtValorTotal);
		
		JLabel lblValorTotalPrev = new JLabel("Total previsto");
		lblValorTotalPrev.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValorTotalPrev.setBounds(27, 223, 76, 14);
		pnlReservaVeiculo.add(lblValorTotalPrev);
		
		JLabel lblMultaTotal = new JLabel("Multa total");
		lblMultaTotal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMultaTotal.setBounds(206, 248, 79, 14);
		pnlReservaVeiculo.add(lblMultaTotal);
		
		JLabel lblValorTotal = new JLabel("Total");
		lblValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValorTotal.setBounds(233, 273, 41, 14);
		pnlReservaVeiculo.add(lblValorTotal);
		
		edtTotalDias = new JTextField();
		edtTotalDias.setText("");
		edtTotalDias.setForeground(Color.WHITE);
		edtTotalDias.setFocusable(false);
		edtTotalDias.setEditable(false);
		edtTotalDias.setColumns(10);
		edtTotalDias.setBackground(Color.GRAY);
		edtTotalDias.setBounds(94, 245, 90, 20);
		pnlReservaVeiculo.add(edtTotalDias);
		
		JLabel lblTotalDias = new JLabel("Total dias");
		lblTotalDias.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTotalDias.setBounds(45, 248, 57, 14);
		pnlReservaVeiculo.add(lblTotalDias);
		
		edtDiasPrev = new JTextField();
		edtDiasPrev.setText("");
		edtDiasPrev.setForeground(Color.WHITE);
		edtDiasPrev.setFocusable(false);
		edtDiasPrev.setEditable(false);
		edtDiasPrev.setColumns(10);
		edtDiasPrev.setBackground(Color.GRAY);
		edtDiasPrev.setBounds(261, 220, 90, 20);
		pnlReservaVeiculo.add(edtDiasPrev);
		
		JLabel lblDiasPre = new JLabel("Dias previstos");
		lblDiasPre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDiasPre.setBounds(190, 223, 75, 14);
		pnlReservaVeiculo.add(lblDiasPre);
		
		edtDiasAtraso = new JTextField();
		edtDiasAtraso.setText("");
		edtDiasAtraso.setForeground(Color.WHITE);
		edtDiasAtraso.setFocusable(false);
		edtDiasAtraso.setEditable(false);
		edtDiasAtraso.setColumns(10);
		edtDiasAtraso.setBackground(Color.GRAY);
		edtDiasAtraso.setBounds(94, 270, 90, 20);
		pnlReservaVeiculo.add(edtDiasAtraso);
		
		JLabel lblDiasAtraso = new JLabel("Dias de atraso");
		lblDiasAtraso.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDiasAtraso.setBounds(23, 273, 76, 14);
		pnlReservaVeiculo.add(lblDiasAtraso);
		
		limpar();
	}
	
	private void limpar() {
		reservaSelecionada = null;
		clienteSelecionado = null;
		veiculoSelecionado = null;
		edtId.setText("");
		edtId.setEnabled(true);
		btnConsultarReservas.setEnabled(true);
		edtIdCliente.setText(usuarioSistema.isAdministrador() ? "" : String.valueOf(usuarioSistema.getId()));
		edtIdCliente.setEnabled(false);
		edtNomeCliente.setText(usuarioSistema.isAdministrador() ? "" : usuarioSistema.getNome());
		btnConsultarClientes.setEnabled(false);
		edtIdVeiculo.setText("");
		edtIdVeiculo.setEnabled(false);
		edtMarcaModeloVeiculo.setText("");
		btnConsultarVeiculos.setEnabled(false);
		edtDtContratacao.setValue("");
		edtDtContratacao.setEnabled(false);
		edtDtPrevDevolucao.setValue("");
		edtDtPrevDevolucao.setEnabled(false);
		edtDtDevolucao.setValue("");
		edtDtDevolucao.setEnabled(false);

		lostFocusCamposData(null);
		
		rgpStatusReserva.setSelected(rbtStAnd.getModel(), true);

		btnNovo.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnCarregar.setEnabled(true);
		btnRelatorio.setEnabled(false);
		
		
		edtId.requestFocus();
	}
	public void carregar() {
		try {
			if (!edtId.getText().trim().equals("")) {
				reservaSelecionada = reservaController.buscarPorId(Integer.parseInt(edtId.getText()));
			} else {
				JOptionPane.showMessageDialog(null, "Digite um id de reserva.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			if (reservaSelecionada != null) {
				if (!usuarioSistema.isAdministrador() && reservaSelecionada.getCliente().getId() != usuarioSistema.getId()) {
					throw new BusinessRuleException("Não é possível carregar reservas de outro cliente.");
				}
				edtId.setText(String.valueOf(reservaSelecionada.getId()));
				edtId.setEnabled(false);
				btnConsultarReservas.setEnabled(false);
				edtIdCliente.setText(String.valueOf(reservaSelecionada.getCliente().getId()));
				edtIdCliente.setEnabled(false);
				edtNomeCliente.setText(reservaSelecionada.getCliente().getNome());
				btnConsultarClientes.setEnabled(false);
				clienteSelecionado = reservaSelecionada.getCliente();
				edtIdVeiculo.setText(String.valueOf(reservaSelecionada.getVeiculo().getId()));
				edtIdVeiculo.setEnabled(false);
				edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(reservaSelecionada.getVeiculo()));
				btnConsultarVeiculos.setEnabled(false);
				veiculoSelecionado = reservaSelecionada.getVeiculo();

				edtDtContratacao.setValue(ViewUtils.formatarDataParaJFormattedTextField(reservaSelecionada.getDtContratacao()));
				edtDtContratacao.setEnabled(false);
				edtDtPrevDevolucao.setValue(ViewUtils.formatarDataParaJFormattedTextField(reservaSelecionada.getDtPrevDevolucao()));
				edtDtPrevDevolucao.setEnabled(false);
				edtDtDevolucao.setValue(ViewUtils.formatarDataParaJFormattedTextField(reservaSelecionada.getDtDevolucao()));
				edtDtDevolucao.setEnabled(usuarioSistema.isAdministrador() && reservaSelecionada.getStatus() == StatusReserva.EM_ANDAMENTO);
				
				edtValorDiaria.setText(ViewUtils.formatarPrecoEmReais(reservaSelecionada.getValorDiaria()));
				edtPercMultaDiaria.setText(ViewUtils.formatarPercentual(reservaSelecionada.getPercMultaDiaria()));
				edtValorTotalPrev.setText(ViewUtils.formatarPrecoEmReais(reservaSelecionada.getValorTotalPrev()));
				edtDiasPrev.setText(String.valueOf(reservaSelecionada.getDiasPrev()));
				edtTotalDias.setText(String.valueOf(reservaSelecionada.getTotalDias()));
				edtValorMultaTotal.setText(ViewUtils.formatarPrecoEmReais(reservaSelecionada.getMultaTotal()));
				edtDiasAtraso.setText(String.valueOf(reservaSelecionada.getDiasAtraso()));
				edtValorTotal.setText(ViewUtils.formatarPrecoEmReais(reservaSelecionada.getValorTotal()));

				ViewUtils.setRadioGroupStatusReserva(reservaSelecionada.getStatus(), rgpStatusReserva, rbtStAnd, rbtStCan, rbtStPago);
				rbtStAnd.setEnabled(reservaSelecionada.getStatus() == StatusReserva.EM_ANDAMENTO);
				rbtStCan.setEnabled(reservaSelecionada.getStatus() == StatusReserva.EM_ANDAMENTO);
				rbtStPago.setEnabled(usuarioSistema.isAdministrador() && reservaSelecionada.getStatus() != StatusReserva.PAGO);

				btnNovo.setEnabled(true);
				btnSalvar.setEnabled(reservaSelecionada.getStatus() == StatusReserva.EM_ANDAMENTO);
				btnCarregar.setEnabled(false);
				btnRelatorio.setEnabled(true);
			}
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void salvar() {
		if (reservaSelecionada == null) {
			reservaSelecionada = new Reserva(0);
			if (!edtId.getText().trim().equals("")) {
				reservaSelecionada.setId(Integer.parseInt(edtId.getText()));
			}
			reservaSelecionada.setCliente(clienteSelecionado);
			reservaSelecionada.setVeiculo(veiculoSelecionado);
		}
		reservaSelecionada.setStatus(ViewUtils.getStatusReservaFromRadioButton(rgpStatusReserva, rbtStAnd, rbtStCan, rbtStPago));
		
		try {
			reservaSelecionada.setDtContratacao(ViewUtils.formatarDataDeJFormattedTextField(edtDtContratacao, "Data de contratação inválida."));
			reservaSelecionada.setDtPrevDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevDevolucao, "Data prevista de devolução inválida."));
			reservaSelecionada.setDtDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtDevolucao, "Data de devolução inválida."));
			
			reservaSelecionada = reservaController.calcularValoresReserva(reservaSelecionada, reservaSelecionada.getId() == 0);
			
			String msgSucesso;
			if (reservaSelecionada.getId() == 0) {
				reservaSelecionada = reservaController.salvar(reservaSelecionada);
				edtId.setText(String.valueOf(reservaSelecionada.getId()));
				msgSucesso = "Reserva efetuada com sucesso.";
			} else {
				reservaController.editar(reservaSelecionada);
				msgSucesso = "Reserva editada com sucesso.";
			}
			carregar();
			JOptionPane.showMessageDialog(null, msgSucesso);
			if (reservaSelecionada.getStatus() == StatusReserva.PAGO) {
				gerarRelatorio();
			}
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void abrirFrmConsultaUsuarios() {
		FrmConsultaUsuarios frmConsultaUsuarios = new FrmConsultaUsuarios(usuarioSistema, true);
		frmConsultaUsuarios.setVisible(true);
		clienteSelecionado = frmConsultaUsuarios.getUsuarioSelecionado();
		if (clienteSelecionado != null) {
			edtIdCliente.setText(String.valueOf(clienteSelecionado.getId()));
			edtNomeCliente.setText(clienteSelecionado.getNome());
		}
	}
	
	private void abrirFrmConsultaVeiculos() {
		try {
			FrmConsultaVeiculos frmConsultaVeiculos = new FrmConsultaVeiculos(usuarioSistema, true);
			frmConsultaVeiculos.setVisible(true);
			veiculoSelecionado = frmConsultaVeiculos.getVeiculoSelecionado();
			veiculoSelecionado = reservaController.buscarVeiculoPorId(veiculoSelecionado.getId());
			if (veiculoSelecionado != null) {
				edtIdVeiculo.setText(String.valueOf(veiculoSelecionado.getId()));
				edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculoSelecionado));
				edtValorDiaria.setText(ViewUtils.formatarPrecoEmReais(veiculoSelecionado.getCategoria().getValorDiaria()));
				edtPercMultaDiaria.setText(ViewUtils.formatarPercentual(veiculoSelecionado.getCategoria().getPercMultaDiaria()));
			}
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void novo() {
		reservaSelecionada = null;
		edtId.setText("");
		edtId.setEnabled(false);
		btnConsultarReservas.setEnabled(false);
		edtIdCliente.setEnabled(usuarioSistema.isAdministrador());
		btnConsultarClientes.setEnabled(usuarioSistema.isAdministrador());
		edtIdVeiculo.setEnabled(true);
		btnConsultarVeiculos.setEnabled(true);
		edtDtContratacao.setEnabled(true);
		edtDtPrevDevolucao.setEnabled(true);
		edtDtDevolucao.setEnabled(false);
		edtDtDevolucao.setText("");

		rgpStatusReserva.setSelected(rbtStAnd.getModel(), true);
		rbtStAnd.setEnabled(false);
		rbtStCan.setEnabled(false);
		rbtStPago.setEnabled(false);

		btnNovo.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnCarregar.setEnabled(false);
		
		edtIdCliente.requestFocus();
	}
	
	private void sair() {
		setVisible(false);
	}
	
	private void carregarClientePorId() {
		try {
			if (!edtIdCliente.getText().trim().equals("")) {
				clienteSelecionado = reservaController.buscarClientePorId(Integer.parseInt(edtIdCliente.getText()));
				if (clienteSelecionado != null) {
					edtIdCliente.setText(String.valueOf(clienteSelecionado.getId()));
					edtNomeCliente.setText(clienteSelecionado.getNome());
				}
			} else {
				edtIdCliente.setText("");
				edtNomeCliente.setText("");
			}
		} catch (BusinessRuleException ex) {
			edtNomeCliente.setText("");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			edtIdCliente.setText("");
		}
	}
		
	private void abrirFrmConsultaReservas() {
		FrmConsultaReservas frmConsultaReservas = new FrmConsultaReservas(usuarioSistema, true);
		frmConsultaReservas.setVisible(true);
		reservaSelecionada = frmConsultaReservas.getReservaSelecionada();
		if (reservaSelecionada != null) {
			edtId.setText(String.valueOf(reservaSelecionada.getId()));
			carregar();
		}
	}
	
	private void carregarVeiculoPorId() {
		try {
			if (!edtIdVeiculo.getText().trim().equals("")) {
				veiculoSelecionado = reservaController.buscarVeiculoPorId(Integer.parseInt(edtIdVeiculo.getText()));
				if (veiculoSelecionado != null) {
					edtIdVeiculo.setText(String.valueOf(veiculoSelecionado.getId()));
					edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculoSelecionado));
					edtValorDiaria.setText(ViewUtils.formatarPrecoEmReais(veiculoSelecionado.getCategoria().getValorDiaria()));
					edtPercMultaDiaria.setText(ViewUtils.formatarPercentual(veiculoSelecionado.getCategoria().getPercMultaDiaria()));
				}
			} else {
				edtIdVeiculo.setText("");
				edtMarcaModeloVeiculo.setText("");
			}
		} catch (BusinessRuleException ex) {
			edtMarcaModeloVeiculo.setText("");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			edtIdVeiculo.setText("");
		}
	}
	
	private void calcularValoresReserva(Reserva reserva){
		reserva = reservaController.calcularValoresReserva(reserva, reserva.getId() == 0);
		if (reserva == null) {
			edtValorDiaria.setText("");
			edtPercMultaDiaria.setText("");
			edtValorTotalPrev.setText("");
			edtDiasPrev.setText("");
			edtTotalDias.setText("");
			edtValorMultaTotal.setText("");
			edtDiasAtraso.setText("");
			edtValorTotal.setText("");
		} else {
			edtValorDiaria.setText(ViewUtils.formatarPrecoEmReais(reserva.getValorDiaria()));
			edtPercMultaDiaria.setText(ViewUtils.formatarPercentual(reserva.getPercMultaDiaria()));
			edtValorTotalPrev.setText(ViewUtils.formatarPrecoEmReais(reserva.getValorTotalPrev()));
			edtDiasPrev.setText(String.valueOf(reserva.getDiasPrev()));
			edtTotalDias.setText(String.valueOf(reserva.getTotalDias()));
			edtValorMultaTotal.setText(ViewUtils.formatarPrecoEmReais(reserva.getMultaTotal()));
			edtDiasAtraso.setText(String.valueOf(reserva.getDiasAtraso()));
			edtValorTotal.setText(ViewUtils.formatarPrecoEmReais(reserva.getValorTotal()));
		}
	}
	
	private void lostFocusCamposData(JFormattedTextField edtData) {
		if (edtValorDiaria != null) {
			try {
				if (edtData != null) {
					ViewUtils.validarJFormattedTextFieldData(edtData);
				}
				Reserva reserva;
				if (reservaSelecionada == null) {
					reserva = new Reserva(0);
				} else {
					reserva = reservaSelecionada;
				}
				reserva.setVeiculo(veiculoSelecionado);
				reserva.setDtContratacao(ViewUtils.formatarDataDeJFormattedTextField(edtDtContratacao, "Data de contratação inválida."));
				reserva.setDtPrevDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevDevolucao, "Data prevista de devolução inválida."));
				reserva.setDtDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtDevolucao, "Data de devolução inválida."));
				reserva.setStatus(ViewUtils.getStatusReservaFromRadioButton(rgpStatusReserva, rbtStAnd, rbtStCan, rbtStPago));
				calcularValoresReserva(reserva);
			} catch (BusinessRuleException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void gerarRelatorio() {
		try {
			RelatorioReserva.gerarRelatorio(reservaSelecionada);
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
