package tmsystem.com.tmsystemdriver.data.models;

import java.io.Serializable;

/**
 * Created by kath on 05/01/18.
 */

public class MarkersEntity implements Serializable {
    private String Item;
    private String DireccionReal;
    private String latitude;
    private String longitude;

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getDireccionReal() {
        return DireccionReal;
    }

    public void setDireccionReal(String direccionReal) {
        DireccionReal = direccionReal;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
