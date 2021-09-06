package com.ex1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private TextInputLayout regFullName,regUsername,regEmail,regPhone,regPassword;
    private Button regBtn,regLogin,regDoctor;

    FirebaseDatabase rootNode;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regFullName=findViewById(R.id.reg_fullName);
        regUsername=findViewById(R.id.reg_userName);
        regEmail=findViewById(R.id.reg_email);
        regPhone=findViewById(R.id.reg_PhoneNumber);
        regPassword=findViewById(R.id.reg_password);

        regBtn=findViewById(R.id.reg_btn);
        regLogin=findViewById(R.id.reg_login);
        regDoctor=findViewById(R.id.join);

        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        regDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),RegistrationDoctor.class);
                startActivity(intent2);
            }
        });




        regBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                rootNode=FirebaseDatabase.getInstance();
                myRef=rootNode.getReference("users");

                // getting all the Values
                String name=regFullName.getEditText().getText().toString();
                String username=regUsername.getEditText().getText().toString();
                String email=regEmail.getEditText().getText().toString();
                String phone=regPhone.getEditText().getText().toString();
                String password=regPassword.getEditText().getText().toString();



                UserHelperClass helperClass=new UserHelperClass(name,username,email,phone,password);
                myRef.child(phone).setValue(helperClass);

                Intent intent= new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);

            }
        });
    }
}