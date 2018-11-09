package nescaupower.br.com.keepsoft.Factory.Model;

import java.util.Date;

import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Prioridade;

public class Tarefa {
    private Long id;
    private String titulo;
    private String descricao;
    private String dataLimiteformat;
    private Date dataLimite;
    private Prioridade prioridade;
    private Dificuldade dificuldade;
    private Usuario usuario;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, String dataLimiteformat, Date dataLimite, Prioridade prioridade, Dificuldade dificuldade, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimiteformat = dataLimiteformat;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.dificuldade = dificuldade;
        this.usuario = usuario;
    }

    public Tarefa(Long id, String titulo, String descricao, String dataLimiteformat, Date dataLimite, Prioridade prioridade, Dificuldade dificuldade, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimiteformat = dataLimiteformat;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.dificuldade = dificuldade;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataLimiteformat() {
        return dataLimiteformat;
    }

    public void setDataLimiteformat(String dataLimiteformat) {
        this.dataLimiteformat = dataLimiteformat;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public nescaupower.br.com.keepsoft.Enum.Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(nescaupower.br.com.keepsoft.Enum.Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
