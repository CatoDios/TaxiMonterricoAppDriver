package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by M on 20/12/2017.
 */

public class MovilCaracteristicaEntity implements Serializable {

    private boolean aireacondicionado;
    private boolean laminaseguridad;
    private boolean lunapolarizada;
    private boolean botiquin;
    private boolean maleteraamplia;
    private boolean llantarespuesta;
    private boolean blindajellanta;
    private boolean wifi;
    private boolean cintareflexiva;
    private boolean casquete;
    private boolean fotocheck;
    private boolean extintor;
    private String extintorfecve;
    private int extintordia;

    public boolean isAireacondicionado() {
        return aireacondicionado;
    }

    public void setAireacondicionado(boolean aireacondicionado) {
        this.aireacondicionado = aireacondicionado;
    }

    public boolean isLaminaseguridad() {
        return laminaseguridad;
    }

    public void setLaminaseguridad(boolean laminaseguridad) {
        this.laminaseguridad = laminaseguridad;
    }

    public boolean isLunapolarizada() {
        return lunapolarizada;
    }

    public void setLunapolarizada(boolean lunapolarizada) {
        this.lunapolarizada = lunapolarizada;
    }

    public boolean isBotiquin() {
        return botiquin;
    }

    public void setBotiquin(boolean botiquin) {
        this.botiquin = botiquin;
    }

    public boolean isMaleteraamplia() {
        return maleteraamplia;
    }

    public void setMaleteraamplia(boolean maleteraamplia) {
        this.maleteraamplia = maleteraamplia;
    }

    public boolean isLlantarespuesta() {
        return llantarespuesta;
    }

    public void setLlantarespuesta(boolean llantarespuesta) {
        this.llantarespuesta = llantarespuesta;
    }

    public boolean isBlindajellanta() {
        return blindajellanta;
    }

    public void setBlindajellanta(boolean blindajellanta) {
        this.blindajellanta = blindajellanta;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isCintareflexiva() {
        return cintareflexiva;
    }

    public void setCintareflexiva(boolean cintareflexiva) {
        this.cintareflexiva = cintareflexiva;
    }

    public boolean isCasquete() {
        return casquete;
    }

    public void setCasquete(boolean casquete) {
        this.casquete = casquete;
    }

    public boolean isFotocheck() {
        return fotocheck;
    }

    public void setFotocheck(boolean fotocheck) {
        this.fotocheck = fotocheck;
    }

    public boolean isExtintor() {
        return extintor;
    }

    public void setExtintor(boolean extintor) {
        this.extintor = extintor;
    }

    public String getExtintorfecve() {
        return extintorfecve;
    }

    public void setExtintorfecve(String extintorfecve) {
        this.extintorfecve = extintorfecve;
    }

    public int getExtintordia() {
        return extintordia;
    }

    public void setExtintordia(int extintordia) {
        this.extintordia = extintordia;
    }
}
