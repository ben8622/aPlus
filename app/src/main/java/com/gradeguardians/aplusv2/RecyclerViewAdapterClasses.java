package com.gradeguardians.aplusv2;

import android.content.Context;
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

public class RecyclerViewAdapterClasses extends RecyclerView.Adapter<RecyclerViewAdapterClasses.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapterC";

    private List<Course> course_list;
    private Context mContext;
    DatabaseHelper db_helper;
    SharedPreferences shared_pref;
    String curr_user;
    String curr_sem;

    /* default constructor, we get all our data within adapter no need to pass it in */
    public RecyclerViewAdapterClasses(Context mContext, String curr_user, String curr_semester) {
        db_helper = new DatabaseHelper(mContext);
        shared_pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.curr_user = curr_user;
        this.curr_sem = curr_semester;
        /* initiate here and onBindViewHolder to avoid trying to get a size from void list */
        course_list = db_helper.getAllCourses(curr_user, curr_semester);
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
    /* creates list item for each course belonging to keyed semester */
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /* update data from database for safe measure*/
        course_list.clear();
        course_list = db_helper.getAllCourses(curr_user, curr_sem);
        holder.className.setText(course_list.get(position).getCourseID());
        holder.classGrade.setText(String.format("%.2f", course_list.get(position).getCourseGrade()));
        holder.classWeight.setText(Integer.toString(course_list.get(position).getCourseWeight()));
        /* onclick listener for our delete buttons */
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course c = new Course(course_list.get(position).getUserID(),
                                      course_list.get(position).getSemesterID(),
                                      course_list.get(position).getCourseID(), 0, 0.0);

                /* this is to avoid accessing index that is out of bounds */
                if (position == course_list.size() - 1 || position == 0 ) {
                    course_list.remove(position);
                    db_helper.deleteCourse(c);
                    notifyItemRemoved(position);
                }
                else {
                    int shift=1;
                    while (true) {
                        try {
                            course_list.remove(position-shift);
                            db_helper.deleteCourse(c);
                            notifyItemRemoved(position);
                            break;
                        }
                        catch (IndexOutOfBoundsException e) {
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
