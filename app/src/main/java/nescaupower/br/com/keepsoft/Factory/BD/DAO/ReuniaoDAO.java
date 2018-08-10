package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;

@Dao
public interface ReuniaoDAO {
    @Query("SELECT * FROM reuniao")
    List<Reuniao> getAll();

    @Query("SELECT * FROM reuniao WHERE codigo IN (:codigos)")
    List<Reuniao> loadAllByIds(int[] codigos);

    @Query("SELECT * FROM reuniao WHERE nome LIKE :nome")
    Reuniao findByName(String nome, String email);

    @Insert
    void insertAll(Reuniao... reuniaos);

    @Delete
    void delete(Reuniao reuniao);
}
