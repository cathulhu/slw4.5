package balcorasystems.slw41;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class Fragment_Questions extends Fragment
{

    public interface OnGoToMoneyStuff
    {
        public void finishedQuestions();
    }

    public OnGoToMoneyStuff mListener;

    Integer counter = 0;


    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.questions, selectionContainer, false);

        //!!!!
        //MUST HAVE THIS FOR LISTENER TO WORK!!!!
        mListener = (OnGoToMoneyStuff) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;
        //!!!!
        //!!!!

        final List<String> firstQuestions = new ArrayList<String>();
        firstQuestions.add("Are any of your student loans in a state of default? Typically loans go into default after a period of non payment (delinquency) of about 9 months, or 270 days.");
        firstQuestions.add("Are any of your student loans in a state of delinquency? Delinquency is defined as having missed a payment due date and still having an outstanding owed balance on the missed payment.");
        firstQuestions.add("Are you a parent borrower, next of kin, or other entity who thinks they may be financially responsible for the student loans of an individual who has died?");
        firstQuestions.add("Have you consolidated your loans into a Direct Consolidation loan?");
        firstQuestions.add("Have you undergone loan rehabilitation on these student loans for a past default?");
        firstQuestions.add("Are you a parent borrower with a Parent PLUS Loan?");
        firstQuestions.add("Does your student debt include Federal Family Education Loans (FFEL)? ");
        firstQuestions.add("Does your student debt include Perkins Loans? ");
        firstQuestions.add("Does your student debt include Federal Direct Loans? ");

//        firstQuestions.add("At the time of repayment what will your tax filing status be?");
        firstQuestions.add("Select the industry and position you work in.");
        firstQuestions.add("Select one of the following ranges for the date you received your first student loan.");
        firstQuestions.add("Select your income and debt at the time of repayment or current values if you are already in repayment. Optionally you can enter your current or expected monthly Repayment otherwise it will be automatically calculated.");

//        final List<String> taxOptions = new ArrayList<>();
//        taxOptions.add("Single filing");
//        taxOptions.add("Joint filing");
//        taxOptions.add("Married but filing separately");

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


        mainListView = (ListView) rootLayoutView.findViewById(R.id.questionsList);
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
        mainListView.setAdapter( listAdapter );

        final RelativeLayout layoutForFragment = (RelativeLayout) rootLayoutView.findViewById(R.id.relativeLayout3);
        layoutForFragment.setVisibility(View.GONE);




        final TextView questionTitle = (TextView) rootLayoutView.findViewById(R.id.questionsTitle);
        final TextView currentQuestion = (TextView) rootLayoutView.findViewById(R.id.questionsHere);

        final Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);

        currentQuestion.setText(firstQuestions.get(counter));

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (counter <= summaryTitles.size()) {
                    counter++;

                    mainListView.setVisibility(View.VISIBLE);
                    layoutForFragment.setVisibility(View.GONE);

                    if (counter < 12) {
                        currentQuestion.setText(firstQuestions.get(counter));
                        questionTitle.setText(summaryTitles.get(counter));
                    }
                    else
                    {
                        mListener.finishedQuestions();
                    }

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
                    else
                    {
                        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                        mainListView.setAdapter( listAdapter );
                    }


                }
            }

        });


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (counter > 0) {
                    counter--;
                    currentQuestion.setText(firstQuestions.get(counter));
                    questionTitle.setText(summaryTitles.get(counter));

                    mainListView.setVisibility(View.VISIBLE);
                    layoutForFragment.setVisibility(View.GONE);
                }

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
                else
                {
                    listAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, yesNoOptions);
                    mainListView.setAdapter( listAdapter );
                }

            }
//
        });




        return rootLayoutView;
    }

}
