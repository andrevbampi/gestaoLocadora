package view.report;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

public class SimulacaoRelatorio extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<ModeloRelatorioReserva> listaReservas;
	private JLabel lblTitulo = new JLabel("RESERVA DE VEÍCULO");
	private JLabel lblDescNomeCliente = new JLabel("Cliente:");
	private JLabel lblNomeCliente = new JLabel("Cliente");
	private JLabel lblDescEnderecoCliente = new JLabel("Endereço: ");
	private JLabel lblEnderecoCliente1 = new JLabel("Endereço 1");
	private JLabel lblEnderecoCliente2 = new JLabel("Endereço 2");
	private JLabel lblDescTelefoneCliente = new JLabel("Telefone:");
	private JLabel lblTelefoneCliente = new JLabel("Telefone");
	private JLabel lblLinha1 = new JLabel("____________________________________________________________________________________");
	private JLabel lblDescDescricaoVeiculo = new JLabel("Veículo:");
	private JLabel lblDescricaoVeiculo = new JLabel("Veículo");
	private JLabel lblDescCategoriaVeiculo = new JLabel("Categoria:");
	private JLabel lblCategoriaVeiculo = new JLabel("Categoria");
	private JLabel lblLinha2 = new JLabel("____________________________________________________________________________________");
	private JLabel lblDescDataContratacaoReserva = new JLabel("Contratação:");
	private JLabel lblDataContratacaoReserva = new JLabel("Data de contratação");
	private JLabel lblDataPrevDevolucaoReserva = new JLabel("Data prevista de entrega");
	private JLabel lblDescDataPrevDevolucaoReserva = new JLabel("Previsão de entrega:");
	private JLabel lblDescDataDevolucaoReserva = new JLabel("Devolução:");
	private JLabel lblDataDevolucaoReserva = new JLabel("Data de devolução");
	private JLabel lblDescStatusReserva = new JLabel("Status:");
	private JLabel lblStatusReserva = new JLabel("Status");
	private JLabel lblLinha3 = new JLabel("____________________________________________________________________________________");
	private JLabel lblDescValorDiariaReserva = new JLabel("Valor diária:");
	private JLabel lblValorDiariaReserva = new JLabel("Valor diária");
	private JLabel lblDescPercMultaDiariaReserva = new JLabel("Percentual multa:");
	private JLabel lblPercMultaDiariaReserva = new JLabel("Percentual multa");
	private JLabel lblDescDiasPrevReserva = new JLabel("Dias previstos:");
	private JLabel lblDiasPrevReserva = new JLabel("Dias previstos");
	private JLabel lblDescDiasAtrasoReserva = new JLabel("Dias de atraso:");
	private JLabel lblDiasAtrasoReserva = new JLabel("Dias de atraso");
	private JLabel lblDescTotalDiasReserva = new JLabel("Dias totais:");
	private JLabel lblTotalDiasReserva = new JLabel("Dias totais");
	private JLabel lblDescMultaTotalReserva = new JLabel("Valor multa:");
	private JLabel lblMultaTotalReserva = new JLabel("Valor multa");
	private JLabel lblDecValorTotalPrevReserva = new JLabel("Valor previsto:");
	private JLabel lblValorTotalPrevReserva = new JLabel("Valor previsto:");
	private JLabel lblDescValorTotalReserva = new JLabel("Valor total:");
	private JLabel lblValorTotalReserva = new JLabel("Valor total");
	private JLabel lblAssinaturaLocadora = new JLabel("__________________________________");
	private JLabel lblAssinaturaCliente = new JLabel("__________________________________");
	private JLabel lblDescAssinaturaLocadora = new JLabel("Assinatura da locadora");
	private JLabel lblDescAssinaturaCliente = new JLabel("Assinatura do cliente");

	public SimulacaoRelatorio(List<ModeloRelatorioReserva> listaReservas) {
		setModal(true);
		this.listaReservas = listaReservas;
		setBounds(100, 100, 763, 810);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(43, 27, 664, 733);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTitulo.setBounds(172, 39, 336, 82);
		panel.add(lblTitulo);
		
		lblDescNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescNomeCliente.setBounds(43, 135, 46, 14);
		panel.add(lblDescNomeCliente);
		
		lblNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeCliente.setBounds(95, 135, 521, 14);
		panel.add(lblNomeCliente);
		
		lblDescEnderecoCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescEnderecoCliente.setBounds(28, 155, 65, 14);
		panel.add(lblDescEnderecoCliente);
		
		lblEnderecoCliente1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEnderecoCliente1.setBounds(95, 155, 521, 14);
		panel.add(lblEnderecoCliente1);
		
		lblEnderecoCliente2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEnderecoCliente2.setBounds(95, 175, 521, 14);
		panel.add(lblEnderecoCliente2);
		
		lblDescTelefoneCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescTelefoneCliente.setBounds(32, 195, 66, 14);
		panel.add(lblDescTelefoneCliente);
		
		lblTelefoneCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTelefoneCliente.setBounds(95, 195, 521, 14);
		panel.add(lblTelefoneCliente);
		
		lblLinha1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLinha1.setBounds(28, 210, 588, 14);
		panel.add(lblLinha1);
		
		lblDescDescricaoVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescDescricaoVeiculo.setBounds(42, 235, 53, 14);
		panel.add(lblDescDescricaoVeiculo);
		
		lblDescricaoVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescricaoVeiculo.setBounds(95, 235, 521, 14);
		panel.add(lblDescricaoVeiculo);
		
		lblDescCategoriaVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescCategoriaVeiculo.setBounds(30, 255, 66, 14);
		panel.add(lblDescCategoriaVeiculo);
		
		lblCategoriaVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCategoriaVeiculo.setBounds(95, 255, 521, 14);
		panel.add(lblCategoriaVeiculo);
		
		lblLinha2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLinha2.setBounds(28, 270, 588, 14);
		panel.add(lblLinha2);
		
		lblDescDataContratacaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescDataContratacaoReserva.setBounds(63, 295, 77, 14);
		panel.add(lblDescDataContratacaoReserva);
		
		lblDataContratacaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataContratacaoReserva.setBounds(139, 295, 477, 14);
		panel.add(lblDataContratacaoReserva);
		
		lblDataPrevDevolucaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataPrevDevolucaoReserva.setBounds(139, 320, 477, 14);
		panel.add(lblDataPrevDevolucaoReserva);
		
		lblDescDataPrevDevolucaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescDataPrevDevolucaoReserva.setBounds(20, 320, 118, 14);
		panel.add(lblDescDataPrevDevolucaoReserva);
		
		lblDescDataDevolucaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescDataDevolucaoReserva.setBounds(73, 345, 65, 14);
		panel.add(lblDescDataDevolucaoReserva);
		
		lblDataDevolucaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataDevolucaoReserva.setBounds(139, 345, 477, 14);
		panel.add(lblDataDevolucaoReserva);
		
		lblDescStatusReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescStatusReserva.setBounds(95, 370, 46, 14);
		panel.add(lblDescStatusReserva);
		
		lblStatusReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatusReserva.setBounds(139, 370, 477, 14);
		panel.add(lblStatusReserva);
		
		lblLinha3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLinha3.setBounds(28, 385, 588, 14);
		panel.add(lblLinha3);
		
		lblDescValorDiariaReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescValorDiariaReserva.setBounds(72, 410, 68, 14);
		panel.add(lblDescValorDiariaReserva);
		
		lblValorDiariaReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblValorDiariaReserva.setBounds(139, 410, 180, 14);
		panel.add(lblValorDiariaReserva);
		
		lblDescPercMultaDiariaReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescPercMultaDiariaReserva.setBounds(332, 410, 100, 14);
		panel.add(lblDescPercMultaDiariaReserva);
		
		lblPercMultaDiariaReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPercMultaDiariaReserva.setBounds(436, 410, 180, 14);
		panel.add(lblPercMultaDiariaReserva);
		
		lblDescDiasPrevReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescDiasPrevReserva.setBounds(57, 435, 83, 14);
		panel.add(lblDescDiasPrevReserva);
		
		lblDiasPrevReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDiasPrevReserva.setBounds(139, 435, 180, 14);
		panel.add(lblDiasPrevReserva);
		
		lblDescDiasAtrasoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescDiasAtrasoReserva.setBounds(348, 435, 85, 14);
		panel.add(lblDescDiasAtrasoReserva);
		
		lblDiasAtrasoReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDiasAtrasoReserva.setBounds(436, 435, 180, 14);
		panel.add(lblDiasAtrasoReserva);
		
		lblDescTotalDiasReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescTotalDiasReserva.setBounds(75, 460, 65, 14);
		panel.add(lblDescTotalDiasReserva);
		
		lblTotalDiasReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotalDiasReserva.setBounds(139, 460, 180, 14);
		panel.add(lblTotalDiasReserva);
		
		lblDescMultaTotalReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescMultaTotalReserva.setBounds(363, 460, 70, 14);
		panel.add(lblDescMultaTotalReserva);
		
		lblMultaTotalReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMultaTotalReserva.setBounds(436, 460, 180, 14);
		panel.add(lblMultaTotalReserva);
		
		lblDecValorTotalPrevReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDecValorTotalPrevReserva.setBounds(57, 485, 83, 14);
		panel.add(lblDecValorTotalPrevReserva);
		
		lblValorTotalPrevReserva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblValorTotalPrevReserva.setBounds(139, 485, 477, 14);
		panel.add(lblValorTotalPrevReserva);
		
		lblDescValorTotalReserva.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDescValorTotalReserva.setBounds(40, 520, 124, 31);
		panel.add(lblDescValorTotalReserva);
		
		lblValorTotalReserva.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblValorTotalReserva.setBounds(149, 520, 467, 31);
		panel.add(lblValorTotalReserva);
		
		lblAssinaturaLocadora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAssinaturaLocadora.setBounds(63, 650, 240, 14);
		panel.add(lblAssinaturaLocadora);
		
		lblAssinaturaCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAssinaturaCliente.setBounds(363, 650, 240, 14);
		panel.add(lblAssinaturaCliente);
		
		lblDescAssinaturaLocadora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescAssinaturaLocadora.setBounds(118, 675, 132, 14);
		panel.add(lblDescAssinaturaLocadora);
		
		lblDescAssinaturaCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescAssinaturaCliente.setBounds(425, 675, 132, 14);
		panel.add(lblDescAssinaturaCliente);
		
		gerarRelatorioSimulado();
	}
	
	private void gerarRelatorioSimulado() {
		ModeloRelatorioReserva modelo = listaReservas.get(0);
		
		lblNomeCliente.setText(modelo.getNomeCliente());
		lblEnderecoCliente1.setText(modelo.getEnderecoCliente1());
		lblEnderecoCliente2.setText(modelo.getEnderecoCliente2());
		lblTelefoneCliente.setText(modelo.getTelefoneCliente());
		lblDescricaoVeiculo.setText(modelo.getDescricaoVeiculo());
		lblCategoriaVeiculo.setText(modelo.getCategoriaVeiculo());
		lblDataContratacaoReserva.setText(modelo.getDataContratacaoReserva());
		lblDataPrevDevolucaoReserva.setText(modelo.getDataPrevDevolucaoReserva());
		lblDataDevolucaoReserva.setText(modelo.getDataDevolucaoReserva());
		lblValorDiariaReserva.setText(modelo.getValorDiariaReserva());
		lblPercMultaDiariaReserva.setText(modelo.getPercMultaDiariaReserva());
		lblValorTotalPrevReserva.setText(modelo.getValorTotalPrevReserva());
		lblDiasPrevReserva.setText(modelo.getDiasPrevReserva());
		lblTotalDiasReserva.setText(modelo.getTotalDiasReserva());
		lblMultaTotalReserva.setText(modelo.getMultaTotalReserva());
		lblDiasAtrasoReserva.setText(modelo.getDiasAtrasoReserva());
		lblValorTotalReserva.setText(modelo.getValorTotalReserva());
		lblStatusReserva.setText(modelo.getStatusReserva());
	}
}
