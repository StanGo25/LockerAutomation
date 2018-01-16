package ca.goldenphoenicks.lockerauto;

/**
 * Created by Stanley on 1/10/18.
 */

public class Products {
    @com.google.gson.annotations.SerializedName("product_id")
    private int product_id;
    public int getPid() {
        return product_id;
    }

    @com.google.gson.annotations.SerializedName("product_name")
    private String product_name;
    public String getName() {
        return product_name;
    }
    public void setName(String product_name) {
        this.product_name = product_name;
    }

    @com.google.gson.annotations.SerializedName("product_status")
    private int product_status;
    public Integer getStatus() {
        return product_status;
    }
    public void setStatus(int product_status) {
        this.product_status=product_status;
    }

    @com.google.gson.annotations.SerializedName("product_type")
    private String product_type;
    public String getType () {
        return product_type;
    }
    public void setType(String product_type) {
        this.product_type = product_type;
    }
}
