package balcorasystems.slw41;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Fragment_Selection.OnSwitchSelectionListener
    {


    public void onModeSelection()
    {
        FragmentTransaction modeSelectionTransaction = getSupportFragmentManager().beginTransaction();
        modeSelectionTransaction.replace(R.id.mainFrameLayout, new Fragnent_SimpleMode());
        modeSelectionTransaction.addToBackStack(null);
        modeSelectionTransaction.commit();
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
        Toast.makeText(this, "Test ads are being shown. " + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.", Toast.LENGTH_LONG).show();

        //populate initial selection fragment into main content layout
        //should later add a check to not re populate if view is changed or something
        mainFT.replace(R.id.mainFrameLayout, new Fragment_Selection());
        mainFT.commit();
    }



}
