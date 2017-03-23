package balcorasystems.slw41;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        Fragment_Selection.OnNavigateAwayListener,
        Fragment_Info.onSaveMoneyNowButton,
        Fragment_Advice.goToSummaryListener,
        Fragment_Summary.goProButtonListener
    {


    public void onIncomeFinished()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Advice());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToChoice()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Info());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToSummary()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Summary());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void goPro()
    {

    }


        FragmentTransaction mainFT = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
