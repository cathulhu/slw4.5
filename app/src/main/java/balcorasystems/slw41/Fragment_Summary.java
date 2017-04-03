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
import android.widget.ListView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class Fragment_Summary extends Fragment
{

    public static Double currentPayment = Main2Activity.currentStdPayment;
    public static Double projectedSavings = Main2Activity.projectedMonthlySavings;
    public static Double newMonthlyPayment = Main2Activity.newMonthlyPayment;
    public static String reccomendedPlan = Main2Activity.planRecommendation;
    public static Double idrTotal = Main2Activity.totalIdrSpent;
    public static Double stdTotal = Main2Activity.totalStdSpent;

    public interface goProButtonListener
    {
        public void goPro();
    }
    public goProButtonListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        View rootLayoutView = inflater.inflate(R.layout.simple_summary, selectionContainer, false);
//        mListener = (goProButtonListener) getContext();       //FOR SOME REASON ITS INCREDIBLY IMPORTANT TO SET THIS TO CONTEXT;

        TextView currentPaymentText = (TextView) rootLayoutView.findViewById(R.id.textView9);
        TextView savingsText = (TextView) rootLayoutView.findViewById(R.id.textView11);
        TextView newPaymentText = (TextView) rootLayoutView.findViewById(R.id.textView17);
        TextView reccomendationText = (TextView) rootLayoutView.findViewById(R.id.textView19);

        TextView standardSummaryTotal = (TextView) rootLayoutView.findViewById(R.id.standardSummaryTotal);
        TextView ibrSummaryTotal = (TextView) rootLayoutView.findViewById(R.id.ibrSummaryTotal);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);

        String formattedCurrentPayment = "$" + nf.format(currentPayment);
        String formattedSavings = "-$" + nf.format(projectedSavings);
        String formattedNewPayment = "$" + nf.format(newMonthlyPayment);

        standardSummaryTotal.setText("$" + nf.format(stdTotal));
        ibrSummaryTotal.setText("$" + nf.format(idrTotal));

        currentPaymentText.setText(formattedCurrentPayment);
        savingsText.setText(formattedSavings);
        newPaymentText.setText(formattedNewPayment);
        reccomendationText.setText(reccomendedPlan);

        List<String> loanServicers = new ArrayList<String>();
        loanServicers.add("Nelnet");
        loanServicers.add("Great Lakes Educational Loan Services");
        loanServicers.add("Navient");
        loanServicers.add("MOHELA");
        loanServicers.add("HESC/EdFinancial");
        loanServicers.add("CornerStore");
        loanServicers.add("Granite State - GSMR");
        loanServicers.add("OSLA Servicing");
        loanServicers.add("Debt Management and Collections System");

        final List<Long> loanServicersNumbers = new ArrayList<Long>();
        loanServicersNumbers.add(18884864722L);
        loanServicersNumbers.add(18002364300L);
        loanServicersNumbers.add(18007221300L);
        loanServicersNumbers.add(18006992908L);
        loanServicersNumbers.add(18888664352L);
        loanServicersNumbers.add(18553376884L);
        loanServicersNumbers.add(18006631662L);
        loanServicersNumbers.add(18885560022L);
        loanServicersNumbers.add(18662649762L);
        loanServicersNumbers.add(18006213115L);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, loanServicers);
        ListView loanServicersList = (ListView)rootLayoutView.findViewById(R.id.servicersListView);
        loanServicersList.setAdapter(adapter);

        AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + loanServicersNumbers.get(i)));
                startActivity(dialIntent);

            }
        };

        loanServicersList.setOnItemClickListener(clickHandler);

        return rootLayoutView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            TextView currentPaymentText = (TextView) getView().findViewById(R.id.textView9);
            TextView savingsText = (TextView) getView().findViewById(R.id.textView11);
            TextView newPaymentText = (TextView) getView().findViewById(R.id.textView17);
            TextView reccomendationText = (TextView) getView().findViewById(R.id.textView19);
            TextView standardSummaryTotal = (TextView) getView().findViewById(R.id.standardSummaryTotal);
            TextView ibrSummaryTotal = (TextView) getView().findViewById(R.id.ibrSummaryTotal);

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(0);



            String formattedCurrentPayment = "$" + nf.format(currentPayment);
            String formattedSavings = "-$" + nf.format(projectedSavings);
            String formattedNewPayment = "$" + nf.format(newMonthlyPayment);

            standardSummaryTotal.setText("$" + nf.format(stdTotal));
            ibrSummaryTotal.setText("$" + nf.format(idrTotal));

            currentPaymentText.setText(formattedCurrentPayment);
            savingsText.setText(formattedSavings);
            newPaymentText.setText(formattedNewPayment);
            reccomendationText.setText(reccomendedPlan);
        }

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
