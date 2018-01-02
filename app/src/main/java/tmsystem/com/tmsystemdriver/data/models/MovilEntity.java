package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by M on 20/12/2017.
 */

public class MovilEntity implements Serializable {

    private String nplaca;
    private String DesMarca;
    private String color;
    private int año;
    private int nasientos;
    private int npuertas;
    private String tipoplaca;
    private String modelo;
    private String tipoafiliacion;
    private String tipocombustible;
    private String tipocilindrogas;

    private String movil;
    private String fotmovilla;


    public String getNplaca() {
        return nplaca;
    }

    public void setNplaca(String nplaca) {
        this.nplaca = nplaca;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getFotmovilla() {
        return fotmovilla;
    }

    public void setFotmovilla(String fotmovilla) {
        this.fotmovilla = fotmovilla;
    }

    public String getDesMarca() {
        return DesMarca;
    }

    public void setDesMarca(String desMarca) {
        DesMarca = desMarca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getNasientos() {
        return nasientos;
    }

    public void setNasientos(int nasientos) {
        this.nasientos = nasientos;
    }

    public int getNpuertas() {
        return npuertas;
    }

    public void setNpuertas(int npuertas) {
        this.npuertas = npuertas;
    }

    public String getTipoplaca() {
        return tipoplaca;
    }

    public void setTipoplaca(String tipoplaca) {
        this.tipoplaca = tipoplaca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoafiliacion() {
        return tipoafiliacion;
    }

    public void setTipoafiliacion(String tipoafiliacion) {
        this.tipoafiliacion = tipoafiliacion;
    }

    public String getTipocombustible() {
        return tipocombustible;
    }

    public void setTipocombustible(String tipocombustible) {
        this.tipocombustible = tipocombustible;
    }

    public String getTipocilindrogas() {
        return tipocilindrogas;
    }

    public void setTipocilindrogas(String tipocilindrogas) {
        this.tipocilindrogas = tipocilindrogas;
    }
}
