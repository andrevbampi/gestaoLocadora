package model;

public enum StatusVeiculo {
	
	DISPONIVEL("Disponível"),
	RESERVADO("Reservado"),
    MANUTENCAO("Em manutenção"),
    RESERVADO_MANUTENCAO("Reservado e em manutenção");
	
    private String status;
    private StatusVeiculo(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusVeiculo getStatusVeiculo(int numero){
        int i = 0;
        for (StatusVeiculo status : StatusVeiculo.values()){
            if(i == numero){
                return status;
            }
            i++;
        }
        return null;
    }
}
