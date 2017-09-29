package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Questions_Adapter extends RecyclerView.Adapter<Questions_Adapter.ViewHolder>
{
    Integer uberIndex=Fragment_MasterQuestionSpawner.index;
    static ArrayList<String> Options = new ArrayList<>();
    static Boolean fowardNavBoolean=true;


    public interface NextAfter
    {
        void NextAfterSelection();
    }

    public NextAfter mListener;

    @Override
    public Questions_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_text_row, parent, false);
        final Questions_Adapter.ViewHolder genericViewholder = new ViewHolder(view);

        mListener = (NextAfter) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    }
                    else if (choice==1)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween98to07=true;
                    }
                    else if (choice==2)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween07to11=true;
                    }
                    else if (choice==3)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeBetween11to14=true;
                    }
                    else if (choice==4)
                    {
                        Fragment_MasterQuestionSpawner.Borrower.timeAfter14=true;
                    }

                }
                else if (currentTopic.equals("Debt, Income, and Payment"))
                {
                    //will write data to target objects within this fragment
                }
                else if (currentTopic.equals("Tax Status"))
                {
                    //will write data to target objects within this fragment
                }
                else if (currentTopic.equals("Tax Dependants"))
                {
                    //will write data to target objects within this fragment
                }
                else if (currentTopic.equals("Debt Servicer"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.servicer=choiceVerbose;
                }

                //this part navigates away
                mListener.NextAfterSelection();


            }
        });


        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(Questions_Adapter.ViewHolder holder, int position)
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
