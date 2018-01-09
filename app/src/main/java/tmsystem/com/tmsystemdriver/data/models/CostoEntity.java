package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 08/01/18.
 */

public class CostoEntity implements Serializable {
    private int IdReserva;
    private String nvale;
    private Double Peaje;
    private Double Parqueo;
    private int EsperaTiempo;
    private Double EsperaCosto;

    public CostoEntity(int idReserva, String nvale, Double peaje, Double parqueo, int esperaTiempo, Double esperaCosto) {
        IdReserva = idReserva;
        this.nvale = nvale;
        Peaje = peaje;
        Parqueo = parqueo;
        EsperaTiempo = esperaTiempo;
        EsperaCosto = esperaCosto;
    }

    public int getIdReserva() {
        return IdReserva;
    }

    public void setIdReserva(int idReserva) {
        IdReserva = idReserva;
    }

    public String getNvale() {
        return nvale;
    }

    public void setNvale(String nvale) {
        this.nvale = nvale;
    }

    public Double getPeaje() {
        return Peaje;
    }

    public void setPeaje(Double peaje) {
        Peaje = peaje;
    }

    public Double getParqueo() {
        return Parqueo;
    }

    public void setParqueo(Double parqueo) {
        Parqueo = parqueo;
    }

    public int getEsperaTiempo() {
        return EsperaTiempo;
    }

    public void setEsperaTiempo(int esperaTiempo) {
        EsperaTiempo = esperaTiempo;
    }

    public Double getEsperaCosto() {
        return EsperaCosto;
    }

    public void setEsperaCosto(Double esperaCosto) {
        EsperaCosto = esperaCosto;
    }
}
