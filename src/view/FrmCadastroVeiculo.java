package view;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import controller.VeiculoController;
import controller.exception.BusinessException;
import model.Usuario;
import model.Veiculo;
import view.util.ViewUtils;

public class FrmCadastroVeiculo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtId;
	private JTextField edtMarca;
	private JTextField edtModelo;
	private JTextField edtAno;
	private JTextField edtCor;
	private final ButtonGroup rgpCategoriaVeiculo = new ButtonGroup();
	private final ButtonGroup rgpStatusVeiculo = new ButtonGroup();
	private Usuario usuarioSistema;
	VeiculoController veiculoController;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JRadioButton rbtCatPeq = new JRadioButton("Pequeno");
	private JRadioButton rbtCatFam = new JRadioButton("Família");
	private JRadioButton rbtCatVan = new JRadioButton("Van");
	private JRadioButton rbtStMan = new JRadioButton("Em manutenção");
	private JRadioButton rbtStDisp = new JRadioButton("Disponível");
	private JRadioButton rbtStRes = new JRadioButton("Reservado");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCadastroVeiculo frame = new FrmCadastroVeiculo(new Usuario(0));
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
	public FrmCadastroVeiculo(Usuario usuarioSistema) {
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		this.usuarioSistema = usuarioSistema;
		veiculoController = new VeiculoController(usuarioSistema);
		
		setBounds(100, 100, 486, 347);
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
		
		JLabel lblTitulo = new JLabel("CADASTRO DE VEÍCULO");
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
		
		JPanel pnlCadastroVeiculo = new JPanel();
		pnlCadastroVeiculo.setLayout(null);
		pnlCadastroVeiculo.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlCadastroVeiculo, BorderLayout.CENTER);
		
		rgpCategoriaVeiculo.add(rbtCatPeq);
		rbtCatPeq.setSelected(true);
		rbtCatPeq.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatPeq.setEnabled(false);
		rbtCatPeq.setBackground(Color.LIGHT_GRAY);
		rbtCatPeq.setBounds(94, 150, 71, 23);
		pnlCadastroVeiculo.add(rbtCatPeq);
		
		rgpCategoriaVeiculo.add(rbtCatFam);
		rbtCatFam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatFam.setEnabled(false);
		rbtCatFam.setBackground(Color.LIGHT_GRAY);
		rbtCatFam.setBounds(175, 150, 71, 23);
		pnlCadastroVeiculo.add(rbtCatFam);
		
		rgpCategoriaVeiculo.add(rbtCatVan);
		rbtCatVan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatVan.setEnabled(false);
		rbtCatVan.setBackground(Color.LIGHT_GRAY);
		rbtCatVan.setBounds(248, 150, 58, 23);
		pnlCadastroVeiculo.add(rbtCatVan);
		
		rgpStatusVeiculo.add(rbtStMan);
		rbtStMan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStMan.setEnabled(false);
		rbtStMan.setBackground(Color.LIGHT_GRAY);
		rbtStMan.setBounds(248, 175, 111, 23);
		pnlCadastroVeiculo.add(rbtStMan);
		
		rbtStDisp.setSelected(true);
		rgpStatusVeiculo.add(rbtStDisp);
		rbtStDisp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStDisp.setEnabled(false);
		rbtStDisp.setBackground(Color.LIGHT_GRAY);
		rbtStDisp.setBounds(175, 175, 79, 23);
		pnlCadastroVeiculo.add(rbtStDisp);
		
		rgpStatusVeiculo.add(rbtStRes);
		rbtStRes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStRes.setEnabled(false);
		rbtStRes.setBackground(Color.LIGHT_GRAY);
		rbtStRes.setBounds(94, 175, 79, 23);
		pnlCadastroVeiculo.add(rbtStRes);
		
		btnConsultarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaVeiculos();
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});		
		
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novo();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
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
		
		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblId.setBounds(80, 23, 19, 14);
		pnlCadastroVeiculo.add(lblId);
		
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
		edtId.setBounds(94, 20, 225, 20);
		pnlCadastroVeiculo.add(edtId);
		
		edtMarca = new JTextField();
		edtMarca.setEnabled(false);
		edtMarca.setColumns(10);
		edtMarca.setBounds(94, 45, 255, 20);
		pnlCadastroVeiculo.add(edtMarca);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMarca.setBounds(59, 48, 30, 14);
		pnlCadastroVeiculo.add(lblMarca);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblModelo.setBounds(55, 73, 46, 14);
		pnlCadastroVeiculo.add(lblModelo);
		
		edtModelo = new JTextField();
		edtModelo.setEnabled(false);
		edtModelo.setColumns(10);
		edtModelo.setBounds(94, 70, 255, 20);
		pnlCadastroVeiculo.add(edtModelo);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCategoria.setBounds(38, 154, 58, 14);
		pnlCadastroVeiculo.add(lblCategoria);
		
		edtCor = new JTextField();
		edtCor.setEnabled(false);
		edtCor.setColumns(10);
		edtCor.setBounds(94, 120, 255, 20);
		pnlCadastroVeiculo.add(edtCor);
		
		JLabel lblCor = new JLabel("Cor");
		lblCor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCor.setBounds(70, 123, 30, 14);
		pnlCadastroVeiculo.add(lblCor);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setBounds(55, 179, 40, 14);
		pnlCadastroVeiculo.add(lblStatus);

		btnConsultarVeiculos.setBounds(324, 19, 25, 23);
		pnlCadastroVeiculo.add(btnConsultarVeiculos);
		
		edtAno = new JTextField();
		edtAno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
			}
		});
		edtAno.setEnabled(false);
		edtAno.setColumns(10);
		edtAno.setBounds(94, 95, 255, 20);
		pnlCadastroVeiculo.add(edtAno);
		
		JLabel lblAno = new JLabel("Ano");
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAno.setBounds(69, 98, 30, 14);
		pnlCadastroVeiculo.add(lblAno);
	}

	public void carregar() {
		Veiculo veiculo = null;
		try {
			if (!edtId.getText().trim().equals("")) {
				veiculo = veiculoController.buscarPorId(Integer.parseInt(edtId.getText()));
			} else {
				JOptionPane.showMessageDialog(null, "Digite um id de veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			if (veiculo != null) {
				edtId.setText(String.valueOf(veiculo.getId()));
				edtId.setEnabled(false);
				btnConsultarVeiculos.setEnabled(false);
				edtMarca.setText(veiculo.getMarca());
				edtMarca.setEnabled(true);
				edtModelo.setText(veiculo.getModelo());
				edtModelo.setEnabled(true);
				edtAno.setText(String.valueOf(veiculo.getAno()));
				edtAno.setEnabled(true);
				edtCor.setText(veiculo.getCor());
				edtCor.setEnabled(true);
				ViewUtils.setRadioGroupCategoriaVeiculo(veiculo.getCategoria(), rgpCategoriaVeiculo, rbtCatPeq, rbtCatFam, rbtCatVan);
				rbtCatPeq.setEnabled(true);
				rbtCatFam.setEnabled(true);
				rbtCatVan.setEnabled(true);
				ViewUtils.setRadioGroupStatusVeiculo(veiculo.getStatus(), rgpStatusVeiculo, rbtStRes, rbtStDisp, rbtStMan);
				rbtStRes.setEnabled(true);
				rbtStDisp.setEnabled(true);
				rbtStMan.setEnabled(true);

				btnCarregar.setEnabled(false);
				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	private void limpar() {
		edtId.setText("");
		edtId.setEnabled(true);
		btnConsultarVeiculos.setEnabled(true);
		edtMarca.setText("");
		edtMarca.setEnabled(false);
		edtModelo.setText("");
		edtModelo.setEnabled(false);
		edtAno.setText("");
		edtAno.setEnabled(false);
		edtCor.setText("");
		edtCor.setEnabled(false);
		rgpCategoriaVeiculo.setSelected(rbtCatPeq.getModel(), true);
		rbtCatPeq.setEnabled(false);
		rbtCatFam.setEnabled(false);
		rbtCatVan.setEnabled(false);
		rgpStatusVeiculo.setSelected(rbtStDisp.getModel(), true);
		rbtStRes.setEnabled(false);
		rbtStDisp.setEnabled(false);
		rbtStMan.setEnabled(false);

		btnCarregar.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);
		
		edtId.requestFocus();
	}
	
	private void abrirFrmConsultaVeiculos() {
		FrmConsultaVeiculos frmConsultaVeiculos = new FrmConsultaVeiculos(usuarioSistema, true);
		frmConsultaVeiculos.setVisible(true);
		Veiculo veiculoSelecionado = frmConsultaVeiculos.getVeiculoSelecionado();
		if (veiculoSelecionado != null) {
			edtId.setText(String.valueOf(veiculoSelecionado.getId()));
			carregar();
		}
	}
	
	private void salvar() {
		Veiculo veiculo = new Veiculo(0);
		if (!edtId.getText().trim().equals("")) {
			veiculo.setId(Integer.parseInt(edtId.getText()));
		}
		veiculo.setMarca(edtMarca.getText());
		veiculo.setModelo(edtModelo.getText());
		if (!edtAno.getText().trim().equals("")) {
			veiculo.setAno(Integer.parseInt(edtAno.getText()));
		}
		veiculo.setCor(edtCor.getText());
		veiculo.setCategoria(ViewUtils.getCategoriaVeiculoFromRadioButton(rgpCategoriaVeiculo, rbtCatPeq, rbtCatFam, rbtCatVan));
		veiculo.setStatus(ViewUtils.getStatusVeiculoFromRadioButton(rgpStatusVeiculo, rbtStRes, rbtStDisp, rbtStMan));
		
		try {
			if (veiculo.getId() == 0) {
				veiculo = veiculoController.salvar(veiculo);
				edtId.setText(String.valueOf(veiculo.getId()));
			} else {
				veiculoController.editar(veiculo);
			}
			btnNovo.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Veículo salvo com sucesso.");
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void novo() {
		edtId.setText("");
		edtId.setEnabled(false);
		btnConsultarVeiculos.setEnabled(false);
		edtMarca.setEnabled(true);
		edtModelo.setEnabled(true);
		edtAno.setEnabled(true);
		edtCor.setEnabled(true);
		rbtCatPeq.setEnabled(true);
		rbtCatFam.setEnabled(true);
		rbtCatVan.setEnabled(true);
		rbtStRes.setEnabled(true);
		rbtStDisp.setEnabled(true);
		rbtStMan.setEnabled(true);

		btnCarregar.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);

		btnCarregar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnNovo.setEnabled(false);
		btnExcluir.setEnabled(false);
		
		edtMarca.requestFocus();
	}
	
	private void excluir() {
		Object[] options = { "Sim", "Não" };
		int n = JOptionPane.showOptionDialog(null,
						"Tem certeza que deseja excluir este veículo?",
						"Excluir veículo", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			int id = 0;
			if (!edtId.getText().trim().equals("")) {
				id = Integer.parseInt(edtId.getText());
			}
			try {
				veiculoController.excluir(id);
				limpar();
				JOptionPane.showMessageDialog(null, "Veículo excluído com sucesso.");
			} catch (BusinessException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void sair() {
		setVisible(false);
	}

}
