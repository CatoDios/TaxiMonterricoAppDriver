package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 7/01/18.
 */

public class SeguimientoResponse implements Serializable {
    private int IdSeguimiento;
    private String Desseguimiento;
    private int SecSeguimiento;

    public int getIdSeguimiento() {
        return IdSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        IdSeguimiento = idSeguimiento;
    }

    public String getDesseguimiento() {
        return Desseguimiento;
    }

    public void setDesseguimiento(String desseguimiento) {
        Desseguimiento = desseguimiento;
    }

    public int getSecSeguimiento() {
        return SecSeguimiento;
    }

    public void setSecSeguimiento(int secSeguimiento) {
        SecSeguimiento = secSeguimiento;
    }
}
