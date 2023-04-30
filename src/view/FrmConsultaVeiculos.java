package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.exception.BusinessRuleException;
import model.Veiculo;
import model.Usuario;
import view.util.ViewUtils;
import controller.VeiculoController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmConsultaVeiculos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuarioSistema;
	private boolean retornarIdParaParent;
	private Veiculo veiculoSelecionado;
	private JTable tblVeiculos;
	private JTextField edtId;
	private JTextField edtMarca;
	private JTextField edtModelo;
	private JTextField edtAno;
	private JTextField edtCor;
	private final ButtonGroup rgpCategoriaVeiculo = new ButtonGroup();
	private final ButtonGroup rgpStatusVeiculo = new ButtonGroup();
	VeiculoController veiculoController;
	private DefaultTableModel dtm;
	private JButton btnSelecionar = new JButton("Selecionar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnSair = new JButton("Sair");
	private JRadioButton rbtCatTodos = new JRadioButton("Todas");
	private JRadioButton rbtCatPeq = new JRadioButton("Pequeno");
	private JRadioButton rbtCatFam = new JRadioButton("Familia");
	private JRadioButton rbtCatVan = new JRadioButton("Van");
	private JRadioButton rbtStTodos = new JRadioButton("Todos");
	private JRadioButton rbtStRes = new JRadioButton("Reservado");
	private JRadioButton rbtStDisp = new JRadioButton("Disponível");
	private JRadioButton rbtStMan = new JRadioButton("Em manutenção");
	private MaskFormatter mascaraData;
	private JFormattedTextField edtDtInicioRef;
	private JFormattedTextField edtDtFimRef;
	private JCheckBox cbxVerifAtivo = new JCheckBox("Verificar ativo?");
	private JCheckBox cbxAtivo = new JCheckBox("Ativo?");
	private MaskFormatter mascaraPlaca;
	private JFormattedTextField edtPlaca;

	public FrmConsultaVeiculos(Usuario usuarioSistema, boolean retornarIdParaParent) {
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		veiculoSelecionado = null;
		this.retornarIdParaParent = retornarIdParaParent;
		veiculoController = new VeiculoController(usuarioSistema);
		this.usuarioSistema = usuarioSistema;
		
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
		
		JLabel lblConsultaDeVeculos = new JLabel("CONSULTA DE VEÍCULOS");
		lblConsultaDeVeculos.setForeground(Color.WHITE);
		lblConsultaDeVeculos.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblConsultaDeVeculos);

		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.GRAY);
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarEsair();
			}
		});
		
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregar();
			}
		});
		pnlBotoes.add(btnCarregar);
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
		
		JPanel pnlConsultaVeiculos = new JPanel();
		pnlConsultaVeiculos.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlConsultaVeiculos, BorderLayout.CENTER);
		pnlConsultaVeiculos.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setBackground(Color.LIGHT_GRAY);
		pnlConsultaVeiculos.add(pnlFiltro, BorderLayout.NORTH);
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
		edtId.setColumns(10);
		pnlCamposFiltroCima.add(edtId);
		
		JLabel lblMarca = new JLabel("Marca");
		pnlCamposFiltroCima.add(lblMarca);
		
		edtMarca = new JTextField();
		edtMarca.setColumns(10);
		pnlCamposFiltroCima.add(edtMarca);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setHorizontalAlignment(SwingConstants.LEFT);
		pnlCamposFiltroCima.add(lblModelo);
		
		edtModelo = new JTextField();
		edtModelo.setColumns(10);
		pnlCamposFiltroCima.add(edtModelo);
		
		JLabel lblAno = new JLabel("Ano");
		pnlCamposFiltroCima.add(lblAno);
		
		edtAno = new JTextField();
		edtAno.setColumns(10);
		pnlCamposFiltroCima.add(edtAno);
		
		JLabel lblCor = new JLabel("Cor");
		pnlCamposFiltroCima.add(lblCor);
		
		edtCor = new JTextField();
		pnlCamposFiltroCima.add(edtCor);
		edtCor.setColumns(10);
		
		JLabel lblPlaca = new JLabel("Placa");
		pnlCamposFiltroCima.add(lblPlaca);
		
		try {
			mascaraPlaca = new MaskFormatter(ViewUtils.getMascaraCampoPlaca());
		} catch (ParseException e1) {
			mascaraPlaca = null;
		}
		
		edtPlaca = new JFormattedTextField(mascaraPlaca);
		edtPlaca.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldPlaca(edtPlaca);
			}
		});
		edtPlaca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				edtPlaca.setText(edtPlaca.getText().toUpperCase());
			}
		});
		edtPlaca.setText("");
		edtPlaca.setBounds(94, 145, 255, 20);
		pnlCamposFiltroCima.add(edtPlaca);
		
		try {
			mascaraData = new MaskFormatter(ViewUtils.getMascaraCampoData());
		} catch (ParseException e1) {
			mascaraData = null;
		}
		
		JPanel pnlFiltroBaixo = new JPanel();
		pnlFiltroBaixo.setBackground(Color.LIGHT_GRAY);
		pnlFiltro.add(pnlFiltroBaixo, BorderLayout.SOUTH);
		pnlFiltroBaixo.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlCamposFiltroBaixo = new JPanel();
		pnlCamposFiltroBaixo.setBackground(Color.LIGHT_GRAY);
		pnlFiltroBaixo.add(pnlCamposFiltroBaixo, BorderLayout.WEST);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pnlCamposFiltroBaixo.add(lblCategoria);
		
		rgpCategoriaVeiculo.add(rbtCatTodos);
		rbtCatTodos.setSelected(true);
		rbtCatTodos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatTodos.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtCatTodos);
		
		rgpCategoriaVeiculo.add(rbtCatPeq);
		rbtCatPeq.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatPeq.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtCatPeq);
		
		rgpCategoriaVeiculo.add(rbtCatFam);
		rbtCatFam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatFam.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtCatFam);
		
		rgpCategoriaVeiculo.add(rbtCatVan);
		rbtCatVan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatVan.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtCatVan);
		
		JLabel lblStatus = new JLabel("Status");
		pnlCamposFiltroBaixo.add(lblStatus);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		rgpStatusVeiculo.add(rbtStTodos);
		pnlCamposFiltroBaixo.add(rbtStTodos);
		rbtStTodos.setSelected(true);
		rbtStTodos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStTodos.setBackground(Color.LIGHT_GRAY);
		
		rgpStatusVeiculo.add(rbtStRes);
		pnlCamposFiltroBaixo.add(rbtStRes);
		rbtStRes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStRes.setBackground(Color.LIGHT_GRAY);
		
		rgpStatusVeiculo.add(rbtStDisp);
		pnlCamposFiltroBaixo.add(rbtStDisp);
		rbtStDisp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStDisp.setBackground(Color.LIGHT_GRAY);
		
		rgpStatusVeiculo.add(rbtStMan);
		pnlCamposFiltroBaixo.add(rbtStMan);
		rbtStMan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStMan.setBackground(Color.LIGHT_GRAY);
		
		cbxVerifAtivo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbxAtivo.setEnabled(cbxVerifAtivo.isSelected());
			}
		});
		
		JLabel lblPeriodoDeReferncia = new JLabel("Período de referência");
		pnlCamposFiltroBaixo.add(lblPeriodoDeReferncia);
		lblPeriodoDeReferncia.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		edtDtInicioRef = new JFormattedTextField(mascaraData);
		pnlCamposFiltroBaixo.add(edtDtInicioRef);
		edtDtInicioRef.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtInicioRef);
			}
		});
		
		edtDtFimRef = new JFormattedTextField(mascaraData);
		pnlCamposFiltroBaixo.add(edtDtFimRef);
		edtDtFimRef.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtFimRef);
			}
		});
		cbxVerifAtivo.setSelected(true);
		cbxVerifAtivo.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(cbxVerifAtivo);
		
		cbxAtivo.setSelected(true);
		cbxAtivo.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(cbxAtivo);
		
		JPanel pnlCamposFiltroBaixo_1 = new JPanel();
		pnlCamposFiltroBaixo_1.setBackground(Color.LIGHT_GRAY);
		pnlFiltroBaixo.add(pnlCamposFiltroBaixo_1, BorderLayout.CENTER);
		
		tblVeiculos = new JTable();
		tblVeiculos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		            selecionarEsair();
		        }
			}
		});
		tblVeiculos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Marca", "Modelo", "Ano", "Cor", "Categoria", "Placa", "Status", "Ativo?"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblVeiculos.setDefaultEditor(Object.class, null);
		
		JScrollPane barraRolagem = new JScrollPane(tblVeiculos);
		pnlConsultaVeiculos.add(barraRolagem, BorderLayout.CENTER);
		
		limpar();
	}
	
	public Veiculo getVeiculoSelecionado() {
		return veiculoSelecionado;
	}
	
	private void sair() {
		setVisible(false);
	}
	
	private void limpar() {
		edtId.setText("");
		edtMarca.setText("");
		edtModelo.setText("");
		edtAno.setText("");
		edtCor.setText("");
		edtPlaca.setText("");
		rgpCategoriaVeiculo.setSelected(rbtCatTodos.getModel(), true);
		rgpStatusVeiculo.setSelected(rbtStTodos.getModel(), true);
		cbxVerifAtivo.setEnabled(usuarioSistema.isAdministrador());
		cbxVerifAtivo.setSelected(true);
		cbxAtivo.setSelected(true);
		cbxAtivo.setEnabled(usuarioSistema.isAdministrador());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		edtDtInicioRef.setValue(ViewUtils.formatarDataParaJFormattedTextField(new java.sql.Date(cal.getTimeInMillis())));
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		edtDtFimRef.setValue(ViewUtils.formatarDataParaJFormattedTextField(new java.sql.Date(cal.getTimeInMillis())));
		
		dtm = new DefaultTableModel();
        dtm = (DefaultTableModel) tblVeiculos.getModel();

        dtm.setRowCount(0);
        
        edtId.requestFocus();
	}
	
	public void selecionarEsair() {
        if (retornarIdParaParent && tblVeiculos.getSelectedRow() != -1) {
        	veiculoSelecionado = new Veiculo(Integer.parseInt(tblVeiculos.getModel().getValueAt(tblVeiculos.getSelectedRow(), 0).toString()));
        	veiculoSelecionado.setMarca(tblVeiculos.getModel().getValueAt(tblVeiculos.getSelectedRow(), 1).toString());
        	veiculoSelecionado.setModelo(tblVeiculos.getModel().getValueAt(tblVeiculos.getSelectedRow(), 2).toString());
        	veiculoSelecionado.setAno(Integer.parseInt(tblVeiculos.getModel().getValueAt(tblVeiculos.getSelectedRow(), 3).toString()));
        	veiculoSelecionado.setCor(tblVeiculos.getModel().getValueAt(tblVeiculos.getSelectedRow(), 4).toString());
            this.dispose();
        }
    }
	
	private void carregar() {
		int idVeiculo = 0;
		if (!edtId.getText().trim().equals("")) {
			idVeiculo = Integer.parseInt(edtId.getText().trim());
		}
		Veiculo veiculo = new Veiculo(idVeiculo);
		veiculo.setMarca(edtMarca.getText());
		veiculo.setModelo(edtModelo.getText());
		if (!edtAno.getText().trim().equals("")) {
			veiculo.setAno(Integer.parseInt(edtAno.getText()));
		}
		veiculo.setCor(edtCor.getText());
		veiculo.setStatus(ViewUtils.getStatusVeiculoFromRadioButton(rgpStatusVeiculo, rbtStRes, rbtStDisp, rbtStMan));
		veiculo.setCategoria(ViewUtils.getCategoriaVeiculoFromRadioButton(rgpCategoriaVeiculo, rbtCatPeq, rbtCatFam, rbtCatVan));
		veiculo.setPlaca(edtPlaca.getText().replace("-", "").trim());
		veiculo.setAtivo(cbxAtivo.isSelected());
		try {
			Date dataInicioRef = ViewUtils.formatarDataDeJFormattedTextField(edtDtInicioRef, "Data inicial de referência inválida.");
			Date dataFimRef = ViewUtils.formatarDataDeJFormattedTextField(edtDtFimRef, "Data final de referência inválida.");
			List<Veiculo> listaVeiculos = veiculoController.listarVeiculos(veiculo, cbxVerifAtivo.isSelected(), dataInicioRef, dataFimRef, 0, 0);
			Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
			for (Veiculo veiculoLista : listaVeiculos) {
				Vector<Object> registroDb = new Vector<Object>();
				registroDb.add(veiculoLista.getId());
				registroDb.add(veiculoLista.getMarca());
				registroDb.add(veiculoLista.getModelo());
				registroDb.add(veiculoLista.getAno());
				registroDb.add(veiculoLista.getCor());
				registroDb.add(veiculoLista.getCategoria().getCategoria());
				registroDb.add(ViewUtils.formatarPlaca(veiculoLista.getPlaca()));
				registroDb.add(veiculoLista.getStatus().getStatus());
				registroDb.add(veiculoLista.isAtivo() ? "Sim" : "Não");
				
				dados.add(registroDb);
			}
			dtm = (DefaultTableModel) tblVeiculos.getModel();
	        dtm.setRowCount(0);
			for (Vector<?> v : dados) {
	            dtm.addRow(v);
	        }
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

}
