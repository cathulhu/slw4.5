package balcorasystems.slw41;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import static android.R.attr.fragment;

public class Main2Activity extends AppCompatActivity implements Fragment_Info.sendDebtToMain, Fragment_Info.sendIncomeToMain
{

    public static Integer simpleIncome = 27000;
    public static Integer simpleDebt = 35000;
    public static Double currentStdPayment = 0.0;
    public static Double projectedMonthlySavings = 0.0;
    public static Double newMonthlyPayment = 0.0;
    public static Double totalIdrSpent = 0.0;
    public static Double totalStdSpent = 0.0;
    public static String planRecommendation = "";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onRecieveDebtData(int debt)
    {
        simpleDebt = debt;
        Fragment_Results.debt=debt;
    }

    @Override
    public void onRecieveIncomeData(int income)
    {
        simpleIncome = income;
        Fragment_Results.income=income;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setOffscreenPageLimit(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main2, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }









    public static Fragment_Info newInfoInstance()
    {
        Fragment_Info infoFragment = new Fragment_Info();
        return infoFragment;
    }

    public static Fragment_Results newResultsInstance()
    {
        Fragment_Results resultsFragment = new Fragment_Results();
        return resultsFragment;
    }

    public static Fragment_Summary newSummaryInstance()
    {
        Fragment_Summary summaryFragment = new Fragment_Summary();
        return summaryFragment;
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object)
        {

            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return newInfoInstance();
                case 1:
                    return newResultsInstance();
                case 2:
                    return newSummaryInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}