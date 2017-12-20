package tmasociados.com.taximonterrico.data.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by M on 20/12/2017.
 */

public class RevisionTecnicaEntity implements Serializable {

    private String desrevisiontecnica;
    private Date fecvencrevisiontecnica;
    private int fecvencrevisiontecnicadia;
    private String ImaRevisionTecnicala;
    private String ImaRevisionTecnicalb;

    public String getDesrevisiontecnica() {
        return desrevisiontecnica;
    }

    public void setDesrevisiontecnica(String desrevisiontecnica) {
        this.desrevisiontecnica = desrevisiontecnica;
    }

    public Date getFecvencrevisiontecnica() {
        return fecvencrevisiontecnica;
    }

    public void setFecvencrevisiontecnica(Date fecvencrevisiontecnica) {
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
}
