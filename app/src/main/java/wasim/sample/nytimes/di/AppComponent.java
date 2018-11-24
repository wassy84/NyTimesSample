package wasim.sample.nytimes.di;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import wasim.sample.nytimes.views.ArticlesActivity;
import wasim.sample.nytimes.views.Settings;
import wasim.sample.nytimes.views.fragments.ArticlesFragment;

@Singleton
@Component(modules = {AppModules.class})
public interface AppComponent {
    void inject(ArticlesFragment articlesFragment);
    void inject(Settings settings);
}
