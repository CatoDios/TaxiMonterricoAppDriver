package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class CostosResponse implements Serializable {

    private String TipoPago;
    private  String Nvale;
    private int NRuta;
    private float Peaje;
    private float Parqueo;
    private int EsperaTiempo;
    private float EsperaCosto;
    private float SubTotalRuta;
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

    public float getPeaje() {
        return Peaje;
    }

    public void setPeaje(float peaje) {
        Peaje = peaje;
    }

    public float getParqueo() {
        return Parqueo;
    }

    public void setParqueo(float parqueo) {
        Parqueo = parqueo;
    }

    public int getEsperaTiempo() {
        return EsperaTiempo;
    }

    public void setEsperaTiempo(int esperaTiempo) {
        EsperaTiempo = esperaTiempo;
    }

    public float getEsperaCosto() {
        return EsperaCosto;
    }

    public void setEsperaCosto(float esperaCosto) {
        EsperaCosto = esperaCosto;
    }

    public float getSubTotalRuta() {
        return SubTotalRuta;
    }

    public void setSubTotalRuta(float subTotalRuta) {
        SubTotalRuta = subTotalRuta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
