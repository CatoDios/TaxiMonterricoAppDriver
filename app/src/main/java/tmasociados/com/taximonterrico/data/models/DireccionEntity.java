package tmasociados.com.taximonterrico.data.models;

import java.io.Serializable;

/**
 * Created by M on 20/12/2017.
 */

public class DireccionEntity implements Serializable {

    private String direccion;
    private String deszona;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDeszona() {
        return deszona;
    }

    public void setDeszona(String deszona) {
        this.deszona = deszona;
    }
}
