package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

import nescaupower.br.com.keepsoft.Factory.Factory;

public class Projeto {
    private Long codigo;
    private String nome;
    private String descricao;
    private String dataCriacaoFormat;
    private String dataFinalizacaoFormat;
    private String dataPrevFinalizacaoFormat;
    private Date dataCriacao;
    private Date dataFinalizacao;
    private Date dataPrevFinalizacao;

    private static Projeto ultimoProjetoUsado;

    public static Projeto getUltimoProjetoUsado() {
        if (ultimoProjetoUsado == null) {
            ultimoProjetoUsado = Factory.startProjeto();
        }
        return ultimoProjetoUsado;
    }

    public static void setUltimoProjetoUsado(Projeto projeto) {
        ultimoProjetoUsado = projeto;
    }


    private Usuario usuarioAdm;

    public Projeto() {
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

    public Date getDataPrevFinalizacao() {
        return dataPrevFinalizacao;
    }

    public void setDataPrevFinalizacao(Date dataPrevFinalizacao) {
        this.dataPrevFinalizacao = dataPrevFinalizacao;
    }

    public Usuario getUsuarioAdm() {
        return usuarioAdm;
    }

    public void setUsuarioAdm(Usuario usuarioAdm) {
        this.usuarioAdm = usuarioAdm;
    }

    public String getDataCriacaoFormat() {
        return dataCriacaoFormat;
    }

    public void setDataCriacaoFormat(String dataCriacaoFormat) {
        this.dataCriacaoFormat = dataCriacaoFormat;
    }

    public String getDataFinalizacaoFormat() {
        return dataFinalizacaoFormat;
    }

    public void setDataFinalizacaoFormat(String dataFinalizacaoFormat) {
        this.dataFinalizacaoFormat = dataFinalizacaoFormat;
    }

    public String getDataPrevFinalizacaoFormat() {
        return dataPrevFinalizacaoFormat;
    }

    public void setDataPrevFinalizacaoFormat(String dataPrevFinalizacaoFormat) {
        this.dataPrevFinalizacaoFormat = dataPrevFinalizacaoFormat;
    }
}
