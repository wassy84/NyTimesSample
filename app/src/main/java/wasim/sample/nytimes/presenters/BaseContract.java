package wasim.sample.nytimes.presenters;

public interface BaseContract {

    interface Presenter{
        void dettachView();
        void destroyPresenter();
    }

    interface ViewOps{
    }

}
