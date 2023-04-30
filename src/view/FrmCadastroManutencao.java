package view;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ManutencaoController;
import controller.exception.BusinessRuleException;
import model.Manutencao;
import model.StatusManutencao;
import model.Usuario;
import model.Veiculo;
import view.util.ViewUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FrmCadastroManutencao extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtId;
	private JTextField edtIdVeiculo;
	private JTextField edtDescricao;
	private JTextField edtMarcaModeloVeiculo;
	private final ButtonGroup rgpStatusManutencao = new ButtonGroup();
	private MaskFormatter mascaraData;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnCarregar = new JButton("Carregar");
	private JButton btnSair = new JButton("Sair");
	private JButton btnConsultarManutencoes = new JButton("...");
	private JButton btnConsultarVeiculos = new JButton("...");
	private JRadioButton rbtStManAbt = new JRadioButton("Em aberto");
	private JRadioButton rbtStManCan = new JRadioButton("Cancelada");
	private JRadioButton rbtStManCon = new JRadioButton("Concluída");
	private JFormattedTextField edtDtInicio;
	private JFormattedTextField edtDtPrevFim;
	private JFormattedTextField edtDtFim;
	private ManutencaoController manutencaoController;
	private Usuario usuarioSistema;
	private Manutencao manutencaoSelecionada;
	private Veiculo veiculoSelecionado;

	public FrmCadastroManutencao(Usuario usuarioSistema) {
		setResizable(false);
		setTitle("Gestão de Locadora de Veículos");
		setModal(true);
		this.usuarioSistema = usuarioSistema;
		manutencaoSelecionada = null;
		veiculoSelecionado = null;
		manutencaoController = new ManutencaoController(usuarioSistema);
		setBounds(100, 100, 488, 338);
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
		
		JLabel lblCadastroDeManuteno = new JLabel("CADASTRO DE MANUTENÇÃO");
		lblCadastroDeManuteno.setForeground(Color.WHITE);
		lblCadastroDeManuteno.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTitulo.add(lblCadastroDeManuteno);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(Color.GRAY);
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Color.GRAY);
		pnlBottom.add(pnlBotoes, BorderLayout.EAST);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novo();
			}
		});
		
		btnNovo.setEnabled(true);
		pnlBotoes.add(btnNovo);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		
		btnSalvar.setEnabled(false);
		pnlBotoes.add(btnSalvar);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		
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
		
		btnCarregar.setEnabled(true);
		pnlBotoes.add(btnCarregar);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		
		pnlBotoes.add(btnSair);
		
		JPanel pnlManutencaoVeiculo = new JPanel();
		pnlManutencaoVeiculo.setLayout(null);
		pnlManutencaoVeiculo.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlManutencaoVeiculo, BorderLayout.CENTER);
		rgpStatusManutencao.add(rbtStManAbt);
		rbtStManAbt.setSelected(true);
		rbtStManAbt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManAbt.setEnabled(false);
		rbtStManAbt.setBackground(Color.LIGHT_GRAY);
		rbtStManAbt.setBounds(94, 170, 79, 23);
		pnlManutencaoVeiculo.add(rbtStManAbt);
		rgpStatusManutencao.add(rbtStManCan);
		rbtStManCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManCan.setEnabled(false);
		rbtStManCan.setBackground(Color.LIGHT_GRAY);
		rbtStManCan.setBounds(180, 170, 79, 23);
		pnlManutencaoVeiculo.add(rbtStManCan);
		rgpStatusManutencao.add(rbtStManCon);
		
		rbtStManCon.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbtStManCon.setEnabled(false);
		rbtStManCon.setBackground(Color.LIGHT_GRAY);
		rbtStManCon.setBounds(270, 170, 76, 23);
		pnlManutencaoVeiculo.add(rbtStManCon);
		
		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblId.setBounds(80, 23, 19, 14);
		pnlManutencaoVeiculo.add(lblId);
		
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
		edtId.setText("");
		edtId.setEnabled(true);
		edtId.setColumns(10);
		edtId.setBounds(94, 20, 225, 20);
		pnlManutencaoVeiculo.add(edtId);
		
		JLabel lblDtInicio = new JLabel("Início");
		lblDtInicio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDtInicio.setBounds(65, 98, 30, 14);
		pnlManutencaoVeiculo.add(lblDtInicio);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setBounds(55, 175, 40, 14);
		pnlManutencaoVeiculo.add(lblStatus);
		btnConsultarManutencoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaManutencoes();
			}
		});
		
		btnConsultarManutencoes.setEnabled(true);
		btnConsultarManutencoes.setBounds(324, 19, 25, 23);
		pnlManutencaoVeiculo.add(btnConsultarManutencoes);
		
		JLabel lblVeiculo = new JLabel("Veículo");
		lblVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVeiculo.setBounds(55, 48, 41, 14);
		pnlManutencaoVeiculo.add(lblVeiculo);
		
		edtIdVeiculo = new JTextField();
		edtIdVeiculo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				carregarVeiculoPorId();
			}
		});
		edtIdVeiculo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtils.restringirJTextFieldApenasNuMeros(e);
			}
		});
		edtIdVeiculo.setText("");
		edtIdVeiculo.setEnabled(false);
		edtIdVeiculo.setColumns(10);
		edtIdVeiculo.setBounds(94, 45, 35, 20);
		pnlManutencaoVeiculo.add(edtIdVeiculo);
		btnConsultarVeiculos.setFocusable(false);
		btnConsultarVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirFrmConsultaVeiculos();
			}
		});
		
		btnConsultarVeiculos.setEnabled(false);
		btnConsultarVeiculos.setBounds(324, 44, 25, 23);
		pnlManutencaoVeiculo.add(btnConsultarVeiculos);
		
		edtMarcaModeloVeiculo = new JTextField();
		edtMarcaModeloVeiculo.setText("");
		edtMarcaModeloVeiculo.setForeground(Color.WHITE);
		edtMarcaModeloVeiculo.setFocusable(false);
		edtMarcaModeloVeiculo.setEditable(false);
		edtMarcaModeloVeiculo.setColumns(10);
		edtMarcaModeloVeiculo.setBackground(Color.GRAY);
		edtMarcaModeloVeiculo.setBounds(132, 45, 187, 20);
		pnlManutencaoVeiculo.add(edtMarcaModeloVeiculo);
		
		try {
			mascaraData = new MaskFormatter(ViewUtils.getMascaraCampoData());
		} catch (ParseException e1) {
			mascaraData = null;
		}
		
		edtDtInicio = new JFormattedTextField(mascaraData);
		edtDtInicio.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtInicio);
			}
		});
		edtDtInicio.setEnabled(false);
		edtDtInicio.setBounds(94, 95, 257, 20);
		pnlManutencaoVeiculo.add(edtDtInicio);
		
		edtDtPrevFim = new JFormattedTextField(mascaraData);
		edtDtPrevFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtPrevFim);
			}
		});
		edtDtPrevFim.setEnabled(false);
		edtDtPrevFim.setBounds(94, 120, 257, 20);
		pnlManutencaoVeiculo.add(edtDtPrevFim);
		
		JLabel lblDtPrevFim = new JLabel("Previsão");
		lblDtPrevFim.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDtPrevFim.setBounds(50, 123, 48, 14);
		pnlManutencaoVeiculo.add(lblDtPrevFim);
		
		edtDtFim = new JFormattedTextField(mascaraData);
		edtDtFim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ViewUtils.limparJFormattedTextFieldData(edtDtFim);
			}
		});
		edtDtFim.setEnabled(false);
		edtDtFim.setBounds(94, 145, 257, 20);
		pnlManutencaoVeiculo.add(edtDtFim);
		
		JLabel lblDtFim = new JLabel("Término");
		lblDtFim.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDtFim.setBounds(53, 148, 45, 14);
		pnlManutencaoVeiculo.add(lblDtFim);

		edtDescricao = new JTextField();
		edtDescricao.setText("");
		edtDescricao.setEnabled(false);
		edtDescricao.setColumns(10);
		edtDescricao.setBounds(94, 70, 257, 20);
		pnlManutencaoVeiculo.add(edtDescricao);
		
		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDescricao.setBounds(45, 73, 55, 14);
		pnlManutencaoVeiculo.add(lblDescricao);

		limpar();
	}
	
	private void limpar() {
		manutencaoSelecionada = null;
		veiculoSelecionado = null;
		edtId.setText("");
		edtId.setEnabled(true);
		btnConsultarManutencoes.setEnabled(true);
		edtIdVeiculo.setText("");
		edtIdVeiculo.setEnabled(false);
		edtMarcaModeloVeiculo.setText("");
		btnConsultarVeiculos.setEnabled(false);
		edtDescricao.setText("");
		edtDescricao.setEnabled(false);
		edtDtInicio.setValue("");
		edtDtInicio.setEnabled(false);
		edtDtPrevFim.setValue("");
		edtDtPrevFim.setEnabled(false);
		edtDtFim.setValue("");
		edtDtFim.setEnabled(false);
		
		rgpStatusManutencao.setSelected(rbtStManAbt.getModel(), true);
		
		btnCarregar.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);
		
		edtId.requestFocus();
	}
	
	public void carregar() {
		try {
			if (!edtId.getText().trim().equals("")) {
				manutencaoSelecionada = manutencaoController.buscarPorId(Integer.parseInt(edtId.getText()));
			} else {
				JOptionPane.showMessageDialog(null, "Digite um id de manutenção.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			if (manutencaoSelecionada != null) {
				edtId.setText(String.valueOf(manutencaoSelecionada.getId()));
				edtId.setEnabled(false);
				btnConsultarManutencoes.setEnabled(false);
				edtIdVeiculo.setText(String.valueOf(manutencaoSelecionada.getVeiculo().getId()));
				edtIdVeiculo.setEnabled(false);
				edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(manutencaoSelecionada.getVeiculo()));
				btnConsultarVeiculos.setEnabled(false);
				edtDescricao.setText(manutencaoSelecionada.getDescricao());
				edtDescricao.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);

				edtDtInicio.setValue(ViewUtils.formatarDataParaJFormattedTextField(manutencaoSelecionada.getDtInicio()));
				edtDtInicio.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);
				edtDtPrevFim.setValue(ViewUtils.formatarDataParaJFormattedTextField(manutencaoSelecionada.getDtPrevFim()));
				edtDtPrevFim.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);
				edtDtFim.setValue(ViewUtils.formatarDataParaJFormattedTextField(manutencaoSelecionada.getDtFim()));
				edtDtFim.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);

				ViewUtils.setRadioGroupStatusManutencao(manutencaoSelecionada.getStatus(), rgpStatusManutencao, rbtStManAbt, rbtStManCan, rbtStManCon);
				rbtStManAbt.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);
				rbtStManCan.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);
				rbtStManCon.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);

				btnNovo.setEnabled(true);
				btnCarregar.setEnabled(false);
				btnSalvar.setEnabled(manutencaoSelecionada.getStatus() == StatusManutencao.EM_ABERTO);
				btnExcluir.setEnabled(true);
			}
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void salvar() {
		if (manutencaoSelecionada == null) {
			manutencaoSelecionada = new Manutencao(0);
			if (!edtId.getText().trim().equals("")) {
				manutencaoSelecionada.setId(Integer.parseInt(edtId.getText()));
			}
		}
		int idVeiculo = 0;
		if (!edtIdVeiculo.getText().trim().equals("")) {
			idVeiculo = Integer.parseInt(edtIdVeiculo.getText());
		}
		manutencaoSelecionada.setVeiculo(new Veiculo(idVeiculo));
		manutencaoSelecionada.setDescricao(edtDescricao.getText());
		manutencaoSelecionada.setStatus(ViewUtils.getStatusManutencaoFromRadioButton(rgpStatusManutencao, rbtStManAbt, rbtStManCan, rbtStManCon));
		
		try {
			manutencaoSelecionada.setDtInicio(ViewUtils.formatarDataDeJFormattedTextField(edtDtInicio, "Data de início inválida."));
			manutencaoSelecionada.setDtPrevFim(ViewUtils.formatarDataDeJFormattedTextField(edtDtPrevFim, "Data prevista de término inválida."));
			manutencaoSelecionada.setDtFim(ViewUtils.formatarDataDeJFormattedTextField(edtDtFim, "Data de término inválida."));

			String msgSucesso;
			if (manutencaoSelecionada.getId() == 0) {
				manutencaoSelecionada = manutencaoController.salvar(manutencaoSelecionada);
				edtId.setText(String.valueOf(manutencaoSelecionada.getId()));
				msgSucesso = "Manutenção cadastrada com sucesso.";
			} else {
				manutencaoController.editar(manutencaoSelecionada);
				msgSucesso = "Manutenção editada com sucesso.";
			}
			carregar();
			JOptionPane.showMessageDialog(null, msgSucesso);
		} catch (BusinessRuleException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void abrirFrmConsultaVeiculos() {
		FrmConsultaVeiculos frmConsultaVeiculos = new FrmConsultaVeiculos(usuarioSistema, true);
		frmConsultaVeiculos.setVisible(true);
		veiculoSelecionado = frmConsultaVeiculos.getVeiculoSelecionado();
		if (veiculoSelecionado != null) {
			edtIdVeiculo.setText(String.valueOf(veiculoSelecionado.getId()));
			edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculoSelecionado));
		}
	}
	
	private void excluir() {
		Object[] options = { "Sim", "Não" };
		int n = JOptionPane.showOptionDialog(null,
						"Tem certeza que deseja excluir esta manutenção?",
						"Excluir manutenção", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			int id = 0;
			if (!edtId.getText().trim().equals("")) {
				id = Integer.parseInt(edtId.getText());
			}
			try {
				manutencaoController.excluir(id);
				limpar();
				JOptionPane.showMessageDialog(null, "Manutenção excluída com sucesso.");
			} catch (BusinessRuleException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void novo() {
		manutencaoSelecionada = null;
		edtId.setText("");
		edtId.setEnabled(false);
		btnConsultarManutencoes.setEnabled(false);
		edtIdVeiculo.setEnabled(true);
		btnConsultarVeiculos.setEnabled(true);
		edtDescricao.setEnabled(true);
		edtDtInicio.setEnabled(true);
		edtDtPrevFim.setEnabled(true);
		edtDtFim.setEnabled(false);

		rgpStatusManutencao.setSelected(rbtStManAbt.getModel(), true);
		rbtStManAbt.setEnabled(true);
		rbtStManCan.setEnabled(true);
		rbtStManCon.setEnabled(true);

		btnCarregar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnNovo.setEnabled(false);
		btnExcluir.setEnabled(false);
		
		edtIdVeiculo.requestFocus();
	}
	
	private void sair() {
		setVisible(false);
	}
	
	private void abrirFrmConsultaManutencoes() {
		FrmConsultaManutencoes frmConsultaManutencoes = new FrmConsultaManutencoes(usuarioSistema, true);
		frmConsultaManutencoes.setVisible(true);
		manutencaoSelecionada = frmConsultaManutencoes.getManutencaoSelecionada();
		if (manutencaoSelecionada != null) {
			edtId.setText(String.valueOf(manutencaoSelecionada.getId()));
			carregar();
		}
	}
	
	private void carregarVeiculoPorId() {
		Veiculo veiculo = null;
		try {
			if (!edtIdVeiculo.getText().trim().equals("")) {
				veiculo = manutencaoController.buscarVeiculoPorId(Integer.parseInt(edtIdVeiculo.getText()));
				if (veiculo != null) {
					edtIdVeiculo.setText(String.valueOf(veiculo.getId()));
					edtMarcaModeloVeiculo.setText(ViewUtils.formatarDescricaoVeiculo(veiculo));
				}
			} else {
				edtMarcaModeloVeiculo.setText("");
			}
		} catch (BusinessRuleException ex) {
			edtMarcaModeloVeiculo.setText("");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			edtIdVeiculo.setText("");
		}
	}
	
}
