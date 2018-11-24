package wasim.sample.nytimes.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;


public class InternetCheck {

    private Context mContext;

    public InternetCheck(Application app){
        mContext = app.getApplicationContext();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
