package nescaupower.br.com.keepsoft.Factory;

import android.arch.persistence.room.Room;
import android.content.Context;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Sprint;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

public abstract class Factory {


    public static AppDatabase startDatabase(Context context){
        String database = Settings.DATABASE;
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, database).allowMainThreadQueries().build() ;
        return db;
    }

    public static Usuario startUsuario(){
        return new Usuario();
    }

    public static Usuario getLogado() {
        return Usuario.getUsuarioLogado();
    }

    public static void setLogado(Usuario usuario) {
        Usuario.setUsuarioLogado(usuario);
    }
    public static void setProjetoLogado(Projeto projeto) {Projeto.setUltimoProjetoUsado(projeto);}

    public static Projeto startProjeto() {
        return new Projeto();
    }

    public static Sprint startSprint() {
        return new Sprint();
    }

}
