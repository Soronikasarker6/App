package com.ex1.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;



public class MainActivity extends AppCompatActivity {

    Button logOut;
    private AlertDialog.Builder alertDialog;
    private EditText searchEditText;
    private ImageButton searchButton;
    private RecyclerView recyclerView;
    private DatabaseReference mUserDatabase;


//    Toolbar toolbar;
//
//     DrawerLayout drawerLayout;
    //    RecyclerView recyclerView;
//    myadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        drawerLayout = findViewById(R.id.drawer);

        logOut = findViewById(R.id.logOutBtn);
        searchEditText= findViewById(R.id.searchField);
        searchButton = findViewById( R.id.searchBtn);
        recyclerView = findViewById(R.id.resultList);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

//        searchButton.setOnClickListener(new View.OnClickListener() {

//            public void onClick(View v) {
//               firebaseUserSearch();
//            }
//        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(MainActivity.this);

                //for icon
                alertDialog.setIcon(R.drawable.ic_baseline_medical_services_24);
                //  for title
                alertDialog.setTitle("");
                //for msg
                alertDialog.setMessage("Do you want to exit?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //exit
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"You have clicked on NO button",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
    }
//    public void firebaseUserSearch(){
//        FirebaseRecyclerAdapter<Users, UserViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Users, UserViewHolder>(
//                Users.class, R.layout.list_layout,
//        ) {
//            @Override
//            protected void onBindViewHolder(@NonNull @NotNull UserViewHolder holder, int position, @NonNull @NotNull Users model) {
//
//            }
//
//            public UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//                return null;
//            }
//        };
//    }
//
//    public class UserViewHolder extends RecyclerView.ViewHolder{
//        View mView; //Viewholder class
//
//        public UserViewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            mView = itemView;
//        }
//    }
     //Feedback and share
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId()== R.id.homeMenu){
            intent = new Intent(this, PatientHomeActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()== R.id.profileMenu) {
            intent = new Intent(this, EditProfile.class);
            startActivity(intent);
        }
        else if (item.getItemId()== R.id.chatMenu){
            intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.shareId) {
            intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            String subject = "HealthCare App";
            String body = " This app is help to make your life happy. \n com.ex1.myapplication";
            intent.putExtra("android.intent.extra.SUBJECT", subject);
            intent.putExtra("android.intent.extra.TEXT", body);
            this.startActivity(Intent.createChooser(intent, " share with "));
        } else if (item.getItemId() == R.id.feedbackId) {
            intent = new Intent(this.getApplicationContext(), FeedBack.class);
            this.startActivity(intent);
        }else if (item.getItemId() == R.id.aboutUsId) {
            intent = new Intent(this.getApplicationContext(), AboutUs.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
