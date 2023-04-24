package controller.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
    
    public BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
