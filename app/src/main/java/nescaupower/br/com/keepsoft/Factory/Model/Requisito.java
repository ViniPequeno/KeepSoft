package nescaupower.br.com.keepsoft.Factory.Model;

import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Prioridade;

public class Requisito {
    private Long codigo;
    private String nome;
    private String descricao;
    private Prioridade prioridade;
    private Dificuldade dificuldade;

    public Requisito() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }
}
