package suventure.nikhil.com.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    String userName,firstName,lastName,desc;
    TextView txtUsername,txtFirstname,textLastName,txtDesc;


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.v("DetailsFragment","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("DetailsFragment","onCreateView");
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        return view;
    }


}
