package com.example.aplusv2;

import android.content.Context;
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

public class RecyclerViewAdapterClasses extends RecyclerView.Adapter<RecyclerViewAdapterClasses.ViewHolder> {
    // for debugging
    private static final String TAG = "RecyclerViewAdapterC";

    /*
    These are the variables we need for this class. Later on we need to implement the grabbing
    of these values from our "USER" so there can be multiple people. We may have text files
    declaring users and their passwords, and then individual text files holding their data
    for each one.
     */
    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mClassGrades = new ArrayList<>();
    private ArrayList<String> mClassWeights = new ArrayList<>();
    private Context mContext;

    // default constructor
    public RecyclerViewAdapterClasses(Context mContext, ArrayList<String> mClassNames, ArrayList<String> mClassGrades, ArrayList<String> mClassWeights) {
        this.mClassNames = mClassNames;
        this.mClassGrades = mClassGrades;
        this.mClassWeights = mClassWeights;
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

        holder.className.setText(mClassNames.get(position));
        holder.classGrade.setText(mClassGrades.get(position));
        holder.classWeight.setText(mClassWeights.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clocked on: " + mClassNames.get(position));

                // For now this will just display a popup of the class name, but we want it
                // to eventually bring you to a new activity holding the classes of the class
                Toast.makeText(mContext, mClassNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        // tells the adapter how many list items are there, if it was zero nothing would show up
        return mClassNames.size();
    }

    // Hold the views in memeory
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView className;
        TextView classGrade;
        TextView classWeight;
        RelativeLayout parentLayout; // we need this to attach the onclick listener

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             // Viewholder class created
             className = itemView.findViewById(R.id.class_name);
             classGrade = itemView.findViewById(R.id.class_grade);
            classWeight = itemView.findViewById(R.id.class_weight);
             parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
