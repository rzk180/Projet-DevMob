package fr.traza.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {


    private EditText emailReg;
    private EditText passwordReg;
    private EditText passconfReg;
    private EditText phonenum;
    private Button btnReg_reg;
    //Firebase Auth
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        Registration();

    }
    private void Registration(){
        emailReg=findViewById(R.id.email_registration);
        passwordReg=findViewById(R.id.registration_password);
        passconfReg=findViewById(R.id.regconfirm_password);
        phonenum=findViewById(R.id.reg_phonenumber);

        btnReg_reg=findViewById(R.id.btn_Reg);


        btnReg_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailReg.getText().toString().trim();
                String pass=passwordReg.getText().toString().trim();
                String passC=passconfReg.getText().toString().trim();
                String phoneN=phonenum.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailReg.setError("Required field...");
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    passwordReg.setError("Required field...");
                    return;
                }

                if (TextUtils.isEmpty(passC)){
                    passconfReg.setError("Required field...");
                    return;
                }

                if (TextUtils.isEmpty(phoneN)){
                    phonenum.setError("Required field...");
                    return;
                }
                mDialog.setMessage("Processing...");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Succesful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            mDialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(),"Registration Failed...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}