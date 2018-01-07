package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class SeguimientoEntity implements Serializable {

    private int IdReserva;
    private int IdSeguimiento;
    private String observaciones;

    public int getIdReserva() {
        return IdReserva;
    }

    public void setIdReserva(int idReserva) {
        IdReserva = idReserva;
    }

    public int getIdSeguimiento() {
        return IdSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        IdSeguimiento = idSeguimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
