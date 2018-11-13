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
    private String dataEnvioFormat;
    private Date dataEnvio;
    private boolean visto;

    public Convite() {
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

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public String getDataEnvioFormat() {
        return dataEnvioFormat;
    }

    public void setDataEnvioFormat(String dataEnvioFormat) {
        this.dataEnvioFormat = dataEnvioFormat;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
