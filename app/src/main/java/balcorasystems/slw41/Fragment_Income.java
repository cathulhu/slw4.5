package balcorasystems.slw41;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class Fragment_Income extends Fragment
{

    public static Integer incomeRaw =0;
    public static Integer spouseIncomeRaw =0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.debt_income, selectionContainer, false);

        SeekBar spouseIncomeSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar2);
        SeekBar incomeSeeker = (SeekBar) rootLayoutView.findViewById(R.id.seekBar3);
        final TextView spouseIncomeText = (TextView) rootLayoutView.findViewById(R.id.textView29);
        final TextView spouseIncomeLabel = (TextView) rootLayoutView.findViewById(R.id.textView24);
        final TextView incomeText = (TextView) rootLayoutView.findViewById(R.id.textView37);

        if (!MainActivity.masterBorrower.isMarried)
        {
            spouseIncomeSeeker.setVisibility(View.GONE);
            spouseIncomeText.setVisibility(View.GONE);
            spouseIncomeLabel.setVisibility(View.GONE);
        }
        else
        {
            spouseIncomeSeeker.setVisibility(View.VISIBLE);
            spouseIncomeText.setVisibility(View.VISIBLE);
            spouseIncomeLabel.setVisibility(View.VISIBLE);
        }

        spouseIncomeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                spouseIncomeRaw=i*1000;
                spouseIncomeText.setText("$" + String.valueOf(i * 1000));
                Object_Borrower.startingSpouseIncome = Double.valueOf(spouseIncomeRaw);
                Object_Borrower.PopulateSpouseIncome();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        incomeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                incomeRaw=i*1000;
                incomeText.setText("$" + String.valueOf(i * 1000));
                Object_Borrower.startingPrimaryIncome = Double.valueOf(incomeRaw);
                Object_Borrower.PopulatePrimaryIncome();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMaximumFractionDigits(0);

        return rootLayoutView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
