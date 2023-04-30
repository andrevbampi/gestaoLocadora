package model;

public enum StatusManutencao {

	EM_ABERTO("Em aberto"),
    CANCELADA("Cancelada"),
	CONCLUIDA("Concluída");
	
    private String status;
    private StatusManutencao(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusManutencao getStatusManutencao(int numero){
        int i = 0;
        for (StatusManutencao status : StatusManutencao.values()){
            if(i == numero){
                return status;
            }
            i++;
        }
        return null;
    }
    
}
