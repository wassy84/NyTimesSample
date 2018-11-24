package wasim.sample.nytimes.di;


import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wasim.sample.nytimes.NyTimesApplication;
import wasim.sample.nytimes.models.network.DataSource;
import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.presenters.articles.ArticlePresenter;
import wasim.sample.nytimes.presenters.settings.SettingsPresenter;
import wasim.sample.nytimes.utils.Constants;
import wasim.sample.nytimes.utils.InternetCheck;
import wasim.sample.nytimes.utils.schedulers.BaseSchedulerProvider;
import wasim.sample.nytimes.utils.schedulers.SchedulerProvider;
import wasim.sample.nytimes.views.adapters.ArticlesAdapter;

@Module
public class AppModules {

    private NyTimesApplication nyTimesApplication;

    public AppModules(NyTimesApplication application){
        nyTimesApplication = application;
    }


    @Provides
    @Singleton
    NyTimesApplication provideApplication(){
        return nyTimesApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return nyTimesApplication.getApplicationContext();
    }

    @Singleton
    @Provides
    DataSource provideRemoteDataSource(OkHttpClient okHttpClient) {
        return new DataSource( new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build());}
    @Singleton
    @Provides
    OkHttpClient provideOkhttpClient(Interceptor newInceptor, Cache cache) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(newInceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
        return okHttpClient;
    }

    @Provides
    Interceptor provideInterceptor(InternetCheck internet){
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = null;
                if(internet.isOnline()) {
                    request = original.newBuilder().header("Cache-Control", "public, max-age=" + 10).build();
                }else
                    request = original.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        };

    }

    @Provides
    @Singleton
    Cache provideHttpCache(NyTimesApplication application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);

    }

    @Provides
    InternetCheck provideInternetCheck(NyTimesApplication application){
        return new InternetCheck(application);
    }

    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Provides
    ArticlesAdapter provideArticlesAdapter(){
        return new ArticlesAdapter();
    }


    @Provides
    PreferenceDataManager providePreferenceDataManager( Context Ctx){
        return new PreferenceDataManager(Ctx,"sample_pref");
    }

    @Provides
    SettingsPresenter provideSettingsPresenter( PreferenceDataManager pref){
        return new SettingsPresenter(pref);
    }

    @Provides
    ArticlePresenter provideArticlePresenterArticlePresenter(DataSource src, BaseSchedulerProvider prvd, InternetCheck internet, PreferenceDataManager pref){
        return new ArticlePresenter(src, prvd, internet, pref);
    }


}
