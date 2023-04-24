package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Usuario;
import javax.swing.JLabel;
import java.awt.Font;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuarioSistema;
	JLabel lblDescricaoUsuario = new JLabel("Olá!");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPrincipal frame = new FrmPrincipal(new Usuario(0));
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
	public FrmPrincipal(Usuario usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
		lblDescricaoUsuario.setText("Olá " + usuarioSistema.getNome() + "!");
		
		setTitle("Gestão de Locadora de Veículos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar mebMenuPrincipal = new JMenuBar();
		mebMenuPrincipal.setBackground(new Color(240, 240, 240));
		setJMenuBar(mebMenuPrincipal);
		
		JMenu menUsuario = new JMenu("Usuário");
		mebMenuPrincipal.add(menUsuario);
		
		JMenuItem mitCadastroUsuario = new JMenuItem("Cadastro");
		mitCadastroUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				abrirFrmCadastroUsuario();
			}
		});
		
		JMenuItem mitLogout = new JMenuItem("Logout");
		mitLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				efetuarLogout();
			}
		});
		menUsuario.add(mitLogout);
		menUsuario.add(mitCadastroUsuario);
		
		JMenuItem mitConsultaUsuario = new JMenuItem("Consulta");
		mitConsultaUsuario.setEnabled(usuarioSistema.isAdministrador());
		mitConsultaUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				abrirFrmConsultaUsuarios();
			}
		});
		menUsuario.add(mitConsultaUsuario);
		
		JMenu menVeiculo = new JMenu("Veículo");
		mebMenuPrincipal.add(menVeiculo);
		
		JMenuItem mitCadastroVeiculo = new JMenuItem("Cadastro");
		mitCadastroVeiculo.setEnabled(usuarioSistema.isAdministrador());
		mitCadastroVeiculo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				abrirFrmCadastroVeiculo();
			}
		});
		menVeiculo.add(mitCadastroVeiculo);
		
		JMenuItem mitConsultaVeiculo = new JMenuItem("Consulta");
		mitConsultaVeiculo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				abrirFrmConsultaVeiculos();
			}
		});
		menVeiculo.add(mitConsultaVeiculo);
		
		JMenu menReserva = new JMenu("Reserva");
		mebMenuPrincipal.add(menReserva);
		
		JMenuItem mitReservar = new JMenuItem("Reservar");
		mitReservar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				abrirFrmCadastroReserva();
			}
		});
		menReserva.add(mitReservar);
		
		JMenuItem mitConsultarReserva = new JMenuItem("Consultar");
		mitConsultarReserva.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				abrirFrmConsultaReservas();
			}
		});
		menReserva.add(mitConsultarReserva);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.GRAY);
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnlBotoes.add(btnSair);
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlLogin, BorderLayout.CENTER);
		pnlLogin.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlUsuario = new JPanel();
		pnlUsuario.setBackground(Color.GRAY);
		pnlLogin.add(pnlUsuario, BorderLayout.NORTH);
		pnlUsuario.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTituloUsuario = new JPanel();
		pnlTituloUsuario.setBackground(Color.GRAY);
		pnlUsuario.add(pnlTituloUsuario, BorderLayout.WEST);
		
		lblDescricaoUsuario.setForeground(Color.WHITE);
		lblDescricaoUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTituloUsuario.add(lblDescricaoUsuario);
	}
	
	private void abrirFrmCadastroReserva() {
		new FrmCadastroReserva(usuarioSistema).setVisible(true);
	}
	
	private void abrirFrmCadastroUsuario() {
		new FrmCadastroUsuario(usuarioSistema).setVisible(true);
	}
	
	private void abrirFrmCadastroVeiculo() {
		if (usuarioSistema.isAdministrador()) {
			new FrmCadastroVeiculo(usuarioSistema).setVisible(true);
		}
	}
	
	private void abrirFrmConsultaReservas() {
		new FrmConsultaReservas(usuarioSistema, false).setVisible(true);
	}
	
	private void abrirFrmConsultaUsuarios() {
		if (usuarioSistema.isAdministrador()) {
			new FrmConsultaUsuarios(usuarioSistema, false).setVisible(true);
		}
	}
	
	private void abrirFrmConsultaVeiculos() {
		new FrmConsultaVeiculos(usuarioSistema, false).setVisible(true);
	}
	
	private void efetuarLogout() {
		new FrmLogin().setVisible(true);
		dispose();
	}
	
	private void sair() {
		System.exit(ABORT);
	}
}
