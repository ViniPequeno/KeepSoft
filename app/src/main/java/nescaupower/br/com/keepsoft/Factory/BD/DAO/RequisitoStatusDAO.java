package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.RequisitoStatus;

@Dao
public interface RequisitoStatusDAO {
    @Query("SELECT * FROM requisitoStatus")
    List<RequisitoStatus> getAll();

    @Query("SELECT * FROM requisitoStatus WHERE codigoRequisito IN (:codigoRequisitos) AND " +
            "idStatus IN (:idStatus)")
    List<RequisitoStatus> loadAllByIds(int[] codigoRequisitos, int[] idStatus);

    @Query("SELECT * FROM requisitoStatus WHERE codigoRequisito IN (:codigoRequisitos)")
    List<RequisitoStatus> loadAllByCodigoRequisito(int[] codigoRequisitos);

    @Query("SELECT * FROM requisitoStatus WHERE idStatus IN (:idStatus)")
    List<RequisitoStatus> loadAllByIdStatus(int[] idStatus);

    @Insert
    void insertAll(RequisitoStatus... requisitoStatuss);

    @Delete
    void delete(RequisitoStatus requisitoStatus);
}
