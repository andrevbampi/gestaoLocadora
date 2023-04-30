package model;

public enum StatusReserva {
	
    EM_ANDAMENTO("Em andamento"),
    CANCELADO("Cancelado"),
    PAGO("Pago");
	
    private String status;
    private StatusReserva(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusReserva getStatusReserva(int numero){
        int i = 0;
        for (StatusReserva status : StatusReserva.values()){
            if(i == numero){
                return status;
            }
            i++;
        }
        return null;
    }
    
}
