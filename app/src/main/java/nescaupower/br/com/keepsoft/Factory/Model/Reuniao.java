package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Reuniao {
    @PrimaryKey
    private long codigo;
    private String nome;
    private String relatorio;
    private String assunto;
    private Date data;

    public Reuniao() {
        codigo = 0;
        nome="";
        relatorio="";
        assunto="";
        data= Calendar.getInstance().getTime();
    }

    public Reuniao(long codigo, String nome, String relatorio, String assunto, Date data) {
        this.codigo = codigo;
        this.nome = nome;
        this.relatorio = relatorio;
        this.assunto = assunto;
        this.data = data;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
