package wasim.sample.nytimes.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import wasim.sample.nytimes.NyTimesApplication;
import wasim.sample.nytimes.R;
import wasim.sample.nytimes.models.pref.PreferenceDataManager;
import wasim.sample.nytimes.presenters.settings.SettingsContract;
import wasim.sample.nytimes.presenters.settings.SettingsPresenter;

public class Settings extends AppCompatActivity implements SettingsContract.ViewOps{


    @Inject
    PreferenceDataManager mPrefmanager;
    @Inject
    SettingsPresenter mSetPresenter;
    public ImageButton mDays, mSections;
    public TextView mDaysTxt, mSectionsTxt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        ((NyTimesApplication)getApplicationContext()).getAppComponent().inject(this);
        initView();
        mSetPresenter.attachView(this);
        mSetPresenter.fetchIntialValue();
    }

    private void initView(){
        mDays = (ImageButton)findViewById(R.id.btn_days);
        mSections = (ImageButton)findViewById(R.id.btn_sections);
        mDaysTxt = (TextView)findViewById(R.id.txt_days);
        mSectionsTxt = (TextView)findViewById(R.id.txt_sections);

        mDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPresenter.getDialogDaysValue();
            }
        });
        mSections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPresenter.getDialogSelectionValue();
            }
        });
    }

    @Override
    public void showDaysValue(String nodays) {
        mDaysTxt.setText(nodays);
    }

    @Override
    public void showSectionValue(String section) {
        mSectionsTxt.setText(section);
    }

    @Override
    public void errorWhileFetching(String msg) {

    }

    @Override
    public void saveDaysCatagorySuccess(String msg) {
        mDaysTxt.setText(msg);
    }

    @Override
    public void saveSectionSuccess(String msg) {
        mSectionsTxt.setText(msg);
    }

    @Override
    public void createDaysDialog(int sel) {
        String[] menuArray = getResources().getStringArray(R.array.days);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select Day");
        b.setSingleChoiceItems(menuArray, sel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSetPresenter.saveDaysCatagory(menuArray[which], which);
                dialog.dismiss();
            }
        });

        AlertDialog d = b.create();
        d.show();
    }

    @Override
    public void createSectionDialog(int sel) {
        String[] menuArray = getResources().getStringArray(R.array.sections);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select Section");
        b.setSingleChoiceItems(menuArray, sel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSetPresenter.saveSectionCatagory(menuArray[which], which);
                dialog.dismiss();
            }
        });
        AlertDialog d = b.create();
        d.show();
    }

    @Override
    public void onBackPressed() {
        mSetPresenter.destroyPresenter();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mSetPresenter.destroyPresenter();
        super.onDestroy();
    }
}
