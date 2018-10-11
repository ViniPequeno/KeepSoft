package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Perfil;

@Dao
public interface PerfilDAO {
    @Query("SELECT * FROM perfil")
    List<Perfil> getAll();

    @Query("SELECT * FROM perfil WHERE id in (:ids)")
    List<Perfil> loadAllByIds(long[] ids);

    @Query("SELECT * FROM perfil WHERE id = :id")
    Perfil findById(long id);

    @Query("SELECT * FROM perfil p WHERE p.codProjeto= :codProjeto")
    List<Perfil> findByProjectID(long codProjeto);

    @Query("SELECT * FROM perfil p WHERE p.codProjeto= :codProjeto AND p.idUsuario= :idUsuario")
    Perfil findByUserIdAndProjectID(long codProjeto, long idUsuario);

    @Insert
    void insertAll(Perfil... perfis);

    @Update
    void updateAll(Perfil... perfis);

    @Delete
    void delete(Perfil perfil);
}
