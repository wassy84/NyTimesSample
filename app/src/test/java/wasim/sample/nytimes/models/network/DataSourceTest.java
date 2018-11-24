package wasim.sample.nytimes.models.network;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;
import wasim.sample.nytimes.di.AppComponent;
import wasim.sample.nytimes.models.pojo.Response;
import wasim.sample.nytimes.models.pojo.Result;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceTest {
    List<Result> mResultList;
    Response mResponse;

    MockWebServer mMockWebServer;

    TestSubscriber<Response> mSubscriber;

    @Before
    public void setUp() throws IOException {
        //Creating Response Object
        Result article1 = new Result();
        article1.setTitle("News id great");
        article1.setPublishedDate("21-11-1980");
        article1.setUrl("http://www.google.com");
        article1.setByline("By Paper");

        Result article2 = new Result();
        article2.setTitle("Social Networking");
        article2.setPublishedDate("21-11-1980");
        article2.setUrl("http://www.facebook.com");
        article2.setByline("By Paper");


        mResultList = new ArrayList();
        mResponse = new Response();
        mResultList.add(article1);
        mResultList.add(article2);
        mResponse.setCopyright("CopyWrite");
        mResponse.setNumResults(2);
        mResponse.setStatus("OK");
        mResponse.setResults(mResultList);

        mMockWebServer = new MockWebServer();
        mMockWebServer.start();
        mSubscriber = new TestSubscriber<>();
    }


    @Test
    public void serverCallWithError() {
        //Given
        String url = "fakeurl/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mResponse)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        DataSource remoteDataSource = new DataSource(retrofit);

        //When
        remoteDataSource.getArticlesRx("all-sections","7").subscribe(mSubscriber);


        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertCompleted();
    }

    @Test
    public void severCallWithSuccessful() {
        //Given
        String url = "http://api.nytimes.com/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mResponse)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        DataSource remoteDataSource = new DataSource(retrofit);

        //When
        remoteDataSource.getArticlesRx("all-sections","7").subscribe(mSubscriber);

        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertCompleted();
    }

    @After
    public void tearDown() throws Exception{
        mMockWebServer.shutdown();
    }
}
