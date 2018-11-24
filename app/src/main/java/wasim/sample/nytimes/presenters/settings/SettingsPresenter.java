package wasim.sample.nytimes.presenters.settings;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import wasim.sample.nytimes.models.pref.PreferenceDataManager;


public class SettingsPresenter implements SettingsContract.Presenter{


    private WeakReference<SettingsContract.ViewOps> mView;
    private PreferenceDataManager mPrefernceManager;


    public SettingsPresenter( PreferenceDataManager pref){
        mPrefernceManager = pref;
    }


    @Override
    public void attachView(SettingsContract.ViewOps view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void saveDaysCatagory(String dayString, int index) {
        mPrefernceManager.setCurrentPeriod(dayString);
        mPrefernceManager.setSelectionDays(index);
        mView.get().saveDaysCatagorySuccess(dayString);
    }

    @Override
    public void saveSectionCatagory(String section, int index) {
        mPrefernceManager.setCurrentSection(section);
        mPrefernceManager.setSelectionSection(index);
        mView.get().saveSectionSuccess(section);

    }

    @Override
    public void fetchIntialValue() {
        mView.get().showDaysValue(mPrefernceManager.getCurrentPeriod());
        mView.get().showSectionValue(mPrefernceManager.getCurrentSection());
    }

    @Override
    public void getDialogSelectionValue() {
        mView.get().createSectionDialog(mPrefernceManager.getSelectionSection());
    }

    @Override
    public void getDialogDaysValue() {
        mView.get().createDaysDialog(mPrefernceManager.getSelectionDays());
    }


    @Override
    public void dettachView() {
        this.mView = null;
    }

    @Override
    public void destroyPresenter() {
        mPrefernceManager = null;
        this.mView = null;
    }
}
