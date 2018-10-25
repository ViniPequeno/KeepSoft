package nescaupower.br.com.keepsoft.Controller;

import java.text.SimpleDateFormat;
import java.util.List;

import nescaupower.br.com.keepsoft.Factory.BD.DAO.PerfilDAO;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;

public class PerfilController {
    private String mensagem;
    private PerfilDAO perfilDAO;

    public PerfilController() {
        perfilDAO = new PerfilDAO();
    }

    public void inserir(Perfil... perfis) {
        perfilDAO.insertAll(perfis);
    }

    public Perfil procurarPorId(Long codigo) {
        return perfilDAO.findById(codigo);
    }

    public Perfil procurarPorProjetoUsuario(Long codProjeto, Long idUsuario) {
        return perfilDAO.findByUserIdAndProjectID(codProjeto, idUsuario);
    }

    public void atualizar(Perfil perfil) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        perfil.setDataInicioFormat(formato.format(perfil.getDataInicio()));
        perfilDAO.updateAll(perfil);
    }

    public boolean cadastrar(Perfil... perfil) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        for(Perfil perfil1 : perfil) {
            if(perfil1.getDataInicio() != null) {
                perfil1.setDataInicioFormat(formato.format(perfil1.getDataInicio()));
            }else{
                perfil1.setDataInicio(null);
                perfil1.setDataInicioFormat("");
            }

        }
        perfilDAO.insertAll(perfil);
        this.mensagem = "Cadastrado!";
        return true;
    }

    public boolean deletar(Perfil perfil) {
        if (perfilDAO.findById(perfil.getId()) != null) {
            perfilDAO.delete(perfil);
            this.mensagem = "O perfil foi deletado!";
            return true;
        } else {
            this.mensagem = "O perfil não existe";
            return false;
        }
    }

    public List<Perfil> listarPorProjeto(long codProjeto) {
        return perfilDAO.findByProjectID(codProjeto);
    }
    public List<Perfil> listarTodos() {
        return perfilDAO.getAll();
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
