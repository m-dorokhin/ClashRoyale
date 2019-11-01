package com.example.clashroyale.api;

import androidx.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApiModule {
    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://www.clashapi.xyz")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    public ClashRoyaleApi provideClashRoyalApi(@NonNull Retrofit retrofit) {
        return retrofit.create(ClashRoyaleApi.class);
    }

    @Provides
    public Api provideApi(@NonNull ClashRoyaleApi clashRoyaleApi) {
        return new Api(clashRoyaleApi);
    }
}
