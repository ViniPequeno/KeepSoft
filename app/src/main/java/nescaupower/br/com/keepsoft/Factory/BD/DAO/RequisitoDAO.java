package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Requisito;

@Dao
public interface RequisitoDAO {
    @Query("SELECT * FROM requisito")
    List<Requisito> getAll();

    @Query("SELECT * FROM requisito WHERE codigo IN (:codigos)")
    List<Requisito> loadAllByIds(int[] codigos);

    @Query("SELECT * FROM requisito WHERE nome LIKE :nome")
    Requisito findByName(String nome);

    @Insert
    void insertAll(Requisito... requisitos);

    @Delete
    void delete(Requisito requisito);
}
