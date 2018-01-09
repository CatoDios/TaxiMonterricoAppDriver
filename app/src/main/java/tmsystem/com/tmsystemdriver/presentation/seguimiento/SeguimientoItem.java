package tmsystem.com.tmsystemdriver.presentation.seguimiento;

import tmsystem.com.tmsystemdriver.data.models.SeguimientoEntity;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;

/**
 * Created by kath on 08/01/18.
 */

public interface SeguimientoItem {


    void clickItem(SeguimientoResponse seguimientoResponse);

    void deleteItem(SeguimientoResponse seguimientoResponse, int position);

}
