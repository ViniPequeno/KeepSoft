package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Convite;

@Dao
public interface ConviteDAO {
    @Query("SELECT * FROM convite")
    List<Convite> getAll();

    @Query("SELECT * FROM convite c WHERE c.codProjeto= :codProjeto AND c.remetenteId= :remetenteId AND c.destinatarioId= :destinatarioId")
    List<Convite> findById(long remetenteId, long destinatarioId, long codProjeto);

    @Query("SELECT * FROM convite c WHERE c.codProjeto= :codProjeto")
    List<Convite> findByProjectID(long codProjeto);

    @Query("SELECT * FROM convite c WHERE c.destinatarioId= :destinatarioId")
    List<Convite> findByReceiverID(long destinatarioId);

    @Insert
    void insertAll(Convite... convites);

    @Update
    void updateAll(Convite... convites);

    @Delete
    void delete(Convite convite);
}
