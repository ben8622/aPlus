package com.example.aplusv2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // for debugging
    private static final String TAG = "RecyclerViewAdapter";

    /*
    These are the variables we need for this class. Later on we need to implement the grabbing
    of these values from our "USER" so there can be multiple people. We may have text files
    declaring users and their passwords, and then individual text files holding their data
    for each one.
     */
    private ArrayList<String> mSemesterNames = new ArrayList<>();
    private ArrayList<String> mSemesterGrades = new ArrayList<>();
    private Context mContext;

    // default constructor
    public RecyclerViewAdapter(Context mContext, ArrayList<String> mSemesterNames, ArrayList<String> mSemesterGrades) {
        this.mSemesterNames = mSemesterNames;
        this.mSemesterGrades = mSemesterGrades;
        this.mContext = mContext;
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

        holder.semesterName.setText(mSemesterNames.get(position));
        holder.semesterGrade.setText(mSemesterGrades.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clocked on: " + mSemesterNames.get(position));

                // For now this will just display a popup of the semester name, but we want it
                // to eventually bring you to a new activity holding the classes of the semester
                Toast.makeText(mContext, mSemesterNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, viewCourses.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        // tells the adapter how many list items are there, if it was zero nothing would show up
        return mSemesterNames.size();
    }

    // Hold the views in memeory
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView semesterName;
        TextView semesterGrade;
        RelativeLayout parentLayout; // we need this to attach the onclick listener

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             // Viewholder class created
             semesterName = itemView.findViewById(R.id.semester_name);
             semesterGrade = itemView.findViewById(R.id.semester_grade);
             parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
