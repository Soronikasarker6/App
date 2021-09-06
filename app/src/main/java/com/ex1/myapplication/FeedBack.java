package com.ex1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBack extends AppCompatActivity implements View.OnClickListener {

    private Button sendButton,clearButton;
    private EditText nameEditText, msgEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

       sendButton  = this.findViewById(R.id.feedbackSend);
       clearButton = this.findViewById(R.id.feedbackClear);
       nameEditText= this.findViewById(R.id.feedbackName);
       msgEditText = this.findViewById(R.id.feedbackMsg);

       sendButton.setOnClickListener(this);
       clearButton.setOnClickListener(this);
    }
    public void onClick(View v) {
        try {
            String name = this.nameEditText.getText().toString();
            String msg = this.msgEditText.getText().toString();
            if (v.getId() == R.id.feedbackSend) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/email");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"soronika.sarker.cse@gmail.com", "naznin.nahar.cse@ulab.edu.bd"});
                intent.putExtra("android.intent.extra.SUBJECT", "Feedback from app");
                intent.putExtra("android.intent.extra.TEXT", "Name: " + name + "\n Message: " + msg);
                this.startActivity(Intent.createChooser(intent, "Feedback with"));
            } else if (v.getId() == R.id.feedbackClear) {
                this.nameEditText.setText("");
                this.msgEditText.setText("");
            }
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "Exception" + e, Toast.LENGTH_SHORT).show();
        }

    }
}