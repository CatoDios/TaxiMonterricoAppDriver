package tmasociados.com.taximonterrico.data.models;

import java.io.Serializable;

/**
 * Created by kath on 29/12/17.
 */

public class EstadoResponse implements Serializable {


    private int IdEstado;
    private String desestado;
    private int IdReserva;
    private String message;

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int idEstado) {
        IdEstado = idEstado;
    }

    public String getDesestado() {
        return desestado;
    }

    public void setDesestado(String desestado) {
        this.desestado = desestado;
    }

    public int getIdReserva() {
        return IdReserva;
    }

    public void setIdReserva(int idReserva) {
        IdReserva = idReserva;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
