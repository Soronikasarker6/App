package com.ex1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationDoctor extends AppCompatActivity {
    private TextInputLayout docFullName,docUsername,docEmail,docPhone,docPassword;
    private Button docBtn,docLogin;
    private ImageButton upBtn;
    private CircleImageView docImageView;

    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    private Uri docImageUri;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_doctor);


        docFullName=findViewById(R.id.doc_fullName);
        docUsername=findViewById(R.id.doc_userName);
        docEmail=findViewById(R.id.doc_email);
        docPhone=findViewById(R.id.doc_PhoneNumber);
        docPassword=findViewById(R.id.doc_password);
        docImageView= findViewById(R.id.upPic);

        docBtn=findViewById(R.id.doc_btn);
        docLogin=findViewById(R.id.doc_login);
        upBtn= findViewById(R.id.upPic2);

        docImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docImageUri != null){
                    uploadToFirebase(docImageUri);
                }else{
                    Toast.makeText(RegistrationDoctor.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        docLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);

            }
        });





        docBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                rootNode=FirebaseDatabase.getInstance();
                myRef=rootNode.getReference("doctor");

                // getting all the Values
                String name=docFullName.getEditText().getText().toString();
                String username=docUsername.getEditText().getText().toString();
                String email=docEmail.getEditText().getText().toString();
                String phone=docPhone.getEditText().getText().toString();
                String password=docPassword.getEditText().getText().toString();
                String imgUrl = docImageView.toString();



                DocHelperClass helperClass=new DocHelperClass(name,username,email,phone,password,imgUrl);
                myRef.child(phone).setValue(helperClass);

                Intent intent= new Intent(getApplicationContext(),DashBoard.class);
                startActivity(intent);

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            docImageUri = data.getData();
            docImageView.setImageURI(docImageUri);

        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef =reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DocHelperClass docHelper = new DocHelperClass(uri.toString());
                        String modelId = myRef.push().getKey();
                        myRef.child(modelId).setValue(docHelper);

                        Toast.makeText(RegistrationDoctor.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        docImageView.setImageResource(R.drawable.ic_launcher_foreground);

                        Toast.makeText(RegistrationDoctor.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}