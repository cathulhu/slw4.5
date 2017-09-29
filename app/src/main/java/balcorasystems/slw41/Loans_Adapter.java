package balcorasystems.slw41;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class Loans_Adapter extends RecyclerView.Adapter<Loans_Adapter.ViewHolder>
{
    //no need for a gotonext fragment thing since I don't actually do any navigating away or reloading from within the adapter, that's handled at the Fragment_Loans level.

    @Override
    public Loans_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_recycler_row, parent, false);
        final Loans_Adapter.ViewHolder genericViewholder = new ViewHolder(view);

//        mListener = (NextAfter) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        final EditText balance = (EditText) view.findViewById(R.id.editBalance);
        final EditText interest = (EditText) view.findViewById(R.id.editInterest);
        final Spinner loanTypesSpinner = (Spinner) view.findViewById(R.id.spinner);

        Button deleteLoan = (Button) view.findViewById(R.id.deleteButton);
        deleteLoan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Object_Debt.deleteLoan(genericViewholder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });


        balance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String rawInput = String.valueOf(balance.getText());
                Long inputBalance=0L;

                if (!rawInput.equals(""))
                {
                    inputBalance = Long.valueOf(rawInput);
                }
                //do an input check then write the changes
                Object_Debt.loanPortfolio.get(genericViewholder.getAdapterPosition()).currentBalance= inputBalance;
            }
        });


        interest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String rawInput = String.valueOf(interest.getText());
                Double inputInterest=0.0;

                if (!rawInput.equals(""))
                {
                    inputInterest = Double.valueOf(rawInput);
                }
                //do an input check then write the changes
                Object_Debt.loanPortfolio.get(genericViewholder.getAdapterPosition()).interestRate= inputInterest;
                //do an input check then write the changes
            }
        });

        loanTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String loanTypeSelection = adapterView.getItemAtPosition(i).toString();
                Object_Debt.loanPortfolio.get(genericViewholder.getAdapterPosition()).type = loanTypeSelection;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return genericViewholder;
    }


    @Override
    public void onBindViewHolder(Loans_Adapter.ViewHolder holder, int position)
    {
        //this is behaviour that happens every time an element is added to the recycler_plans view I think


    }


    @Override
    public int getItemCount()
    {

        return Object_Debt.loanPortfolio.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //declare ui elements here

        public ViewHolder(View itemView)
        {
            super(itemView);

            //set ui elements = to their xml here

        }
    }
}
