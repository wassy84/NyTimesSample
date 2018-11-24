package wasim.sample.nytimes.models.pref;

public interface PreferenceInterface {

        String getCurrentSection();
        void setCurrentSection(String section);
        String getCurrentPeriod();
        void setCurrentPeriod(String period);
        int getSelectionDays();
        int getSelectionSection();
        void setSelectionSection(int val);
        void setSelectionDays(int val);


}
