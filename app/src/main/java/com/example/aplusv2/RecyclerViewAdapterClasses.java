package com.example.aplusv2;

import android.content.Context;
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

public class RecyclerViewAdapterClasses extends RecyclerView.Adapter<RecyclerViewAdapterClasses.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapterC";

    private List<Course> course_list;
    private Context mContext;
    DatabaseHelper db_helper;

    /* default constructor, we get all our data within adapter no need to pass it in */
    public RecyclerViewAdapterClasses(Context mContext) {
        db_helper = new DatabaseHelper(mContext);
        /* initiate here and onBindViewHolder to avoid trying to get a size from void list */
        course_list = db_helper.getAllCourses("user", "Semester1");
        this.mContext = mContext;
    }

    @NonNull
    @Override
    /* method that's responsible for inflating the view, usually same for any recylcerivew/adapter */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_classes, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    /* important method that will change based on your layout and what you want them to look like */
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called."); //prints out to log every time item put in (debugging)

        /* update data from database*/
        course_list.clear();
        course_list = db_helper.getAllCourses("user", "Semester1");

        holder.className.setText(course_list.get(position).getCourseID());
        holder.classGrade.setText(String.format("%.2f", course_list.get(position).getCourseGrade()));
        holder.classWeight.setText(Integer.toString(course_list.get(position).getCourseWeight()));

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clocked on: " + course_list.get(position).getCourseID());

                String user_id = "user"; //grab from shared_prefs
                String sem_id = "Semester1"; //grab from shared_prefs
                String course_id = course_list.get(position).getCourseID();

                Course c = new Course(user_id, sem_id, course_id, 0, 0.0);

                /* this is to avoid accessing index that is out of bounds */
                if (position == course_list.size() - 1) { // if last element is deleted, no need to shift
                    course_list.remove(position);
                    db_helper.deleteCourse(c);
                    notifyItemRemoved(position);
                } else { // if the element deleted is not the last one
                    int shift=1; // not zero, shift=0 is the case where position == dataList.size() - 1, which is already checked above
                    while (true) {
                        try {
                            course_list.remove(position-shift);
                            db_helper.deleteCourse(c);
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
        return course_list.size();
    }

    // Hold the views in memeory
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView className;
        TextView classGrade;
        TextView classWeight;
        Button btn_del;
        RelativeLayout parentLayout; // we need this to attach the onclick listener

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             // Viewholder class created
             className = itemView.findViewById(R.id.class_name);
             classGrade = itemView.findViewById(R.id.class_grade);
             classWeight = itemView.findViewById(R.id.class_weight);
             parentLayout = itemView.findViewById(R.id.parent_layout);
             btn_del = itemView.findViewById(R.id.btn_del);
        }
    }
}
