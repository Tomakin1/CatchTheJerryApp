package com.example.d.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d.R;
import com.example.d.databinding.ActivityStartBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);


        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameIntent = new Intent(StartActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });


        ChipNavigationBar chipNavigationBar = binding.bottomMenu;


        chipNavigationBar.setItemSelected(R.id.home, true);


        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                if (id == R.id.Profile) {

                    Intent profileIntent = new Intent(StartActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                }

            }
        });
    }
}
