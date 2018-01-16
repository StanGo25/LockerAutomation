package ca.goldenphoenicks.lockerauto;

/**
 * Created by Stanley on 1/10/18.
 */

public class Users {
    @com.google.gson.annotations.SerializedName("user_id")
    private int user_id;
    public Integer getId() {
        return user_id;
    }

    @com.google.gson.annotations.SerializedName("user_pin")
    private int user_pin;
    public Integer getPIN() {

        return user_pin;
    }
    public void setPIN(Integer user_pin) {
        this.user_pin = user_pin;
    }

    @com.google.gson.annotations.SerializedName("user_name")
    private String user_name;
    public String getName() {
        return user_name;
    }
    public void setName(String name) {
        this.user_name = name;
    }

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    public Users(String id,int user_id, int user_pin, String user_name) {
        this.id = id;
        this.user_id = user_id;
        this.setPIN(user_pin);
        this.setName(user_name);
    }

}
