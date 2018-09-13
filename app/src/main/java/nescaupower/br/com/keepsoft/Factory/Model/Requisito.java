package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Prioridade;
import nescaupower.br.com.keepsoft.Utils.Converters;

@Entity
public class Requisito {
    @PrimaryKey
    private long codigo;
    private String nome;
    private String descricao;
    @TypeConverters({Converters.class})
    private Prioridade prioridade;
    @TypeConverters({Converters.class})
    private Dificuldade dificuldade;

    public Requisito() {
        codigo=0;
        nome="";
        descricao="";
    }

    public Requisito(long codigo, String nome, String descricao, Prioridade prioridade, Dificuldade dificuldade) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.dificuldade = dificuldade;
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

    public Prioridade getPrioridade() {return prioridade;}

    public void setPrioridade(Prioridade prioridade) {this.prioridade = prioridade;}

    public Dificuldade getDificuldade() {return dificuldade;}

    public void setDificuldade(Dificuldade dificuldade) {this.dificuldade = dificuldade;}
}
