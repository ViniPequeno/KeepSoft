package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Usuario.class,
                parentColumns = "id",
                childColumns = "idUsuario")
})
public class Notificacao {
    private long idUsuario;
    private Date dataCriacao;

    public Notificacao() {
    }
}
