package wasim.sample.nytimes.models.pref;

import android.content.SharedPreferences;

import javax.inject.Inject;

import wasim.sample.nytimes.utils.Constants;

public class PreferenceDataManager implements PreferenceInterface {

    public SharedPreferences mPrefs;


    @Inject
    public PreferenceDataManager(SharedPreferences mpref) {
        mPrefs = mpref;
    }


    @Override
    public String getCurrentSection() {
        return mPrefs.getString(Constants.PREF_KEY_SECTION_SELECTED, "all-sections");
    }

    @Override
    public void setCurrentSection(String section) {
        mPrefs.edit().putString(Constants.PREF_KEY_SECTION_SELECTED, section).apply();
    }


    @Override
    public String getCurrentPeriod(){
        return mPrefs.getString(Constants.PREF_KEY_PERIOD_SELECTED, "1");
    }

    @Override
    public void setCurrentPeriod(String period) {
        mPrefs.edit().putString(Constants.PREF_KEY_PERIOD_SELECTED, period).apply();
    }



    @Override
    public int getSelectionSection() {
        return mPrefs.getInt(Constants.PREF_KEY_SECTION_SELECTED_INDEX, 0);
    }

    @Override
    public void setSelectionSection(int val) {
        mPrefs.edit().putInt(Constants.PREF_KEY_SECTION_SELECTED_INDEX, val).apply();
    }

    @Override
    public void setSelectionDays(int val) {
        mPrefs.edit().putInt(Constants.PREF_KEY_PERIOD_SELECTED_INDEX, val).apply();
    }

    @Override
    public int getSelectionDays() {
        return mPrefs.getInt(Constants.PREF_KEY_PERIOD_SELECTED_INDEX, 0);
    }

}
