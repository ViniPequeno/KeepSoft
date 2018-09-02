package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Calendar;
import java.util.Date;

@Entity(primaryKeys = {"codigoRequisito", "idStatus"},
        foreignKeys = {
        @ForeignKey(
                entity = Requisito.class,
                parentColumns = "codigo",
                childColumns = "codigoRequisito"
        ),
        @ForeignKey(
                entity = Status.class,
                parentColumns = "id",
                childColumns = "idStatus"
        )
})
public class RequisitoStatus {
    private long codigoRequisito;
    private long idStatus;
    private Date dataInicio;
    private Date dataFim;

    public RequisitoStatus() {
        codigoRequisito = 0;
        idStatus = 0;
        dataInicio = Calendar.getInstance().getTime();
    }

    public RequisitoStatus(long codigoRequisito, long idStatus, Date dataInicio, Date dataFim) {
        this.codigoRequisito = codigoRequisito;
        this.idStatus = idStatus;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public long getCodigoRequisito() {
        return codigoRequisito;
    }

    public void setCodigoRequisito(long codigoRequisito) {
        this.codigoRequisito = codigoRequisito;
    }

    public long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(long idStatus) {
        this.idStatus = idStatus;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}
