package ca.goldenphoenicks.lockerauto.databases;

/**
 * Represents an item in a ToDo list
 */
public class Users {

    @com.google.gson.annotations.SerializedName("user_id")
    private int id;

    @com.google.gson.annotations.SerializedName("user_name")
    private String name;

    @com.google.gson.annotations.SerializedName("user_pin")
    private int pin;

    public Users() {

    }

    public Users(int id, String name, int pin) {
        this.setId(id);
        this.setName(name);
        this.setPin(pin);
    }

    public int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name=name;
    }

    public int getPin() {
        return pin;
    }

    public final void setPin(int pin) {
        this.pin=pin;
    }
}