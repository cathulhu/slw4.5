package balcorasystems.slw41;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
        dateQuestionOptions.add("My first student loans were issued prior to October 1st, 1998");
        dateQuestionOptions.add("My first student loans were issued between October 1st, 1998 and October 1st, 2007");
        dateQuestionOptions.add("My first student loans were issued between October 1st, 2007 and October 1st, 2011");
        dateQuestionOptions.add("My first student loans were issued between October 1st, 2011 and October 1st, 2014");
        dateQuestionOptions.add("My first student loans were issued after October 1st, 2014");




        final TextView currentQuestion = (TextView) rootLayoutView.findViewById(R.id.questionsHere);

        final Button nextButton = (Button) rootLayoutView.findViewById(R.id.nextButton);
        Button previousButton = (Button) rootLayoutView.findViewById(R.id.prevButton);

        currentQuestion.setText(firstQuestions.get(counter));

        final RadioGroup detailRadioGroup = (RadioGroup) rootLayoutView.findViewById(R.id.detailRadioGroup);

        final RadioButton yesButton = (RadioButton) rootLayoutView.findViewById(R.id.YesButton);
        final RadioButton noButton = (RadioButton) rootLayoutView.findViewById(R.id.noButton);
        final RadioButton skipButton = (RadioButton) rootLayoutView.findViewById(R.id.skipButton);

        final RadioButton det1 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton1);
        final RadioButton det2 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton2);
        final RadioButton det3 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton3);
        final RadioButton det4 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton4);
        final RadioButton det5 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton5);
        final RadioButton det6 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton6);
        final RadioButton det7 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton7);
        final RadioButton det8 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton8);
        final RadioButton det9 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton9);
        final RadioButton det10 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton10);
        final RadioButton det11 = (RadioButton) rootLayoutView.findViewById(R.id.detailQbutton11);


        det1.setVisibility(View.INVISIBLE);
        det2.setVisibility(View.INVISIBLE);
        det3.setVisibility(View.INVISIBLE);
        det4.setVisibility(View.INVISIBLE);
        det5.setVisibility(View.INVISIBLE);
        det6.setVisibility(View.INVISIBLE);
        det7.setVisibility(View.INVISIBLE);
        det8.setVisibility(View.INVISIBLE);
        det9.setVisibility(View.INVISIBLE);
        det10.setVisibility(View.INVISIBLE);
        det11.setVisibility(View.INVISIBLE);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (counter <= 10 )
                {
                    counter++;

                    if (counter < 11)
                    {
                        currentQuestion.setText(firstQuestions.get(counter));
                    }
                    else
                    {
                        mListener.finishedQuestions();
                    }


                }


                if (counter >= 9)
                {
                    yesButton.setVisibility(View.INVISIBLE);
                    noButton.setVisibility(View.INVISIBLE);
                    skipButton.setVisibility(View.INVISIBLE);

                    detailRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    yesButton.setVisibility(View.VISIBLE);
                    noButton.setVisibility(View.VISIBLE);
                    skipButton.setVisibility(View.VISIBLE);

                    detailRadioGroup.setVisibility(View.INVISIBLE);
                }




                if (counter==9)
                {
                    det1.setText(employmentOptions.get(0));
                    det2.setText(employmentOptions.get(1));
                    det3.setText(employmentOptions.get(2));
                    det4.setText(employmentOptions.get(3));
                    det5.setText(employmentOptions.get(4));
                    det6.setText(employmentOptions.get(5));
                    det7.setText(employmentOptions.get(6));
                    det8.setText(employmentOptions.get(7));
                    det9.setText(employmentOptions.get(8));
                    det10.setText(employmentOptions.get(9));
                    det11.setText(employmentOptions.get(10));

                    det1.setVisibility(View.VISIBLE);
                    det2.setVisibility(View.VISIBLE);
                    det3.setVisibility(View.VISIBLE);
                    det4.setVisibility(View.VISIBLE);
                    det5.setVisibility(View.VISIBLE);
                    det6.setVisibility(View.VISIBLE);
                    det7.setVisibility(View.VISIBLE);
                    det8.setVisibility(View.VISIBLE);
                    det9.setVisibility(View.VISIBLE);
                    det10.setVisibility(View.VISIBLE);
                    det11.setVisibility(View.VISIBLE);

                }
                else if (counter==10)
                {
                    det1.setText(dateQuestionOptions.get(0));
                    det2.setText(dateQuestionOptions.get(1));
                    det3.setText(dateQuestionOptions.get(2));
                    det4.setText(dateQuestionOptions.get(3));
                    det5.setText(dateQuestionOptions.get(4));

                    det1.setVisibility(View.VISIBLE);
                    det2.setVisibility(View.VISIBLE);
                    det3.setVisibility(View.VISIBLE);
                    det4.setVisibility(View.VISIBLE);
                    det5.setVisibility(View.VISIBLE);

                    det6.setVisibility(View.INVISIBLE);
                    det7.setVisibility(View.INVISIBLE);
                    det8.setVisibility(View.INVISIBLE);
                    det9.setVisibility(View.INVISIBLE);
                    det10.setVisibility(View.INVISIBLE);
                    det11.setVisibility(View.INVISIBLE);

                    nextButton.setText("Go To Next Section");
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
                    currentQuestion.setText(firstQuestions.get(counter));
                }

                if (counter == 10 || counter == 9)
                {
                    yesButton.setVisibility(View.INVISIBLE);
                    noButton.setVisibility(View.INVISIBLE);
                    skipButton.setVisibility(View.INVISIBLE);

                    detailRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    yesButton.setVisibility(View.VISIBLE);
                    noButton.setVisibility(View.VISIBLE);
                    skipButton.setVisibility(View.VISIBLE);

                    detailRadioGroup.setVisibility(View.INVISIBLE);
                }

                if (counter==9)
                {
                    detailRadioGroup.setVisibility(View.VISIBLE);
                    det1.setText(employmentOptions.get(0));
                    det2.setText(employmentOptions.get(1));
                    det3.setText(employmentOptions.get(2));
                    det4.setText(employmentOptions.get(3));
                    det5.setText(employmentOptions.get(4));
                    det6.setText(employmentOptions.get(5));
                    det7.setText(employmentOptions.get(6));
                    det8.setText(employmentOptions.get(7));
                    det9.setText(employmentOptions.get(8));
                    det10.setText(employmentOptions.get(9));
                    det11.setText(employmentOptions.get(10));

                    det1.setVisibility(View.VISIBLE);
                    det2.setVisibility(View.VISIBLE);
                    det3.setVisibility(View.VISIBLE);
                    det4.setVisibility(View.VISIBLE);
                    det5.setVisibility(View.VISIBLE);
                    det6.setVisibility(View.VISIBLE);
                    det7.setVisibility(View.VISIBLE);
                    det8.setVisibility(View.VISIBLE);
                    det9.setVisibility(View.VISIBLE);
                    det10.setVisibility(View.VISIBLE);
                    det11.setVisibility(View.VISIBLE);

                }
                else if (counter==10)
                {
                    detailRadioGroup.setVisibility(View.VISIBLE);
                    det1.setText(dateQuestionOptions.get(0));
                    det2.setText(dateQuestionOptions.get(1));
                    det3.setText(dateQuestionOptions.get(2));
                    det4.setText(dateQuestionOptions.get(3));
                    det5.setText(dateQuestionOptions.get(4));

                    det1.setVisibility(View.VISIBLE);
                    det2.setVisibility(View.VISIBLE);
                    det3.setVisibility(View.VISIBLE);
                    det4.setVisibility(View.VISIBLE);
                    det5.setVisibility(View.VISIBLE);

                    det6.setVisibility(View.INVISIBLE);
                    det7.setVisibility(View.INVISIBLE);
                    det8.setVisibility(View.INVISIBLE);
                    det9.setVisibility(View.INVISIBLE);
                    det10.setVisibility(View.INVISIBLE);
                    det11.setVisibility(View.INVISIBLE);
                }



            }
        });





        return rootLayoutView;
    }

}
