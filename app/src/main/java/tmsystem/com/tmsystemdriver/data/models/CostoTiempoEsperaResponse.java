package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class CostoTiempoEsperaResponse implements Serializable {

    private float costofinal;
    private String message;

    public float getCostofinal() {
        return costofinal;
    }

    public void setCostofinal(float costofinal) {
        this.costofinal = costofinal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
