package suventure.nikhil.com.profile.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import suventure.nikhil.com.profile.DetailsActivity;
import suventure.nikhil.com.profile.DetailsFragment;
import suventure.nikhil.com.profile.ListingActivity;
import suventure.nikhil.com.profile.R;
import suventure.nikhil.com.profile.model.Item;

/**
 * Created by suventure on 25/7/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>   {

    private Activity context;
    private List<Item> items;
    private Configuration configuration;





    public ItemAdapter(Activity context, List<Item> items) {
        this.items = items;
        this.context = context;
        configuration=context.getResources().getConfiguration();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Item item=items.get(position);
        holder.textUserName.setText(item.getUser_name());
        holder.textFirstName.setText(item.getFirst_name());
        holder.textLastName.setText(item.getLast_name());


        Log.v("ItemAdapter","Image : "+item.getUser_image().replace("=>",":"));
        Picasso.with(context)
                .load(item.getUser_image().replace("=>",":"))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgItem);

        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE) {
            if(items.size()>=0) {
                ((ListingActivity) context).updateUI(items.get(0));
            }
        }
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("bundle", item);
            context.startActivity(intent);
        }else {


            ((ListingActivity) context).updateUI(item);

        }

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textUserName,textFirstName,textLastName;
        CircleImageView imgItem;
        RelativeLayout rlItem;
        public MyViewHolder(View view) {
            super(view);
            textUserName= (TextView)view.findViewById(R.id.txt_user_name);
            textFirstName= (TextView)view.findViewById(R.id.txt_first_name);
            textLastName=(TextView)view.findViewById(R.id.txt_last_name);
            imgItem=(CircleImageView)view.findViewById(R.id.item_image);
            rlItem=(RelativeLayout)view.findViewById(R.id.rl_item);

        }
    }




}
