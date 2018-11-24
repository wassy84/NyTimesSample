package wasim.sample.nytimes;

import android.app.Application;

import wasim.sample.nytimes.di.AppComponent;
import wasim.sample.nytimes.di.AppModules;
import wasim.sample.nytimes.di.DaggerAppComponent;

public class NyTimesApplication extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModules(new AppModules(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
