package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 02/01/18.
 */

public class SendEstado implements Serializable {

    private int IdAsociado;
    private int idestado;

    public SendEstado(int idAsociado, int idestado) {
        IdAsociado = idAsociado;
        this.idestado = idestado;
    }

    public int getIdAsociado() {
        return IdAsociado;
    }

    public void setIdAsociado(int idAsociado) {
        IdAsociado = idAsociado;
    }

    public int getIdestado() {
        return idestado;
    }

    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }
}
