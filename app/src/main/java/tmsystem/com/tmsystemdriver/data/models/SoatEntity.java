package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by M on 20/12/2017.
 */

public class SoatEntity implements Serializable {

    private String dessoat;
    private String fecvencsoat;
    private int soatdia;
    private String Imasoatla;
    private String Imasoatlb;

    public String getDessoat() {
        return dessoat;
    }

    public void setDessoat(String dessoat) {
        this.dessoat = dessoat;
    }

    public String getFecvencsoat() {
        return fecvencsoat;
    }

    public void setFecvencsoat(String fecvencsoat) {
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

    public String getDay(){
        if (getFecvencsoat() == null ){
            return "";
        }
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail =  new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", new Locale("es","ES"));

        try {
            tempDate = parseDateFromServer.parse(getFecvencsoat());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDateForShowDetail.format(tempDate);
    }
}
