package model;

public enum GeneroUsuario {
	
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");
	
    private String genero;
    private GeneroUsuario(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public static GeneroUsuario getGeneroUsuario(int numero){
        int i = 0;
        for (GeneroUsuario genero : GeneroUsuario.values()){
            if(i == numero){
                return genero;
            }
            i++;
        }
        return null;
    }
    
}
