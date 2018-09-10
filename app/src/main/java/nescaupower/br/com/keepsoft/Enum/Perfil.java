package nescaupower.br.com.keepsoft.Enum;

public enum Perfil {
    SCRUM_MASTER("Scrum Master"), PRODUCTOWNER("Product Owner"), TEAM("Scrum Team");

    private String perfil;

    Perfil(final String text) {
        this.perfil = text;
    }

    @Override
    public String toString(){
        return this.perfil;
    }

    public Perfil[] toArray(){
        return Perfil.values();
    }
}
