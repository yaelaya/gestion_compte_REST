package ma.ensaj.gestion_compte_retrofit.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class CompteService {
    private static Retrofit retrofit;

    public static Retrofit getInstance(boolean isXmlFormat) {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.217:8082/")
                    .addConverterFactory(isXmlFormat ? SimpleXmlConverterFactory.create() : GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }
}

