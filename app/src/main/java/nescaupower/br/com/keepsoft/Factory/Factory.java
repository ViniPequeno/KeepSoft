package nescaupower.br.com.keepsoft.Factory;

import android.arch.persistence.room.Room;
import android.content.Context;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public abstract class Factory {



    public static Usuario startUsuario(){
        return new Usuario();
    }

    public static Usuario getUsuarioLogado() {
        return Usuario.getUsuarioLogado();
    }

    public static void setUsuarioLogado(Usuario usuario) {
        Usuario.setUsuarioLogado(usuario);
    }

    public static Projeto startProjeto() {
        return new Projeto();
    }

    public static void setProjetoLogado(Projeto projeto) {
        Projeto.setUltimoProjetoUsado(projeto);
    }

    public static Sprint startSprint() {
        return new Sprint();
    }

    public static Perfil startPerfil() {
        return new Perfil();
    }

    public static Convite startConvite(){ return new Convite();}

}
