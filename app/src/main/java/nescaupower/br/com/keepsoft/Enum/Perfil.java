package nescaupower.br.com.keepsoft.Enum;

public enum Perfil {
    SCRUM(1), PRODUCTOWNER(2), TEAM(3);

    private int perfil;

    Perfil(int i) {
        this.perfil = i;
    }


    public int getPerfil(){
        return this.perfil;
    }
}
