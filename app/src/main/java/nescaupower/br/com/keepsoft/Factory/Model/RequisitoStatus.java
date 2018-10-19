package nescaupower.br.com.keepsoft.Factory.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import java.util.Calendar;
import java.util.Date;

public class RequisitoStatus {
    private Long id;
    private Requisito codigoRequisito;
    private Status idStatus;
    private Date dataInicio;
    private Date dataFim;

    public RequisitoStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Requisito getCodigoRequisito() {
        return codigoRequisito;
    }

    public void setCodigoRequisito(Requisito codigoRequisito) {
        this.codigoRequisito = codigoRequisito;
    }

    public Status getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Status idStatus) {
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
