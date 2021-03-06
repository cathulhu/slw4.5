package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class MainActivity extends AppCompatActivity implements Fragment_Selection.OnNavigateAwayListener, Plans_Adapter.OnNavigateToDetail, Fragment_PlanDetail.OnGoToDetailPlan, Fragment_Summary.returnToMain, Questions_Adapter.NextAfter, Fragment_MasterQuestionSpawner.goToNext, Review_Adapter.OnGoToChangeValue, TaxStatus_Adapter.NextAfter, Fragment_Dependants.NextAfter, Fragment_Calendar.NextAfter, Report_RecyclerFragment.backToReview, Report_RecyclerFragment.goToPlans

{
    public static Object_Borrower masterBorrower = new Object_Borrower();


//    public static boolean noPaymentDataYet =true;
    public static Integer detailID=0;

    public Integer backCounter =0;
    public Integer launchCount=0;

    public void NextAfterSelection()
    {
        Fragment_MasterQuestionSpawner reloadedFragment = new Fragment_MasterQuestionSpawner();
        Fragment_MasterQuestionSpawner.index++;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, reloadedFragment, "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void NextAfterSelection(String reviewFlag)
    {
        Fragment_MasterQuestionSpawner reloadedFragment = new Fragment_MasterQuestionSpawner();
        if (reviewFlag.equals("review"))
        {
            //this should load just the review page
//            reloadedFragment.index=reloadedFragment.summaryTitles.size();
            //TODO: fix this so it reloads properly and while your at it put in a feature to so that review changes one thing then snaps back to review status
            reloadedFragment.reviewLoad=true;
        }
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, reloadedFragment, "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void toReport()
    {
        Report_RecyclerFragment reportFragment = new Report_RecyclerFragment();
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, reportFragment, "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void toReviewQuestion ()
    {
        Fragment_MasterQuestionSpawner reloadedFragment = new Fragment_MasterQuestionSpawner();
        Fragment_MasterQuestionSpawner.index =Review_Adapter.choice;
        Fragment_MasterQuestionSpawner.first =true;
        Fragment_MasterQuestionSpawner.reviewLoad =true;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, reloadedFragment, "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void toDetailPlan()
    {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, new Fragment_Summary(), "summary");
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void recyclerToDetail(Integer selection)
    {
        Fragment_PlanDetail loadedFragment = new  Fragment_PlanDetail();
        Fragment_PlanDetail.selection =selection;
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.mainFrameLayout, loadedFragment);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    public void navigateToChoice(Integer selection)
    {
        if (selection==0)
        {
            Fragment_MasterQuestionSpawner loadedFragment = new Fragment_MasterQuestionSpawner();
            //reseting the index to 0 so it doesn't load out of bounds if launched a second time, just in case it wasn't properly done on leaving it
            Fragment_MasterQuestionSpawner.index =0;
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

    public void gotoPlans()
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
    public void onCreate(Bundle savedInstanceState) {
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
        loadSelection();
    }
}
