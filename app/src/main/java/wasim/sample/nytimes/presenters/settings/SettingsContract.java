package wasim.sample.nytimes.presenters.settings;

import wasim.sample.nytimes.presenters.BaseContract;

public interface SettingsContract extends BaseContract.Presenter {

    interface Presenter extends BaseContract.Presenter{
        void attachView(SettingsContract.ViewOps View);
        void saveDaysCatagory(String day, int index);
        void saveSectionCatagory(String section, int index);
        void fetchIntialValue();;
        void getDialogSelectionValue();
        void getDialogDaysValue();
    }

    interface ViewOps extends BaseContract.ViewOps{
        void showDaysValue(String nodays);
        void showSectionValue(String section);
        void errorWhileFetching(String msg);
        void saveDaysCatagorySuccess(String msg);
        void saveSectionSuccess(String msg);
        void createDaysDialog(int sel);
        void createSectionDialog(int sel);
    }
}
