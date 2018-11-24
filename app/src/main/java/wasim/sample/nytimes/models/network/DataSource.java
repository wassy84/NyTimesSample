package wasim.sample.nytimes.models.network;


import retrofit2.Retrofit;
import rx.Observable;
import wasim.sample.nytimes.models.pojo.Response;

public class DataSource implements ApiInterface{

    private ApiInterface mApiInterface;

    public DataSource(Retrofit rf){
        mApiInterface = rf.create(ApiInterface.class);
    }

    @Override
    public Observable<Response> getArticlesRx(String section, String day_limit) {
        return mApiInterface.getArticlesRx(section, day_limit);
    }

}
