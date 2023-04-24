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

}
