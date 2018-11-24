package wasim.sample.nytimes.presenters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import wasim.sample.nytimes.models.network.DataSource;
import wasim.sample.nytimes.models.pojo.Response;
import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.presenters.articles.ArticlePresenter;
import wasim.sample.nytimes.presenters.articles.ArticlesContract;
import wasim.sample.nytimes.utils.InternetCheck;
import wasim.sample.nytimes.utils.schedulers.BaseSchedulerProvider;
import wasim.sample.nytimes.utils.schedulers.ImmediateSchedulerProvider;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticlePresenterTest {

    @Mock
    private DataSource mDataSource;

    @Mock
    private ArticlesContract.ViewOps mView;

    @Mock
    InternetCheck mInterCheck;

    @Mock
    PreferenceDataManager mPreferenceData;

    private BaseSchedulerProvider mSchedulerProvider;

    ArticlePresenter mPresenter;

    List<Result> mResultList;
    List<Result> mResultEmptyList;
    Response mEmptyResponse;
    Response mResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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

        mResultEmptyList =new ArrayList<>();
        mEmptyResponse = new Response();
        mEmptyResponse.setNumResults(0);
        mEmptyResponse.setResults(mResultEmptyList);
        mSchedulerProvider = new ImmediateSchedulerProvider();
        mPresenter = new ArticlePresenter(mDataSource, mSchedulerProvider, mInterCheck, mPreferenceData);
        mPresenter.attachView(mView);




    }

    @Test
    public void fetchData() {
        when(mDataSource.getArticlesRx("all-section","1")).thenReturn(rx.Observable.just(mResponse));
        when(mInterCheck.isOnline()).thenReturn(true);
        when(mPreferenceData.getCurrentPeriod()).thenReturn("1");
        when(mPreferenceData.getCurrentSection()).thenReturn("all-section");
        mPresenter.fetchData(true);
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showData(mResultList);
        inOrder.verify(mView).datafetchingComplete();

    }

    @Test
    public void fetchDataWithZeroValues() {
        when(mDataSource.getArticlesRx("all-section","1")).thenReturn(rx.Observable.just(mEmptyResponse));
        when(mInterCheck.isOnline()).thenReturn(true);
        when(mPreferenceData.getCurrentPeriod()).thenReturn("1");
        when(mPreferenceData.getCurrentSection()).thenReturn("all-section");
        mPresenter.fetchData(true);
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).errorWhileFetching("Sorry! No Stories avaliable for this topic");
        verify(mView, never()).showData(anyList());

    }

    @Test
    public void fetchError() {
        when(mDataSource.getArticlesRx("all-section","1")).thenReturn(Observable.error(new Throwable("Sorry! Something went Wrong")));
        when(mInterCheck.isOnline()).thenReturn(true);
        when(mPreferenceData.getCurrentPeriod()).thenReturn("1");
        when(mPreferenceData.getCurrentSection()).thenReturn("all-section");
        mPresenter.fetchData( true);
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).errorWhileFetching("Sorry! Something went Wrong");
        verify(mView, never()).showData(anyList());
    }

    @Test
    public void fetchErrorTrue() {
        when(mDataSource.getArticlesRx("all-section","1")).thenReturn(rx.Observable.just(mResponse));
        when(mInterCheck.isOnline()).thenReturn(true);
        when(mPreferenceData.getCurrentPeriod()).thenReturn("1");
        when(mPreferenceData.getCurrentSection()).thenReturn("all-section");
        mPresenter.fetchData(true);
        mPresenter.fetchData(false);
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showData(mResultList);
        inOrder.verify(mView).datafetchingComplete();
    }

    @Test
    public void refreshTest() {
        when(mDataSource.getArticlesRx("all-section","1")).thenReturn(rx.Observable.just(mResponse));
        when(mInterCheck.isOnline()).thenReturn(true);
        when(mPreferenceData.getCurrentPeriod()).thenReturn("1");
        when(mPreferenceData.getCurrentSection()).thenReturn("all-section");
        mPresenter.refresh();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showData(mResultList);
        inOrder.verify(mView).datafetchingComplete();
    }

    @Test
    public void launchNewActivityTest() {
        mPresenter.launchNewActivity();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView, never()).showData(anyList());
    }

    @Test
    public void RefreshOnResumeIfRequiredTest() {
        when(mDataSource.getArticlesRx("all-section","1")).thenReturn(rx.Observable.just(mResponse));
        when(mInterCheck.isOnline()).thenReturn(true);
        when(mPreferenceData.getCurrentPeriod()).thenReturn("1");
        when(mPreferenceData.getCurrentSection()).thenReturn("all-section");
        mPresenter.RefreshOnResumeIfRequired();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showData(mResultList);
        inOrder.verify(mView).datafetchingComplete();
    }



    @Test
    public void InternetTest_pass(){
        when(mInterCheck.isOnline()).thenReturn(true);
        boolean val = mInterCheck.isOnline();
        assertThat(val).isEqualTo(true);
    }

    @Test
    public void InternetTest_fail(){
        when(mInterCheck.isOnline()).thenReturn(false);
        boolean val = mInterCheck.isOnline();
        assertThat(val).isEqualTo(false);
    }

    @Test
    public void dettachViewTest(){
        mPresenter.dettachView();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void destroyPresenterTest() {
        mPresenter.destroyPresenter();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verifyNoMoreInteractions();
    }



}
