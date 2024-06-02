package fr.traza.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.traza.project.Model.Data;

public class AllJobActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private DatabaseReference mAllJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);


        mAllJobPost= FirebaseDatabase.getInstance().getReference().child("Public Database");
        mAllJobPost.keepSynced(true);

        toolbar=findViewById(R.id.all_job_post);
        setSupportActionBar(toolbar);
        getActionBar().setTitle("all Job Post");

        recyclerView=findViewById(R.id.recycler_all_job);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mAllJobPost, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data, AllJobsPostViewHolder> adapter = new FirebaseRecyclerAdapter<Data, AllJobsPostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AllJobsPostViewHolder holder, int position, @NonNull Data model) {
                holder.setJobTitle(model.getTitle());
                holder.setJobDate(model.getDate());
                holder.setJobPlace(model.getPlace());
                holder.setJobSalary(model.getSalary());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
            }

            @NonNull
            @Override
            public AllJobsPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.job_post_item, parent, false);
                return new AllJobsPostViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class AllJobsPostViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public AllJobsPostViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setJobTitle(String title){
            TextView mTitle=myview.findViewById(R.id.joball_title_item);
            mTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mDate=myview.findViewById(R.id.joball_date_item);
            mDate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mDescriptione=myview.findViewById(R.id.joball_des_item);
            mDescriptione.setText(description);
        }


        public void setJobPlace(String place){
            TextView mPlace=myview.findViewById(R.id.joball_place_item);
            mPlace.setText(place);
        }

        public void setJobSalary(String salary){
            TextView mSalary=myview.findViewById(R.id.joball_salary_item);
            mSalary.setText(salary);
        }

        public void setJobSkills(String skills){
            TextView mSkills=myview.findViewById(R.id.joball_skills_item);
            mSkills.setText(skills);
        }


    }



}