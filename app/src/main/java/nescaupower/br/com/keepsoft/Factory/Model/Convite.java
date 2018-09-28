package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Utils.Converters;

/**
 * Created by Aluno on 28/09/2018.
 */
@Entity(primaryKeys = {"remetenteId", "destinatarioId", "codProjeto"},
        foreignKeys = {@ForeignKey(
                entity = Usuario.class,
                parentColumns = "id",
                childColumns = "remetenteId"),
                @ForeignKey(
                        entity = Usuario.class,
                        parentColumns = "id",
                        childColumns = "destinatarioId"),
                @ForeignKey(
                        entity = Projeto.class,
                        parentColumns = "codigo",
                        childColumns = "codProjeto")})
public class Convite {
    @TypeConverters({Converters.class})
    Perfil funcao;
    private long remetenteId;
    private long destinatarioId;
    private long codProjeto;
    private Date data;

    public Convite() {
        remetenteId = 0;
        destinatarioId = 0;
        codProjeto = 0;
    }

    public Convite(long remetenteId, long destinatarioId, long codProjeto, Date data, Perfil funcao) {
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.codProjeto = codProjeto;
        this.data = data;
        this.funcao = funcao;
    }

    public long getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public long getCodProjeto() {
        return codProjeto;
    }

    public void setCodProjeto(long codProjeto) {
        this.codProjeto = codProjeto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Perfil getFuncao() {
        return funcao;
    }

    public void setFuncao(Perfil funcao) {
        this.funcao = funcao;
    }
}
