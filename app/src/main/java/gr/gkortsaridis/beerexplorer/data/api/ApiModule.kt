package gr.gkortsaridis.beerexplorer.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    }
}