package tmasociados.com.taximonterrico.data.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by M on 20/12/2017.
 */

public class AseguradoraEntity implements Serializable {

    private String desaseguradora;
    private Date fecvencaseguradora;
    private int aseguradoradia;
    private String Imaaseguradorala;
    private String Imaaseguradoralb;

    public String getDesaseguradora() {
        return desaseguradora;
    }

    public void setDesaseguradora(String desaseguradora) {
        this.desaseguradora = desaseguradora;
    }

    public Date getFecvencaseguradora() {
        return fecvencaseguradora;
    }

    public void setFecvencaseguradora(Date fecvencaseguradora) {
        this.fecvencaseguradora = fecvencaseguradora;
    }

    public int getAseguradoradia() {
        return aseguradoradia;
    }

    public void setAseguradoradia(int aseguradoradia) {
        this.aseguradoradia = aseguradoradia;
    }

    public String getImaaseguradorala() {
        return Imaaseguradorala;
    }

    public void setImaaseguradorala(String imaaseguradorala) {
        Imaaseguradorala = imaaseguradorala;
    }

    public String getImaaseguradoralb() {
        return Imaaseguradoralb;
    }

    public void setImaaseguradoralb(String imaaseguradoralb) {
        Imaaseguradoralb = imaaseguradoralb;
    }
}
