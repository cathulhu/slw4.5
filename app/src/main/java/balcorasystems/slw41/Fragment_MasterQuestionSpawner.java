package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Fragment_MasterQuestionSpawner extends Fragment
{
    public static Object_Borrower Borrower = new Object_Borrower();
    public static ArrayList<ArrayList> uberOptions = new ArrayList<>();
    public static Integer index=0;
    public static Boolean first = true;
    public static Boolean reviewLoad = false;

    final static ArrayList<String> mainQuestions = new ArrayList<String>();
    final static ArrayList<String> employmentOptions = new ArrayList<>();
    final static ArrayList<String> dateQuestionOptions = new ArrayList<String>();
    final static ArrayList<String> taxOptions = new ArrayList<>();
    final static ArrayList<String> yesNoOptions = new ArrayList<>();
    final static ArrayList<String> summaryTitles = new ArrayList<>();
    final static ArrayList<String> servicerTitles = new ArrayList<>();
    final static ArrayList<String> repaymentStatusQuestions = new ArrayList<>();
    final static ArrayList<String> maritalStuatss = new ArrayList<>();

//    public interface reload
//    {
//        public void NextAfterSelection();
//    }
//    public reload reloadListener;


    public interface goToNext
    {
        void toReport();
    }

    public goToNext leaveListener;
    // public NextAfterSelection mListener;

    public void moveOther(String backOrForward)
    {

        if (first==false || reviewLoad==false)
        {
            if (backOrForward.equals("forward"))
            {
                index++;
            }
            else if (backOrForward.equals("backward"))
            {
                index--;
            }
            else
            {
                //otherwise I can specify other argument if I don't want to increment index for some other reason then review or first load
            }

        }
        else
        {
            if (first==true)
            {
                //turning off reviewload after I don't need it.
                reviewLoad=false;
            }

            if (reviewLoad==true)
            {
                //turning off reviewload after I don't need it.
                reviewLoad=false;
            }
        }

        // have to put the check for if we've reached the end of the questions first, otherwise it'll ask to check out of bounds with the summary titles
        if (index==summaryTitles.size())
        {
            index=0;
            //for safety setting index=0 so nothing bad can happen out of bounds, especially if the fragment is reloaded to run a new set of calculations, so it starts from begining.
            leaveListener.toReport();
            //go to analysis
            // index=0;
        }
        else if (summaryTitles.get(index).equals("Repayment Status"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
        }
        else if (summaryTitles.get(index).equals("Deceased Borrower"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Loan Rehabilitation"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("First Loan Date"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Repayment Date"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Calendar());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Debt Servicer"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Employment"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Marital Status"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Questions_Text());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Tax Status"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_TaxStatus_Text());
            fTransaction.commit();
        }
        else if (summaryTitles.get(index).equals("Tax Dependants"))
        {
            //load different fragments, not the recycler view
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Dependants());
            fTransaction.commit();
        }
        else if (summaryTitles.get(index).equals("Income"))
        {
            //load different fragments, not the recycler view
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Income());
            fTransaction.commit();
        }
        else if (summaryTitles.get(index).equals("Loan Details"))
        {
            Borrower.currentlyEditing=summaryTitles.get(index);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Fragment_Loans());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }
        else if (summaryTitles.get(index).equals("Review"))
        {
            //sets the master copy of the borrower object in the main activity = to the changes made before leaving
            MainActivity.masterBorrower=Borrower;

            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragmentSection, new Review_Fragment());
            fTransaction.commit();
            //load different fragments, not the recycler view
        }


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.master_questions, selectionContainer, false);

        leaveListener  = (goToNext) getContext();
//        reloadListener = (reload) getActivity();

        final TextView questionTitle = (TextView) rootLayoutView.findViewById(R.id.planTimelineTItle);
        final TextView currentQuestion = (TextView) rootLayoutView.findViewById(R.id.questionsHere);
        final Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        final Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);

        if (first==true)
        {
            populateArrays();
        }

        //question and title are populated here either when first run or when fragment is restarted with a deliberate index
        questionTitle.setText(summaryTitles.get(index));
        currentQuestion.setText(mainQuestions.get(index));

        //if the fragment was launched from the review screen sending nuetral won't incriment index, it'll just load the corresponding content
        //alternatively if the fragment is running for the first time we also want to load content without incrimenting index
        if (reviewLoad==true || first==true)
        {
            moveOther("nuetral");
        }



        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                moveOther("forward");
                //updating the title and question after the content fragment changed

                //must make sure nothing happens with out of bounds if index is too high.
                if (index < summaryTitles.size() && index >= 0)
                {
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
                //must make sure nothing happens with out of bounds if index is too high.
                if (index < summaryTitles.size() && index > 0)
                {
                    moveOther("backward");

                    questionTitle.setText(summaryTitles.get(index));
                    currentQuestion.setText(mainQuestions.get(index));

                }

            }
        });

        return rootLayoutView;
    }









    public void populateArrays()
    {

        if (uberOptions.size() == 0)
        {
            mainQuestions.add("Which stage of loan repayment are you in currently? Default occurs after a period of non payment (delinquency) of 9 months, or 270 days.");
            mainQuestions.add("Are you a parent borrower, next of kin, or other entity who thinks they may be financially responsible for the student loans of an individual who has died?");
            mainQuestions.add("Have you undergone loan rehabilitation on these student loans for a past default?");
            mainQuestions.add("Select one of the following ranges for the date you received your first student loan.");
            mainQuestions.add("When is your next payment due? Or When do you estimate entering repayment if you aren't currently in repayment.");
            mainQuestions.add("Select your debt servicer from the list below.");
            mainQuestions.add("Select the industry and position you work in.");
            mainQuestions.add("Are you currently married or will you be married before your next repayment due date?");
            mainQuestions.add("At the time of repayment what will your tax filing status be?");
            mainQuestions.add("At the time of repayment how many dependencies will you be claiming on your tax filing?");
            mainQuestions.add("Select your gross income (income before taxes) at the time of repayment or current values if you are already in repayment and the income of your spouse if applicable.");
            mainQuestions.add("How much of the total debt is comprised of each different loan type? Tap on s slice of the pie chart and use the slider to adjust the fraction that fraction.");
            mainQuestions.add("Review your answers to the questionnaire below, click on any item to go back and change your answer. When you're ready to proceed tap Analyze Loans.");

            summaryTitles.add("Repayment Status");
            summaryTitles.add("Deceased Borrower");
            summaryTitles.add("Loan Rehabilitation");
            summaryTitles.add("First Loan Date");
            summaryTitles.add("Repayment Date");
            summaryTitles.add("Debt Servicer");
            summaryTitles.add("Employment");
            summaryTitles.add("Marital Status");
            summaryTitles.add("Tax Status");
            summaryTitles.add("Tax Dependants");
            summaryTitles.add("Income");
            summaryTitles.add("Loan Details");
            summaryTitles.add("Review");

            taxOptions.add("Single filing");
            taxOptions.add("Head of Household");
            taxOptions.add("Married filing jointly");
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

            repaymentStatusQuestions.add("In Repayment");
            repaymentStatusQuestions.add("Not in Repayment (still in school)");
            repaymentStatusQuestions.add("In grace period");
            repaymentStatusQuestions.add("In Deferment");
            repaymentStatusQuestions.add("In Forbearance");
            repaymentStatusQuestions.add("In Delinquency");
            repaymentStatusQuestions.add("In Default");

            maritalStuatss.add("Married");
            maritalStuatss.add("Single");

            uberOptions.add(repaymentStatusQuestions);

            for (int i = 0; i < 2; i++)
            {
                uberOptions.add(yesNoOptions);
            }

            uberOptions.add(dateQuestionOptions);
            uberOptions.add(yesNoOptions);  //just putting this here as a blank since text isn't used in the one between these two for now
            uberOptions.add(servicerTitles);
            uberOptions.add(employmentOptions);
            uberOptions.add(maritalStuatss);
            uberOptions.add(taxOptions);
        }


    }
}
