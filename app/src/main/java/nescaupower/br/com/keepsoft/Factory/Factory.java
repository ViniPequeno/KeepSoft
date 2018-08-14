package nescaupower.br.com.keepsoft.Factory;

import android.arch.persistence.room.Room;
import android.content.Context;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.BD.DAO.UsuarioDAO;
import nescaupower.br.com.keepsoft.Factory.BD.Database.AppDatabase;
import nescaupower.br.com.keepsoft.Factory.Model.*;
import nescaupower.br.com.keepsoft.Factory.BD.DAO.*;
import nescaupower.br.com.keepsoft.R;

public abstract class Factory {


    public static AppDatabase startDatabase(Context context){
        String database = Settings.DATABASE;
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, database).allowMainThreadQueries().build() ;
        return db;
    }

    public static Usuario startUsuario(){
        return new Usuario();
    }
    public static Usuario getLogado(){return Usuario.getUsuario_logado();}
    public static void setLogado(Usuario usuario){ Usuario.setUsuario_logado(usuario); };

    public static Projeto startProjet(){
        return new Projeto();
    }

}
