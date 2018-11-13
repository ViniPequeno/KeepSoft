package nescaupower.br.com.keepsoft.Factory.Model;

import java.util.Date;

import nescaupower.br.com.keepsoft.Enum.Perfil;

/**
 * Created by Aluno on 28/09/2018.
 */

public class Convite {
    private Long id;
    private Perfil funcao;
    private Usuario remetenteId;
    private Usuario destinatarioId;
    private Projeto projeto;
    private String dataCriacaoFormat;
    private Date dataCriacao;
    private boolean visto;

    public Convite() {
    }

    public String getDataFormat() {
        return dataCriacaoFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataCriacaoFormat = dataFormat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Perfil getFuncao() {
        return funcao;
    }

    public void setFuncao(Perfil funcao) {
        this.funcao = funcao;
    }

    public Long getRemetenteId() {
        return remetenteId.getId();
    }

    public void setRemetenteId(Usuario remetenteId) {
        this.remetenteId = remetenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId.getId();
    }

    public void setDestinatarioId(Usuario destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public Long getCodProjeto() {
        return projeto.getCodigo();
    }

    public void setCodProjeto(Projeto codProjeto) {
        this.projeto = codProjeto;
    }

    public Date getData() {
        return dataCriacao;
    }

    public void setData(Date data) {
        this.dataCriacao = data;
    }

    public String getDataCriacaoFormat() {
        return dataCriacaoFormat;
    }

    public void setDataCriacaoFormat(String dataCriacaoFormat) {
        this.dataCriacaoFormat = dataCriacaoFormat;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
