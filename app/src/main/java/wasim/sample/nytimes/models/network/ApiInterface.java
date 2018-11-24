package wasim.sample.nytimes.models.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import wasim.sample.nytimes.models.pojo.Response;

public interface ApiInterface {

    @GET("svc/mostpopular/v2/mostviewed/{section}/{day_limit}.json?api-key=fefdd99eada24fe9a2fbf1ee7418f356")
    Observable<Response> getArticlesRx(@Path("section") String section, @Path("day_limit") String day_limit);
}
