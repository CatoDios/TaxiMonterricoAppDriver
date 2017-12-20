package tmasociados.com.taximonterrico.data.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by M on 20/12/2017.
 */

public class SoatEntity implements Serializable {

    private String dessoat;
    private Date fecvencsoat;
    private int soatdia;
    private String Imasoatla;
    private String Imasoatlb;

    public String getDessoat() {
        return dessoat;
    }

    public void setDessoat(String dessoat) {
        this.dessoat = dessoat;
    }

    public Date getFecvencsoat() {
        return fecvencsoat;
    }

    public void setFecvencsoat(Date fecvencsoat) {
        this.fecvencsoat = fecvencsoat;
    }

    public int getSoatdia() {
        return soatdia;
    }

    public void setSoatdia(int soatdia) {
        this.soatdia = soatdia;
    }

    public String getImasoatla() {
        return Imasoatla;
    }

    public void setImasoatla(String imasoatla) {
        Imasoatla = imasoatla;
    }

    public String getImasoatlb() {
        return Imasoatlb;
    }

    public void setImasoatlb(String imasoatlb) {
        Imasoatlb = imasoatlb;
    }
}
