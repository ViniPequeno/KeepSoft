package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Status;

@Dao
public interface StatusDAO {
    @Query("SELECT * FROM status")
    List<Status> getAll();

    @Query("SELECT * FROM status WHERE id IN (:ids)")
    List<Status> loadAllByIds(int[] ids);

    @Query("SELECT * FROM status WHERE nome LIKE :nome")
    Status findByName(String nome);

    @Insert
    void insertAll(Status... statuss);

    @Delete
    void delete(Status status);
}
