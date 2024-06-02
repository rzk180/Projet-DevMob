package fr.traza.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import fr.traza.project.Model.Data;

public class PostJobActivity extends AppCompatActivity {

    private FloatingActionButton fabBtn;

    private RecyclerView recyclerView;

    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        fabBtn=findViewById(R.id.fab_add);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();

        JobPostDatabase= FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        recyclerView=findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InsertJobPostActivity.class));

            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(JobPostDatabase, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {
                holder.setJobTitle(model.getTitle());
                holder.setJobDate(model.getDate());
                holder.setJobPlace(model.getPlace());
                holder.setJobSalary(model.getSalary());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkills(model.getSkills());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.job_post_item, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
        }

        public void setJobTitle(String title){
            TextView mTitle=myView.findViewById(R.id.job_title_item);
            mTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mDate=myView.findViewById(R.id.job_date_item);
            mDate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mDescriptione=myView.findViewById(R.id.job_des_item);
            mDescriptione.setText(description);
        }


        public void setJobPlace(String place){
            TextView mPlace=myView.findViewById(R.id.job_place_item);
            mPlace.setText(place);
        }

        public void setJobSalary(String salary){
            TextView mSalary=myView.findViewById(R.id.job_salary_item);
            mSalary.setText(salary);
        }

        public void setJobSkills(String skills){
            TextView mSkills=myView.findViewById(R.id.job_skills_item);
            mSkills.setText(skills);
        }




    }

}