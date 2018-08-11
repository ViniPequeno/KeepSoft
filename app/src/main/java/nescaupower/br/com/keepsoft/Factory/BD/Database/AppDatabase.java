package nescaupower.br.com.keepsoft.Factory.BD.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;


import nescaupower.br.com.keepsoft.Factory.BD.DAO.*;
import nescaupower.br.com.keepsoft.Factory.Model.*;
import nescaupower.br.com.keepsoft.Utils.Converters;


@Database(entities = {Usuario.class, Projeto.class, RequisitoStatus.class, Reuniao.class, Sprint.class, Status.class, Requisito.class},
        version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDAO usuarioDAO();
    public abstract ProjetoDAO projetoDAO();
    public abstract RequisitoStatusDAO requisitoStatusDAO();
    public abstract RequisitoDAO requisitoDAO();
    public abstract ReuniaoDAO reuniaoDAO();
    public abstract SprintDAO sprintDAO();
    public abstract StatusDAO statusDAO();


}
