package com.example.mappoints.di

import android.app.Application
import androidx.room.Room
import com.example.mappoints.db.AppDatabase
import com.example.mappoints.db.PointDao
import com.example.mappoints.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDb(context: Application): AppDatabase {
        return  Room.databaseBuilder(context, AppDatabase::class.java, "point_db").build()
    }

    @Provides
    @Singleton
    fun providePointDao(db: AppDatabase): PointDao {
        return db.pointDao()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        return Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/").client(
            okHttpClient
        )
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return  GsonBuilder()
            .setLenient()
            .create()
    }
}