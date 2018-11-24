package wasim.sample.nytimes.utils;

import wasim.sample.nytimes.BuildConfig;

public class Constants {
    //base Url
    public final static String URL_BASE = "http://api.nytimes.com/";

    public final static boolean DEBUG = BuildConfig.DEBUG;
    public final static String LOG_NAME= "NYTimes";

    //Pref Keys
    public static final String PREF_KEY_SECTION_SELECTED = "pref_key_section_selected";
    public static final String PREF_KEY_SECTION_SELECTED_INDEX = "pref_key_section_selected_index";
    public static final String PREF_KEY_PERIOD_SELECTED = "pref_key_period_selected";
    public static final String PREF_KEY_PERIOD_SELECTED_INDEX = "pref_key_period_selected_index";
}
