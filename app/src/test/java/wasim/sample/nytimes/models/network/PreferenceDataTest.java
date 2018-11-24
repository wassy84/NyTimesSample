package wasim.sample.nytimes.models.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.utils.Constants;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PreferenceDataTest {

    private PreferenceDataManager preferenceDataManager;

    Context mContext;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mContext = Mockito.mock(Context.class);
        preferenceDataManager = new PreferenceDataManager(mContext, "myPrefs");
        preferenceDataManager.mPrefs =  Mockito.mock(SharedPreferences.class);
    }

    @Test
    public void getCurrentSectionTest() {
        Mockito.when(preferenceDataManager.getSelectionDays()).thenReturn(10);
        int i = preferenceDataManager.getSelectionDays();
        assertThat(i).isEqualTo(10);
    }

    @Test
    public void getCurrentSectionTest_Wrong() {
        Mockito.when(preferenceDataManager.getSelectionDays()).thenReturn(1);
        int i = preferenceDataManager.getSelectionDays();
        assertThat(i).isNotEqualTo(10);
    }

    @Test
    public void getCurrentPeriod(){
        Mockito.when(preferenceDataManager.getCurrentPeriod()).thenReturn("30");
        String day = preferenceDataManager.getCurrentPeriod();
        assertThat(day).isEqualTo("30");
    }

    @Test
    public void getSelectionSectionTest() {
        Mockito.when(preferenceDataManager.getSelectionSection()).thenReturn(10);
        int i = preferenceDataManager.getSelectionSection();
        assertThat(i).isEqualTo(10);
    }
    @Test
    public void getSelectionDaysTest() {
        Mockito.when(preferenceDataManager.getSelectionDays()).thenReturn(5);
        int i = preferenceDataManager.getSelectionDays();
        assertThat(i).isEqualTo(5);
    }


}
