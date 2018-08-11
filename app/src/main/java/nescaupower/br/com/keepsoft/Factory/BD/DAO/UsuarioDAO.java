package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM usuario WHERE login IN (:logins)")
    List<Usuario> loadAllByIds(int[] logins);

    @Query("SELECT * FROM usuario WHERE email LIKE :email LIMIT 1")
    Usuario findByEmail(String email);

    @Insert
    void insertAll(Usuario... usuarios);

    @Delete
    void delete(Usuario usuario);
}
