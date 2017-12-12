package ca.goldenphoenicks.lockerauto.sqlite;

/**
 * Represents an item in a ToDo list
 */
public class Users {

    private int id;

    private String name;

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