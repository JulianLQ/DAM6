package co.visionscore.dam6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button btn_signup, btn_login;

    private Activity mySelf;

    FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySelf = this;
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: cambiar SignUp por la actividad de registro (Milena)
                Intent signup = new Intent(mySelf, SignUp.class);
                startActivity(signup);
            }

        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()){
                    if (password.length()>5){
                        signInWithEmailAndPassword(username, password);
                    }else{
                        Toast.makeText(MainActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO: este botón debe dirigir a la actividad del Navigation Drawer
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String username = et_username.getText().toString();
//                String password = et_password.getText().toString();
//
//                if (username.equals("admin") && password.equals("123")) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
//                    builder.setTitle(R.string.txt_builder);
//                    builder.setMessage(R.string.txt_builder_success);
//                    builder.setPositiveButton(R.string.txt_accept, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            Intent navigationDrawer = new Intent(mySelf, NavigationDrawer.class);
//
//                            navigationDrawer.putExtra("username", username);
//                            navigationDrawer.putExtra("password", password);
//
//                            startActivity(navigationDrawer);
//
//                        }
//                    });
//                    builder.setNegativeButton(R.string.txt_cancel, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                }else {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
//                    builder.setTitle(R.string.txt_builder);
//                    builder.setMessage(R.string.txt_error_login);
//                    builder.setPositiveButton(R.string.txt_accept, null);
//                    builder.setCancelable(false);
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void reload() {

    }

    public void signInWithEmailAndPassword(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            startActivity(new Intent(mySelf, NavigationDrawer.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser user) {

    }
}