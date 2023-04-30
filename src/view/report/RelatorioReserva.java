package view.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import controller.exception.BusinessRuleException;
import model.Endereco;
import model.Reserva;
import model.Usuario;
import model.Veiculo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.util.ViewUtils;

public abstract class RelatorioReserva {

	public static void gerarRelatorio(Reserva reserva) throws BusinessRuleException {
		// TODO Resolver problemas do iReport/JasperReports
		/*try {
			Connection connection = ConnectionMariaDB.getConnection();
			//try {
				InputStream arquivo = RelatorioReserva.class.getResourceAsStream("RelatorioReserva.jrxml");
				JasperReport report =  JasperCompileManager.compileReport(arquivo);
				JasperPrint print = JasperFillManager.fillReport(report, null, connection);
				JasperViewer view = new JasperViewer(print);
				view.setVisible(true);
			//} finally {
			//	ConnectionMariaDB.closeConnection();
			//}
		} catch (JRException ex) {
			throw new BusinessRuleException("Erro ao gerar relatório: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new BusinessRuleException("Erro de conexão de banco de dados ao gerar o relatório: " + ex.getMessage());
		}*/
		
		try {
			List<ModeloRelatorioReserva>listaReservas = setarValores(reserva);
			//InputStream arquivo = RelatorioReserva.class.getResourceAsStream("RelatorioTesteLista.jrxml");
			InputStream arquivo = RelatorioReserva.class.getResourceAsStream("RelatorioReserva.jrxml");
				
			JasperReport jasperReport =  JasperCompileManager.compileReport(arquivo);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(listaReservas));
			
			//JasperViewer.viewReport(print, false);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint);

			JDialog viewer = new JDialog(new javax.swing.JFrame(),"Visualização do Relatório", true);
			
			Toolkit tk = Toolkit.getDefaultToolkit();
			//Dimension screenSize = tk.getScreenSize();
			//viewer.setBounds(0, 0, screenSize.width, screenSize.height-40);
			viewer.setSize(1024, 768);
			viewer.getContentPane().add(jasperViewer.getContentPane());
			viewer.setLocationRelativeTo(null);
			viewer.setVisible(true);
			//jasperViewer.setVisible(true);
			//jasperViewer.setVisible(false);
		} catch (JRException ex) {
			throw new BusinessRuleException("Erro ao gerar relatório: " + ex.getMessage());
		}
		
		//List<ModeloRelatorioReserva>listaReservas = setarValores(reserva);
		//gerarRelatorioSimulado(listaReservas);
	}
	
	private static List<ModeloRelatorioReserva> setarValores(Reserva reserva) {
		List<ModeloRelatorioReserva>listaReservas = new ArrayList<ModeloRelatorioReserva>();
		ModeloRelatorioReserva modelo = new ModeloRelatorioReserva();
		
		Usuario cliente = reserva.getCliente();
		Veiculo veiculo = reserva.getVeiculo();
		Endereco endereco = cliente.getEndereco();
		String endereco1 = endereco.getLogradouro() + ", número " 
				+ endereco.getNumero() + ", " 
				+ endereco.getComplemento();
		String endereco2 = "Cep " + ViewUtils.formatarCep(endereco.getCep()) + ", " 
				+ endereco.getBairro() + ", " 
				+ endereco.getCidade() + ", "
				+ endereco.getUf();
		String descricaoVeiculo = veiculo.getMarca() + " "
				+ veiculo.getModelo() + " "
				+ veiculo.getCor() 
				+ " ano " + veiculo.getAno()
				+ " placa " + ViewUtils.formatarPlaca(veiculo.getPlaca());
		
		modelo.setNomeCliente(cliente.getNome());
		modelo.setEnderecoCliente1(endereco1);
		modelo.setEnderecoCliente2(endereco2);
		modelo.setTelefoneCliente(cliente.getTelefone());
		modelo.setDescricaoVeiculo(descricaoVeiculo);
		modelo.setCategoriaVeiculo(veiculo.getCategoria().getCategoria());
		modelo.setCodigoReserva(String.valueOf(reserva.getId()));
		modelo.setDataContratacaoReserva(ViewUtils.formatarDataParaJFormattedTextField(reserva.getDtContratacao()));
		modelo.setDataPrevDevolucaoReserva(ViewUtils.formatarDataParaJFormattedTextField(reserva.getDtPrevDevolucao()));
		modelo.setDataDevolucaoReserva(ViewUtils.formatarDataParaJFormattedTextField(reserva.getDtDevolucao()));
		modelo.setValorDiariaReserva(ViewUtils.formatarPrecoEmReais(reserva.getValorDiaria()));
		modelo.setPercMultaDiariaReserva(ViewUtils.formatarPercentual(reserva.getPercMultaDiaria()));
		modelo.setValorTotalPrevReserva(ViewUtils.formatarPrecoEmReais(reserva.getValorTotalPrev()));
		modelo.setDiasPrevReserva(String.valueOf(reserva.getDiasPrev()));
		modelo.setTotalDiasReserva(String.valueOf(reserva.getTotalDias()));
		modelo.setMultaTotalReserva(ViewUtils.formatarPrecoEmReais(reserva.getMultaTotal()));
		modelo.setDiasAtrasoReserva(String.valueOf(reserva.getDiasAtraso()));
		modelo.setValorTotalReserva(ViewUtils.formatarPrecoEmReais(reserva.getValorTotal()));
		modelo.setStatusReserva(reserva.getStatus().getStatus());
		
		listaReservas.add(modelo);
		
		return listaReservas;
	}
	
	private static void gerarRelatorioSimulado(List<ModeloRelatorioReserva> listaReservas) {
		SimulacaoRelatorio simulacaoRelatorio = new SimulacaoRelatorio(listaReservas);
		simulacaoRelatorio.setLocationRelativeTo(null);
		simulacaoRelatorio.setVisible(true);
	}
	
}
