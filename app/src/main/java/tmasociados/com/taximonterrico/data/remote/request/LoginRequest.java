package tmasociados.com.taximonterrico.data.remote.request;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tmasociados.com.taximonterrico.data.models.AccessTokenEntity;
import tmasociados.com.taximonterrico.data.models.UserEntity;

/**
 * Created by katherine on 10/05/17.
 */

public interface LoginRequest {
    @FormUrlEncoded
    @POST("token")
    Call<AccessTokenEntity> login(@Field("username") String email, @Field("password") String password,@Field("grant_type") String type);

    @GET("api/AppAsociado/GetLogin")
    Call<UserEntity> getUser(@Header("Authorization") String token,
                             @Query("email") String email,
                             @Query("Password") String pass);

   /*
    @FormUrlEncoded
    @POST("user/recovery/")
    Call<UserEntity> recovery(@Field("email") String email);

    @PUT("user/{id}/photo/")
    Call<UploadResponse> updatePhoto(@Header("Authorization") String token,
                                     @Path("id") int id,
                                     @Body RequestBody body);
*/

}
