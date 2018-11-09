package nescaupower.br.com.keepsoft.Enum;

public enum Dificuldade {
    FACIL("Fácil"), MEDIO("Médio"), DIFICL("Difícil"), MUITO_DIFICIL("Muito Difícil");

    private String dificuldade;

    Dificuldade(final String text) {
        this.dificuldade = text;
    }

    @Override
    public String toString(){
        return this.dificuldade;
    }

    public Dificuldade[] toArray(){
        return Dificuldade.values();
    }
}
