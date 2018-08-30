package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Projeto {
    @PrimaryKey(autoGenerate = true)
    private long codigo;
    private String nome;
    private String descricao;
    private Date dataCriacao;
    private Date dataFinalizacao;
    private Date dataPrevFinalizacao;

    private static Projeto getProjeto;

    public static Projeto getGetProjeto() {
        if (getProjeto == null) {
            getProjeto = new Projeto();
        }
        return getProjeto;
    }

    public static void setGetProjeto(Projeto projeto) {
        getProjeto = projeto;
    }

    @ForeignKey(
            entity = Usuario.class,
            parentColumns = "id",
            childColumns = "codigo"
    )
    private long idUsuario;

    public Projeto() {
        codigo = 0;
        nome = "";
        descricao = "";
        dataCriacao = Calendar.getInstance().getTime();
    }

    public Projeto(long codigo, String nome, String descricao, Date dataCriacao, Date dataFinalizacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataFinalizacao = dataFinalizacao;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getDataPrevFinalizacao() {
        return dataPrevFinalizacao;
    }

    public void setDataPrevFinalizacao(Date dataPrevFinalizacao) {
        this.dataPrevFinalizacao = dataPrevFinalizacao;
    }
}
