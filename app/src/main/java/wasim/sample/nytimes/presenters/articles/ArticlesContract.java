package wasim.sample.nytimes.presenters.articles;

import java.util.List;

import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.presenters.BaseContract;

public interface ArticlesContract  {

    interface Presenter extends BaseContract.Presenter {
        void attachView(ViewOps View);
        void fetchData(boolean forceFetch);
        void refresh();
        void RefreshOnResumeIfRequired();
        void launchNewActivity();
    }

    interface ViewOps extends BaseContract.ViewOps{
        void showData(List<Result> listArticles);
        void errorWhileFetching(String msg);
        void showDataProgressFetching();
        void datafetchingComplete();
        void hideRefresh();
        void isInternetConnected(boolean val);
        void launchActivity();
    }
}
