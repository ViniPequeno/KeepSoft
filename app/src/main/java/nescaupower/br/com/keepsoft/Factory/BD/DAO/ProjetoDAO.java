package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Projeto;

@Dao
public interface ProjetoDAO {
    @Query("SELECT * FROM projeto")
    List<Projeto> getAll();

    @Query("SELECT * FROM projeto WHERE codigo IN (:codigos)")
    List<Projeto> loadAllByIds(int[] codigos);

    @Query("SELECT * FROM projeto WHERE nome LIKE :nome")
    Projeto findByName(String nome);

    @Query("SELECT * FROM projeto WHERE codigo = :codigo")
    Projeto findByCodigo(Long codigo);

    @Query("SELECT * FROM projeto p WHERE p.idUsuario = :userID")
    List<Projeto> findByUserID(long userID);

    @Insert
    void insertAll(Projeto... projetos);

    @Delete
    void delete(Projeto projeto);
}
