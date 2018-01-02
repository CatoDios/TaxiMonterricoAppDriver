package tmsystem.com.tmsystemdriver.data.remote.request;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;

/**
 * Created by katherine on 12/06/17.
 */

public interface PostRequest {


    @POST("api/AppAsociado/PostEstado")
    Call<EstadoResponse> sendEstado(@Header("Authorization") String token,
                            @Body SendEstado sendEstado);
}
