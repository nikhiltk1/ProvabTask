package suventure.nikhil.com.profile.model;

import java.io.Serializable;

/**
 * Created by suventure on 25/7/17.
 */

public class Item implements Serializable{


    String user_id,user_name,first_name,last_name,description;
    String user_image;

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getDescription() {
        return description;
    }

    public String getUser_image() {
        return user_image;
    }
}
