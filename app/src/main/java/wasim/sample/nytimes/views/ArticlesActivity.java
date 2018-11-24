package wasim.sample.nytimes.views;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import wasim.sample.nytimes.NyTimesApplication;
import wasim.sample.nytimes.R;
import wasim.sample.nytimes.models.network.DataSource;
import wasim.sample.nytimes.utils.schedulers.BaseSchedulerProvider;
import wasim.sample.nytimes.views.fragments.ArticlesFragment;

public class ArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new ArticlesFragment() )
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
