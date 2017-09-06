package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Fragment_Selection.OnNavigateAwayListener, Fragment_Questions.OnGoToMoneyStuff, Plans_Adapter.OnNavigateToDetail, Fragment_Questions.updateMainLoan, Fragment_PlanDetail.OnGoToDetailPlan, Fragment_Summary.returnToMain, Questions_Adapter.NextAfter, Fragment_MasterQuestionSpawner.goToPlans, Review_Adapter.OnGoToChangeValue, TaxStatus_Adapter.NextAfter, Fragment_Dependants.NextAfter

{
    public static Object_Borrower masterLoan = new Object_Borrower();

    public static Double simpleIncome = 27000.0;
    public static Double simpleDebt = 35000.0;
    public static Double currentStdPayment = 0.7;
    public static Integer simpleRepaymentSelection=0;
    public static boolean noPaymentDataYet =true;
    public static ArrayList<Double> payments = new ArrayList<>();
    public static ArrayList<ArrayList> masterUberPayments;
    public static ArrayList<Double> simpleForgiveness = new ArrayList<>();

    public static Integer detailID=0;

    public Integer backCounter =0;
    public Integer launchCount=0;

    public void NextAfterSelection()
    {
        Fragment_MasterQuestionSpawner reloadedFragment = new Fragment_MasterQuestionSpawner();
        reloadedFragment.index++;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, reloadedFragment, "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();

    }

    public void toReviewQuestion ()
    {
        Fragment_MasterQuestionSpawner reloadedFragment = new Fragment_MasterQuestionSpawner();
        reloadedFragment.index=Review_Adapter.choice;
        reloadedFragment.first=true;
        reloadedFragment.reviewLoad=true;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, reloadedFragment, "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

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
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_PlanDetail());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToChoice(Integer selection)
    {
        if (selection==0)
        {
            Fragment_MasterQuestionSpawner loadedFragment = new Fragment_MasterQuestionSpawner();
            //reseting the index to 0 so it doesn't load out of bounds if launched a second time, just in case it wasn't properly done on leaving it
            loadedFragment.index=0;
            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            fTransaction.replace(R.id.mainFrameLayout, new Fragment_MasterQuestionSpawner());
            fTransaction.addToBackStack(null);
            fTransaction.commit();
        }
        else if (selection==1)
        {
            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            fTransaction.replace(R.id.mainFrameLayout, new Fragment_Tracking(), "questions");
            fTransaction.addToBackStack(null);
            fTransaction.commit();
        }

    }

    public void finishedQuestions()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Plans_RecyclerFragment(), "info");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void loadSelection()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Selection(), "info");
//        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }














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

        //forgiveness hack for now
        simpleForgiveness.add(0.0);

        //populate initial selection fragment into main content layout
        loadSelection();
    }

    @Override
    public void onBackPressed()
    {
        //hack so forgiveness can be used more then once without having to restart app
        simpleForgiveness.clear();
        //need to add in another 0 here so array is right size, man this is a lot of dirty hacks to get this feature working crudely...
        simpleForgiveness.add(0.0);
        super.onBackPressed();
    }



}
