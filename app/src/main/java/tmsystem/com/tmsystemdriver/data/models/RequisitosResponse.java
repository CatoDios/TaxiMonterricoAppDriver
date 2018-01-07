package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class RequisitosResponse implements Serializable {

    private String titulorequisitos;
    private String detallerequisitos;

    public String getTitulorequisitos() {
        return titulorequisitos;
    }

    public void setTitulorequisitos(String titulorequisitos) {
        this.titulorequisitos = titulorequisitos;
    }

    public String getDetallerequisitos() {
        return detallerequisitos;
    }

    public void setDetallerequisitos(String detallerequisitos) {
        this.detallerequisitos = detallerequisitos;
    }
}
