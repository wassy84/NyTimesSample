package wasim.sample.nytimes.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import wasim.sample.nytimes.R;
import wasim.sample.nytimes.models.pojo.MediaMetadatum;
import wasim.sample.nytimes.models.pojo.Medium;
import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.views.adapters.ArticlesAdapter;
import wasim.sample.nytimes.views.viewholder.ArticlesViewHolder;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;



@RunWith(RobolectricTestRunner.class)
public class TestArticlesAdapter  {

    private ArticlesAdapter adapter = null;


    private ArticlesViewHolder mStoryViewHolder;

    private List<Result> story = null;

    private Context context;

    @Before
    public void setUp() throws Exception {
        RuntimeEnvironment.application.getPackageName();
        adapter = new ArticlesAdapter();
        context =  RuntimeEnvironment.application;
    }

    @Test
    public void itemCountless0() {
        assertThat(adapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void itemCount() {
        Result story = new Result();
        adapter.updateDataList(asList(story, story, story));
        assertThat(adapter.getItemCount()).isEqualTo(3);
    }

    @Test
    public void getItemAtPosition() {
        Result firststory = new Result();
        Result secondstory = new Result();
        adapter.updateDataList(asList(firststory, secondstory));
        assertThat(adapter.getItemAtPosition(0)).isEqualTo(firststory);
        assertThat(adapter.getItemAtPosition(1)).isEqualTo(secondstory);
    }

    @Test
    public void onBindViewHolder_setsItemsTest() {
        Result article1 = new Result();
        article1.setTitle("News id great");
        article1.setPublishedDate("21-11-1980");
        article1.setUrl("http://www.google.com");
        article1.setByline("By Paper");

        Result article2 = new Result();
        article2.setTitle("Social Networking");
        article2.setPublishedDate("21-11-1980");
        article2.setUrl("http://www.facebook.com");
        article2.setByline("By fb");


        ArrayList<Result> mResultList = new ArrayList();
        mResultList.add(article1);
        mResultList.add(article2);


        adapter.updateDataList(mResultList);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItemView = inflater.inflate(R.layout.articles_row_view, null, false);
        mStoryViewHolder = new ArticlesViewHolder(listItemView);
        adapter.onBindViewHolder(mStoryViewHolder, 0);
        assertThat(mStoryViewHolder.txtHeader.getText().toString()).isEqualTo("News id great");
        assertThat(mStoryViewHolder.date.getText().toString()).isEqualTo("21-11-1980");

    }

    @Test
    public void onBindViewHolder_sFailure() {
        Result article1 = new Result();
        article1.setTitle("News id great");
        article1.setPublishedDate("21-11-1980");
        article1.setUrl("http://www.google.com");
        article1.setByline("By Paper");

        Result article2 = new Result();
        article2.setTitle("Social Networking");
        article2.setPublishedDate("21-11-1980");
        article2.setUrl("http://www.facebook.com");
        article2.setByline("By fb");


        ArrayList<Result> mResultList = new ArrayList();
        mResultList.add(article1);
        mResultList.add(article2);


        adapter.updateDataList(mResultList);
        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));
        mStoryViewHolder = adapter.onCreateViewHolder(rvParent, 0);
        adapter.onBindViewHolder(mStoryViewHolder, 0);
        assertThat(mStoryViewHolder.byLine.getText().toString()).isNotEqualTo("By fb");
    }


    @Test
    public void onBindViewHolder_ImageinView() {
        Result article1 = new Result();
        article1.setTitle("News id great");
        article1.setPublishedDate("21-11-1980");
        article1.setUrl("http://www.google.com");
        article1.setByline("By Paper");
        MediaMetadatum meta = new MediaMetadatum();
        meta.setUrl("https://image.tmdb.org/t/p/w92/dRLSoufWtc16F5fliK4ECIVs56p.jpg");
        Medium medium = new Medium();
        medium.setMediaMetadata(asList(meta));
        article1.setMedia(asList(medium));


        adapter.updateDataList(asList(article1));
        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));
        mStoryViewHolder = adapter.onCreateViewHolder(rvParent, 0);
        adapter.onBindViewHolder(mStoryViewHolder, 0);
        assertThat(mStoryViewHolder.byLine.getText().toString()).isNotEqualTo("By fb");
    }




}