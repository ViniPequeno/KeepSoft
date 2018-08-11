package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Perfil;

@Dao
public interface PerfilDAO {
    @Query("SELECT * FROM perfil")
    List<Perfil> getAll();

    @Query("SELECT * FROM perfil WHERE id = :id")
    List<Perfil> loadAllByIds(long id);

    @Insert
    void insertAll(Perfil... projetos);

    @Delete
    void delete(Perfil projeto);
}
