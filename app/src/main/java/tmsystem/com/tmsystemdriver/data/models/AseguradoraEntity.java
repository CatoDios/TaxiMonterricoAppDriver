package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by M on 20/12/2017.
 */

public class AseguradoraEntity implements Serializable {

    private String desaseguradora;
    private String fecvencaseguradora;
    private int aseguradoradia;
    private String Imaaseguradorala;
    private String Imaaseguradoralb;

    public String getDesaseguradora() {
        return desaseguradora;
    }

    public void setDesaseguradora(String desaseguradora) {
        this.desaseguradora = desaseguradora;
    }

    public String getFecvencaseguradora() {
        return fecvencaseguradora;
    }

    public void setFecvencaseguradora(String fecvencaseguradora) {
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

    public String getDay(){
        if (getFecvencaseguradora() == null ){
            return "";
        }
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail =  new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", new Locale("es","ES"));

        try {
            tempDate = parseDateFromServer.parse(getFecvencaseguradora());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDateForShowDetail.format(tempDate);
    }

}
