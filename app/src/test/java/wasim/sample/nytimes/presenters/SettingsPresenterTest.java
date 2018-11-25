package wasim.sample.nytimes.presenters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.presenters.settings.SettingsContract;
import wasim.sample.nytimes.presenters.settings.SettingsPresenter;

import static org.mockito.Mockito.when;

public class SettingsPresenterTest {

    @Mock
    private SettingsContract.ViewOps mView;


    @Mock
    private PreferenceDataManager mPreferenceData;

    SettingsPresenter mPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SettingsPresenter(mPreferenceData);
        mPresenter.attachView(mView);
    }

    @Test
    public void SaveDaysTest() {
        mPresenter.saveDaysCatagory("1", 10);
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).saveDaysCatagorySuccess("1");
    }

    @Test
    public void saveSectionCatagoryTest() {
        mPresenter.saveSectionCatagory("Hello", 10);
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).saveSectionSuccess("Hello");

    }

    @Test
    public void fetchIntialValueTest() {
        when(mPreferenceData.getCurrentPeriod()).thenReturn("12");
        when(mPreferenceData.getCurrentSection()).thenReturn("Hello");
        mPresenter.fetchIntialValue();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showDaysValue("12");
        inOrder.verify(mView).showSectionValue("Hello");

    }

    @Test
    public void getDialogSelectionValueTest() {
        when(mPreferenceData.getSelectionSection()).thenReturn(10);
        mPresenter.getDialogSelectionValue();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).createSectionDialog(10);
    }

    @Test
    public void getDialogDaysValueTest() {
        when(mPreferenceData.getSelectionDays()).thenReturn(5);
        mPresenter.getDialogDaysValue();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).createDaysDialog(5);
    }

    @Test
    public void dettachViewTest(){
        mPresenter.dettachView();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void destroyPresenterTest() {
        mPresenter.destroyPresenter();
        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verifyNoMoreInteractions();
    }


}
