package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class CostosResponse implements Serializable {

    private String TipoPago;
    private  String Nvale;
    private int NRuta;
    private Double Peaje;
    private Double Parqueo;
    private int EsperaTiempo;
    private Double EsperaCosto;
    private Double SubTotalRuta;
    private String message;

    public String getTipoPago() {
        return TipoPago;
    }

    public void setTipoPago(String tipoPago) {
        TipoPago = tipoPago;
    }

    public String getNvale() {
        return Nvale;
    }

    public void setNvale(String nvale) {
        Nvale = nvale;
    }

    public int getNRuta() {
        return NRuta;
    }

    public void setNRuta(int NRuta) {
        this.NRuta = NRuta;
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

    public Double getSubTotalRuta() {
        return SubTotalRuta;
    }

    public void setSubTotalRuta(Double subTotalRuta) {
        SubTotalRuta = subTotalRuta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
