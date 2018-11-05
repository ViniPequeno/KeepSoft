package nescaupower.br.com.keepsoft.Factory.Model;

import java.util.Date;


public class Perfil {
    private Long id;

    private nescaupower.br.com.keepsoft.Enum.Perfil perfil;

    private String dataInicioFormat;
    private String dataFimFormat;
    private Date dataInicio;
    private Date dataFim;

    private Projeto projeto;
    private Usuario usuario;

    public Perfil() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public nescaupower.br.com.keepsoft.Enum.Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(nescaupower.br.com.keepsoft.Enum.Perfil perfil) {
        this.perfil = perfil;
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

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDataInicioFormat() {
        return dataInicioFormat;
    }

    public void setDataInicioFormat(String dataInicioFormat) {
        this.dataInicioFormat = dataInicioFormat;
    }

    public String getDataFimFormat() {
        return dataFimFormat;
    }

    public void setDataFimFormat(String dataFimFormat) {
        this.dataFimFormat = dataFimFormat;
    }
}

