package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JTable;

import model.Usuario;
import view.util.ViewUtils;
import controller.UsuarioController;
import controller.exception.BusinessRuleException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FrmConsultaUsuarios extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblUsuarios;
	private Usuario usuarioSistema;
	private boolean retornarIdParaParent;
	UsuarioController usuarioController;
	private DefaultTableModel dtm;
	private final ButtonGroup rgpGenero = new ButtonGroup();
	private Usuario usuarioSelecionado;
	private JTextField edtId;
	private JTextField edtLogin;
	private JTextField edtNome;
	private JCheckBox cbxVerifAdministrador = new JCheckBox("Verificar administrador?");
	private JCheckBox cbxAdministrador = new JCheckBox("Administrador?");
	private JCheckBox cbxVerifAtivo = new JCheckBox("Verificar ativo?");
	private JCheckBox cbxAtivo = new JCheckBox("Ativo?");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnSelecionar = new JButton("Selecionar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSair = new JButton("Sair");
	private JRadioButton rbtGenT = new JRadioButton("Todos");
	private JRadioButton rbtGenM = new JRadioButton("Masculino");
	private JRadioButton rbtGenF = new JRadioButton("Feminino");
	private JRadioButton rbtGenO = new JRadioButton("Outro");

	public FrmConsultaUsuarios(Usuario usuarioSistema, boolean retornarIdParaParent) {
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		usuarioSelecionado = null;
		this.usuarioSistema = usuarioSistema;
		this.retornarIdParaParent = retornarIdParaParent;
		usuarioController = new UsuarioController(usuarioSistema);
		
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
		
		JLabel lblCadastroDeUsurio = new JLabel("CONSULTA DE USUÁRIOS");
		lblCadastroDeUsurio.setForeground(Color.WHITE);
		lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblCadastroDeUsurio);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.GRAY);
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		
		JPanel pnlConsultaUsuarios = new JPanel();
		pnlConsultaUsuarios.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlConsultaUsuarios, BorderLayout.CENTER);
		pnlConsultaUsuarios.setLayout(new BorderLayout(0, 0));
		
		tblUsuarios = new JTable();
		tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
		            selecionarEsair();
		        }
			}
		});
		tblUsuarios.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Login", "Nome", "G\u00EAnero", "Telefone", "Administrador?", "Ativo?"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblUsuarios.setDefaultEditor(Object.class, null);
		JScrollPane barraRolagem = new JScrollPane(tblUsuarios);
		pnlConsultaUsuarios.add(barraRolagem, BorderLayout.CENTER);
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setBackground(new Color(192, 192, 192));
		pnlConsultaUsuarios.add(pnlFiltro, BorderLayout.NORTH);
		pnlFiltro.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlFiltroCima = new JPanel();
		pnlFiltroCima.setBackground(new Color(192, 192, 192));
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
		
		JLabel lblLogin = new JLabel("Login");
		pnlCamposFiltroCima.add(lblLogin);
		
		edtLogin = new JTextField();
		edtLogin.setColumns(10);
		pnlCamposFiltroCima.add(edtLogin);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		pnlCamposFiltroCima.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		pnlCamposFiltroCima.add(edtNome);
		cbxVerifAtivo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbxAtivo.setEnabled(cbxVerifAtivo.isSelected());
			}
		});

		cbxVerifAtivo.setSelected(true);
		cbxVerifAtivo.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroCima.add(cbxVerifAtivo);
		cbxAtivo.setSelected(true);
		cbxAtivo.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroCima.add(cbxAtivo);
		
		JPanel pnlFiltroBaixo = new JPanel();
		pnlFiltroBaixo.setBackground(new Color(192, 192, 192));
		pnlFiltro.add(pnlFiltroBaixo, BorderLayout.SOUTH);
		pnlFiltroBaixo.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlCamposFiltroBaixo = new JPanel();
		pnlCamposFiltroBaixo.setBackground(new Color(192, 192, 192));
		pnlFiltroBaixo.add(pnlCamposFiltroBaixo, BorderLayout.WEST);
		
		JLabel lblGenero = new JLabel("Gênero");
		lblGenero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pnlCamposFiltroBaixo.add(lblGenero);
		
		rgpGenero.add(rbtGenT);
		rbtGenT.setSelected(true);
		rbtGenT.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenT.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtGenT);
		
		rgpGenero.add(rbtGenM);
		rbtGenM.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenM.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtGenM);
		
		rgpGenero.add(rbtGenF);
		rbtGenF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenF.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtGenF);
		
		rgpGenero.add(rbtGenO);
		rbtGenO.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenO.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(rbtGenO);
		cbxVerifAdministrador.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cbxAdministrador.setEnabled(cbxVerifAdministrador.isSelected());
			}
		});
		
		cbxVerifAdministrador.setBackground(new Color(192, 192, 192));
		pnlCamposFiltroBaixo.add(cbxVerifAdministrador);
		
		cbxAdministrador.setEnabled(false);
		cbxAdministrador.setBackground(Color.LIGHT_GRAY);
		pnlCamposFiltroBaixo.add(cbxAdministrador);
		
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		btnSelecionar.setEnabled(retornarIdParaParent);
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
		pnlBotoes.add(btnSelecionar);
		pnlBotoes.add(btnLimpar);
		
		btnSair.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sair();
				}
			}
		});
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnlBotoes.add(btnSair);
	}
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	
	private void sair() {
		setVisible(false);
	}
	
	private void limpar() {
		edtId.setText("");
		edtLogin.setText("");
		edtNome.setText("");
		rgpGenero.setSelected(rbtGenT.getModel(), true);
		cbxVerifAdministrador.setSelected(false);
		cbxAdministrador.setSelected(false);
		cbxAdministrador.setEnabled(false);
		cbxVerifAtivo.setSelected(true);
		cbxAtivo.setSelected(true);
		cbxAtivo.setEnabled(true);
		
		dtm = new DefaultTableModel();
        dtm = (DefaultTableModel) tblUsuarios.getModel();

        dtm.setRowCount(0);
        
        edtId.requestFocus();
	}
	
	public void selecionarEsair() {
        if (retornarIdParaParent && tblUsuarios.getSelectedRow() != -1) {
        	usuarioSelecionado = new Usuario(Integer.parseInt(tblUsuarios.getModel().getValueAt(tblUsuarios.getSelectedRow(), 0).toString()));
            usuarioSelecionado.setNome(tblUsuarios.getModel().getValueAt(tblUsuarios.getSelectedRow(), 2).toString());
            this.dispose();
        }
    }
	
	private void carregar() {
		int idUsuario = 0;
		if (!edtId.getText().trim().equals("")) {
			idUsuario = Integer.parseInt(edtId.getText().trim());
		}
		Usuario usuario = new Usuario(idUsuario);
		usuario.setLogin(edtLogin.getText());
		usuario.setNome(edtNome.getText());
		usuario.setGenero(ViewUtils.getGeneroFromRadioButton(rgpGenero, rbtGenM, rbtGenF, rbtGenO));
		usuario.setAdministrador(cbxAdministrador.isSelected());
		usuario.setAtivo(cbxAtivo.isSelected());
		try {
			List<Usuario> listaUsuario = usuarioController.listarUsuarios(usuario, cbxVerifAdministrador.isSelected(), cbxVerifAtivo.isSelected());
			Vector<Vector<Object>> dados = new Vector<Vector<Object>>();
			for (Usuario usuarioLista : listaUsuario) {
				Vector<Object> registroDb = new Vector<Object>();
				registroDb.add(usuarioLista.getId());
				registroDb.add(usuarioLista.getLogin());
				registroDb.add(usuarioLista.getNome());
				registroDb.add(usuarioLista.getGenero().getGenero());
				registroDb.add(usuarioLista.getTelefone());
				registroDb.add(usuarioLista.isAdministrador() ? "Sim" : "Não");
				registroDb.add(usuarioLista.isAtivo() ? "Sim" : "Não");
				dados.add(registroDb);
			}
			dtm = (DefaultTableModel) tblUsuarios.getModel();
	        dtm.setRowCount(0);
			for (Vector<?> v : dados) {
	            dtm.addRow(v);
	        }
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
