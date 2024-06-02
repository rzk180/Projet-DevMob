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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;

    private Button btnLogin;
    private Button btnRegistration;


    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        LoginFunction();

    }
    private  void LoginFunction(){
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.login_password);

        btnLogin=findViewById(R.id.btn_login);
        btnRegistration=findViewById(R.id.btn_registration);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail=email.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Required field...");
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    password.setError("Required field...");
                    return;
                }
                mDialog.setMessage("Processing...");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(mEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Succesful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            mDialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(),"Login Failed...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));

            }
        });




    }

    // Méthode appelée lorsque le TextView est cliqué
    public void onTextClick(View view) {
        // Actions à effectuer lors du clic
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }


}