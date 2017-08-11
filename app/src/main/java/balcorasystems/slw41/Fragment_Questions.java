package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Questions extends Fragment
{

    public interface OnGoToMoneyStuff
    {
        public void finishedQuestions();
    }

    public OnGoToMoneyStuff mListener;

    public interface updateMainLoan
    {
        void updateLoan();
    }

    public updateMainLoan loanListener;

    Integer counter=0;


    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    public Object_Loan loan = new Object_Loan();

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.questions, selectionContainer, false);

        //!!!!
        //MUST HAVE THIS FOR LISTENER TO WORK!!!!
        mListener = (OnGoToMoneyStuff) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;
        loanListener = (updateMainLoan) getContext();
        //!!!!
        //!!!!

        final List<String> mainQuestions = new ArrayList<String>();
        mainQuestions.add("Are any of your student loans in a state of default? Typically loans go into default after a period of non payment (delinquency) of about 9 months, or 270 days.");
        mainQuestions.add("Are any of your student loans in a state of delinquency? Delinquency is defined as having missed a payment due date and still having an outstanding owed balance on the missed payment.");
        mainQuestions.add("Are you a parent borrower, next of kin, or other entity who thinks they may be financially responsible for the student loans of an individual who has died?");
        mainQuestions.add("Have you consolidated your loans into a Direct Consolidation loan?");
        mainQuestions.add("Have you undergone loan rehabilitation on these student loans for a past default?");
        mainQuestions.add("Are you a parent borrower with a Parent PLUS Loan?");
        mainQuestions.add("Does your student debt include Federal Family Education Loans (FFEL)? ");
        mainQuestions.add("Does your student debt include Perkins Loans? ");
        mainQuestions.add("Does your student debt include Federal Direct Loans? ");
        mainQuestions.add("Select the industry and position you work in.");
        mainQuestions.add("Select one of the following ranges for the date you received your first student loan.");
        mainQuestions.add("Select your income and debt at the time of repayment or current values if you are already in repayment. Optionally you can enter your current or expected monthly Repayment otherwise it will be automatically calculated.");
        mainQuestions.add("At the time of repayment what will your tax filing status be?");
        mainQuestions.add("At the time of repayment how many dependencies will you be claiming on your tax filing?");
        mainQuestions.add("How much of the total debt is comprised of each different loan type? Tap on s slice of the pie chart and use the slider to adjust the fraction that fraction.");
        mainQuestions.add("Select your debt servicer from the list below.");
        mainQuestions.add("Review your answers to the questionnaire below, click on any item to go back and change your answer. When you're ready to proceed tap Analyze Loans.");



        final List<String> taxOptions = new ArrayList<>();
        taxOptions.add("Single filing");
        taxOptions.add("Joint filing");
        taxOptions.add("Married but filing separately");

        final List<String> employmentOptions = new ArrayList<>();
        employmentOptions.add("Independent contractor/Self Employed");
        employmentOptions.add("Private non-profit company (501c3 IRS recognized)");
        employmentOptions.add("Federal, state, local, or tribal employment");
        employmentOptions.add("Health care in a clinical setting");
        employmentOptions.add("Emergency management / public safety");
        employmentOptions.add("Law enforcement or public interest law");
        employmentOptions.add("Care for the elderly or disabled");
        employmentOptions.add("Public education or early childhood education");
        employmentOptions.add("Public university or library services");
        employmentOptions.add("Not employed full time (below 30 hours per week)");
        employmentOptions.add("Private for profit company");

        final List<String> dateQuestionOptions = new ArrayList<String>();
        dateQuestionOptions.add("Prior to October 1st, 1998");
        dateQuestionOptions.add("Between October 1st, 1998 and October 1st, 2007");
        dateQuestionOptions.add("Between October 1st, 2007 and October 1st, 2011");
        dateQuestionOptions.add("Between October 1st, 2011 and October 1st, 2014");
        dateQuestionOptions.add("After October 1st, 2014");

        final List<String> yesNoOptions = new ArrayList<>();
        yesNoOptions.add("Yes");
        yesNoOptions.add("No");

        final List<String> summaryTitles = new ArrayList<>();
        summaryTitles.add("Loan Default");
        summaryTitles.add("Loan Delinquency");
        summaryTitles.add("Deceased Borrower");
        summaryTitles.add("Consolidated Loans");
        summaryTitles.add("Loan Rehabilitation");
        summaryTitles.add("Parent PLUS Loans");
        summaryTitles.add("FFEL Loans");
        summaryTitles.add("Perkins Loans");
        summaryTitles.add("Direct Loans");
        summaryTitles.add("Employment");
        summaryTitles.add("First Loan Date");
        summaryTitles.add("Debt, Income, and Payment");
        summaryTitles.add("Tax Status");
        summaryTitles.add("Tax Dependants");
        summaryTitles.add("Debt Types");
        summaryTitles.add("Debt Servicer");
        summaryTitles.add("Review");

        final List<String> servicerTitles = new ArrayList<>();
        servicerTitles.add("Nelnet");
        servicerTitles.add("Great Lakes Educational Loan Services, Inc.");
        servicerTitles.add("Navient");
        servicerTitles.add("FedLoan Servicing (PHEAA)");
        servicerTitles.add("MOHELA");
        servicerTitles.add("HESC/EdFinancial");
        servicerTitles.add("CornerStone");
        servicerTitles.add("Granite State - GSMR");
        servicerTitles.add("OSLA Servicing");
        servicerTitles.add("Debt Management and Collections System");


        mainListView = (ListView) rootLayoutView.findViewById(R.id.questionsList);
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
        mainListView.setAdapter( listAdapter );

        final NumberPicker taxDependants = (NumberPicker) rootLayoutView.findViewById(R.id.taxNumberPicker);
        taxDependants.setMaxValue(99);
        taxDependants.setMinValue(0);
        taxDependants.setValue(1);
        taxDependants.setVisibility(View.GONE);

        final TextView questionTitle = (TextView) rootLayoutView.findViewById(R.id.planTimelineTItle);
        final TextView currentQuestion = (TextView) rootLayoutView.findViewById(R.id.questionsHere);

        final Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        final Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);

        final RelativeLayout layoutForFragment = (RelativeLayout) rootLayoutView.findViewById(R.id.relativeLayout3);
        layoutForFragment.setVisibility(View.GONE);

        nextButton.setEnabled(false);
        taxDependants.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1)
            {
                loan.dependants=taxDependants.getValue();
                nextButton.setEnabled(true);



            }
        });

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (counter==mainQuestions.size()-1)
                {
                    //review section code

                    counter=i+1;
                    previousButton.performClick();
                }
                else if (counter==0)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.inDefault=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==1)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.inDelinquincy=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==2)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.deceased=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==3)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.hasConsolidated=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==4)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.loanRehab=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==5)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.parentLoans=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==6)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.ffelLoans=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==7)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.perkinsLoans=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==8)
                {

                    if (adapterView.getItemAtPosition(i)=="Yes")
                    {
                        loan.directLoans=true;
                    }
                    nextButton.setEnabled(true);

                }
                else if (counter==9)
                {
                    loan.employmentType=employmentOptions.get(i);
                    nextButton.setEnabled(true);
                }
                else if (counter==10)
                {
                    loan.employmentNumber=i;
                    nextButton.setEnabled(true);
                }
                else if (counter==11)
                {
                    loan.loanDateString=adapterView.getItemAtPosition(i).toString();
                    nextButton.setEnabled(true);
                }
                else if (counter==12)
                {

                    nextButton.setEnabled(true);
                }
                else if (counter==13)
                {

                }
                else if (counter==14)
                {

                    nextButton.setEnabled(true);
                }
                else if (counter==15)
                {
                    loan.servicer=adapterView.getItemAtPosition(i).toString();
                    nextButton.setEnabled(true);
                }




                if (counter < mainQuestions.size()-1)
                {
                    counter++;

                    nextButton.setEnabled(false);

                    mainListView.setVisibility(View.VISIBLE);
                    layoutForFragment.setVisibility(View.GONE);
                    taxDependants.setVisibility(View.GONE);

                    listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                    mainListView.setAdapter(listAdapter);

                    currentQuestion.setText(mainQuestions.get(counter));
                    questionTitle.setText(summaryTitles.get(counter));
                    nextButton.setText("Next");

                    if (counter == 9) {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, employmentOptions);
                        mainListView.setAdapter(listAdapter);

                    } else if (counter == 10) {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, dateQuestionOptions);
                        mainListView.setAdapter(listAdapter);
                    } else if (counter == 11) {
                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        //this is for the income debt and payment fragment text
                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Fragment_Info(), "questions");

                        fTransaction.addToBackStack(null);
                        fTransaction.commit();

                        nextButton.setEnabled(true);

                    } else if (counter == 12) {
                        //processing changes from last section, updating master loan debt income, etc.
                        loanListener.updateLoan();

                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, taxOptions);
                        mainListView.setAdapter(listAdapter);


                    } else if (counter == 13) {
                        //load the number picker here
                        mainListView.setVisibility(View.GONE);
                        taxDependants.setVisibility(View.VISIBLE);
                        loan.dependants = 1;
                        nextButton.setEnabled(true);


                    } else if (counter == 14) {
                        //load the interface for specifying loan fractions, pie chart or something
                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Fragment_DebtRatio(), "questions");
                        fTransaction.addToBackStack(null);
                        fTransaction.commit();

                        nextButton.setEnabled(true);
                    } else if (counter == 15) {
                        //saving data from debt ratio first


                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, servicerTitles);
                        mainListView.setAdapter(listAdapter);
                    } else if (counter == 16) {


                        nextButton.setText("Analyze Loans");
                        //load the review list here with actual values
//                        List<String> reviewTitles = new ArrayList<String>(summaryTitles);
//                        reviewTitles.remove(reviewTitles.size() - 1);
//
//                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, reviewTitles);
//                        mainListView.setAdapter(listAdapter);

                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        //sync the main loan with all the gathered data
                        MainActivity.masterLoan = loan;
                        loanListener.updateLoan();

                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Review_Fragment(), "questions");
                        fTransaction.addToBackStack(null);
                        fTransaction.commit();

                        nextButton.setEnabled(true);



                    } else {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                        mainListView.setAdapter(listAdapter);
                    }
                }



            }
        });







        currentQuestion.setText(mainQuestions.get(counter));

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (counter < mainQuestions.size()-1)
                {
                    counter++;

                    nextButton.setEnabled(false);

                    mainListView.setVisibility(View.VISIBLE);
                    layoutForFragment.setVisibility(View.GONE);
                    taxDependants.setVisibility(View.GONE);

                    listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                    mainListView.setAdapter(listAdapter);

                    currentQuestion.setText(mainQuestions.get(counter));
                    questionTitle.setText(summaryTitles.get(counter));
                    nextButton.setText("Next");

                    if (counter==9)
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, employmentOptions);
                        mainListView.setAdapter( listAdapter );

                    }
                    else if (counter==10)
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, dateQuestionOptions);
                        mainListView.setAdapter( listAdapter );
                    }
                    else if (counter==11)
                    {
                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        //this is for the income debt and payment fragment text
                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Fragment_Info(), "questions");

                        fTransaction.addToBackStack(null);
                        fTransaction.commit();

                        nextButton.setEnabled(true);

                    }
                    else if (counter==12)
                    {
                        //processing changes from last section, updating master loan debt income, etc.
                        loanListener.updateLoan();

                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, taxOptions);
                        mainListView.setAdapter(listAdapter);



                    }
                    else if (counter==13)
                    {
                        //load the number picker here
                        mainListView.setVisibility(View.GONE);
                        taxDependants.setVisibility(View.VISIBLE);
                        loan.dependants=1;
                        nextButton.setEnabled(true);


                    }
                    else if (counter==14)
                    {
                        //load the interface for specifying loan fractions, pie chart or something
                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Fragment_DebtRatio(), "questions");
                        fTransaction.addToBackStack(null);
                        fTransaction.commit();

                        nextButton.setEnabled(true);
                    }
                    else if (counter==15)
                    {
                        //saving data from debt ratio first



                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, servicerTitles);
                        mainListView.setAdapter( listAdapter );
                    }
                    else if (counter==16)
                    {


                        nextButton.setText("Analyze Loans");
                        //load the review list here with actual values
                        List<String> reviewTitles = new ArrayList<String>(summaryTitles);
                        reviewTitles.remove(reviewTitles.size()-1);

                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, reviewTitles);
                        mainListView.setAdapter( listAdapter );

                        nextButton.setEnabled(true);

                        //sync the main loan with all the gathered data
                        MainActivity.masterLoan=loan;
                        loanListener.updateLoan();
                    }
                    else
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                        mainListView.setAdapter( listAdapter );
                    }




                }
                else
                {
                    mListener.finishedQuestions();
                }
            }

        });


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (counter > 0)
                {
                    counter--;

                    mainListView.setVisibility(View.VISIBLE);
                    layoutForFragment.setVisibility(View.GONE);

                    listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                    mainListView.setAdapter(listAdapter);
                    taxDependants.setVisibility(View.GONE);

                    currentQuestion.setText(mainQuestions.get(counter));
                    questionTitle.setText(summaryTitles.get(counter));
                    nextButton.setText("Next");


                    if (counter==9)
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, employmentOptions);
                        mainListView.setAdapter( listAdapter );
                    }
                    else if (counter==10)
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, dateQuestionOptions);
                        mainListView.setAdapter( listAdapter );
                    }
                    else if (counter==11)
                    {
                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        //this is for the income debt and payment fragment text
                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Fragment_Info(), "questions");
                        fTransaction.addToBackStack(null);
                        fTransaction.commit();

                    }
                    else if (counter==12)
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, taxOptions);
                        mainListView.setAdapter(listAdapter);

                    }
                    else if (counter==13)
                    {
                        //load the number picker here
                        mainListView.setVisibility(View.GONE);
                        taxDependants.setVisibility(View.VISIBLE);
                    }
                    else if (counter==14)
                    {
                        //load the interface for specifying loan fractions, pie chart or something
                        mainListView.setVisibility(View.GONE);
                        layoutForFragment.setVisibility(View.VISIBLE);

                        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                        fTransaction.replace(R.id.relativeLayout3, new Fragment_DebtRatio(), "questions");
                        fTransaction.addToBackStack(null);
                        fTransaction.commit();
                    }
                    else if (counter==15)
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, servicerTitles);
                        mainListView.setAdapter( listAdapter );
                    }
                    else if (counter==16)
                    {
                        nextButton.setText("Analyze Loans");
                        //load the review list here with actual values
                        List<String> reviewTitles = new ArrayList<String>(summaryTitles);
                        reviewTitles.remove(reviewTitles.size()-1);

                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, reviewTitles);
                        mainListView.setAdapter( listAdapter );
                    }
                    else
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                        mainListView.setAdapter( listAdapter );
                    }



                }



            }
//
        });

        return rootLayoutView;
    }

}
