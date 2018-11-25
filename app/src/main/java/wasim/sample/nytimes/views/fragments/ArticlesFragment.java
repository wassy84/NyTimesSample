package wasim.sample.nytimes.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import wasim.sample.nytimes.NyTimesApplication;
import wasim.sample.nytimes.R;
import wasim.sample.nytimes.models.network.DataSource;
import wasim.sample.nytimes.models.pojo.Result;
import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.presenters.articles.ArticlePresenter;
import wasim.sample.nytimes.presenters.articles.ArticlesContract;
import wasim.sample.nytimes.utils.InternetCheck;
import wasim.sample.nytimes.utils.schedulers.BaseSchedulerProvider;
import wasim.sample.nytimes.views.Settings;
import wasim.sample.nytimes.views.adapters.ArticlesAdapter;

public class ArticlesFragment extends Fragment implements ArticlesContract.ViewOps{

    public RecyclerView mArticleRecycleView;
    public ProgressBar progressBar;
    public TextView mErrorTextView;
    private SwipeRefreshLayout mSwipeRefresh;
    private LinearLayout mErrorLay;
    public ArticlePresenter mArticlesPresenter;

    @Inject
    public ArticlesAdapter mArticleRecycleViewAdapter;

    @Inject
    public PreferenceDataManager mPref;

    @Inject
    DataSource dataSource;

    @Inject
    BaseSchedulerProvider prvd;

    @Inject
    InternetCheck internet;

    @Inject
    PreferenceDataManager pref;

    @Override
    public void onAttach(Context context) {
        ((NyTimesApplication)context.getApplicationContext()).getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.articles_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mArticlesPresenter == null){
           mArticlesPresenter =  new ArticlePresenter(dataSource, prvd, internet, pref);
        }
        mArticlesPresenter.attachView(this);
        mArticlesPresenter.fetchData( false);
        pullToRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        mArticlesPresenter.RefreshOnResumeIfRequired();
    }

    private void initViews(View view){
        mArticleRecycleView = view.findViewById(R.id.recyclerView);
        mArticleRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArticleRecycleView.setAdapter(mArticleRecycleViewAdapter);
        mArticleRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        progressBar = view.findViewById(R.id.progressBar);
        mErrorTextView = view.findViewById(R.id.error_text);
        mErrorLay = view.findViewById(R.id.error_lay);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.sw_refresh_lay);
    }

    public void pullToRefresh(){
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mArticlesPresenter.refresh();
            }
        });
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onDestroy() {
        mArticlesPresenter.dettachView();
        mArticlesPresenter.destroyPresenter();
        if(mArticleRecycleViewAdapter != null)
            mArticleRecycleViewAdapter.updateDataList(null);
        mArticleRecycleView = null;
        mArticlesPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        mArticlesPresenter.dettachView();
        if(mArticleRecycleViewAdapter != null)
            mArticleRecycleViewAdapter.updateDataList(null);
        mArticleRecycleView = null;
        super.onDestroyView();
    }

    @Override
    public void showData(List<Result> listArticles) {
        mErrorTextView.setVisibility(View.GONE);
        mErrorLay.setVisibility(View.GONE);
        mSwipeRefresh.setVisibility(View.VISIBLE);
        mArticleRecycleViewAdapter.updateDataList(listArticles);
    }

    @Override
    public void errorWhileFetching(String msg) {
        if(mArticleRecycleViewAdapter != null)
            mArticleRecycleViewAdapter.updateDataList(null);
        mSwipeRefresh.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorLay.setVisibility(View.VISIBLE);
        mErrorTextView.setText(msg);
    }

    @Override
    public void showDataProgressFetching() {
        mErrorTextView.setVisibility(View.GONE);
        mErrorLay.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void datafetchingComplete() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideRefresh() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void isInternetConnected(boolean val) {
        if(!val) {
            Snackbar snackbar1 = Snackbar.make(getView(), "Sorry, You dont have an active internet connection!", Snackbar.LENGTH_SHORT);
            snackbar1.show();
        }
    }

    @Override
    public void launchActivity() {
        Intent abc = new Intent(getActivity(), Settings.class);
        startActivity(abc);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.op_settings) {
            mArticlesPresenter.launchNewActivity();
        }
        return super.onOptionsItemSelected(item);
    }


}
