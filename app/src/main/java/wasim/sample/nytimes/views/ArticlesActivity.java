package wasim.sample.nytimes.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
