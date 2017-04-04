package balcorasystems.slw41;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Fragment_Selection.OnNavigateAwayListener

{

    public Integer backCounter =0;
    public Integer launchCount=0;

    public void navigateToChoice()
    {

        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);

//        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
//        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Info());
//        fTransaction.addToBackStack(null);
//        fTransaction.commit();
    }

//    public void onIncomeFinished(int income, int debt)
//    {
//        simpleIncome = income * 1000;
//        simpleDebt = debt * 1000;
//        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
//        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Results());
//        fTransaction.addToBackStack(null);
//        fTransaction.commit();
//    }
//
//    public void navigateToSummary(Double stdPayment, Double newPayment, String plan)
//    {
//        currentStdPayment = stdPayment;
//        newMonthlyPayment = newPayment;
//        planRecommendation = plan;
//        projectedMonthlySavings = stdPayment - newPayment;
//        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
//        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Summary());
//        fTransaction.addToBackStack(null);
//        fTransaction.commit();
//    }

    public void goPro() {

    }


    FragmentTransaction mainFT = getSupportFragmentManager().beginTransaction();

    public StartAppAd StartAppInterstitialAd = new StartAppAd(this);

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (launchCount%3==0)
//        {
//            StartAppInterstitialAd.onResume();
//        }
//
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (launchCount%3==0)
//        {
//            StartAppInterstitialAd.onPause();
//        }
//
//    }

    //back button ads seem kind of obnoxious, better to just put them when static place when restarting main activity.
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        backCounter++;
//
//        if (backCounter%3==0)
//        {
//            StartAppInterstitialAd.onBackPressed();
//        }
//
//    }

    public void showInterstitialAd()
    {
        StartAppInterstitialAd.showAd();

    }

    public void createLaunchNumberFile(Context passedContext)
    {
        String firstLaunchNumber = "1";
        String fileName = "launchCountFile";

        File launchCount = new File(passedContext.getFilesDir(), fileName);
        FileOutputStream outputStream;

        try
        {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(firstLaunchNumber.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Integer loadLaunchNumberFile() throws IOException
    {
            FileInputStream fInputStream = this.openFileInput("launchCountFile");

            int c;
            String temp="";

            while( (c = fInputStream.read()) != -1)
            {
                temp = temp + Character.toString((char)c);
            }
            fInputStream.close();


            Integer launchValue = Integer.valueOf(temp);
            launchValue++;
            launchCount=launchValue;

            FileOutputStream fOutputStream = openFileOutput("launchCountFile", 0);
            fOutputStream.write(String.valueOf(launchValue).getBytes());
            fOutputStream.close();

        return  launchValue;
    }

    public void launchAdCheck (Bundle passedState)
    {
        if (launchCount==0 || launchCount%3!=0)
        {
            StartAppAd.disableSplash();
        }
        else
        {
            StartAppInterstitialAd.showSplash(this, passedState, new SplashConfig()
                    .setTheme(SplashConfig.Theme.ASHEN_SKY)
                    .setAppName("Student Loan Wizard")
                    .setLogo(R.drawable.moneysmall1)
                    .setOrientation(SplashConfig.Orientation.PORTRAIT)
            );
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StartAppSDK.init(this, "000000000", true);
//        real ad id code 202621518

//        try {
//            loadLaunchNumberFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//            createLaunchNumberFile(getApplicationContext());
//        }
//
//        launchAdCheck(savedInstanceState);

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //populate initial selection fragment into main content layout
        mainFT.replace(R.id.mainFrameLayout, new Fragment_Selection());
        mainFT.commit();
    }

}
