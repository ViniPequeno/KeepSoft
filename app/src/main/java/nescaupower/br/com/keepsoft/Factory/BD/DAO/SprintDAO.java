package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Sprint;

@Dao
public interface SprintDAO {
    @Query("SELECT * FROM sprint")
    List<Sprint> getAll();

    @Query("SELECT * FROM sprint WHERE codigo IN (:codigos)")
    List<Sprint> loadAllByIds(int[] codigos);

    @Query("SELECT * FROM sprint WHERE nome LIKE :nome")
    Sprint findByName(String nome);

    @Insert
    void insertAll(Sprint... sprints);

    @Delete
    void delete(Sprint sprint);
}
