package com.example.d.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.d.R;
import com.example.d.databinding.ActivityProfileBinding;
import com.example.d.Tool.pp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseAuth firebaseAuth;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();


        ChipNavigationBar chipNavigationBar = binding.bottomMenu;
        chipNavigationBar.setItemSelected(R.id.home, true);

        chipNavigationBar.setOnItemSelectedListener(id -> {
            if (id == R.id.home) {
                Intent startIntent = new Intent(ProfileActivity.this, StartActivity.class);
                startActivity(startIntent);
                finish();
            }
        });


        binding.exitBtn.setOnClickListener(v -> confirmLogout());


        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            pp.setProfilePic(ProfileActivity.this, selectedImageUri, binding.pp);
                        }
                    }
                });


        binding.pp.setOnClickListener((v) -> {
            ImagePicker.with(this)
                    .cropSquare()
                    .compress(512)
                    .maxResultSize(512, 512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });
    }


    private void confirmLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Çıkış Yap");
        builder.setMessage("Çıkış yapmak istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", (dialog, which) -> {
            firebaseAuth.signOut();
            Intent loginIntent = new Intent(ProfileActivity.this, MainActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        });
        builder.setNegativeButton("Hayır", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
