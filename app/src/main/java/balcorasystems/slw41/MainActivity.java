package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Fragment_Selection.OnNavigateAwayListener, Fragment_Questions.OnGoToMoneyStuff, Fragment_Info.testListener, Plans_Adapter.OnNavigateToDetail, Fragment_Questions.updateMainLoan, Fragment_PlanDetail.OnGoToDetailPlan

{
    public static Object_Loan masterLoan = new Object_Loan();

    public static Double simpleIncome = 27000.0;
    public static Double simpleDebt = 35000.0;
    public static Double currentStdPayment = 0.7;
    public static ArrayList<Double> payments = new ArrayList<>();

    public Integer backCounter =0;
    public Integer launchCount=0;

    public void updateLoan()
    {
        masterLoan.debt=simpleDebt;
        masterLoan.income=simpleIncome;
        masterLoan.currentPayment=currentStdPayment;
        masterLoan.payments=payments;
    }

    public void toDetailPlan()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Summary(), "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void recyclerToDetail()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_PlanDetail(), "details=");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToChoice()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Questions(), "questions");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void finishedQuestions()
    {
        //need to change what this does since the income stuff has been integrated into the questions wizard, this should now go to a recycler_summary.

        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Plans_RecyclerFragment(), "info");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void onIncomeFinished(int income, int debt)
    {
//        simpleIncome = income * 1000;
//        simpleDebt = debt * 1000;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
//        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Results());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToSummary(Double stdPayment, Double newPayment, String plan)
    {
//        currentStdPayment = stdPayment;
//        newMonthlyPayment = newPayment;
//        planRecommendation = plan;
//        projectedMonthlySavings = stdPayment - newPayment;
//        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
//        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Summary());
//        fTransaction.addToBackStack(null);
//        fTransaction.commit();
    }


    FragmentTransaction mainFT = getSupportFragmentManager().beginTransaction();














    //below is all ad related code

//    public StartAppAd StartAppInterstitialAd = new StartAppAd(this);
//
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
//
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

//    back button ads seem kind of obnoxious, better to just put them when static place when restarting main activity.
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

//    public void showInterstitialAd()
//    {
//        StartAppInterstitialAd.showAd();
//
//    }

//    public void createLaunchNumberFile(Context passedContext)
//    {
//        String firstLaunchNumber = "1";
//        String fileName = "launchCountFile";
//
//        File launchCount = new File(passedContext.getFilesDir(), fileName);
//        FileOutputStream outputStream;
//
//        try
//        {
//            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//            outputStream.write(firstLaunchNumber.getBytes());
//            outputStream.close();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

//    public Integer loadLaunchNumberFile() throws IOException
//    {
//            FileInputStream fInputStream = this.openFileInput("launchCountFile");
//
//            int c;
//            String temp="";
//
//            while( (c = fInputStream.read()) != -1)
//            {
//                temp = temp + Character.toString((char)c);
//            }
//            fInputStream.close();
//
//
//            Integer launchValue = Integer.valueOf(temp);
//            launchValue++;
//            launchCount=launchValue;
//
//            FileOutputStream fOutputStream = openFileOutput("launchCountFile", 0);
//            fOutputStream.write(String.valueOf(launchValue).getBytes());
//            fOutputStream.close();
//
//        return  launchValue;
//    }

//    public void launchAdCheck (Bundle passedState)
//    {
//        if (launchCount==0 || launchCount%3!=0)
//        {
//            StartAppAd.disableSplash();
//        }
//        else
//        {
//            StartAppInterstitialAd.showSplash(this, passedState, new SplashConfig()
//                    .setTheme(SplashConfig.Theme.ASHEN_SKY)
//                    .setAppName("Student Loan Wizard")
//                    .setLogo(R.drawable.moneysmall1)
//                    .setOrientation(SplashConfig.Orientation.PORTRAIT)
//            );
//        }
//    }

// above is all adcode











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
