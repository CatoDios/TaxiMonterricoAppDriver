package tmasociados.com.taximonterrico.data.models;

import java.io.Serializable;

/**
 * Created by M on 20/12/2017.
 */

public class AsociadoEntity implements Serializable {

    private String apellidos;
    private String nombres;
    private int ndni;
    private String imadnia;
    private String imadnib;
    private String desturno;
    private int telefonop;
    private int telefonos;
    private String imaasoc;

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getNdni() {
        return ndni;
    }

    public void setNdni(int ndni) {
        this.ndni = ndni;
    }

    public String getImadnia() {
        return imadnia;
    }

    public void setImadnia(String imadnia) {
        this.imadnia = imadnia;
    }

    public String getImadnib() {
        return imadnib;
    }

    public void setImadnib(String imadnib) {
        this.imadnib = imadnib;
    }

    public String getDesturno() {
        return desturno;
    }

    public void setDesturno(String desturno) {
        this.desturno = desturno;
    }

    public int getTelefonop() {
        return telefonop;
    }

    public void setTelefonop(int telefonop) {
        this.telefonop = telefonop;
    }

    public int getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(int telefonos) {
        this.telefonos = telefonos;
    }

    public String getImaasoc() {
        return imaasoc;
    }

    public void setImaasoc(String imaasoc) {
        this.imaasoc = imaasoc;
    }
}
