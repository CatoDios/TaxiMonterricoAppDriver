package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by M on 20/12/2017.
 */

public class BreveteEntity implements Serializable {

    private String brevete;
    private String brevetecategoria;
    private String brevetefec;
    private String imabrevetea;
    private String imabreveteb;
    private int brevetedia;


    public String getBrevete() {
        return brevete;
    }

    public void setBrevete(String brevete) {
        this.brevete = brevete;
    }

    public String getBrevetecategoria() {
        return brevetecategoria;
    }

    public void setBrevetecategoria(String brevetecategoria) {
        this.brevetecategoria = brevetecategoria;
    }

    public String getBrevetefec() {
        return brevetefec;
    }

    public void setBrevetefec(String brevetefec) {
        this.brevetefec = brevetefec;
    }

    public String getImabrevetea() {
        return imabrevetea;
    }

    public void setImabrevetea(String imabrevetea) {
        this.imabrevetea = imabrevetea;
    }

    public String getImabreveteb() {
        return imabreveteb;
    }

    public void setImabreveteb(String imabreveteb) {
        this.imabreveteb = imabreveteb;
    }

    public int getBrevetedia() {
        return brevetedia;
    }

    public void setBrevetedia(int brevetedia) {
        this.brevetedia = brevetedia;
    }
}
