package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class FrmSobre extends JDialog {

	private JPanel contentPane;

	public FrmSobre() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 530, 493);
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
		
		JLabel lblTitulo = new JLabel("SOBRE O GESTÃO DE LOCADORA DE VEÍCULOS");
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
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnlBotoes.add(btnSair);
		
		JPanel pnlSobre = new JPanel();
		pnlSobre.setLayout(null);
		pnlSobre.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlSobre, BorderLayout.CENTER);
		
		JLabel lblTituloTexto = new JLabel("GESTÃO DE LOCADORA DE VEÍCULOS");
		lblTituloTexto.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloTexto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTituloTexto.setBounds(0, 5, 504, 65);
		pnlSobre.add(lblTituloTexto);
		
		JLabel lblTexto1 = new JLabel("Aplicação desenvolvida por André Vinicius Bampi em abril de 2023 com objetvo de");
		lblTexto1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTexto1.setBounds(35, 65, 451, 41);
		pnlSobre.add(lblTexto1);
		
		JLabel lblTexto2 = new JLabel("demonstrar suas habilidades e experiências em programação e análise de sistemas.");
		lblTexto2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTexto2.setBounds(10, 90, 476, 41);
		pnlSobre.add(lblTexto2);
		
		JLabel lblTexto3 = new JLabel("André trabalha desde janeiro de 2012 como programador e até o presente");
		lblTexto3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTexto3.setBounds(35, 115, 451, 41);
		pnlSobre.add(lblTexto3);
		
		JLabel lblTexto4 = new JLabel("momento tem experiência comprovada nas seguintes tecnologias:");
		lblTexto4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTexto4.setBounds(10, 140, 476, 41);
		pnlSobre.add(lblTexto4);
		
		JLabel lblTopico1 = new JLabel("- Banco de dados Oracle com PL/SQL (11 anos e 3 meses);");
		lblTopico1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopico1.setBounds(35, 165, 451, 41);
		pnlSobre.add(lblTopico1);
		
		JLabel lblTopico2 = new JLabel("- Delphi (8 anos e 10 meses);");
		lblTopico2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopico2.setBounds(35, 190, 451, 41);
		pnlSobre.add(lblTopico2);
		
		JLabel lblTopico3 = new JLabel("- Java e Javascript (2 anos e 5 meses);");
		lblTopico3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopico3.setBounds(35, 215, 451, 41);
		pnlSobre.add(lblTopico3);
		
		JLabel lblTopico4 = new JLabel("- Metodologia ágil com o framework SAFE (1 ano).");
		lblTopico4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopico4.setBounds(35, 240, 451, 41);
		pnlSobre.add(lblTopico4);
		
		JLabel lblTexto5 = new JLabel("André possui as seguintes folrmações:");
		lblTexto5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTexto5.setBounds(35, 265, 451, 41);
		pnlSobre.add(lblTexto5);
		
		JLabel lblTopico5 = new JLabel("- Técnico em Desenvolvimento de Softwares, CEDUP Hermann Hering, 2011;");
		lblTopico5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopico5.setBounds(35, 290, 451, 41);
		pnlSobre.add(lblTopico5);
		
		JLabel lblTopico6 = new JLabel("- Bacharel em Ciência da Computação, FURB, 2018.");
		lblTopico6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopico6.setBounds(35, 315, 451, 41);
		pnlSobre.add(lblTopico6);
	}
	
	private void sair() {
		setVisible(false);
	}

}
