package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_Questions extends RecyclerView.Adapter<Adapter_Questions.ViewHolder>
{
    Integer uberIndex=Fragment_MasterQuestionSpawner.index;
    static ArrayList<String> Options = new ArrayList<>();
    static Boolean fowardNavBoolean=true;
    static Boolean snapbackReview=false;


    public interface NextAfter
    {
        void NextAfterSelection();
    }

    public interface NextAfterString
    {
        void NextAfterSelection(String passedString);
    }

    public NextAfter mListener;
    public NextAfterString mListenerString;

    @Override
    public Adapter_Questions.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_text_row, parent, false);
        final Adapter_Questions.ViewHolder genericViewholder = new ViewHolder(view);

        mListener = (NextAfter) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;
        mListenerString = (NextAfterString) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: update these with all the new choices so date is being saved by each of them
                //this part saves data to the working loan object
                Integer choice = genericViewholder.getAdapterPosition();
                String choiceVerbose = Options.get(choice);
                Boolean choiceBool=false;
                if (choice==0)
                {
                    choiceBool=true;
                }
                String currentTopic = Fragment_MasterQuestionSpawner.Borrower.currentlyEditing;


                if (currentTopic.equals("Loan Default"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.inDefault=choiceBool;
                }
                else if (currentTopic.equals("Loan Delinquency"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.inDelinquincy=choiceBool;
                }
                else if (currentTopic.equals("Deceased Borrower"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.deceased=choiceBool;
                }
                else if (currentTopic.equals("Loan Rehabilitation"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.loanRehab=choiceBool;
                }
                else if (currentTopic.equals("Employment"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.employmentType=choiceVerbose;
                }
                else if (currentTopic.equals("Repayment Status"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.repaymentStatus=choiceVerbose;
                }
                else if (currentTopic.equals("Marital Status"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.isMarried=choiceBool;
                }
                else if (currentTopic.equals("First Loan Date"))
                {

                    Fragment_MasterQuestionSpawner.Borrower.timeBefore98=false;
                    Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=false;
                    Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=false;
                    Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=false;
                    Fragment_MasterQuestionSpawner.Borrower.timeAfter14=false;

                    if (choice==0)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBefore98=true;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeAfter14=false;
                    }
                    else if (choice==1)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=true;
                        Fragment_MasterQuestionSpawner.Borrower.timeBefore98=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeAfter14=false;
                    }
                    else if (choice==2)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=true;
                        Fragment_MasterQuestionSpawner.Borrower.timeBefore98=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeAfter14=false;
                    }
                    else if (choice==3)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=true;
                        Fragment_MasterQuestionSpawner.Borrower.timeBefore98=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeAfter14=false;
                    }
                    else if (choice==4)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeAfter14=true;
                        Fragment_MasterQuestionSpawner.Borrower.timeBefore98=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=false;
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=false;
                    }

                }
                else if (currentTopic.equals("Income"))
                {
                    //will write data to target objects within this fragment
                }
                else if (currentTopic.equals("Tax Status"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.repaymentStatus=choiceVerbose;
                }
                else if (currentTopic.equals("Tax Dependants"))
                {
                    //will write data to target objects within this fragment
                }
                else if (currentTopic.equals("Debt Servicer"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.servicer=choiceVerbose;
                }

                if (Fragment_MasterQuestionSpawner.snapbackReview)
                {
                    mListenerString.NextAfterSelection("snapback");
                }
                else
                {
                    //this part navigates away
                    mListener.NextAfterSelection();
                }

            }
        });


        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Adapter_Questions.ViewHolder holder, int position)
    {
        //this is behaviour that happens every time an element is added to the recycler_plans view I think
        holder.option.setText(Options.get(position));




    }


    @Override
    public int getItemCount()
    {
        Options=Fragment_MasterQuestionSpawner.uberOptions.get(uberIndex);
        return Options.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView option;
        TextView extraDesc;

        public ViewHolder(View itemView)
        {
            super(itemView);

            option = (TextView) itemView.findViewById(R.id.optionText);
            extraDesc = (TextView) itemView.findViewById(R.id.extraText);

        }
    }
}
