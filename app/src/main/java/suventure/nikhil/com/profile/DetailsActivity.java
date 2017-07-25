package suventure.nikhil.com.profile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import suventure.nikhil.com.profile.model.Item;

public class DetailsActivity extends AppCompatActivity {

    Item item;
    Context context;
    TextView txtUserName,txtFirstName,txtLastName,txtDesc;
    CircleImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context=DetailsActivity.this;
        txtUserName=(TextView)findViewById(R.id.txt_user_name);
        txtFirstName=(TextView)findViewById(R.id.txt_first_name);
        txtLastName=(TextView)findViewById(R.id.txt_last_name);
        txtDesc=(TextView)findViewById(R.id.txt_desc);
        userImage=(CircleImageView)findViewById(R.id.user_image);
        item = (Item) getIntent().getSerializableExtra("bundle");

        txtUserName.setText(item.getUser_name());
        txtFirstName.setText(item.getFirst_name());
        txtLastName.setText(item.getLast_name());
        txtDesc.setText(item.getDescription());

        Picasso.with(context)
                .load(item.getUser_image().replace("=>",":"))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(userImage);





    }
}
