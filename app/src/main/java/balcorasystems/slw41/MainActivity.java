package balcorasystems.slw41;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kofigyan.stateprogressbar.StateProgressBar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class MainActivity extends AppCompatActivity implements
        Fragment_Selection.OnNavigateAwayListener,
        Fragment_Info.onSaveMoneyNowButton,
        Fragment_Results.goToSummaryListener,
        Fragment_Summary.goProButtonListener {

    public static Integer simpleIncome = 0;
    public static Integer simpleDebt = 0;
    public static Double currentStdPayment = 0.0;
    public static Double projectedMonthlySavings = 0.0;
    public static Double newMonthlyPayment = 0.0;
    public static String planReccomendation = "";
    public Integer topState = 1;

    public void navigateToChoice() {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Info());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void onIncomeFinished(int income, int debt) {
        simpleIncome = income * 1000;
        simpleDebt = debt * 1000;

        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Results());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToSummary(Double stdPayment, Double newPayment, String plan) {
        currentStdPayment = stdPayment;
        newMonthlyPayment = newPayment;
        planReccomendation = plan;
        projectedMonthlySavings = stdPayment - newPayment;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Summary());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void goPro() {

    }


    FragmentTransaction mainFT = getSupportFragmentManager().beginTransaction();
    public static StateProgressBar topProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        String[] topStateDescriptors = {"Welcome", "Info", "Savings", "Summary", "Finish"};

        topProgressBar = (StateProgressBar) findViewById(R.id.state_progress_bar);
        topProgressBar.enableAnimationToCurrentState(true);
        topProgressBar.setStateDescriptionData(topStateDescriptors);

//        FrameLayout centralFragmentFrame = (FrameLayout) findViewById(R.id.mainFrameLayout);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
//        Toast.makeText(this, "Test ads are being shown. " + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.", Toast.LENGTH_LONG).show();

        //populate initial selection fragment into main content layout
        //should later add a check to not re populate if view is changed or something
        mainFT.replace(R.id.mainFrameLayout, new Fragment_Selection());
        mainFT.commit();
    }

}
