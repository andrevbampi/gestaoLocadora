package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Manutencao;
import model.Usuario;
import model.Veiculo;
import view.util.ViewUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ManutencaoController;
import controller.exception.BusinessRuleException;

import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmConsultaManutencoes extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuarioSistema;
	private boolean retornarIdParaParent;
	private JTextField edtId;
	private JTextField edtDescricao;
	private JTextField edtIdVeiculo;
	private JTextField edtMarcaModeloVeiculo;
	private JTable tblManutencoes;
	private MaskFormatter mascaraData;
	private final ButtonGroup rgpStatusManutencao = new ButtonGroup();
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSelecionar = new JButton("Selecionar");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JRadioButton rbtStManTodos = new JRadioButton("Todos");
	private JRadioButton rbtStManAbt = new JRadioButton("Em aberto");
	private JRadioButton rbtStManCan = new JRadioButton("Cancelada");
	private JRadioButton rbtStManCon = new JRadioButton("Concluída");
	private JFormattedTextField edtDtInicioIni;
	private JFormattedTextField edtDtInicioFim;
	private JFormattedTextField edtDtPrevFimIni;
	private JFormattedTextField edtDtPrevFimFim;
	private JFormattedTextField edtDtFimIni;
	private JFormattedTextField edtDtFimFim;
	private Manutencao manutencaoSelecionada;
	private DefaultTableModel dtm;
	private ManutencaoController manutencaoController;

	public FrmConsultaManutencoes(Usuario usuarioSistema, boolean retornarIdParaParent) {
		setModal(true);
		manutencaoSelecionada = null;
		this.usuarioSistema = usuarioSistema;
		this.retornarIdParaParent = retornarIdParaParent;
		manutencaoController = new ManutencaoController(usuarioSistema);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height-40);
		
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
		
		JLabel lblConsultaDeManutenes = new JLabel("CONSULTA DE MANUTENÇÔES");
		lblConsultaDeManutenes.setForeground(Color.WHITE);
		lblConsultaDeManutenes.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblConsultaDeManutenes);
		
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
				sair();
			}
		});
		
		pnlBotoes.add(btnSair);
		
		JPanel pnlConsultaManutencoes = new JPanel();
		pnlConsultaManutencoes.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlConsultaManutencoes, BorderLayout.CENTER);
		pnlConsultaManutencoes.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setBackground(Color.LIGHT_GRAY);
		pnlConsultaManutencoes.add(pnlFiltro, BorderLayout.NORTH);
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
		edtId.setText("");
		edtId.setColumns(10);
		pnlCamposFiltroCima.add(edtId);
		
		JLabel lblIdVeiculo = new JLabel("Veículo");
		lblIdVeiculo.setHorizontalAlignment(SwingConstants.LEFT);
		pnlCamposFiltroCima.add(lblIdVeiculo);
		
		edtIdVeiculo = new JTextField();
		edtIdVeiculo.setText("");
		edtIdVeiculo.setColumns(10);
		pnlCamposFiltroCima.add(edtIdVeiculo);
		btnConsultarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaVeiculos();
			}
		});
		
		pnlCamposFiltroCima.add(btnConsultarVeiculos);
		
		edtMarcaModeloVeiculo = new JTextField();
		edtMarcaModeloVeiculo.setText("");
		edtMarcaModeloVeiculo.setForeground(Color.WHITE);
		edtMarcaModeloVeiculo.setFocusable(false);
		edtMarcaModeloVeiculo.setEditable(false);
		edtMarcaModeloVeiculo.setColumns(10);
		edtMarcaModeloVeiculo.setBackground(Color.GRAY);
		pnlCamposFiltroCima.add(edtMarcaModeloVeiculo);
		
		JLabel lblDescricao = new JLabel("Descrição");
		pnlCamposFiltroCima.add(lblDescricao);
		
		edtDescricao = new JTextField();
		edtDescricao.setEnabled(false);
		edtDescricao.setColumns(10);
		pnlCamposFiltroCima.add(edtDescricao);
		
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
		
		rgpStatusManutencao.add(rbtStManTodos);
		rbtStManTodos.setSelected(true);
		rbtStManTodos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManTodos.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStManTodos);
		
		rgpStatusManutencao.add(rbtStManAbt);
		rbtStManAbt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManAbt.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStManAbt);
		
		rgpStatusManutencao.add(rbtStManCan);
		rbtStManCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManCan.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStManCan);
		
		rgpStatusManutencao.add(rbtStManCon);
		rbtStManCon.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManCon.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtStManCon);
		
		JLabel lblDtInicio = new JLabel("Início");
		pnlCamposFiltroBaixo.add(lblDtInicio);
		
		try {
			mascaraData = new MaskFormatter(ViewUtils.getMascaraCampoData());
		} catch (ParseException e1) {
			mascaraData = null;
		}
		
		edtDtInicioIni = new JFormattedTextField(mascaraData);
		edtDtInicioIni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtInicioIni);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtInicioIni);
		
		edtDtInicioFim = new JFormattedTextField(mascaraData);
		edtDtInicioFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtInicioFim);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtInicioFim);
		
		JLabel lblDtPrevFim = new JLabel("Previsão");
		pnlCamposFiltroBaixo.add(lblDtPrevFim);
		
		edtDtPrevFimIni = new JFormattedTextField(mascaraData);
		edtDtPrevFimIni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtPrevFimIni);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtPrevFimIni);
		
		edtDtPrevFimFim = new JFormattedTextField(mascaraData);
		edtDtPrevFimFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtPrevFimFim);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtPrevFimFim);
		
		JLabel lblDtDevolucao = new JLabel("Devolução");
		pnlCamposFiltroBaixo.add(lblDtDevolucao);
		
		edtDtFimIni = new JFormattedTextField(mascaraData);
		edtDtFimIni.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtFimIni);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtFimIni);
		
		edtDtFimFim = new JFormattedTextField(mascaraData);
		edtDtFimFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtFimFim);
			}
		});
		pnlCamposFiltroBaixo.add(edtDtFimFim);
		
		tblManutencoes = new JTable();
		tblManutencoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		            selecionarEsair();
		        }
			}
		});
		tblManutencoes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Descri\u00E7\u00E3o", "Ve\u00EDculo", "Status", "In\u00EDcio", "Previs\u00E3o", "T\u00E9rmino"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblManutencoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblManutencoes.setDefaultEditor(Object.class, null);
		JScrollPane barraRolagem = new JScrollPane(tblManutencoes);
		pnlConsultaManutencoes.add(barraRolagem, BorderLayout.CENTER);
		
		limpar();
	}
	
	public Manutencao getManutencaoSelecionada() {
		return manutencaoSelecionada;
	}

	public void selecionarEsair() {
        if (retornarIdParaParent && tblManutencoes.getSelectedRow() != -1) {
        	manutencaoSelecionada = new Manutencao(Integer.parseInt(tblManutencoes.getModel().getValueAt(tblManutencoes.getSelectedRow(), 0).toString()));
            this.dispose();
        }
    }
	
	private void limpar() {
		edtId.setText("");
		edtIdVeiculo.setText("");
		edtMarcaModeloVeiculo.setText("");
		edtDescricao.setText("");
		edtDtInicioIni.setValue("");
		edtDtInicioFim.setValue("");
		edtDtPrevFimIni.setValue("");
		edtDtPrevFimFim.setValue("");
		edtDtFimIni.setValue("");
		edtDtFimFim.setValue("");
		rgpStatusManutencao.setSelected(rbtStManTodos.getModel(), true);

		dtm = new DefaultTableModel();
        dtm = (DefaultTableModel) tblManutencoes.getModel();

        dtm.setRowCount(0);

		edtId.requestFocus();
	}

	private void carregar() {
		int idManutencao = 0;
		if (!edtId.getText().trim().equals("")) {
			idManutencao = Integer.parseInt(edtId.getText().trim());
		}
		Manutencao manutencao = new Manutencao(idManutencao);
		int idVeiculo = 0;
		if (!edtIdVeiculo.getText().trim().equals("")) {
			idVeiculo = Integer.parseInt(edtIdVeiculo.getText());
		}
		manutencao.setVeiculo(new Veiculo(idVeiculo));
		manutencao.setDescricao(edtDescricao.getText());
		manutencao.setStatus(ViewUtils.getStatusManutencaoFromRadioButton(rgpStatusManutencao, rbtStManAbt, rbtStManCan, rbtStManCon));
		
		try {
			manutencao.setDtInicio(ViewUtils.formatarDataDeJFormattedTextField(edtDtInicioIni, "Data inicial de início inválida."));
			Date dtInicioFim = ViewUtils.formatarDataDeJFormattedTextField(edtDtInicioFim, "Data final de início inválida.");
			manutencao.setDtPrevFim(ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevFimIni, "Data inicial prevista de término inválida."));
			Date dtPrevFimFim = ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevFimFim, "Data final prevista de término inválida.");
			manutencao.setDtFim(ViewUtils.formatarDataDeJFormattedTextField(edtDtFimIni, "Data inicial de término inválida."));
			Date dtFimFim = ViewUtils.formatarDataDeJFormattedTextField(edtDtFimFim, "Data inicial de término inválida.");

			List<Manutencao> listaManutencoes = manutencaoController.listarManutencoes(manutencao, dtInicioFim, dtPrevFimFim, dtFimFim);
			Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
			for (Manutencao manutencaoLista : listaManutencoes) {
				Vector<Object> registroDb = new Vector<Object>();
				registroDb.add(manutencaoLista.getId());
				registroDb.add(manutencaoLista.getVeiculo().getId() + " " + ViewUtils.formatarDescricaoVeiculo(manutencaoLista.getVeiculo()));
				registroDb.add(manutencaoLista.getDescricao());
				registroDb.add(manutencaoLista.getStatus().getStatus());
				registroDb.add(ViewUtils.formatarDataParaJFormattedTextField(manutencaoLista.getDtInicio()));
				registroDb.add(ViewUtils.formatarDataParaJFormattedTextField(manutencaoLista.getDtPrevFim()));
				registroDb.add(ViewUtils.formatarDataParaJFormattedTextField(manutencaoLista.getDtFim()));
				dados.add(registroDb);
			}
			
			dtm = (DefaultTableModel) tblManutencoes.getModel();
	        dtm.setRowCount(0);
			for (Vector<?> v : dados) {
	            dtm.addRow(v);
	        }
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
	
	private void sair() {
		setVisible(false);
	}

}
