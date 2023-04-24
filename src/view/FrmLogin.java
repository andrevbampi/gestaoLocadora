package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import controller.UsuarioController;
import model.Usuario;
import controller.exception.BusinessException;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField edtLogin;
	private JPasswordField edtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLogin frame = new FrmLogin();
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
	public FrmLogin() {
		setLocationRelativeTo( null );
		UsuarioController usuarioController = new UsuarioController(new Usuario(0));
		
		setResizable(false);
		setTitle("Gestão de Locadora de Veículos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 260);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(new Color(128, 128, 128));
		getContentPane().add(pnlTop, BorderLayout.NORTH);
		pnlTop.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTitulo = new JPanel();
		pnlTitulo.setBackground(new Color(128, 128, 128));
		pnlTop.add(pnlTitulo, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("GESTÃO DE LOCADORA");
		pnlTitulo.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(new Color(128, 128, 128));
		getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(new Color(128, 128, 128));
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					efetuarLogin(usuarioController);
				}
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				efetuarLogin(usuarioController);
			}
		});
		pnlBotoes.add(btnLogin);
		
		JButton btnSair = new JButton("Sair");
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
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(new Color(192, 192, 192));
		getContentPane().add(pnlLogin, BorderLayout.CENTER);
		pnlLogin.setLayout(null);
		
		edtLogin = new JTextField();
		edtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					efetuarLogin(usuarioController);
				}
			}
		});
		edtLogin.setBounds(132, 49, 141, 20);
		pnlLogin.add(edtLogin);
		edtLogin.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(91, 52, 46, 14);
		pnlLogin.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(91, 78, 46, 14);
		pnlLogin.add(lblSenha);
		
		edtSenha = new JPasswordField();
		edtSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					efetuarLogin(usuarioController);
				}
			}
		});
		edtSenha.setBounds(132, 75, 141, 20);
		pnlLogin.add(edtSenha);
	}
	
	private void efetuarLogin(UsuarioController usuarioController) {
		if (edtLogin.getText().trim().equals("") 
		|| String.valueOf(edtSenha.getPassword()).trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Login e senha devem ser digitados.", "Erro", JOptionPane.ERROR_MESSAGE);
		} else {
			Usuario usuario = new Usuario(0);
			usuario.setLogin(edtLogin.getText());
			usuario.setSenha(String.valueOf(edtSenha.getPassword()));
			try {
				new FrmPrincipal(usuarioController.efetuarLogin(usuario)).setVisible(true);
				dispose();
			} catch (BusinessException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void sair() {
		System.exit(ABORT);
	}
	
}
