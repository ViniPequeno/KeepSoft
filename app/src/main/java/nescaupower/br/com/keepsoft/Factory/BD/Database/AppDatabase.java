package nescaupower.br.com.keepsoft.Factory.BD.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.user.keepsoft.DAO.*;
import com.example.user.keepsoft.models.*;

@Database(entities = {Usuario.class, Projeto.class, RequisitoStatus.class, Reuniao.class, Sprint.class, Status.class, Requisito.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDAO usuarioDAO();
    public abstract ProjetoDAO projetoDAO();
    public abstract RequisitoStatusDAO requisitoStatusDAO();
    public abstract RequisitoDAO requisitoDAO();
    public abstract ReuniaoDAO reuniaoDAO();
    public abstract SprintDAO sprintDAO();
    public abstract StatusDAO statusDAO();


}
