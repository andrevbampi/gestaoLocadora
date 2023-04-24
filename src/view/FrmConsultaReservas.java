package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ReservaController;
import controller.exception.BusinessException;
import model.Usuario;
import model.Veiculo;
import view.util.ViewUtils;
import model.Reserva;

import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmConsultaReservas extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblReservas;
	private JTextField edtId;
	private JTextField edtIdCliente;
	private JTextField edtIdVeiculo;
	private JTextField edtNomeCliente;
	private JTextField edtMarcaModeloVeiculo;
	private final ButtonGroup rgpStatusReserva = new ButtonGroup();
	private JFormattedTextField edtDtContratacaoIni;
	private JFormattedTextField edtDtContratacaoFim;
	private JFormattedTextField edtDtPrevDevolucaoIni;
	private JFormattedTextField edtDtPrevDevolucaoFim;
	private JFormattedTextField edtDtDevolucaoIni;
	private JFormattedTextField edtDtDevolucaoFim;
	private MaskFormatter mascaraData;
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSelecionar = new JButton("Selecionar");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarClientes = new JButton("...");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JRadioButton rbtStTodos = new JRadioButton("Todos");
	private JRadioButton rbtStAnd = new JRadioButton("Em andamento");
	private JRadioButton rbtStCan = new JRadioButton("Cancelado");
	private JRadioButton rbtStPago = new JRadioButton("Pago");
	private Usuario usuarioSistema;
	private boolean retornarIdParaParent;
	private Reserva reservaSelecionada;
	private ReservaController reservaController;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmConsultaReservas frame = new FrmConsultaReservas(new Usuario(0), false);
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
	public FrmConsultaReservas(Usuario usuarioSistema, boolean retornarIdParaParent) {
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		reservaSelecionada = null;
		this.retornarIdParaParent = retornarIdParaParent;
		this.usuarioSistema = usuarioSistema;
		reservaController = new ReservaController(usuarioSistema);
		setBounds(100, 100, 988, 550);
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
		
		JLabel lblConsultaDeReservas = new JLabel("CONSULTA DE RESERVAS DE VEÍCULOS");
		lblConsultaDeReservas.setForeground(Color.WHITE);
		lblConsultaDeReservas.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblConsultaDeReservas);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.GRAY);
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregar();
			}
		});
		
		pnlBotoes.add(btnCarregar);
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarEsair();
			}
		});
		
		btnSelecionar.setEnabled(retornarIdParaParent);
		pnlBotoes.add(btnSelecionar);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		pnlBotoes.add(btnLimpar);
		
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		pnlBotoes.add(btnSair);
		
		JPanel pnlConsultaReservas = new JPanel();
		pnlConsultaReservas.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlConsultaReservas, BorderLayout.CENTER);
		pnlConsultaReservas.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setBackground(Color.LIGHT_GRAY);
		pnlConsultaReservas.add(pnlFiltro, BorderLayout.NORTH);
		pnlFiltro.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlFiltroCima = new JPanel();
		pnlFiltroCima.setBackground(Color.LIGHT_GRAY);
		pnlFiltro.add(pnlFiltroCima, BorderLayout.NORTH);
		pnlFiltroCima.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlCamposFiltroCima = new JPanel();
		pnlCamposFiltroCima.setBackground(Color.LIGHT_GRAY);
		pnlFiltroCima.add(pnlCamposFiltroCima, BorderLayout.WEST);
		
		JLabel lblId = new JLabel("Id");
		pnlCamposFiltroCima.add(lblId);
		
		edtId = new JTextField();
		edtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
			}
		});
		edtId.setColumns(10);
		pnlCamposFiltroCima.add(edtId);
		
		JLabel lblIdCliente = new JLabel("Cliente");
		pnlCamposFiltroCima.add(lblIdCliente);
		
		edtIdCliente = new JTextField();
		edtIdCliente.setEnabled(false);
		edtIdCliente.setColumns(10);
		pnlCamposFiltroCima.add(edtIdCliente);
		btnConsultarClientes.setEnabled(false);
		btnConsultarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaUsuarios();
			}
		});
		pnlCamposFiltroCima.add(btnConsultarClientes);
		
		edtNomeCliente = new JTextField();
		edtNomeCliente.setForeground(Color.WHITE);
		edtNomeCliente.setFocusable(false);
		edtNomeCliente.setEditable(false);
		edtNomeCliente.setColumns(10);
		edtNomeCliente.setBackground(Color.GRAY);
		pnlCamposFiltroCima.add(edtNomeCliente);
		
		JLabel lblIdVeiculo = new JLabel("Veículo");
		lblIdVeiculo.setHorizontalAlignment(SwingConstants.LEFT);
		pnlCamposFiltroCima.add(lblIdVeiculo);
		
		edtIdVeiculo = new JTextField();
		edtIdVeiculo.setColumns(10);
		pnlCamposFiltroCima.add(edtIdVeiculo);
		btnConsultarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmConsultaVeiculos();
			}
		});
		pnlCamposFiltroCima.add(btnConsultarVeiculos);
		
		edtMarcaModeloVeiculo = new JTextField();
		edtMarcaModeloVeiculo.setForeground(Color.WHITE);
		edtMarcaModeloVeiculo.setFocusable(false);
		edtMarcaModeloVeiculo.setEditable(false);
		edtMarcaModeloVeiculo.setColumns(10);
		edtMarcaModeloVeiculo.setBackground(Color.GRAY);
		pnlCamposFiltroCima.add(edtMarcaModeloVeiculo);
		
		JPanel pnlFiltroBaixo = new JPanel();
		pnlFiltroBaixo.setBackground(Color.LIGHT_GRAY);
		pnlFiltro.add(pnlFiltroBaixo, BorderLayout.SOUTH);
		pnlFiltroBaixo.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlCamposFiltroBaixo = new JPanel();
		pnlCamposFiltroBaixo.setBackground(Color.LIGHT_GRAY);
		pnlFiltroBaixo.add(pnlCamposFiltroBaixo, BorderLayout.WEST);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pnlCamposFiltroBaixo.add(lblStatus);
		
		rgpStatusReserva.add(rbtStTodos);
		rbtStTodos.setSelected(true);
		rbtStTodos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStTodos.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStTodos);
		
		rgpStatusReserva.add(rbtStAnd);
		rbtStAnd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStAnd.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStAnd);
		
		rgpStatusReserva.add(rbtStCan);
		rbtStCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStCan.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStCan);
		
		rgpStatusReserva.add(rbtStPago);
		rbtStPago.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStPago.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStPago);
		
		JLabel lblDtContatacao = new JLabel("Contratação");
		pnlCamposFiltroBaixo.add(lblDtContatacao);
		
		try {
			mascaraData = new MaskFormatter(ViewUtils.getMascaraCampoData());
		} catch (ParseException e1) {
			mascaraData = null;
		}
		
		edtDtContratacaoIni = new JFormattedTextField(mascaraData);
		edtDtContratacaoIni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtContratacaoIni);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtContratacaoIni);
		
		edtDtContratacaoFim = new JFormattedTextField(mascaraData);
		edtDtContratacaoFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtContratacaoFim);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtContratacaoFim);
		
		JLabel lblDtPrevDevolucao = new JLabel("Previsão");
		pnlCamposFiltroBaixo.add(lblDtPrevDevolucao);
		
		edtDtPrevDevolucaoIni = new JFormattedTextField(mascaraData);
		edtDtPrevDevolucaoIni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtPrevDevolucaoIni);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtPrevDevolucaoIni);
		
		edtDtPrevDevolucaoFim = new JFormattedTextField(mascaraData);
		edtDtPrevDevolucaoFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtPrevDevolucaoFim);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtPrevDevolucaoFim);
		
		JLabel lblDtDevolucao = new JLabel("Devolução");
		pnlCamposFiltroBaixo.add(lblDtDevolucao);
		
		edtDtDevolucaoIni = new JFormattedTextField(mascaraData);
		edtDtDevolucaoIni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtDevolucaoIni);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtDevolucaoIni);
		
		edtDtDevolucaoFim = new JFormattedTextField(mascaraData);
		edtDtDevolucaoFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtDevolucaoFim);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtDevolucaoFim);
		
		tblReservas = new JTable();
		tblReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		            selecionarEsair();
		        }
			}
		});
		tblReservas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Cliente", "Ve\u00EDculo", "Status", "Contrata\u00E7\u00E3o", "Pevis\u00E3o", "Devolu\u00E7\u00E3o"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblReservas.setDefaultEditor(Object.class, null);
		JScrollPane barraRolagem = new JScrollPane(tblReservas);
		pnlConsultaReservas.add(barraRolagem, BorderLayout.CENTER);
		
		limpar();
	}
	
	public Reserva getReservaSelecionada() {
		return reservaSelecionada;
	}

	public void selecionarEsair() {
        if (retornarIdParaParent && tblReservas.getSelectedRow() != -1) {
        	reservaSelecionada = new Reserva(Integer.parseInt(tblReservas.getModel().getValueAt(tblReservas.getSelectedRow(), 0).toString()));
            this.dispose();
        }
    }
	
	private void limpar() {
		edtId.setText("");
		edtIdCliente.setText(usuarioSistema.isAdministrador() ? "" : String.valueOf(usuarioSistema.getId()));
		edtIdCliente.setEnabled(usuarioSistema.isAdministrador());
		edtNomeCliente.setText(usuarioSistema.isAdministrador() ? "" : usuarioSistema.getNome());
		edtIdVeiculo.setText("");
		edtMarcaModeloVeiculo.setText("");
		edtDtContratacaoIni.setValue("");
		edtDtContratacaoFim.setValue("");
		edtDtPrevDevolucaoIni.setValue("");
		edtDtPrevDevolucaoFim.setValue("");
		edtDtDevolucaoIni.setValue("");
		edtDtDevolucaoFim.setValue("");
		rgpStatusReserva.setSelected(rbtStTodos.getModel(), true);

		dtm = new DefaultTableModel();
        dtm = (DefaultTableModel) tblReservas.getModel();

        dtm.setRowCount(0);

		edtId.requestFocus();
	}

	private void carregar() {
		int idReserva = 0;
		if (!edtId.getText().trim().equals("")) {
			idReserva = Integer.parseInt(edtId.getText().trim());
		}
		Reserva reserva = new Reserva(idReserva);
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
			reserva.setDtContratacao(ViewUtils.formatarDataDeJFormattedTextField(edtDtContratacaoIni, "Data inicial de contratação inválida."));
			Date dtContratacaoFim = ViewUtils.formatarDataDeJFormattedTextField(edtDtContratacaoFim, "Data final de contratação inválida.");
			reserva.setDtPrevDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevDevolucaoIni, "Data inicial prevista de devolução inválida."));
			Date dtPrevDevolucaoFim = ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevDevolucaoFim, "Data final prevista de devolução inválida.");
			reserva.setDtDevolucao(ViewUtils.formatarDataDeJFormattedTextField(edtDtDevolucaoIni, "Data inicial de devolução inválida."));
			Date dtDevolucaoFim = ViewUtils.formatarDataDeJFormattedTextField(edtDtDevolucaoFim, "Data inicial de devolução inválida.");

			List<Reserva> listaReservas = reservaController.listarReservas(reserva, dtContratacaoFim, dtPrevDevolucaoFim, dtDevolucaoFim);
			Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
			for (Reserva reservaLista : listaReservas) {
				Vector<Object> registroDb = new Vector<Object>();
				registroDb.add(reservaLista.getId());
				registroDb.add(reservaLista.getCliente().getId() + " " + reservaLista.getCliente().getNome());
				registroDb.add(reservaLista.getVeiculo().getId() + " " + ViewUtils.formatarDescricaoVeiculo(reservaLista.getVeiculo()));
				registroDb.add(reservaLista.getStatus().getStatus());
				registroDb.add(ViewUtils.formatarDataParaJFormattedTextField(reservaLista.getDtContratacao()));
				registroDb.add(ViewUtils.formatarDataParaJFormattedTextField(reservaLista.getDtPrevDevolucao()));
				registroDb.add(ViewUtils.formatarDataParaJFormattedTextField(reservaLista.getDtDevolucao()));
				dados.add(registroDb);
			}
			
			dtm = (DefaultTableModel) tblReservas.getModel();
	        dtm.setRowCount(0);
			for (Vector<?> v : dados) {
	            dtm.addRow(v);
	        }
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
	
	private void FrmConsultaVeiculos() {
		FrmConsultaVeiculos frmConsultaVeiculos = new FrmConsultaVeiculos(usuarioSistema, true);
		frmConsultaVeiculos.setVisible(true);
		Veiculo veiculoSelecionado = frmConsultaVeiculos.getVeiculoSelecionado();
		if (veiculoSelecionado != null) {
			edtIdVeiculo.setText(String.valueOf(veiculoSelecionado.getId()));
			edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculoSelecionado));
		}
	}

}
