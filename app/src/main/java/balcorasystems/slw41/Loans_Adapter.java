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

import java.util.ArrayList;


public class Loans_Adapter extends RecyclerView.Adapter<Loans_Adapter.ViewHolder>
{
    //here put an arraylist of all the loan objects that get created, then I can return the size of this array for the itemcount size

    public static interface NextAfter
    {
        public void NextAfterSelection();
    }
    public NextAfter mListener;



    @Override
    public Loans_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_recycler_row, parent, false);
        final Loans_Adapter.ViewHolder genericViewholder = new ViewHolder(view);

        mListener = (NextAfter) view.getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        EditText balance = (EditText) view.findViewById(R.id.editBalance);
        EditText interest = (EditText) view.findViewById(R.id.editInterest);
        Spinner loanTypesSpinner = (Spinner) view.findViewById(R.id.spinner);


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
                //do an input check then write the changes
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
                //do an input check then write the changes
            }
        });

        loanTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
        return 2;
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
