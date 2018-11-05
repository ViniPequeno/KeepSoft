package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Utils.Converters;

/**
 * Created by Aluno on 28/09/2018.
 */

public class Convite {
    private Long id;
    private Perfil funcao;
    private Usuario remetenteId;
    private Usuario destinatarioId;
    private Projeto codProjeto;
    private String dataCriacaoFormat;
    private Date dataCriacao;

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
        return codProjeto.getCodigo();
    }

    public void setCodProjeto(Projeto codProjeto) {
        this.codProjeto = codProjeto;
    }

    public Date getData() {
        return dataCriacao;
    }

    public void setData(Date data) {
        this.dataCriacao = data;
    }
}
