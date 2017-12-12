//package ca.goldenphoenicks.lockerauto.databases;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//
//import ca.goldenphoenicks.lockerauto.R;
//
///**
// * Adapter to bind a Users List to a view
// */
//public class UsersAdapter extends ArrayAdapter<Users> {
//
//    /**
//     * Adapter context
//     */
//    Context mContext;
//
//    /**
//     * Adapter View layout
//     */
//    int mLayoutResourceId;
//
//    public UsersAdapter(Context context, int layoutResourceId) {
//        super(context, layoutResourceId);
//
//        mContext = context;
//        mLayoutResourceId = layoutResourceId;
//    }
//
//    /**
//     * Returns the view for a specific item on the list
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = convertView;
//
//        final Users currentItem = getItem(position);
//
//        if (row == null) {
//            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            row = inflater.inflate(mLayoutResourceId, parent, false);
//        }
//
//        row.setTag(currentItem);
//        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkUsers);
//        checkBox.setText(currentItem.getText());
//        checkBox.setChecked(false);
//        checkBox.setEnabled(true);
//
//        checkBox.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                if (checkBox.isChecked()) {
//                    checkBox.setEnabled(false);
//                    if (mContext instanceof ToDoActivity) {
//                        ToDoActivity activity = (ToDoActivity) mContext;
//                        activity.checkItem(currentItem);
//                    }
//                }
//            }
//        });
//
//        return row;
//    }
//
//}
