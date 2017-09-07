package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TaxStatus_Adapter extends RecyclerView.Adapter<TaxStatus_Adapter.ViewHolder>
{
    Integer uberIndex=Fragment_MasterQuestionSpawner.index;
    static ArrayList<String> TaxOptions = new ArrayList<>();

    public static interface NextAfter
    {
        public void NextAfterSelection();
    }

    public NextAfter mListener;


    @Override
    public TaxStatus_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_text_row, parent, false);
        final TaxStatus_Adapter.ViewHolder genericViewholder = new ViewHolder(view);

        mListener = (NextAfter) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this part saves data to the working loan object
                Integer choice = genericViewholder.getAdapterPosition();
                String choiceVerbose = TaxOptions.get(choice);
                Boolean choiceBool=false;
                if (choice==0)
                {
                    choiceBool=true;
                }

                String currentTopic = Fragment_MasterQuestionSpawner.Borrower.currentlyEditing;

                if (currentTopic.equals("Tax Status"))
                {
                    Fragment_MasterQuestionSpawner.Borrower.taxStatus=choiceVerbose;
                }

                //this part navigates away
                mListener.NextAfterSelection();


            }
        });


        return genericViewholder;
    }

    @Override
    public void onBindViewHolder(TaxStatus_Adapter.ViewHolder holder, int position)
    {
        //this is behaviour that happens every time an element is added to the recycler_plans view I think
        holder.option.setText(TaxOptions.get(position));




    }


    @Override
    public int getItemCount()
    {
        TaxOptions=Fragment_MasterQuestionSpawner.uberOptions.get(Fragment_MasterQuestionSpawner.uberOptions.size()-1);
        return TaxOptions.size();
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
