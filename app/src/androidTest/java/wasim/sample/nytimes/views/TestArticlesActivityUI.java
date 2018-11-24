package wasim.sample.nytimes.views;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import wasim.sample.nytimes.R;
import wasim.sample.nytimes.views.adapters.ArticlesAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestArticlesActivityUI {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<ArticlesActivity> mActivityTestRule = new ActivityTestRule<>(ArticlesActivity.class);


    @Test
    public void ensureListViewIsPresent() throws Exception {
        ArticlesActivity activity = mActivityTestRule.getActivity();
        RecyclerView viewById = activity.findViewById(R.id.recyclerView);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView listView = (RecyclerView) viewById;
        ArticlesAdapter adapter = (ArticlesAdapter)listView.getAdapter();
        assertThat(adapter, instanceOf(ArticlesAdapter.class));

    }

    @Test
    public void loadMoreTest() {

        waitFor(10000);
        int swipeUp = 3;
        onView(withId(R.id.recyclerView)).perform(swipeDown());
        SystemClock.sleep(3000);
        onView(withId(R.id.recyclerView)).perform(swipeUp());
        SystemClock.sleep(3000);
        cleanUp();

    }




    public void waitFor(long waitingTime) {

        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);
    }

    public void cleanUp(){
        Espresso.unregisterIdlingResources(idlingResource);
    }

}