package com.hao.bilkentconnect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.ModelClasses.Product;
import com.hao.bilkentconnect.databinding.ActivityShareAnnoucementScreenBinding;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class ShareAnnoucementScreen extends AppCompatActivity {

    private ActivityShareAnnoucementScreenBinding binding;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Uri imageData;
    Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareAnnoucementScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registerLauncher();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void shareAnnouncement(View view) {
        String description = binding.discriptionText.getText().toString();
        String price = binding.setPriceText.getText().toString();
        String title = binding.enterTitleText.getText().toString();

        // Check if any of the inputs are empty
        if (description.isEmpty() || price.isEmpty() || title.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!price.matches("[0-9]+")){ // so cool -Arda
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageData != null) {
            UUID uuid = UUID.randomUUID();
            final String imageName = "images/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(taskSnapshot -> {
                StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                newReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    Product newProduct = new Product(title, downloadUrl, description, userId, price);

                    firebaseFirestore.collection("Products").add(newProduct.toMap()).addOnSuccessListener(documentReference -> {
                        String generatedProductId = documentReference.getId();
                        newProduct.setProductId(generatedProductId);

                        // Update the product document with the generated ID
                        firebaseFirestore.collection("Products").document(generatedProductId).update("productId", generatedProductId);

                    }).addOnFailureListener(e -> {
                        Toast.makeText(ShareAnnoucementScreen.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(ShareAnnoucementScreen.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectImageAdvertisement(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                // ask permission
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"You have to give permission if you want to share stuff",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission.", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // ask permission
                            permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
                        }
                    }).show();
                }else{
                    // ask permission
                    permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
                }

            }else{
                // already have permission
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }else{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                // ask permission
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"You have to give permission if you want to share stuff",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission.", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // ask permission
                            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }).show();
                }else{
                    // ask permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }else{
                // already have permission
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }

    }
    private void registerLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK) {
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null) {
                        // image selected
                        imageData = intentFromResult.getData();
                        binding.imageImage.setImageURI(imageData);


                        try{
                            if (Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(ShareAnnoucementScreen.this.getContentResolver(), imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                binding.imageImage.setImageBitmap(selectedImage);

                            }
                            else {

                                selectedImage = MediaStore.Images.Media.getBitmap(ShareAnnoucementScreen.this.getContentResolver(), imageData);
                                binding.imageImage.setImageBitmap(selectedImage);
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }

            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    // permission granted
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }
                else {
                    // permission denied
                    Toast.makeText(ShareAnnoucementScreen.this,"Permission needed for gallery", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public  void goToSecondHandMain(View view) {
        Intent intent = new Intent(ShareAnnoucementScreen.this, SecondHandMain.class);
        startActivity(intent);
        finish();
    }


}