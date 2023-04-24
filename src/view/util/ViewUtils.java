package view.util;

import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.exception.BusinessException;
import model.CategoriaVeiculo;
import model.GeneroUsuario;
import model.StatusReserva;
import model.StatusVeiculo;
import model.Veiculo;

public abstract class ViewUtils {
	
	public static String getFormatoData() {
		return "dd/MM/yyyy-HH:mm";
	}

	public static String getMascaraCampoData() {
		return "##/##/####-##:##";
	}
	
	public static void restringirJTextFieldApenasNuMeros(KeyEvent e) {
		String caracteres = "0987654321";
		if(!caracteres.contains(e.getKeyChar() + "")){
			e.consume();
		}
	}
	
	public static void limitarCaracteresJTextField(JTextField textField, KeyEvent e, int qtde) {
		if (textField.getText().length() >= qtde){
			e.consume();
		}
	}
	
	public static String formatarDescricaoVeiculo(Veiculo veiculo) {
		return veiculo.getMarca() + " / " + veiculo.getModelo() + " " + veiculo.getAno() + " " + veiculo.getCor();
	}
	
	public static String formatarDataParaJFormattedTextField(Date dataDate) {
		SimpleDateFormat formato = new SimpleDateFormat(ViewUtils.getFormatoData());
		if (dataDate != null) {
        	return formato.format(dataDate);
        }
		return "";
	}
	
	public static Date formatarDataDeJFormattedTextField(JFormattedTextField edtData, String msgErro) throws BusinessException {
		SimpleDateFormat formato = new SimpleDateFormat(ViewUtils.getFormatoData());
		Date dataFormatada;
		try {
			String strDtAux = edtData.getText().replace("/", "").replace("-", "").replace(":", "").trim();
			if (!strDtAux.equals("")) {
				dataFormatada = new Date(formato.parse(edtData.getText()).getTime());
				return dataFormatada;
			}
		} catch (ParseException e) {
			throw new BusinessException(msgErro);
		}
		return null;
	}
	
	public static void limparJFormattedTextFieldData(JFormattedTextField edtData) {
		String strDt = edtData.getText().replace("/", "").replace("-", "").replace(":", "").trim();
		if (strDt.equals("")) {
			edtData.setValue("");
		}
	}
	
	public static void setRadioGroupGenero(GeneroUsuario genero, ButtonGroup rgpGenero, JRadioButton rbtGenM, JRadioButton rbtGenF, JRadioButton rbtGenO) {
		switch (genero) {
			case MASCULINO: rgpGenero.setSelected(rbtGenM.getModel(), true); break;
			case FEMININO: rgpGenero.setSelected(rbtGenF.getModel(), true); break;
			case OUTRO : rgpGenero.setSelected(rbtGenO.getModel(), true);
		}
	}
	
	public static GeneroUsuario getGeneroFromRadioButton(ButtonGroup rgpGenero, JRadioButton rbtGenM, JRadioButton rbtGenF, JRadioButton rbtGenO) {
		if (rgpGenero.getSelection() == rbtGenM.getModel()) {
			return GeneroUsuario.MASCULINO;
		} else if (rgpGenero.getSelection() == rbtGenF.getModel()) {
			return GeneroUsuario.FEMININO;
		} else if (rgpGenero.getSelection() == rbtGenO.getModel()) {
			return GeneroUsuario.OUTRO;
		}
		return null;
	}
	
	public static void setRadioGroupCategoriaVeiculo(CategoriaVeiculo categoria, ButtonGroup rgpCategoriaVeiculo, JRadioButton rbtCatPeq, JRadioButton rbtCatFam, JRadioButton rbtCatVan) {
		switch (categoria) {
			case PEQUENO: rgpCategoriaVeiculo.setSelected(rbtCatPeq.getModel(), true); break;
			case FAMILIA: rgpCategoriaVeiculo.setSelected(rbtCatFam.getModel(), true); break;
			case VAN : rgpCategoriaVeiculo.setSelected(rbtCatVan.getModel(), true);
		}
	}
	
	public static CategoriaVeiculo getCategoriaVeiculoFromRadioButton(ButtonGroup rgpCategoriaVeiculo, JRadioButton rbtCatPeq, JRadioButton rbtCatFam, JRadioButton rbtCatVan) {
		if (rgpCategoriaVeiculo.getSelection() == rbtCatPeq.getModel()) {
			return CategoriaVeiculo.PEQUENO;
		} else if (rgpCategoriaVeiculo.getSelection() == rbtCatFam.getModel()) {
			return CategoriaVeiculo.FAMILIA;
		} else if (rgpCategoriaVeiculo.getSelection() == rbtCatVan.getModel()) {
			return CategoriaVeiculo.VAN;
		}
		return null;
	}
	
	public static void setRadioGroupStatusVeiculo(StatusVeiculo status, ButtonGroup rgpStatusVeiculo, JRadioButton rbtStRes, JRadioButton rbtStDisp, JRadioButton rbtStMan) {
		switch (status) {
			case RESERVADO: rgpStatusVeiculo.setSelected(rbtStRes.getModel(), true); break;
			case DISPONIVEL: rgpStatusVeiculo.setSelected(rbtStDisp.getModel(), true); break;
			case MANUTENCAO : rgpStatusVeiculo.setSelected(rbtStMan.getModel(), true);
		}
	}
	
	public static StatusVeiculo getStatusVeiculoFromRadioButton(ButtonGroup rgpStatusVeiculo, JRadioButton rbtStRes, JRadioButton rbtStDisp, JRadioButton rbtStMan) {
		if (rgpStatusVeiculo.getSelection() == rbtStRes.getModel()) {
			return StatusVeiculo.RESERVADO;
		} else if (rgpStatusVeiculo.getSelection() == rbtStDisp.getModel()) {
			return StatusVeiculo.DISPONIVEL;
		} else if (rgpStatusVeiculo.getSelection() == rbtStMan.getModel()) {
			return StatusVeiculo.MANUTENCAO;
		}
		return null;
	}
	
	public static void setRadioGroupStatusReserva(StatusReserva status, ButtonGroup rgpStatusReserva, JRadioButton rbtStAnd, JRadioButton rbtStCan, JRadioButton rbtStPago) {
		switch (status) {
			case EM_ANDAMENTO: rgpStatusReserva.setSelected(rbtStAnd.getModel(), true); break;
			case CANCELADO: rgpStatusReserva.setSelected(rbtStCan.getModel(), true); break;
			case PAGO : rgpStatusReserva.setSelected(rbtStPago.getModel(), true);
		}
	}
	
	public static StatusReserva getStatusReservaFromRadioButton(ButtonGroup rgpStatusReserva, JRadioButton rbtStAnd, JRadioButton rbtStCan, JRadioButton rbtStPago) {
		if (rgpStatusReserva.getSelection() == rbtStAnd.getModel()) {
			return StatusReserva.EM_ANDAMENTO;
		} else if (rgpStatusReserva.getSelection() == rbtStCan.getModel()) {
			return StatusReserva.CANCELADO;
		} else if (rgpStatusReserva.getSelection() == rbtStPago.getModel()) {
			return StatusReserva.PAGO;
		}
		return null;
	}

}
