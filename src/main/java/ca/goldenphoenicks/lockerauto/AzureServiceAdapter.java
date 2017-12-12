//package ca.goldenphoenicks.lockerauto;
//
//import android.content.Context;
//import com.microsoft.windowsazure.mobileservices.*;
//import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
//
//import java.net.MalformedURLException;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//import ca.goldenphoenicks.lockerauto.databases.Users;
//
//public class AzureServiceAdapter {
//    private String mMobileBackendUrl = "https://lockerauto.azurewebsites.net";
//    private Context mContext;
//    private MobileServiceClient mClient;
//    private static MobileServiceClient ClientConn;
//    private static AzureServiceAdapter mInstance = null;
//
//    public AzureServiceAdapter(Context context) throws MalformedURLException {
//        mContext = context;
//        mClient = new MobileServiceClient(mMobileBackendUrl, mContext);
//    }
//
//    public static void Initialize(Context context) {
//        if (mInstance == null) {
//            try {
//                mInstance = new AzureServiceAdapter(context);
//                ClientConn = mInstance.getClient();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            throw new IllegalStateException("AzureServiceAdapter is already initialized");
//        }
//    }
//
//    public static AzureServiceAdapter getInstance() {
//        if (mInstance == null) {
//            throw new IllegalStateException("AzureServiceAdapter is not initialized");
//        }
//        return mInstance;
//    }
//
//    public MobileServiceClient getClient() {
//        return mClient;
//    }
//
//    public static List<Users> getUsrTab() {
//        List<Users> usr=null;
//
//        MobileServiceTable<Users> userTab = ClientConn.getTable("users",Users.class);
//        try {
//            usr = userTab.where().field("user_pin").eq("1996").execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return usr;
//    }
//}
