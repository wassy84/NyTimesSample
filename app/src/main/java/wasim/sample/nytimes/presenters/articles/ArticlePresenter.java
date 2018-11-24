package wasim.sample.nytimes.presenters.articles;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import wasim.sample.nytimes.models.network.DataSource;
import wasim.sample.nytimes.models.pojo.Response;
import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.presenters.articles.ArticlesContract;
import wasim.sample.nytimes.utils.InternetCheck;
import wasim.sample.nytimes.utils.schedulers.BaseSchedulerProvider;

public class ArticlePresenter implements ArticlesContract.Presenter{

    private WeakReference<ArticlesContract.ViewOps> mView;
    private BaseSchedulerProvider mSchedulerProvider;
    private CompositeSubscription mSubscriptions;
    private DataSource mDataSource;
    private InternetCheck mInternet;
    private List<Result> mResults;
    private PreferenceDataManager mPrefernceManager;
    private String mSectionSel, mLimiter;


    public ArticlePresenter(DataSource src, BaseSchedulerProvider prvd, InternetCheck internet, PreferenceDataManager pref){
        mDataSource = src;
        mSchedulerProvider = prvd;
        mSubscriptions = new CompositeSubscription();
        mInternet =internet;
        mPrefernceManager = pref;
    }


    private void passDataFetchToModel(String section, String limiter, boolean force){
        mView.get().isInternetConnected(mInternet.isOnline());
        mSectionSel = section;
        mLimiter = limiter;
        if(!force){
            if(mResults != null){
                mView.get().datafetchingComplete();
                mView.get().showData(mResults);
                return;
            }
        }
        Subscription subscription = mDataSource.getArticlesRx(section, limiter)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onNext(Response response) {
                        if(response.getNumResults() > 0) {
                            mView.get().showData(response.getResults());
                            mView.get().hideRefresh();
                            mResults = response.getResults();
                        }else{
                            mView.get().errorWhileFetching("Sorry! No Stories avaliable for this topic");
                            mView.get().datafetchingComplete();
                            mView.get().hideRefresh();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.get().datafetchingComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mResults = null;
                        mView.get().errorWhileFetching("Sorry! Something went Wrong");
                        mView.get().datafetchingComplete();
                        mView.get().hideRefresh();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void fetchData( boolean force) {
        mView.get().showDataProgressFetching();
        String limiter = mPrefernceManager.getCurrentPeriod();
        String section = mPrefernceManager.getCurrentSection();
        passDataFetchToModel(section, limiter, force);
    }

    public void RefreshOnResumeIfRequired(){
        String limiter = mPrefernceManager.getCurrentPeriod();
        String section = mPrefernceManager.getCurrentSection();
        if(!limiter.equals(mLimiter) || !section.equals(mSectionSel)){
            mView.get().showDataProgressFetching();
            passDataFetchToModel(section, limiter, true);
        }
    }

    @Override
    public void refresh() {
        String limiter = mPrefernceManager.getCurrentPeriod();
        String section = mPrefernceManager.getCurrentSection();
        passDataFetchToModel(section, limiter, true);
    }

    @Override
    public void launchNewActivity() {
        mView.get().launchActivity();
    }

    @Override
    public void attachView(ArticlesContract.ViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void dettachView() {
        this.mView = null;
    }

    @Override
    public void destroyPresenter() {
        if(mSubscriptions!=null && !mSubscriptions.isUnsubscribed())
            mSubscriptions.unsubscribe();
        mResults = null;
        this.mView = null;
    }
}
