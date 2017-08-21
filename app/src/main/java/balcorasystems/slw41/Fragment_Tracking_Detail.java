package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import org.w3c.dom.Text;


public class Fragment_Tracking_Detail extends Fragment
{


    public Integer coordinate=0;
    public Float paymentValue=0.0f;
    public String repaymentPlan = "Standard Repayment";

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.tracking_month_detail, selectionContainer, false);



        CheckBox payment = (CheckBox) rootLayoutView.findViewById(R.id.checkBox);
        payment.setText("Made payment of $" + paymentValue + " toward " + repaymentPlan + " Plan.");

        TextView number = (TextView) rootLayoutView.findViewById(R.id.number);
        number.setText(String.valueOf(coordinate));




        return rootLayoutView;
    }
}
