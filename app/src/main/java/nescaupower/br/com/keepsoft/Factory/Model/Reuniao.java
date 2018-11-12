package nescaupower.br.com.keepsoft.Factory.Model;

import java.util.Date;

public class Reuniao {
    private Long id;
    private String nome;
    private String relatorio;
    private String assunto;
    private String local;

    private String dataInicioFormat;
    private String horaInicioFormat;
    private String horaFimFormat;

    private Date dataInicio;

    private Projeto projeto;

    private static Reuniao ultimoReuniao;

    public static Reuniao getUltimoReuniao() {
        return ultimoReuniao;
    }

    public static void setUltimoReuniao(Reuniao ultimoReuniao) {
        Reuniao.ultimoReuniao = ultimoReuniao;
    }

    private boolean realizada;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDataInicioFormat() {
        return dataInicioFormat;
    }

    public void setDataInicioFormat(String dataInicioFormat) {
        this.dataInicioFormat = dataInicioFormat;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }


    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public String getHoraInicioFormat() {
        return horaInicioFormat;
    }

    public void setHoraInicioFormat(String horaInicioFormat) {
        this.horaInicioFormat = horaInicioFormat;
    }

    public String getHoraFimFormat() {
        return horaFimFormat;
    }

    public void setHoraFimFormat(String horaFimFormat) {
        this.horaFimFormat = horaFimFormat;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
