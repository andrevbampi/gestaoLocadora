package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import controller.VeiculoController;
import controller.exception.BusinessRuleException;
import model.StatusVeiculo;
import model.Usuario;
import model.Veiculo;
import view.util.ViewUtils;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FrmCadastroVeiculo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtId;
	private JTextField edtMarca;
	private JTextField edtModelo;
	private JTextField edtAno;
	private JTextField edtCor;
	private final ButtonGroup rgpCategoriaVeiculo = new ButtonGroup();
	private Usuario usuarioSistema;
	VeiculoController veiculoController;
	private Veiculo veiculoSelecionado;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JButton btnVerificarStatus;
	private JRadioButton rbtCatPeq = new JRadioButton("Pequeno");
	private JRadioButton rbtCatFam = new JRadioButton("Família");
	private JRadioButton rbtCatVan = new JRadioButton("Van");
	private final JLabel lblPeriodoDeReferncia = new JLabel("Referência");
	private MaskFormatter mascaraData;
	private final JFormattedTextField edtDtInicioRef;
	private final JFormattedTextField edtDtFimRef;
	private JTextField edtStatus;
	private MaskFormatter mascaraPlaca;
	private JFormattedTextField edtPlaca;
	private JCheckBox cbxAtivo = new JCheckBox("Ativo?");

	public FrmCadastroVeiculo(Usuario usuarioSistema) {
		setResizable(false);
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		this.usuarioSistema = usuarioSistema;
		veiculoController = new VeiculoController(usuarioSistema);
		veiculoSelecionado = null;
		
		setBounds(100, 100, 484, 400);
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
		rbtCatPeq.setBounds(94, 175, 71, 23);
		pnlCadastroVeiculo.add(rbtCatPeq);
		
		rgpCategoriaVeiculo.add(rbtCatFam);
		rbtCatFam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatFam.setEnabled(false);
		rbtCatFam.setBackground(Color.LIGHT_GRAY);
		rbtCatFam.setBounds(175, 175, 71, 23);
		pnlCadastroVeiculo.add(rbtCatFam);
		
		rgpCategoriaVeiculo.add(rbtCatVan);
		rbtCatVan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtCatVan.setEnabled(false);
		rbtCatVan.setBackground(Color.LIGHT_GRAY);
		rbtCatVan.setBounds(248, 175, 58, 23);
		pnlCadastroVeiculo.add(rbtCatVan);
		btnConsultarVeiculos.setFocusable(false);
		
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
		edtId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!edtId.getText().trim().equals("")) {
					carregar();
				}
			}
		});
		edtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
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
		lblCategoria.setBounds(38, 179, 58, 14);
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
		lblPeriodoDeReferncia.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPeriodoDeReferncia.setBounds(40, 208, 61, 14);
		
		pnlCadastroVeiculo.add(lblPeriodoDeReferncia);
		
		try {
			mascaraData = new MaskFormatter(ViewUtils.getMascaraCampoData());
		} catch (ParseException e1) {
			mascaraData = null;
		}
		
		edtDtInicioRef = new JFormattedTextField(mascaraData);
		edtDtInicioRef.setEnabled(false);
		edtDtInicioRef.setBounds(94, 205, 110, 20);
		
		pnlCadastroVeiculo.add(edtDtInicioRef);
		
		edtDtFimRef = new JFormattedTextField(mascaraData);
		edtDtFimRef.setEnabled(false);
		edtDtFimRef.setBounds(209, 205, 110, 20);
		
		pnlCadastroVeiculo.add(edtDtFimRef);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setBounds(59, 233, 41, 14);
		pnlCadastroVeiculo.add(lblStatus);
		
		edtStatus = new JTextField();
		edtStatus.setFocusable(false);
		edtStatus.setForeground(new Color(255, 255, 255));
		edtStatus.setBackground(new Color(128, 128, 128));
		edtStatus.setEditable(false);
		edtStatus.setColumns(10);
		edtStatus.setBounds(94, 230, 255, 20);
		pnlCadastroVeiculo.add(edtStatus);
		
		btnVerificarStatus = new JButton("...");
		btnVerificarStatus.setFocusable(false);
		btnVerificarStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarStatusVeiculo();
			}
		});
		btnVerificarStatus.setBounds(323, 204, 25, 23);
		pnlCadastroVeiculo.add(btnVerificarStatus);
		
		try {
			mascaraPlaca = new MaskFormatter(ViewUtils.getMascaraCampoPlaca());
		} catch (ParseException e1) {
			mascaraPlaca = null;
		}
		
		edtPlaca = new JFormattedTextField(mascaraPlaca);
		edtPlaca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				edtPlaca.setText(edtPlaca.getText().toUpperCase());
			}
		});
		edtPlaca.setText("");
		edtPlaca.setEnabled(false);
		edtPlaca.setBounds(94, 145, 255, 20);
		pnlCadastroVeiculo.add(edtPlaca);
		
		JLabel lblPlaca = new JLabel("Placa");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlaca.setBounds(62, 148, 30, 14);
		pnlCadastroVeiculo.add(lblPlaca);
		
		cbxAtivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cbxAtivo.setEnabled(false);
		cbxAtivo.setBackground(Color.LIGHT_GRAY);
		cbxAtivo.setBounds(94, 255, 88, 23);
		pnlCadastroVeiculo.add(cbxAtivo);
		
		limpar();
	}

	public void carregar() {
		try {
			if (!edtId.getText().trim().equals("")) {
				Date dataInicioRef = ViewUtils.formatarDataDeJFormattedTextField(edtDtInicioRef, "Data inicial de referência inválida.");
				Date dataFimRef = ViewUtils.formatarDataDeJFormattedTextField(edtDtInicioRef, "Data final de referência inválida.");
				veiculoSelecionado = veiculoController.buscarPorId(Integer.parseInt(edtId.getText()), dataInicioRef, dataFimRef, 0, 0);
			} else {
				JOptionPane.showMessageDialog(null, "Digite um id de veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			if (veiculoSelecionado != null) {
				edtId.setText(String.valueOf(veiculoSelecionado.getId()));
				edtId.setEnabled(false);
				btnConsultarVeiculos.setEnabled(false);
				edtMarca.setText(veiculoSelecionado.getMarca());
				edtMarca.setEnabled(true);
				edtModelo.setText(veiculoSelecionado.getModelo());
				edtModelo.setEnabled(true);
				edtAno.setText(String.valueOf(veiculoSelecionado.getAno()));
				edtAno.setEnabled(true);
				edtCor.setText(veiculoSelecionado.getCor());
				edtCor.setEnabled(true);
				edtPlaca.setText(veiculoSelecionado.getPlaca());
				edtPlaca.setEnabled(true);
				ViewUtils.setRadioGroupCategoriaVeiculo(veiculoSelecionado.getCategoria(), rgpCategoriaVeiculo, rbtCatPeq, rbtCatFam, rbtCatVan);
				rbtCatPeq.setEnabled(true);
				rbtCatFam.setEnabled(true);
				rbtCatVan.setEnabled(true);
				edtDtInicioRef.setEnabled(true);
				edtDtFimRef.setEnabled(true);
				edtStatus.setText(veiculoSelecionado.getStatus().getStatus());
				btnVerificarStatus.setEnabled(true);
				cbxAtivo.setSelected(veiculoSelecionado.isAtivo());
				cbxAtivo.setEnabled(true);
				inicializarDatasReferencia();
				mostrarStatusVeiculo();

				btnNovo.setEnabled(true);
				btnCarregar.setEnabled(false);
				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	private void limpar() {
		veiculoSelecionado = null;
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
		edtPlaca.setText("");
		edtPlaca.setEnabled(false);
		rgpCategoriaVeiculo.setSelected(rbtCatPeq.getModel(), true);
		rbtCatPeq.setEnabled(false);
		rbtCatFam.setEnabled(false);
		rbtCatVan.setEnabled(false);
		edtStatus.setText("");
		edtDtInicioRef.setEnabled(false);
		edtDtFimRef.setEnabled(false);
		btnVerificarStatus.setEnabled(false);
		cbxAtivo.setSelected(false);
		cbxAtivo.setEnabled(false);
		
		inicializarDatasReferencia();

		btnCarregar.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);
		
		edtId.requestFocus();
	}
	
	private void abrirFrmConsultaVeiculos() {
		FrmConsultaVeiculos frmConsultaVeiculos = new FrmConsultaVeiculos(usuarioSistema, true);
		frmConsultaVeiculos.setVisible(true);
		veiculoSelecionado = frmConsultaVeiculos.getVeiculoSelecionado();
		if (veiculoSelecionado != null) {
			edtId.setText(String.valueOf(veiculoSelecionado.getId()));
			carregar();
		}
	}
	
	private void salvar() {
		if (veiculoSelecionado == null) {
			veiculoSelecionado = new Veiculo(0);
		}
		veiculoSelecionado.setMarca(edtMarca.getText());
		veiculoSelecionado.setModelo(edtModelo.getText());
		if (!edtAno.getText().trim().equals("")) {
			veiculoSelecionado.setAno(Integer.parseInt(edtAno.getText()));
		}
		veiculoSelecionado.setCor(edtCor.getText());
		veiculoSelecionado.setCategoria(ViewUtils.getCategoriaVeiculoFromRadioButton(rgpCategoriaVeiculo, rbtCatPeq, rbtCatFam, rbtCatVan));
		veiculoSelecionado.setPlaca(edtPlaca.getText().replace("-", "").trim());
		veiculoSelecionado.setAtivo(cbxAtivo.isSelected());
		try {
			String msgSucesso;
			if (veiculoSelecionado.getId() == 0) {
				veiculoSelecionado = veiculoController.salvar(veiculoSelecionado);
				edtId.setText(String.valueOf(veiculoSelecionado.getId()));
				msgSucesso = "Veículo salvo com sucesso.";
			} else {
				veiculoController.editar(veiculoSelecionado);
				msgSucesso = "Veículo editado com sucesso.";
			}
			carregar();
			JOptionPane.showMessageDialog(null, msgSucesso);
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void novo() {
		veiculoSelecionado = null;
		edtId.setText("");
		edtId.setEnabled(false);
		btnConsultarVeiculos.setEnabled(false);
		edtMarca.setEnabled(true);
		edtModelo.setEnabled(true);
		edtAno.setEnabled(true);
		edtCor.setEnabled(true);
		edtPlaca.setEnabled(true);
		rbtCatPeq.setEnabled(true);
		rbtCatFam.setEnabled(true);
		rbtCatVan.setEnabled(true);
		edtDtInicioRef.setEnabled(false);
		edtDtInicioRef.setText("");
		edtDtFimRef.setEnabled(false);
		edtDtFimRef.setText("");
		edtStatus.setText(StatusVeiculo.DISPONIVEL.getStatus());
		btnVerificarStatus.setEnabled(false);
		cbxAtivo.setEnabled(true);
		cbxAtivo.setSelected(true);

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
			} catch (BusinessRuleException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void mostrarStatusVeiculo() {
		if (!edtId.getText().trim().equals("")) {
			try {
				Date dataInicioRef = ViewUtils.formatarDataDeJFormattedTextField(edtDtInicioRef, "Data inicial de referência inválida.");
				Date dataFimRef = ViewUtils.formatarDataDeJFormattedTextField(edtDtFimRef, "Data final de referência inválida.");
				edtStatus.setText(veiculoController.buscarStatusVeiculo(Integer.parseInt(edtId.getText()), dataInicioRef, dataFimRef, 0, 0).getStatus());
			} catch (BusinessRuleException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void inicializarDatasReferencia() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		edtDtInicioRef.setValue(ViewUtils.formatarDataParaJFormattedTextField(new Date(cal.getTimeInMillis())));
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		edtDtFimRef.setValue(ViewUtils.formatarDataParaJFormattedTextField(new Date(cal.getTimeInMillis())));
	}
	
	private void sair() {
		setVisible(false);
	}
}
