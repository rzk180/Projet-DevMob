package fr.traza.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import fr.traza.project.Model.Data;


public class InsertJobPostActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText job_title;
    private EditText job_place;
    private EditText job_salary;
    private EditText job_description;
    private EditText job_skills;

    private Button btn_post_job;

    private FirebaseAuth mAuth;
    private DatabaseReference mJobPost;

    private DatabaseReference mPublicDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);

        Toolbar toolbar=findViewById(R.id.insert_job_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post job");

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();

        mJobPost=FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        mPublicDatabase=FirebaseDatabase.getInstance().getReference().child("Public Database");

        InsertJob();


    }

    private  void InsertJob(){
        job_title=findViewById(R.id.job_title);
        job_place=findViewById(R.id.job_place);
        job_salary=findViewById(R.id.job_salary);
        job_description=findViewById(R.id.job_description);
        job_skills=findViewById(R.id.job_Skills);

        btn_post_job=findViewById(R.id.btn_job_post);

        btn_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=job_title.getText().toString().trim();
                String place=job_place.getText().toString().trim();
                String salary=job_salary.getText().toString().trim();
                String description=job_description.getText().toString().trim();
                String skills=job_skills.getText().toString().trim();

                if (TextUtils.isEmpty(title)){
                    job_description.setError("Required Field...");
                    return;
                }

                if (TextUtils.isEmpty(place)){
                    job_place.setError("Required Field...");
                    return;
                }

                if (TextUtils.isEmpty(salary)){
                    job_salary.setError("Required Field...");
                    return;
                }

                if (TextUtils.isEmpty(description)){
                    job_description.setError("Required Field...");
                    return;
                }

                if (TextUtils.isEmpty(skills)){
                    job_skills.setError("Required Field...");
                    return;
                }

                String id=mJobPost.push().getKey();

                String date= DateFormat.getDateInstance().format(new Date());

                Data data=new Data(title,place,salary,description,skills,id,date);

                mJobPost.child(id).setValue(data);

                mPublicDatabase.child(id).setValue(data);

                Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),PostJobActivity.class));

            }
        });

    }


}