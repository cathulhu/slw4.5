package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class Fragment_MasterQuestionSpawner extends Fragment
{
    public static Object_Loan masterLoan = new Object_Loan();
    public static ArrayList<ArrayList> uberOptions = new ArrayList<>();
    public static Integer index=0;
    public static Boolean first = true;

    final static ArrayList<String> mainQuestions = new ArrayList<String>();
    final static ArrayList<String> employmentOptions = new ArrayList<>();
    final static ArrayList<String> dateQuestionOptions = new ArrayList<String>();
    final static ArrayList<String> taxOptions = new ArrayList<>();
    final static ArrayList<String> yesNoOptions = new ArrayList<>();
    final static ArrayList<String> summaryTitles = new ArrayList<>();
    final static ArrayList<String> servicerTitles = new ArrayList<>();

    public static interface NextAfterSelection
    {
        public void nextQuestionRecycler(Boolean sentBoolean);
    }

    public interface OnGoToMoneyStuff
    {
        public void finishedQuestions();
    }

    public OnGoToMoneyStuff leaveListener;
    public NextAfterSelection mListener;

    public void nextRecycler(Boolean next)
    {
        if (first==false)
        {
            if (next==true)
            {
                index++;
            }
            else
            {
                index--;
            }
        }
        else
        {
            first=false;
        }



        masterLoan.currentlyEditing=summaryTitles.get(index);
        Fragment_Questions_Text chosenFragment= new Fragment_Questions_Text();
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        fTransaction.replace(R.id.fragmentSection, chosenFragment);
        fTransaction.commit();
    }

    public void nextOther()
    {
        index++;

        if (index==summaryTitles.size())
        {
            leaveListener.finishedQuestions();
            //go to analysis
            //load different fragments, not the recycler view
            index=0;
        }

         if (summaryTitles.get(index).equals("Loan Details"))
            {
                FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                fTransaction.replace(R.id.fragmentSection, new Fragment_DebtRatio());
                fTransaction.commit();
                //load different fragments, not the recycler view
            }
            else if (summaryTitles.get(index).equals("Review"))
            {
                FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                fTransaction.replace(R.id.fragmentSection, new Review_Fragment());
                fTransaction.commit();
                //load different fragments, not the recycler view
            }


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.master_questions, selectionContainer, false);

        leaveListener  = (OnGoToMoneyStuff) getContext();

        final TextView questionTitle = (TextView) rootLayoutView.findViewById(R.id.planTimelineTItle);
        final TextView currentQuestion = (TextView) rootLayoutView.findViewById(R.id.questionsHere);
        final Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        final Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);


        if (first==true)
        {
            populateArrays();
        }

        questionTitle.setText(summaryTitles.get(index));
        currentQuestion.setText(mainQuestions.get(index));


        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (index >= uberOptions.size() )
                {
                    nextOther();
                    questionTitle.setText(summaryTitles.get(index));
                    currentQuestion.setText(mainQuestions.get(index));
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (index > 0)
                {
                    nextRecycler(FALSE);

                    if (index <= (summaryTitles.size()))
                    {
                        //updating question and title text
                        questionTitle.setText(summaryTitles.get(index));
                        currentQuestion.setText(mainQuestions.get(index));
                    }

                }
            }
        });


        if (index < uberOptions.size())
        {
            nextRecycler(TRUE);
            questionTitle.setText(summaryTitles.get(index));
            currentQuestion.setText(mainQuestions.get(index));

        }

        if (summaryTitles.get(index).equals("Debt, Income, and Payment"))
        {
            //load different fragments, not the recycler view

            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Info());
            fTransaction.commit();

        }


        return rootLayoutView;
    }








    public void populateArrays()
    {

        if (uberOptions.size() == 0)
        {
            mainQuestions.add("Are any of your student loans in a state of default? Typically loans go into default after a period of non payment (delinquency) of about 9 months, or 270 days.");
            mainQuestions.add("Are any of your student loans in a state of delinquency? Delinquency is defined as having missed a payment due date and still having an outstanding owed balance on the missed payment.");
            mainQuestions.add("Are you a parent borrower, next of kin, or other entity who thinks they may be financially responsible for the student loans of an individual who has died?");
            mainQuestions.add("Have you undergone loan rehabilitation on these student loans for a past default?");
            mainQuestions.add("Select one of the following ranges for the date you received your first student loan.");
            mainQuestions.add("Select your debt servicer from the list below.");
            mainQuestions.add("Select the industry and position you work in.");

            mainQuestions.add("Select your income and debt at the time of repayment or current values if you are already in repayment. Optionally you can enter your current or expected monthly Repayment otherwise it will be automatically calculated.");
            mainQuestions.add("At the time of repayment what will your tax filing status be?");
            mainQuestions.add("At the time of repayment how many dependencies will you be claiming on your tax filing?");
            mainQuestions.add("How much of the total debt is comprised of each different loan type? Tap on s slice of the pie chart and use the slider to adjust the fraction that fraction.");
            mainQuestions.add("Review your answers to the questionnaire below, click on any item to go back and change your answer. When you're ready to proceed tap Analyze Loans.");

            summaryTitles.add("Loan Default");
            summaryTitles.add("Loan Delinquency");
            summaryTitles.add("Deceased Borrower");
            summaryTitles.add("Loan Rehabilitation");
            summaryTitles.add("First Loan Date");
            summaryTitles.add("Debt Servicer");
            summaryTitles.add("Employment");

            summaryTitles.add("Debt, Income, and Payment");
            summaryTitles.add("Tax Status");
            summaryTitles.add("Tax Dependants");
            summaryTitles.add("Loan Details");
            summaryTitles.add("Review");

            taxOptions.add("Single filing");
            taxOptions.add("Joint filing");
            taxOptions.add("Married but filing separately");

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

            dateQuestionOptions.add("Prior to October 1st, 1998");
            dateQuestionOptions.add("Between October 1st, 1998 and October 1st, 2007");
            dateQuestionOptions.add("Between October 1st, 2007 and October 1st, 2011");
            dateQuestionOptions.add("Between October 1st, 2011 and October 1st, 2014");
            dateQuestionOptions.add("After October 1st, 2014");

            yesNoOptions.add("Yes");
            yesNoOptions.add("No");

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

            for (int i = 0; i < 4; i++)
            {
                uberOptions.add(yesNoOptions);
            }

            uberOptions.add(dateQuestionOptions);
            uberOptions.add(servicerTitles);
            uberOptions.add(employmentOptions);
        }



    }
}
