package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

import nescaupower.br.com.keepsoft.Utils.Converters;

@Entity(indices = {
        @Index(
            value = {"codProjeto","idUsuario","perfil"},
            unique = true)},
        foreignKeys = {
        @ForeignKey(
                entity = Projeto.class,
                parentColumns = "codigo",
                childColumns = "codProjeto",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Usuario.class,
                parentColumns = "id",
                childColumns = "idUsuario"
        )
})
public class Perfil {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @TypeConverters({Converters.class})
    private nescaupower.br.com.keepsoft.Enum.Perfil perfil;

    private Date dataInicio;
    private Date dataFim;

    private long codProjeto;
    private long idUsuario;

    public Perfil() {
        id = 0;
        dataInicio = Calendar.getInstance().getTime();
    }

    public Perfil(long id, Date dataInicio, Date dataFim) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataInicio() {

        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public nescaupower.br.com.keepsoft.Enum.Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(nescaupower.br.com.keepsoft.Enum.Perfil perfil) {
        this.perfil = perfil;
    }

    public long getCodProjeto() {
        return codProjeto;
    }

    public void setCodProjeto(long codProjeto) {
        this.codProjeto = codProjeto;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

}

