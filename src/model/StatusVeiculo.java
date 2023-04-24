package model;

public enum StatusVeiculo {
	
    RESERVADO("Reservado"),
    DISPONIVEL("Disponível"),
    MANUTENCAO("Manutenção");
	
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
