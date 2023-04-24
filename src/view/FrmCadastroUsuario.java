package view;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.UsuarioController;
import controller.EnderecoController;
import controller.exception.BusinessException;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Usuario;
import view.util.ViewUtils;
import model.Endereco;
import model.GeneroUsuario;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;

public class FrmCadastroUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtId;
	private JTextField edtLogin;
	private JTextField edtNome;
	private JTextField edtTelefone;
	private JPasswordField edtSenha;
	private JPasswordField edtSenhaRep;
	private JTextField edtLogradouro;
	private JTextField edtNumero;
	private JTextField edtComplemento;
	private JTextField edtBairro;
	private JTextField edtCidade;
	private JTextField edtUf;
	private JTextField edtPais;
	private final ButtonGroup rgpGenero = new ButtonGroup();
	private JCheckBox cbxAdministrador = new JCheckBox("Administrador?");
	private Usuario usuarioSistema;
	private UsuarioController usuarioController;
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnNovo = new JButton("Novo");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnConsultarUsuarios = new JButton("...");
	private JFormattedTextField edtCep;
	private MaskFormatter mascaraCep;
	private JRadioButton rbtGenM = new JRadioButton("Masculino");
	private JRadioButton rbtGenF = new JRadioButton("Feminino");
	private JRadioButton rbtGenO = new JRadioButton("Outro");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCadastroUsuario frame = new FrmCadastroUsuario(new Usuario(0));
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
	public FrmCadastroUsuario(Usuario usuarioSistema) {
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		this.usuarioSistema = usuarioSistema;
		usuarioController = new UsuarioController(usuarioSistema);
		setBounds(100, 100, 639, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(new Color(128, 128, 128));
		contentPane.add(pnlTop, BorderLayout.NORTH);
		pnlTop.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTitulo = new JPanel();
		pnlTitulo.setBackground(Color.GRAY);
		pnlTop.add(pnlTitulo, BorderLayout.WEST);
		
		JLabel lblCadastroDeUsurio = new JLabel("CADASTRO DE USUÁRIO");
		lblCadastroDeUsurio.setForeground(Color.WHITE);
		lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblCadastroDeUsurio);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(new Color(128, 128, 128));
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		
		JPanel pnlCadastroUsuario = new JPanel();
		
		rbtGenM.setEnabled(false);
		rbtGenM.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenM.setSelected(true);
		rbtGenM.setBackground(new Color(192, 192, 192));
		rgpGenero.add(rbtGenM);
		rbtGenM.setBounds(94, 95, 71, 23);
		pnlCadastroUsuario.add(rbtGenM);
		
		rbtGenF.setEnabled(false);
		rbtGenF.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenF.setBackground(new Color(192, 192, 192));
		rgpGenero.add(rbtGenF);
		rbtGenF.setBounds(167, 95, 71, 23);
		pnlCadastroUsuario.add(rbtGenF);
		
		rbtGenO.setEnabled(false);
		rbtGenO.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtGenO.setBackground(new Color(192, 192, 192));
		rgpGenero.add(rbtGenO);
		rbtGenO.setBounds(240, 95, 58, 23);
		pnlCadastroUsuario.add(rbtGenO);
		
		try {
			mascaraCep = new MaskFormatter("#####-###");
		} catch (ParseException e1) {
			mascaraCep = null;
		}
		edtCep = new JFormattedTextField(mascaraCep);
		
		edtCep.setEnabled(false);
		edtCep.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				buscarDadosViaCep();
			}
		});
		edtCep.setBounds(380, 95, 195, 20);
		pnlCadastroUsuario.add(edtCep);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
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
		
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		btnExcluir.setEnabled(false);
		pnlBotoes.add(btnExcluir);
		pnlBotoes.add(btnLimpar);
		
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregar();
			}
		});
		pnlBotoes.add(btnCarregar);
		
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnlBotoes.add(btnSair);
		
		pnlCadastroUsuario.setBackground(new Color(192, 192, 192));
		contentPane.add(pnlCadastroUsuario, BorderLayout.CENTER);
		pnlCadastroUsuario.setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblId.setBounds(80, 23, 19, 14);
		pnlCadastroUsuario.add(lblId);
		
		edtId = new JTextField();
		edtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					carregar();
				}
			}
		});
		edtId.setColumns(10);
		edtId.setBounds(94, 20, 165, 20);
		pnlCadastroUsuario.add(edtId);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLogin.setBounds(350, 23, 30, 14);
		pnlCadastroUsuario.add(lblLogin);
		
		edtLogin = new JTextField();
		edtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					carregar();
				}
			}
		});
		edtLogin.setColumns(10);
		edtLogin.setBounds(380, 20, 195, 20);
		pnlCadastroUsuario.add(edtLogin);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSenha.setBounds(59, 48, 30, 14);
		pnlCadastroUsuario.add(lblSenha);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNome.setBounds(59, 73, 30, 14);
		pnlCadastroUsuario.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setEnabled(false);
		edtNome.setColumns(10);
		edtNome.setBounds(94, 70, 195, 20);
		pnlCadastroUsuario.add(edtNome);
		
		JLabel lblGenero = new JLabel("Gênero");
		lblGenero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGenero.setBounds(51, 99, 37, 14);
		pnlCadastroUsuario.add(lblGenero);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTelefone.setBounds(334, 73, 46, 14);
		pnlCadastroUsuario.add(lblTelefone);
		
		edtTelefone = new JTextField();
		edtTelefone.setEnabled(false);
		edtTelefone.setColumns(10);
		edtTelefone.setBounds(380, 70, 195, 20);
		pnlCadastroUsuario.add(edtTelefone);
		
		JLabel lblRepitaASenha = new JLabel("Repita a senha");
		lblRepitaASenha.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRepitaASenha.setBounds(305, 48, 72, 14);
		pnlCadastroUsuario.add(lblRepitaASenha);
		
		edtSenha = new JPasswordField();
		edtSenha.setEnabled(false);
		edtSenha.setBounds(94, 45, 195, 20);
		pnlCadastroUsuario.add(edtSenha);
		
		edtSenhaRep = new JPasswordField();
		edtSenhaRep.setEnabled(false);
		edtSenhaRep.setBounds(380, 45, 195, 20);
		pnlCadastroUsuario.add(edtSenhaRep);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCep.setBounds(357, 98, 25, 14);
		pnlCadastroUsuario.add(lblCep);
		
		edtLogradouro = new JTextField();
		edtLogradouro.setEnabled(false);
		edtLogradouro.setColumns(10);
		edtLogradouro.setBounds(94, 120, 195, 20);
		pnlCadastroUsuario.add(edtLogradouro);
		
		JLabel lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLogradouro.setBounds(31, 123, 58, 14);
		pnlCadastroUsuario.add(lblLogradouro);
		
		edtNumero = new JTextField();
		edtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
			}
		});
		edtNumero.setEnabled(false);
		edtNumero.setColumns(10);
		edtNumero.setBounds(380, 120, 52, 20);
		pnlCadastroUsuario.add(edtNumero);
		
		JLabel lblNumero = new JLabel("Número");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumero.setBounds(340, 123, 46, 14);
		pnlCadastroUsuario.add(lblNumero);
		
		edtComplemento = new JTextField();
		edtComplemento.setEnabled(false);
		edtComplemento.setColumns(10);
		edtComplemento.setBounds(476, 120, 99, 20);
		pnlCadastroUsuario.add(edtComplemento);
		
		JLabel lblComplemento = new JLabel("Compl.");
		lblComplemento.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblComplemento.setBounds(442, 123, 37, 14);
		pnlCadastroUsuario.add(lblComplemento);
		
		edtBairro = new JTextField();
		edtBairro.setEnabled(false);
		edtBairro.setColumns(10);
		edtBairro.setBounds(94, 145, 195, 20);
		pnlCadastroUsuario.add(edtBairro);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBairro.setBounds(59, 148, 30, 14);
		pnlCadastroUsuario.add(lblBairro);
		
		edtCidade = new JTextField();
		edtCidade.setEnabled(false);
		edtCidade.setColumns(10);
		edtCidade.setBounds(380, 145, 195, 20);
		pnlCadastroUsuario.add(edtCidade);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCidade.setBounds(343, 148, 37, 14);
		pnlCadastroUsuario.add(lblCidade);
		
		edtUf = new JTextField();
		edtUf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.limitarCaracteresJTextField(edtUf, e, 2);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				edtUf.setText(edtUf.getText().toUpperCase());
			}
		});
		edtUf.setEnabled(false);
		edtUf.setColumns(10);
		edtUf.setBounds(94, 170, 195, 20);
		pnlCadastroUsuario.add(edtUf);
		
		JLabel lblUf = new JLabel("UF");
		lblUf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUf.setBounds(75, 173, 19, 14);
		pnlCadastroUsuario.add(lblUf);
		
		edtPais = new JTextField();
		edtPais.setEnabled(false);
		edtPais.setColumns(10);
		edtPais.setBounds(380, 170, 195, 20);
		pnlCadastroUsuario.add(edtPais);
		
		JLabel lblPais = new JLabel("País");
		lblPais.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPais.setBounds(357, 173, 30, 14);
		pnlCadastroUsuario.add(lblPais);
		
		btnConsultarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaUsuarios();
			}
		});
		btnConsultarUsuarios.setBounds(263, 19, 25, 23);
		pnlCadastroUsuario.add(btnConsultarUsuarios);
		cbxAdministrador.setEnabled(false);
		
		cbxAdministrador.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cbxAdministrador.setBackground(new Color(192, 192, 192));
		cbxAdministrador.setBounds(90, 195, 118, 23);
		pnlCadastroUsuario.add(cbxAdministrador);

		limpar();
	}
	
	private void limpar() {
		edtId.setText(usuarioSistema.isAdministrador() ? "" : String.valueOf(usuarioSistema.getId()));
		edtId.setEnabled(usuarioSistema.isAdministrador());
		btnConsultarUsuarios.setEnabled(usuarioSistema.isAdministrador());
		edtLogin.setText("");
		edtLogin.setEnabled(usuarioSistema.isAdministrador());
		edtSenha.setText("");
		edtSenha.setEnabled(false);
		edtSenhaRep.setText("");
		edtSenhaRep.setEnabled(false);
		edtNome.setText("");
		edtNome.setEnabled(false);
		cbxAdministrador.setSelected(false);
		cbxAdministrador.setEnabled(false);
		edtTelefone.setText("");
		edtTelefone.setEnabled(false);
		rgpGenero.setSelected(rbtGenM.getModel(), true);
		rbtGenM.setEnabled(false);
		rbtGenF.setEnabled(false);
		rbtGenO.setEnabled(false);
		
		edtCep.setText("");
		edtCep.setEnabled(false);
		edtLogradouro.setText("");
		edtLogradouro.setEnabled(false);
		edtNumero.setText("");
		edtNumero.setEnabled(false);
		edtComplemento.setText("");
		edtComplemento.setEnabled(false);
		edtBairro.setText("");
		edtBairro.setEnabled(false);
		edtCidade.setText("");
		edtCidade.setEnabled(false);
		edtUf.setText("");
		edtUf.setEnabled(false);
		edtPais.setText("");
		edtPais.setEnabled(false);

		btnCarregar.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnNovo.setEnabled(usuarioSistema.isAdministrador());
		btnExcluir.setEnabled(false);
		
		if (edtId.getText().trim().equals("")) {
			edtId.requestFocus();
		} else {
			carregar();
		}
	}
	
	public void carregar() {
		Usuario usuario = null;
		try {
			if (!edtId.getText().trim().equals("")) {
				usuario = usuarioController.buscarPorId(Integer.parseInt(edtId.getText()));
			} else if (!edtLogin.getText().trim().equals("")) {
				usuario = usuarioController.buscarPorLogin(edtLogin.getText());
			} else {
				JOptionPane.showMessageDialog(null, "Digite um id ou login de usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			if (usuario != null) {
				edtId.setText(String.valueOf(usuario.getId()));
				edtId.setEnabled(false);
				btnConsultarUsuarios.setEnabled(false);
				edtLogin.setText(usuario.getLogin());
				edtLogin.setEnabled(true);
				edtSenha.setText("");
				edtSenha.setEnabled(true);
				edtSenhaRep.setText("");
				edtSenhaRep.setEnabled(true);
				edtNome.setText(usuario.getNome());
				edtNome.setEnabled(true);
				edtTelefone.setText(usuario.getTelefone());
				edtTelefone.setEnabled(true);
				cbxAdministrador.setSelected(usuario.isAdministrador());
				cbxAdministrador.setEnabled(usuarioSistema.isAdministrador());
				ViewUtils.setRadioGroupGenero(usuario.getGenero(), rgpGenero, rbtGenM, rbtGenF, rbtGenO);
				rbtGenM.setEnabled(true);
				rbtGenF.setEnabled(true);
				rbtGenO.setEnabled(true);
				
				edtCep.setText(String.valueOf(usuario.getEndereco().getCep()));
				edtCep.setEnabled(true);
				edtLogradouro.setText(usuario.getEndereco().getLogradouro());
				edtLogradouro.setEnabled(true);
				edtNumero.setText(String.valueOf(usuario.getEndereco().getNumero()));
				edtNumero.setEnabled(true);
				edtComplemento.setText(usuario.getEndereco().getComplemento());
				edtComplemento.setEnabled(true);
				edtBairro.setText(usuario.getEndereco().getBairro());
				edtBairro.setEnabled(true);
				edtCidade.setText(usuario.getEndereco().getCidade());
				edtCidade.setEnabled(true);
				edtUf.setText(usuario.getEndereco().getUf());
				edtUf.setEnabled(true);
				edtPais.setText(usuario.getEndereco().getPais());
				edtPais.setEnabled(true);
				
				btnCarregar.setEnabled(false);
				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(usuarioSistema.isAdministrador());
			}
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void buscarDadosViaCep() {
		String strCep = edtCep.getText().replace("-", "").trim(); 
		if (!strCep.equals("")) {
			Endereco endereco = new EnderecoController().buscarDadosViaCep(Integer.parseInt(strCep));
			if (endereco != null) {
				edtLogradouro.setText(endereco.getLogradouro());
				edtComplemento.setText(endereco.getComplemento());
				edtBairro.setText(endereco.getBairro());
				edtCidade.setText(endereco.getCidade());
				edtUf.setText(endereco.getUf());
				edtPais.setText(endereco.getPais());
			}
		}
	}
	
	private void salvar() {
		Usuario usuario = new Usuario(0);
		if (!edtId.getText().trim().equals("")) {
			usuario.setId(Integer.parseInt(edtId.getText()));
		}
		usuario.setLogin(edtLogin.getText());
		usuario.setSenha(String.valueOf(edtSenha.getPassword()));
		usuario.setNome(edtNome.getText());
		usuario.setGenero(ViewUtils.getGeneroFromRadioButton(rgpGenero, rbtGenM, rbtGenF, rbtGenO));
		usuario.setTelefone(edtTelefone.getText());
		usuario.setAdministrador(cbxAdministrador.isSelected());
		
		Endereco endereco = new Endereco(0);
		String strCep = edtCep.getText().replace("-", "").trim();
		if (strCep.equals("")) {
			endereco.setCep(0);
		} else {
			endereco.setCep(Integer.parseInt(strCep));
		}
		endereco.setLogradouro(edtLogradouro.getText());
		if (edtNumero.getText().trim().equals("")) {
			endereco.setNumero(0);
		} else {
			endereco.setNumero(Integer.parseInt(edtNumero.getText()));
		}
		endereco.setComplemento(edtComplemento.getText());
		endereco.setBairro(edtBairro.getText());
		endereco.setCidade(edtCidade.getText());
		endereco.setUf(edtUf.getText());
		endereco.setPais(edtPais.getText());
		
		usuario.setEndereco(endereco);
		
		try {
			if (usuario.getId() == 0) {
				usuario = usuarioController.salvar(usuario, String.valueOf(edtSenhaRep.getPassword()));
				edtId.setText(String.valueOf(usuario.getId()));
			} else {
				usuarioController.editar(usuario, String.valueOf(edtSenhaRep.getPassword()));
			}
			edtSenha.setText("");
			edtSenhaRep.setText("");
			btnNovo.setEnabled(usuarioSistema.isAdministrador());
			JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso.");
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void excluir() {
		Object[] options = { "Sim", "Não" };
		int n = JOptionPane.showOptionDialog(null,
						"Tem certeza que deseja excluir este usuário?",
						"Excluir usuário", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			int id = 0;
			if (!edtId.getText().trim().equals("")) {
				id = Integer.parseInt(edtId.getText());
			}
			try {
				usuarioController.excluir(id);
				limpar();
				JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso.");
			} catch (BusinessException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void novo() {
		edtId.setText("");
		edtId.setEnabled(false);
		btnConsultarUsuarios.setEnabled(false);
		edtLogin.setEnabled(true);
		edtSenha.setEnabled(true);
		edtSenhaRep.setEnabled(true);
		edtNome.setEnabled(true);
		edtTelefone.setEnabled(true);
		cbxAdministrador.setEnabled(usuarioSistema.isAdministrador());
		rbtGenM.setEnabled(true);
		rbtGenF.setEnabled(true);
		rbtGenO.setEnabled(true);
		
		edtCep.setEnabled(true);
		edtLogradouro.setEnabled(true);
		edtNumero.setEnabled(true);
		edtComplemento.setEnabled(true);
		edtBairro.setEnabled(true);
		edtCidade.setEnabled(true);
		edtUf.setEnabled(true);
		edtPais.setEnabled(true);

		btnCarregar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnNovo.setEnabled(false);
		btnExcluir.setEnabled(false);
		
		edtLogin.requestFocus();
	}
	
	private void sair() {
		setVisible(false);
	}
	
	private void abrirFrmConsultaUsuarios() {
		FrmConsultaUsuarios frmConsultaUsuarios = new FrmConsultaUsuarios(usuarioSistema, true);
		frmConsultaUsuarios.setVisible(true);
		Usuario usuarioSelecionado = frmConsultaUsuarios.getUsuarioSelecionado();
		if (usuarioSelecionado != null) {
			edtId.setText(String.valueOf(usuarioSelecionado.getId()));
			carregar();
		}
	}
	
	private void setButtonIcons() {
		//btnSalvar.setIcon(new ImageIcon(FrmCadastroUsuario.class.getResource("/view/icons/save-icon.png")));
		//btnExcluir.setIcon(new ImageIcon(FrmCadastroUsuario.class.getResource("/view/icons/delete-icon.png")));
	}
	
}
