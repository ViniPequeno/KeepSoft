package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Sprint {
    @PrimaryKey(autoGenerate = true)
    private long codigo;
    private String titulo;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    @ForeignKey(
            entity = Projeto.class,
            parentColumns = "codigo",
            childColumns = "codigo",
            onDelete = ForeignKey.CASCADE
    )
    private long codProjeto;

    public Sprint() {
        codigo = 0;
        titulo ="";
        descricao="";
        dataInicio = Calendar.getInstance().getTime();
    }

    public Sprint(long codigo, String titulo, String descricao, Date dataInicio, Date dataFim) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
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

    public long getCodProjeto() {return codProjeto;}

    public void setCodProjeto(long codProjeto) {this.codProjeto = codProjeto;}
}
