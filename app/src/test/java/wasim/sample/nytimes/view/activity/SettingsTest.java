package wasim.sample.nytimes.view.activity;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import wasim.sample.nytimes.BuildConfig;
import wasim.sample.nytimes.R;
import wasim.sample.nytimes.views.Settings;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class SettingsTest {

    private Settings activity;
    private ActivityController<Settings> controller;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(Settings.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView tvDays= (TextView) activity.findViewById(R.id.hdr_days);
        assertNotNull("View Not Found", tvDays);
        assertTrue("Select Days".equals(tvDays.getText().toString()));
        TextView tvSection= (TextView) activity.findViewById(R.id.hdr_sections);
        assertNotNull("Select Section", tvSection);
        assertTrue("Select Days".equals(tvDays.getText().toString()));


    }

    @Test
    public void showDaysValue_test() {
        activity.showDaysValue("hello");
        assertThat(activity.mDaysTxt.getText()).isEqualTo("hello");
    }

    @Test
    public void showSectionValue_test() {
        activity.showSectionValue("Love");
        assertThat(activity.mSectionsTxt.getText()).isEqualTo("Love");
    }

    @Test
    public void saveDaysCatagorySuccess_test() {
        activity.saveDaysCatagorySuccess("hate");
        assertThat(activity.mDaysTxt.getText()).isEqualTo("hate");
    }

    @Test
    public void saveSectionSuccess_test() {
        activity.saveSectionSuccess("hello");
        assertThat(activity.mSectionsTxt.getText()).isEqualTo("hello");
    }

}
