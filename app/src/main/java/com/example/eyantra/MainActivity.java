package com.example.eyantra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Ref;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int Image_Request_Code = 1;

    Button btnbrowse, btnupload, next;
    EditText txtdata,shopName, phoneNumber, address, locality ;
    ImageView imgview;
    Uri FilePathUri;
    uploadinfo uploadinfo;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog ;
    StorageTask uploadtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference.setValue(uploadinfo);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadinfo");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);
        txtdata = (EditText)findViewById(R.id.txtdata);
        shopName = (EditText)findViewById(R.id.shopName);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        address = (EditText)findViewById(R.id.address);
        locality = (EditText)findViewById(R.id.locality);
        imgview = (ImageView)findViewById(R.id.image_view);
        progressDialog = new ProgressDialog(MainActivity.this);// context name as per your project name
        uploadinfo = new uploadinfo();


        next=(Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,List.class);
                startActivity(intent);

            }
        });


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

           FilePathUri = data.getData();


           try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


        public void UploadImage () {

            if (FilePathUri != null && txtdata != null && shopName != null && phoneNumber != null && address != null && locality != null) {

                progressDialog.setTitle("Uploading your details...");
                progressDialog.show();


                String imageid;
                imageid = System.currentTimeMillis() + "." + GetFileExtension(FilePathUri);
                uploadinfo.setImageName(txtdata.getText().toString().trim());
                uploadinfo.setShopName(shopName.getText().toString().trim());
                uploadinfo.setPhone(phoneNumber.getText().toString().trim());
                uploadinfo.setShopAddress(address.getText().toString().trim());
                uploadinfo.setShopLocality(locality.getText().toString().trim());
                uploadinfo.setImageURL(imageid);
                databaseReference.push().setValue(uploadinfo);

               StorageReference ref = storageReference.child(imageid);
               uploadtask = ref.putFile(FilePathUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Details Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                /*uploadinfo upload = new uploadinfo(txtdata.getText().toString().trim(),
                                        taskSnapshot.getDownloadUrl().toString());
                                String uploadid = databaseReference.push().getKey();
                                databaseReference.child(uploadid).setValue(upload);
*/

                                Intent in = new Intent(MainActivity.this, List.class);
                                startActivity(in);

                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(MainActivity.this, "Uploading Failed...Please try again!", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(in);
                                // Write failed
                                // ...
                            }
                        });
            } else {

                Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();

            }
        }


    }