package nescaupower.br.com.keepsoft.Factory.BD.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import nescaupower.br.com.keepsoft.Factory.Model.Usuario;

@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM usuario WHERE login IN (:logins)")
    List<Usuario> loadAllByIds(int[] logins);

    @Query("SELECT * FROM usuario WHERE id = :id")
    Usuario findByID(long id);

    @Query("SELECT * FROM usuario WHERE login = :login")
    Usuario findByLogin(String login);

    @Query("SELECT * FROM usuario WHERE (login LIKE LOWER(:login)) OR (nome LIKE LOWER(:name))")
    List<Usuario> findByLoginAndName(String login, String name);

    @Query("SELECT * FROM usuario WHERE email = :email")
    Usuario findByEmail(String email);

    @Query("SELECT * FROM usuario WHERE (login = :login OR email = :login) AND senha = :senha")
    Usuario login(String login, String senha);

    @Insert
    void insertAll(Usuario... usuarios);

    @Update
    void updateAll(Usuario... usuarios);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT id as _id, nome, login, email, telefone FROM usuario WHERE nome LIKE :login AND id <> :id")
    Cursor listUsuariosCursor(String login, long id);
}
