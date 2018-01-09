package tmsystem.com.tmsystemdriver.data.remote.request;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tmsystem.com.tmsystemdriver.data.models.CostoEntity;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.GpsEntity;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoEntity;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;

/**
 * Created by katherine on 12/06/17.
 */

public interface PostRequest {


    @POST("api/AppAsociado/PostEstado")
    Call<EstadoResponse> sendEstado(@Header("Authorization") String token,
                            @Body SendEstado sendEstado);

    @POST("api/AppAsociado/PostServiciosSeguimiento")
    Call<String> seguimiento(@Header("Authorization") String token,
                             @Body SeguimientoEntity seguimientoEntity);

    @POST("api/AppAsociado/PostServiciosCostos")
    Call<String> sendCostos(@Header("Authorization") String token,
                            @Body CostoEntity costoEntity);


    @POST("api/AppAsociado/PostServiciosEnvioGPS")
    Call<String> sendGPS(@Header("Authorization") String token,
                         @Body GpsEntity gpsEntity);


}

