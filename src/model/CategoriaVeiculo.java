package model;

public enum CategoriaVeiculo {
	
    PEQUENO("Pequeno"),
    FAMILIA("Família"),
    VAN("Van");
	
    private String categoria;
    private CategoriaVeiculo(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public static CategoriaVeiculo getCategoriaVeiculo(int numero){
        int i = 0;
        for (CategoriaVeiculo categoria : CategoriaVeiculo.values()){
            if(i == numero){
                return categoria;
            }
            i++;
        }
        return null;
    }
    
    public double getValorDiaria() {
    	switch (this) {
			case PEQUENO: return 50;
			case FAMILIA: return 60;
			case VAN: return 70;
			default: return 0;
    	}
    }
    
    public double getPercMultaDiaria() {
    	return 12.5;
    }

}
