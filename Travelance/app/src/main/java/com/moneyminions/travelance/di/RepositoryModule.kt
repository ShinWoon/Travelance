package com.moneyminions.travelance.di

import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.example.ExampleDataSource
import com.moneyminions.data.datasource.remote.home.HomeDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.datasource.remote.mypage.MyPageDataSource
import com.moneyminions.data.datasource.remote.traveldetail.TravelDetailDataSource
import com.moneyminions.data.datasource.remote.traveledit.TravelEditDataSource
import com.moneyminions.data.datasource.remote.travellist.TravelListDataSource
import com.moneyminions.data.repository.PreferenceRepositoryImpl
import com.moneyminions.data.repository.example.ExampleRepositoryImpl
import com.moneyminions.data.repository.home.HomeRepositoryImpl
import com.moneyminions.data.repository.login.LoginRepositoryImpl
import com.moneyminions.data.repository.mypage.MyPageRepositoryImpl
import com.moneyminions.data.repository.traveldetail.TravelDetailRepositoryImpl
import com.moneyminions.data.repository.traveledit.TravelEditRepositoryImpl
import com.moneyminions.data.repository.travellist.TravelListRepositoryImpl
import com.moneyminions.domain.repository.PreferenceRepository
import com.moneyminions.domain.repository.example.ExampleRepository
import com.moneyminions.domain.repository.home.HomeRepository
import com.moneyminions.domain.repository.login.LoginRepository
import com.moneyminions.domain.repository.mypage.MyPageRepository
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import com.moneyminions.domain.repository.traveledit.TravelEditRepository
import com.moneyminions.domain.repository.travellist.TravelListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    //    @Singleton
//    @Provides
//    fun provideGitHubRepository(gitHubService: GitHubService): GitHubRepository{
//        return GitHubRepositoryImpl(gitHubService)
//    }
    @Singleton
    @Provides
    fun provideExampleRepository(exampleDataSource: ExampleDataSource): ExampleRepository {
        return ExampleRepositoryImpl(exampleDataSource)
    }

    @Singleton
    @Provides
    fun providePreferenceRepository(preferenceDataSource: PreferenceDataSource): PreferenceRepository {
        return PreferenceRepositoryImpl(preferenceDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource, preferenceDataSource: PreferenceDataSource): LoginRepository {
        return LoginRepositoryImpl(loginDataSource, preferenceDataSource)
    }

    @Singleton
    @Provides
    fun provideTravelListRepository(travelListDataSource: TravelListDataSource): TravelListRepository {
        return TravelListRepositoryImpl(travelListDataSource)
    }
    @Singleton
    @Provides
    fun provideMyPageRepository(myPageDataSource: MyPageDataSource): MyPageRepository {
        return MyPageRepositoryImpl(myPageDataSource)
    }

    @Singleton
    @Provides
    fun provideTravelDetailRepository(travelDetailDataSource: TravelDetailDataSource): TravelDetailRepository {
        return TravelDetailRepositoryImpl(travelDetailDataSource)
    }
    
    @Singleton
    @Provides
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository {
        return HomeRepositoryImpl(homeDataSource)
    }

    @Singleton
    @Provides
    fun provideTravelEditRepository(travelEditDataSource: TravelEditDataSource): TravelEditRepository {
        return TravelEditRepositoryImpl(travelEditDataSource)
    }
}