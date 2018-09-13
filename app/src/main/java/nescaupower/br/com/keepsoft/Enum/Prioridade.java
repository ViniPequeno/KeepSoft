package nescaupower.br.com.keepsoft.Enum;

public enum Prioridade {
    BAIXA("Baixa"), MEDIA("Média"), ALTA("Alta"), MUITO_ALTA("Muito alta");

    private String prioridade;

    Prioridade(final String text) {
        this.prioridade = text;
    }

    @Override
    public String toString(){
        return this.prioridade;
    }

    public Prioridade[] toArray(){
        return Prioridade.values();
    }
}
