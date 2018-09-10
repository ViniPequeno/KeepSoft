package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Sprint;

@Dao
public interface SprintDAO {
    @Query("SELECT * FROM sprint")
    List<Sprint> getAll();

    @Query("SELECT * FROM sprint WHERE codigo IN (:codigos)")
    List<Sprint> loadAllByIds(int[] codigos);

    @Query("SELECT * FROM sprint WHERE codigo LIKE :codigo")
    Sprint findById(long codigo);

    @Query("SELECT * FROM sprint WHERE titulo LIKE :titulo")
    Sprint findByName(String titulo);

    @Query("SELECT * FROM sprint s WHERE s.codProjeto= :codProjeto")
    List<Sprint> findByProjectID(long codProjeto);

    @Insert
    void insertAll(Sprint... sprints);

    @Update
    void updateAll(Sprint... sprints);

    @Delete
    void delete(Sprint sprint);
}
