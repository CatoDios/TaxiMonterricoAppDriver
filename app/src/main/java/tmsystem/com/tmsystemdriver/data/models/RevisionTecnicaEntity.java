package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by M on 20/12/2017.
 */

public class RevisionTecnicaEntity implements Serializable {

    private String desrevisiontecnica;
    private String fecvencrevisiontecnica;
    private int fecvencrevisiontecnicadia;
    private String ImaRevisionTecnicala;
    private String ImaRevisionTecnicalb;

    public String getDesrevisiontecnica() {
        return desrevisiontecnica;
    }

    public void setDesrevisiontecnica(String desrevisiontecnica) {
        this.desrevisiontecnica = desrevisiontecnica;
    }

    public String getFecvencrevisiontecnica() {
        return fecvencrevisiontecnica;
    }

    public void setFecvencrevisiontecnica(String fecvencrevisiontecnica) {
        this.fecvencrevisiontecnica = fecvencrevisiontecnica;
    }

    public int getFecvencrevisiontecnicadia() {
        return fecvencrevisiontecnicadia;
    }

    public void setFecvencrevisiontecnicadia(int fecvencrevisiontecnicadia) {
        this.fecvencrevisiontecnicadia = fecvencrevisiontecnicadia;
    }

    public String getImaRevisionTecnicala() {
        return ImaRevisionTecnicala;
    }

    public void setImaRevisionTecnicala(String imaRevisionTecnicala) {
        ImaRevisionTecnicala = imaRevisionTecnicala;
    }

    public String getImaRevisionTecnicalb() {
        return ImaRevisionTecnicalb;
    }

    public void setImaRevisionTecnicalb(String imaRevisionTecnicalb) {
        ImaRevisionTecnicalb = imaRevisionTecnicalb;
    }
    public String getDay(){
        if (getFecvencrevisiontecnica() == null ){
            return "";
        }
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail =  new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", new Locale("es","ES"));

        try {
            tempDate = parseDateFromServer.parse(getFecvencrevisiontecnica());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDateForShowDetail.format(tempDate);
    }
}
