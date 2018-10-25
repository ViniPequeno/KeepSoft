package nescaupower.br.com.keepsoft.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.SprintDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public class SprintController {
    private String mensagem;
    private SprintDAO sprintDAO;

    public SprintController() {
        sprintDAO = new SprintDAO();
    }


    public Sprint procurarPorNome(String nome) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Sprint sprint = sprintDAO.findByName(nome);
        try {
            sprint.setDataFim(formato.parse(sprint.getDataFimFormat()));
            sprint.setDataInicio(formato.parse(sprint.getDataInicioFormat()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sprint;
    }

    public Perfil findPerfilSprintUsuario(Sprint sprint, Usuario usuario) {
        return sprintDAO.findPerfilOfSprintUsuario(usuario.getId(), sprint.getProjeto().getCodigo());
    }

    public Sprint procurarPorCodigo(Long codigo) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Sprint sprint = sprintDAO.findById(codigo);
        try {
            sprint.setDataFim(formato.parse(sprint.getDataFimFormat()));
            sprint.setDataInicio(formato.parse(sprint.getDataInicioFormat()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sprint;
    }

    public void atualizar(Sprint sprint) {


        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        sprint.setDataInicioFormat(formato.format(sprint.getDataInicio()));
        sprint.setDataFimFormat(formato.format(sprint.getDataFim()));
        sprintDAO.updateAll(sprint);
    }

    public boolean cadastrar(Sprint sprint) {
        if (sprintDAO.findByName(sprint.getTitulo()) != null) {
            this.mensagem = "O nome de sprint já existe!";
            return false;
        } else {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            sprint.setDataInicioFormat(formato.format(sprint.getDataInicio()));
            sprint.setDataFimFormat(formato.format(sprint.getDataFim()));
            sprintDAO.insertAll(sprint);
            this.mensagem = "Cadastrado!";
            return true;
        }
    }

    public boolean deletar(Sprint sprint) {
        if (sprintDAO.findById(sprint.getId()) != null) {
            sprintDAO.delete(sprint);
            this.mensagem = "O sprint foi deletado!";
            return true;
        } else {
            this.mensagem = "O sprint não existe";
            return false;
        }
    }

    public List<Sprint> listarPorProjeto(Long codProjeto) {
        return sprintDAO.findByProjectID(codProjeto);
    }

    public List<Sprint> listarTodos() {
        return sprintDAO.getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
