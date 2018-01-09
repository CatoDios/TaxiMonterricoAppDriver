package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class GpsEntity implements Serializable {
    private int IdAsociado;
    private Double latitude;
    private Double longitude;
    private int velocidad;
    private Double angulo;

    public GpsEntity(int idAsociado, Double latitude, Double longitude, int velocidad, Double angulo) {
        IdAsociado = idAsociado;
        this.latitude = latitude;
        this.longitude = longitude;
        this.velocidad = velocidad;
        this.angulo = angulo;
    }

    public int getIdAsociado() {
        return IdAsociado;
    }

    public void setIdAsociado(int idAsociado) {
        IdAsociado = idAsociado;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Double getAngulo() {
        return angulo;
    }

    public void setAngulo(Double angulo) {
        this.angulo = angulo;
    }
}
