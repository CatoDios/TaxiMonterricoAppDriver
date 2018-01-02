package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by M on 20/12/2017.
 */

public class IdiomaEntity implements Serializable {

    private boolean IIngles;
    private boolean IFrances;
    private boolean IAleman;
    private boolean IPortugues;
    private boolean IChinoMandarin;

    public boolean isIIngles() {
        return IIngles;
    }

    public void setIIngles(boolean IIngles) {
        this.IIngles = IIngles;
    }

    public boolean isIFrances() {
        return IFrances;
    }

    public void setIFrances(boolean IFrances) {
        this.IFrances = IFrances;
    }

    public boolean isIAleman() {
        return IAleman;
    }

    public void setIAleman(boolean IAleman) {
        this.IAleman = IAleman;
    }

    public boolean isIPortugues() {
        return IPortugues;
    }

    public void setIPortugues(boolean IPortugues) {
        this.IPortugues = IPortugues;
    }

    public boolean isIChinoMandarin() {
        return IChinoMandarin;
    }

    public void setIChinoMandarin(boolean IChinoMandarin) {
        this.IChinoMandarin = IChinoMandarin;
    }
}
