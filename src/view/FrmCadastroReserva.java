package view;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ReservaController;
import controller.exception.BusinessException;
import model.Reserva;
import model.Usuario;
import model.Veiculo;
import view.util.ViewUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarReservas = new JButton("...");
	private JButton btnConsultarClientes = new JButton("...");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JRadioButton rbtStPago = new JRadioButton("Pago");
	private JRadioButton rbtStCan = new JRadioButton("Cancelado");
	private JRadioButton rbtStAnd = new JRadioButton("Em andamento");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCadastroReserva frame = new FrmCadastroReserva(new Usuario(0));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmCadastroReserva(Usuario usuarioSistema) {
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		this.usuarioSistema = usuarioSistema;
		reservaController = new ReservaController(usuarioSistema);
		setBounds(100, 100, 487, 332);
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
				ViewUtils.limparJFormattedTextFieldData(edtDtContratacao);
			}
		});
		edtDtPrevDevolucao = new JFormattedTextField(mascaraData);
		edtDtPrevDevolucao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtPrevDevolucao);
			}
		});
		edtDtDevolucao = new JFormattedTextField(mascaraData);
		edtDtDevolucao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtDevolucao);
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
		btnConsultarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaUsuarios();
			}
		});
		btnConsultarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaVeiculos();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novo();
			}
		});
		
		pnlBotoes.add(btnNovo);

		btnSalvar.setEnabled(false);
		pnlBotoes.add(btnSalvar);
		
		btnExcluir.setEnabled(false);
		pnlBotoes.add(btnExcluir);
		
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
		pnlBotoes.add(btnSair);
		
		JPanel pnlReservaVeiculo = new JPanel();
		pnlReservaVeiculo.setLayout(null);
		pnlReservaVeiculo.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlReservaVeiculo, BorderLayout.CENTER);
		
		rgpStatusReserva.add(rbtStPago);
		rbtStPago.setEnabled(false);
		rbtStPago.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStPago.setBackground(Color.LIGHT_GRAY);
		rbtStPago.setBounds(275, 170, 57, 23);
		pnlReservaVeiculo.add(rbtStPago);

		rgpStatusReserva.add(rbtStCan);
		rbtStCan.setEnabled(false);
		rbtStCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStCan.setBackground(Color.LIGHT_GRAY);
		rbtStCan.setBounds(195, 170, 79, 23);
		pnlReservaVeiculo.add(rbtStCan);

		rgpStatusReserva.add(rbtStAnd);
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
		
		limpar();
	}
	
	private void limpar() {
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
		
		rgpStatusReserva.setSelected(rbtStAnd.getModel(), true);
		
		btnCarregar.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);
		
		edtId.requestFocus();
	}
	public void carregar() {
		Reserva reserva = null;
		try {
			if (!edtId.getText().trim().equals("")) {
				reserva = reservaController.buscarPorId(Integer.parseInt(edtId.getText()));
			} else {
				JOptionPane.showMessageDialog(null, "Digite um id de reserva.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			if (reserva != null) {
				if (!usuarioSistema.isAdministrador() && reserva.getCliente().getId() != usuarioSistema.getId()) {
					throw new BusinessException("Não é possível carregar reservas de outro cliente.");
				}
				edtId.setText(String.valueOf(reserva.getId()));
				edtId.setEnabled(false);
				btnConsultarReservas.setEnabled(false);
				edtIdCliente.setText(String.valueOf(reserva.getCliente().getId()));
				edtIdCliente.setEnabled(usuarioSistema.isAdministrador());
				edtNomeCliente.setText(reserva.getCliente().getNome());
				btnConsultarClientes.setEnabled(true);
				edtIdVeiculo.setText(String.valueOf(reserva.getVeiculo().getId()));
				edtIdVeiculo.setEnabled(true);
				edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(reserva.getVeiculo()));
				btnConsultarVeiculos.setEnabled(true);

				edtDtContratacao.setValue(ViewUtils.formatarDataParaJFormattedTextField(reserva.getDtContratacao()));
				edtDtContratacao.setEnabled(true);
				edtDtPrevDevolucao.setValue(ViewUtils.formatarDataParaJFormattedTextField(reserva.getDtPrevDevolucao()));
				edtDtPrevDevolucao.setEnabled(true);
				edtDtDevolucao.setValue(ViewUtils.formatarDataParaJFormattedTextField(reserva.getDtDevolucao()));
				edtDtDevolucao.setEnabled(usuarioSistema.isAdministrador());

				ViewUtils.setRadioGroupStatusReserva(reserva.getStatus(), rgpStatusReserva, rbtStAnd, rbtStCan, rbtStPago);
				rbtStAnd.setEnabled(true);
				rbtStCan.setEnabled(true);
				rbtStPago.setEnabled(usuarioSistema.isAdministrador());

				btnCarregar.setEnabled(false);
				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(usuarioSistema.isAdministrador());
			}
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void salvar() {
		Reserva reserva = new Reserva(0);
		if (!edtId.getText().trim().equals("")) {
			reserva.setId(Integer.parseInt(edtId.getText()));
		}
		int idCliente = 0;
		if (!edtIdCliente.getText().trim().equals("")) {
			idCliente = Integer.parseInt(edtIdCliente.getText());
		}
		int idVeiculo = 0;
		if (!edtIdVeiculo.getText().trim().equals("")) {
			idVeiculo = Integer.parseInt(edtIdVeiculo.getText());
		}
		reserva.setCliente(new Usuario(idCliente));
		reserva.setVeiculo(new Veiculo(idVeiculo));
		reserva.setStatus(ViewUtils.getStatusReservaFromRadioButton(rgpStatusReserva, rbtStAnd, rbtStCan, rbtStPago));
		
		try {
			reserva.setDtContratacao(ViewUtils.formatarDataDeJFormattedTextField(edtDtContratacao, "Data de contratação inválida."));
			reserva.setDtPrevDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevDevolucao, "Data prevista de devolução inválida."));
			reserva.setDtDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtDevolucao, "Data de devolução inválida."));

			if (reserva.getId() == 0) {
				reserva = reservaController.salvar(reserva);
				edtId.setText(String.valueOf(reserva.getId()));
			} else {
				reservaController.editar(reserva);
			}
			btnNovo.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Reserva efetuada com sucesso.");
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void abrirFrmConsultaUsuarios() {
		FrmConsultaUsuarios frmConsultaUsuarios = new FrmConsultaUsuarios(usuarioSistema, true);
		frmConsultaUsuarios.setVisible(true);
		Usuario usuarioSelecionado = frmConsultaUsuarios.getUsuarioSelecionado();
		if (usuarioSelecionado != null) {
			edtIdCliente.setText(String.valueOf(usuarioSelecionado.getId()));
			edtNomeCliente.setText(usuarioSelecionado.getNome());
		}
	}
	
	private void abrirFrmConsultaVeiculos() {
		FrmConsultaVeiculos frmConsultaVeiculos = new FrmConsultaVeiculos(usuarioSistema, true);
		frmConsultaVeiculos.setVisible(true);
		Veiculo veiculoSelecionado = frmConsultaVeiculos.getVeiculoSelecionado();
		if (veiculoSelecionado != null) {
			edtIdVeiculo.setText(String.valueOf(veiculoSelecionado.getId()));
			edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculoSelecionado));
		}
	}
	
	private void excluir() {
		Object[] options = { "Sim", "Não" };
		int n = JOptionPane.showOptionDialog(null,
						"Tem certeza que deseja excluir esta reserva?",
						"Excluir reserva", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			int id = 0;
			if (!edtId.getText().trim().equals("")) {
				id = Integer.parseInt(edtId.getText());
			}
			try {
				reservaController.excluir(id);
				limpar();
				JOptionPane.showMessageDialog(null, "Reserva excluída com sucesso.");
			} catch (BusinessException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void novo() {
		edtId.setText("");
		edtId.setEnabled(false);
		btnConsultarReservas.setEnabled(false);
		edtIdCliente.setEnabled(usuarioSistema.isAdministrador());
		btnConsultarClientes.setEnabled(true);
		edtIdVeiculo.setEnabled(true);
		btnConsultarVeiculos.setEnabled(true);
		edtDtContratacao.setEnabled(true);
		edtDtPrevDevolucao.setEnabled(true);
		edtDtDevolucao.setEnabled(usuarioSistema.isAdministrador());

		rgpStatusReserva.setSelected(rbtStAnd.getModel(), true);
		rbtStAnd.setEnabled(true);
		rbtStCan.setEnabled(true);
		rbtStPago.setEnabled(usuarioSistema.isAdministrador());

		btnCarregar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnNovo.setEnabled(false);
		btnExcluir.setEnabled(false);
		
		edtIdCliente.requestFocus();
	}
	
	private void sair() {
		setVisible(false);
	}
	
	private void carregarClientePorId() {
		Usuario cliente = null;
		try {
			if (!edtIdCliente.getText().trim().equals("")) {
				cliente = reservaController.buscarClientePorId(Integer.parseInt(edtIdCliente.getText()));
				if (cliente != null) {
					edtIdCliente.setText(String.valueOf(cliente.getId()));
					edtNomeCliente.setText(cliente.getNome());
				}
			} else {
				edtNomeCliente.setText("");
			}
		} catch (BusinessException ex) {
			edtNomeCliente.setText("");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			edtIdCliente.setText("");
		}
	}
	
	private void abrirFrmConsultaReservas() {
		FrmConsultaReservas frmConsultaReservas = new FrmConsultaReservas(usuarioSistema, true);
		frmConsultaReservas.setVisible(true);
		Reserva reservaSelecionada = frmConsultaReservas.getReservaSelecionada();
		if (reservaSelecionada != null) {
			edtId.setText(String.valueOf(reservaSelecionada.getId()));
			carregar();
		}
	}
	
	private void carregarVeiculoPorId() {
		Veiculo veiculo = null;
		try {
			if (!edtIdVeiculo.getText().trim().equals("")) {
				veiculo = reservaController.buscarVeiculoPorId(Integer.parseInt(edtIdVeiculo.getText()));
				if (veiculo != null) {
					edtIdVeiculo.setText(String.valueOf(veiculo.getId()));
					edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculo));
				}
			} else {
				edtMarcaModeloVeiculo.setText("");
			}
		} catch (BusinessException ex) {
			edtMarcaModeloVeiculo.setText("");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			edtIdVeiculo.setText("");
		}
	}
	
}
