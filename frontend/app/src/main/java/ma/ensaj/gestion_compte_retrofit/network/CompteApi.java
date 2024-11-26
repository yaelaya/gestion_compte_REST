package ma.ensaj.gestion_compte_retrofit.network;

import java.util.List;

import ma.ensaj.gestion_compte_retrofit.models.Compte;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CompteApi {
    @GET("banque/comptes") // Récupérer tous les comptes
    Call<List<Compte>> getAllComptes(@Header("Accept") String acceptHeader);


    @PUT("banque/comptes/{id}") // Mettre à jour un compte
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte);

    @DELETE("banque/comptes/{id}") // Supprimer un compte
    Call<Void> deleteCompte(@Path("id") Long id);
    @POST("banque/comptes")
    Call<Compte> addCompte(@Body Compte compte);
}
