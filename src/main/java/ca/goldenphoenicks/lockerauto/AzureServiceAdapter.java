<<<<<<< HEAD
package ca.goldenphoenicks.lockerauto;

/**
 * Created by Stanley on 1/10/18.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AzureServiceAdapter {
    private String mMobileBackendUrl = "https://lockerauto.azurewebsites.net";
    private Context mContext;
    private MobileServiceClient mClient;
    private static AzureServiceAdapter mInstance = null;

    AzureServiceAdapter(Context context) {
        mContext = context;
        try {
            mClient = new MobileServiceClient(mMobileBackendUrl, mContext);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void Initialize(Context context) {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        } else {
            throw new IllegalStateException("AzureServiceAdapter is already initialized");
        }
    }

    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    public MobileServiceClient getClient() {
        return mClient;
    }

    public ArrayList<Users> getUsers() throws MobileServiceException, ExecutionException, InterruptedException {
        MobileServiceTable<Users> table = mClient.getTable("dbo.users",Users.class);
        ArrayList<Users> lis = table.where().field("user_id").eq(1).execute().get();
//        table.execute(new TableQueryCallback<Users>() {
//            @Override
//            public void onCompleted(List<Users> result, int count, Exception exception, ServiceFilterResponse response) {
//                if(exception == null) {
//                    for(int i  = 0; i < count; i++) {
//                        Log.e("getUserName","Added a user to array list");
//                        testList.add(result.get(i));
//                    }
//                } else {
//                    Log.e("getUserName","Users not found");
//                }
//            }
//        });
//        return testList;
        return lis;
    }
=======
package ca.goldenphoenicks.lockerauto;

/**
 * Created by Stanley on 1/10/18.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AzureServiceAdapter {
    private String mMobileBackendUrl = "https://lockerauto.azurewebsites.net";
    private Context mContext;
    private MobileServiceClient mClient;
    private static AzureServiceAdapter mInstance = null;

    AzureServiceAdapter(Context context) {
        mContext = context;
        try {
            mClient = new MobileServiceClient(mMobileBackendUrl, mContext);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void Initialize(Context context) {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        } else {
            throw new IllegalStateException("AzureServiceAdapter is already initialized");
        }
    }

    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    public MobileServiceClient getClient() {
        return mClient;
    }

    public ArrayList<Users> getUsers() throws MobileServiceException, ExecutionException, InterruptedException {
        MobileServiceTable<Users> table = mClient.getTable("dbo.users",Users.class);
        ArrayList<Users> lis = table.where().field("user_id").eq(1).execute().get();
//        table.execute(new TableQueryCallback<Users>() {
//            @Override
//            public void onCompleted(List<Users> result, int count, Exception exception, ServiceFilterResponse response) {
//                if(exception == null) {
//                    for(int i  = 0; i < count; i++) {
//                        Log.e("getUserName","Added a user to array list");
//                        testList.add(result.get(i));
//                    }
//                } else {
//                    Log.e("getUserName","Users not found");
//                }
//            }
//        });
//        return testList;
        return lis;
    }
>>>>>>> e85493692c1994a6b92a212fedb5823ed428d526
}