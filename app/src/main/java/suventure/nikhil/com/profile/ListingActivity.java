package suventure.nikhil.com.profile;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import suventure.nikhil.com.profile.adapters.ItemAdapter;
import suventure.nikhil.com.profile.model.Item;

public class ListingActivity extends AppCompatActivity {


    Configuration configuration;
    RecyclerView recyclerList;
    ItemAdapter itemAdapter;
    ArrayList<Item> items;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        context=ListingActivity.this;
        recyclerList=(RecyclerView)findViewById(R.id.recycler_list);
        configuration=getResources().getConfiguration();


        if(CheckWifiAndMobileData.IsConnected(context)) {

            new AsyncTask<Void, Void, JSONArray>() {

                @Override
                protected JSONArray doInBackground(Void... voids) {

                    try {

                        String response = Downloader.downloadData_GET("http://provabdev.in/android_system_task/api2.php", "GET");

                        JSONArray responseJsonObject = new JSONArray(response);
                        Log.v("ListingActivity", "Response : " + responseJsonObject);
                        return responseJsonObject;


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(JSONArray data) {
                    super.onPostExecute(data);
                    Type listType = new TypeToken<List<Item>>() {
                    }.getType();

                    List<Item> items = new Gson().fromJson(data.toString(), listType);


                    itemAdapter = new ItemAdapter(ListingActivity.this, items);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerList.setLayoutManager(mLayoutManager);
                    recyclerList.setItemAnimator(new DefaultItemAnimator());
                    recyclerList.setAdapter(itemAdapter);


                }
            }.execute();
        }else {
            Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_SHORT).show();
        }




        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {

        }else {




        }
    }
    public void updateUI(Item item)
    {

        TextView txtUserName,txtFirstName,txtLastName,txtDesc;
        txtUserName=(TextView)findViewById(R.id.txt_user_name_land);
        txtFirstName=(TextView)findViewById(R.id.txt_first_name_land);
        txtLastName=(TextView)findViewById(R.id.txt_last_name_land);
        txtDesc=(TextView)findViewById(R.id.txt_desc_land);

        txtUserName.setText(item.getUser_name());
        txtFirstName.setText(item.getFirst_name());
        txtLastName.setText(item.getLast_name());
        txtDesc.setText(item.getDescription());
    }
}
