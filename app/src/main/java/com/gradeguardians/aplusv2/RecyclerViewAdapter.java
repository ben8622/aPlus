package com.gradeguardians.aplusv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // for debugging
    private static final String TAG = "RecyclerViewAdapter";

    /*
    These are the variables we need for this class. Later on we need to implement the grabbing
    of these values from our "USER" so there can be multiple people. We may have text files
    declaring users and their passwords, and then individual text files holding their data
    for each one.
     */
    List<Semester> mSemesters;
    private Context mContext;
    DatabaseHelper db_helper;
    SharedPreferences shared_pref;
    String user_id;

    // default constructor
    public RecyclerViewAdapter(Context mContext, List <Semester> mSemesters, String curr_user) {
        db_helper = new DatabaseHelper(mContext);
        shared_pref = PreferenceManager.getDefaultSharedPreferences(mContext);

        user_id = shared_pref.getString("username", "error");
        user_id = curr_user;

        this.mContext = mContext;
        this.mSemesters = db_helper.getAllSemester(user_id);
    }

    @NonNull
    @Override
    // method that's responsible for inflating the view, usually same for any recylcerivew/adapter
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    // important method that will change based on your layout and what you want them to look like
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called."); //prints out to log every time item put in (debugging)

        holder.semesterName.setText(mSemesters.get(position).getSemesterID());
        holder.semesterGrade.setText(Double.toString(mSemesters.get(position).getSemesterGPA()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clocked on: " + mSemesters.get(position).getSemesterID());

                SharedPreferences.Editor prefEditor = shared_pref.edit();
                try{
                    prefEditor.putString("semester", mSemesters.get(position).getSemesterID());
                    prefEditor.apply();
                }
                catch (Exception e){
                    Toast.makeText( mContext, "ERROR in adding user to preferences", Toast.LENGTH_LONG).show();
                }
                // For now this will just display a popup of the semester name, but we want it
                // to eventually bring you to a new activity holding the classes of the semester
                Toast.makeText(mContext, mSemesters.get(position).getSemesterID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, viewCourses.class);
                mContext.startActivity(intent);
            }
        });
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clocked on: " + mSemesters.get(position).getSemesterID());

                String user_id = "user"; //grab from shared_prefs
                String sem_id = mSemesters.get(position).getSemesterID();

                Semester s = new Semester(user_id, sem_id, 0);

                /* this is to avoid accessing index that is out of bounds */
                if (position == mSemesters.size() - 1) { // if last element is deleted, no need to shift
                    mSemesters.remove(position);
                    db_helper.deleteSemester(s);
                    notifyItemRemoved(position);
                } else { // if the element deleted is not the last one
                    int shift=1; // not zero, shift=0 is the case where position == dataList.size() - 1, which is already checked above
                    while (true) {
                        try {
                            mSemesters.remove(position-shift);
                            db_helper.deleteSemester(s);
                            notifyItemRemoved(position);
                            break;
                        } catch (IndexOutOfBoundsException e) { // if fails, increment the shift and try again
                            shift++;
                        }
                    }
                }

                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        // tells the adapter how many list items are there, if it was zero nothing would show up
        return mSemesters.size();
    }

    // Hold the views in memeory
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView semesterName;
        TextView semesterGrade;
        RelativeLayout parentLayout; // we need this to attach the onclick listener
        Button btn_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             // Viewholder class created
             semesterName = itemView.findViewById(R.id.semester_name);
             semesterGrade = itemView.findViewById(R.id.semester_grade);
             parentLayout = itemView.findViewById(R.id.parent_layout);
             btn_del = itemView.findViewById(R.id.btn_del);
        }
    }
    
}
