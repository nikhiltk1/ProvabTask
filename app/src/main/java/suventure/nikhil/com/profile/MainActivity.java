package suventure.nikhil.com.profile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import suventure.nikhil.com.profile.adapters.ItemAdapter;
import suventure.nikhil.com.profile.model.Item;

public class MainActivity extends AppCompatActivity {



    Button btnNext;
    Context context;
    EditText editUsername,editFirstName,editLastName;
    CircleImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        btnNext=(Button)findViewById(R.id.btn_next);
        editFirstName=(EditText)findViewById(R.id.edit_firstname);
        editUsername=(EditText) findViewById(R.id.edit_username);
        editLastName=(EditText)findViewById(R.id.edit_lastname);
        userImage=(CircleImageView)findViewById(R.id.profile_image);





        if(CheckWifiAndMobileData.IsConnected(context)) {
            new AsyncTask<Void, Void, JSONArray>() {

                @Override
                protected JSONArray doInBackground(Void... voids) {

                    try {

                        String response = Downloader.downloadData_GET("http://provabdev.in/android_system_task/api1.php", "GET");

                        JSONArray responseJsonObject = new JSONArray(response);
                        return responseJsonObject;


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(JSONArray data) {
                    super.onPostExecute(data);
                    Log.v("MainActivity", "Length : " + data.length());

                    if (data.length() >= 0) {
                        try {
                            JSONObject jsonObject = (JSONObject) data.get(0);
                            editUsername.setText(jsonObject.getString("user_name"));
                            editFirstName.setText(jsonObject.getString("first_name"));
                            editLastName.setText(jsonObject.getString("last_name"));
                            Picasso.with(context)
                                    .load(jsonObject.getString("user_image").replace("=>", ":"))
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher)
                                    .into(userImage);

                        } catch (JSONException e) {
                            Toast.makeText(context, "Server error occured", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }


                }
            }.execute();
        }else {
            Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_SHORT).show();
        }

        /*
        */
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ListingActivity.class));
            }
        });
    }
}
