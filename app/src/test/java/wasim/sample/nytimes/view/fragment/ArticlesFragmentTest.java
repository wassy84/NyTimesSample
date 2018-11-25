package wasim.sample.nytimes.view.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import wasim.sample.nytimes.R;
import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.views.fragments.ArticlesFragment;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
public class ArticlesFragmentTest {

    private ArticlesFragment mFragment;
    private List<Result> mList;
    

    @Before
    public void setup() {
        RuntimeEnvironment.application.getPackageName();
        RuntimeEnvironment.application.setTheme(R.style.AppTheme);
        mFragment = new ArticlesFragment();
        //mFragment = Robolectric.buildFragment(ArticlesFragment.class).create().get()
    }

    @Test
    public void minimalFragmentTest() throws Exception {
        SupportFragmentTestUtil.startVisibleFragment(mFragment) ;
        assertThat(mFragment.getView()).isNotNull();
    }


    @Test
    public void DestroyView_test() throws Exception {
        SupportFragmentTestUtil.startVisibleFragment(mFragment) ;
        mFragment.onDestroyView();
        assertThat(mFragment.mArticlesPresenter).isNotNull();
        assertThat(mFragment.mArticleRecycleView).isNull();
    }


    @Test
    public void Destroy_test() throws Exception {
        SupportFragmentTestUtil.startVisibleFragment(mFragment) ;
        mFragment.onDestroy();
        assertThat(mFragment.mArticlesPresenter).isNull();
        assertThat(mFragment.mArticleRecycleView).isNull();
    }

    @Test
    public void errorView_test() throws Exception {
        SupportFragmentTestUtil.startVisibleFragment(mFragment) ;
        mFragment.errorWhileFetching("Wrong Data");
        assertThat(mFragment.mErrorTextView.getVisibility()).isEqualTo(0);
        assertThat(mFragment.mErrorTextView.getText()).isEqualTo("Wrong Data");
    }



    @Test
    public void datafetchingComplete() throws Exception {
        SupportFragmentTestUtil.startVisibleFragment(mFragment) ;
        mFragment.showDataProgressFetching();
        assertThat(mFragment.progressBar.getVisibility()).isEqualTo(0);
        mFragment.datafetchingComplete();
        assertThat(mFragment.progressBar.getVisibility()).isEqualTo(8);
    }





    @Test
    public void showStories_test() throws Exception {
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


        mList = new ArrayList();
        mList.add(article1);
        mList.add(article2);
        SupportFragmentTestUtil.startVisibleFragment(mFragment) ;
        mFragment.showData(mList);
        assertThat(mFragment.mArticleRecycleViewAdapter.getItemCount()).isEqualTo(2);
        assertThat(mFragment.mArticleRecycleViewAdapter.getItemAtPosition(0).getPublishedDate()).isEqualToIgnoringCase("21-11-1980");

    }
}
