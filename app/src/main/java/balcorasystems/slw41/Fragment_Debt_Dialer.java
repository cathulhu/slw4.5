package balcorasystems.slw41;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import org.w3c.dom.Text;

import me.angrybyte.circularslider.CircularSlider;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by L on 10/24/2017.
 */

public class Fragment_Debt_Dialer extends Fragment
{
    double debt=0;
    Integer last =0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View rootview = inflater.inflate(R.layout.debt_dial, container, false);

        SeekArc debtslider = (SeekArc) rootview.findViewById(R.id.debtArc);
        final TextView debtAmount = (TextView) rootview.findViewById(R.id.debtSum);
        debtslider.setStartAngle(0);

        debtslider.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b)
            {
                if (seekArc.getProgress() == last)
                {

                }
                else if (seekArc.getProgress() > last)
                {
                    if (seekArc.getProgress()==99)
                    {
                        seekArc.setClockwise(TRUE);
                    }
                    else
                    {
                        seekArc.setClockwise(FALSE);
                    }
                    debt += i;
                }
                else if (seekArc.getProgress() < last)
                {

                    if (seekArc.getProgress()==99)
                    {
                        seekArc.setClockwise(FALSE);
                    }
                    else
                    {
                        seekArc.setClockwise(TRUE);
                    }
                    debt += i;
                }

                debtAmount.setText(String.valueOf(debt));
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc)
            {
                last=seekArc.getProgress();
                debtAmount.setText(String.valueOf(last));
            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

        return rootview;
    }
}
