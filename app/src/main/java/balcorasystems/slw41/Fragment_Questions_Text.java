package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Fragment_Questions_Text extends Fragment
{

    public View onCreateView(LayoutInflater inflater, ViewGroup selectionContainer, Bundle savedInstanceState) {
        final View rootLayoutView = inflater.inflate(R.layout.questions_text_layout, selectionContainer, false);

        final RecyclerView recyclerView = (RecyclerView) rootLayoutView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new Questions_YN_Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootLayoutView;
    }

}
