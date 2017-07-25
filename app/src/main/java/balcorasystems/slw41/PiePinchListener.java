package balcorasystems.slw41;

import android.content.Context;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.SimpleOnItemTouchListener;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

/**
 * Created by L on 7/20/2017.
 */

public class PiePinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
{
    Context viewContext;
    float x;
    float y;

    PiePinchListener (Context passedContext)
    {
        viewContext=passedContext;
    }



    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d("TAG", "PINCH! OUCH!");

        Toast.makeText(viewContext, String.valueOf("Pinch "+detector.getScaleFactor()) , Toast.LENGTH_SHORT).show();
        return false;
    }


}
