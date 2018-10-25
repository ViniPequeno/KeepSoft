package nescaupower.br.com.keepsoft.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.ProjetoDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;

public class ProjetoController {
    private String mensagem;
    private ProjetoDAO projetoDAO;

    public ProjetoController() {
        projetoDAO = new ProjetoDAO();
    }

    public void inserir(Projeto projeto) {
        projetoDAO.insertAll(projeto);
    }


    public Projeto procurarPorCodigo(Long codigo) {
        return projetoDAO.findById(codigo);
    }

    public void atualizar(Projeto projeto) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        projeto.setDataCriacaoFormat(formato.format(projeto.getDataCriacao()));
        projeto.setDataPrevFinalizacaoFormat(formato.format(projeto.getDataPrevFinalizacao()));
        projetoDAO.updateAll(projeto);
    }

    public Projeto cadastrar(Projeto projeto) {
        if (projetoDAO.findByName(projeto.getNome()) != null) {
            this.mensagem = "O nome de projeto já existe!";
            return null;
        } else {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            projeto.setDataCriacaoFormat(formato.format(projeto.getDataCriacao()));
            projeto.setDataPrevFinalizacaoFormat(formato.format(projeto.getDataPrevFinalizacao()));
            Projeto projetoRetorno =  projetoDAO.insertAll(projeto);
            this.mensagem = "Cadastrado!";
            return projetoRetorno;
        }
    }

    public boolean deletar(Projeto projeto) {
        if (projetoDAO.findById(projeto.getCodigo()) != null) {
            projetoDAO.delete(projeto);
            this.mensagem = "O projeto foi deletado!";
            return true;
        } else {
            this.mensagem = "O projeto não existe";
            return false;
        }
    }

    public List<Projeto> listarPorUsuario(long idUsuario) {
        return projetoDAO.findByUserID(idUsuario);
    }

    public List<Projeto> listarPorUsuarioParticipando(long idUsuario) {
        return projetoDAO.findByParticipatingUserID(idUsuario);
    }

    public List<Projeto> listarTodos() {
        return projetoDAO.getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
