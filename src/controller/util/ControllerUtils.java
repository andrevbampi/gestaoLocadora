package controller.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Calendar;

import controller.exception.BusinessRuleException;
import model.Reserva;
import model.StatusReserva;

public abstract class ControllerUtils {
	
	public static int contarDias(Date dataInicial, Date dataFinal) {
		if (dataInicial == null || dataFinal == null) {
			return 0;
		}
		Calendar calInicial = Calendar.getInstance();
		Calendar calFinal = Calendar.getInstance();
		calInicial.setTime(dataInicial);
		calFinal.setTime(dataFinal);
		return calFinal.get(Calendar.DAY_OF_YEAR) - calInicial.get(Calendar.DAY_OF_YEAR);
	}

	public static int contarTotalDiasReserva(Reserva reserva) {
		if (reserva.getStatus() == StatusReserva.CANCELADO) {
			return 0;
		}
		Date dataInicial = reserva.getDtContratacao();
		Date dataFinal = reserva.getDtDevolucao();
		return contarDias(dataInicial, dataFinal);
	}
	
	public static int contarDiasPrevistosReserva(Reserva reserva) {
		Date dataInicial = reserva.getDtContratacao();
		Date dataFinal = reserva.getDtPrevDevolucao();
		return contarDias(dataInicial, dataFinal);
	}
	
	public static int contarDiasAtrasoReserva(Reserva reserva) {
		if (reserva.getStatus() == StatusReserva.CANCELADO) {
			return 0;
		}
		int diasAtrasoReserva = contarTotalDiasReserva(reserva) - contarDiasPrevistosReserva(reserva);
		if (diasAtrasoReserva < 0) {
			diasAtrasoReserva = 0;
		}
		return diasAtrasoReserva;
	}
	
	public static String gerarHashMD5(String texto) throws BusinessRuleException {
		MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
        	throw new BusinessRuleException("Erro ao gerar hash: " + ex.getMessage());
        }
        BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
        return hash.toString(16);
	}
}
