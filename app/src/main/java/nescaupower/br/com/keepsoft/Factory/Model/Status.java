package nescaupower.br.com.keepsoft.Factory.Model;

public class Status {
    private Long id;
    private String nome;
    private String descricao;
    private int cor;
    private Projeto projeto;

    public Status() {}

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCor() { return cor; }

    public void setCor(int cor) { this.cor = cor; }

    public Projeto getProjeto() { return projeto; }

    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
}
