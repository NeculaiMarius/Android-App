package com.example.recipes_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecipeActivity extends AppCompatActivity {
    private EditText etNume;
    private EditText etDescriere;
    private Switch swVegetariana;
    private RadioGroup rgTip;
    private Button btnAdauga;
    private Recipe reteta=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Intent intent=getIntent();
//        reteta=intent.getParcelableExtra("movieKey");



        initializeControls();
        initializeEvents();

        if(reteta==null){
            reteta=new Recipe();

            reteta.setImageUrl(null);
            reteta.setVegetarian(false);
            reteta.setTipReteta(TipReteta.Gustare);
            reteta.setVegetarian(swVegetariana.isChecked());
        }
    }

    private void initializeEvents() {
        swVegetariana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reteta.setVegetarian(isChecked);
            }
        });

        rgTip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=findViewById(checkedId);
                if (rb.getText().toString().equals("Mic dejun")){
                    reteta.setTipReteta(TipReteta.Mic_dejun);
                }
                else{
                    reteta.setTipReteta(TipReteta.valueOf(rb.getText().toString()));
                }
            }
        });

        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reteta.setNume(etNume.getText().toString());
                reteta.setDescriere(etDescriere.getText().toString());


                Log.w("RecipeActivity",reteta.toString());

                Intent intent=new Intent();
                intent.putExtra("recipeKey",reteta);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void initializeControls() {
        etNume=findViewById(R.id.etNume);
        etDescriere=findViewById(R.id.etDescriere);
        swVegetariana=findViewById(R.id.swVegetariana);
        rgTip=findViewById(R.id.rgTip);
        btnAdauga=findViewById(R.id.btnAdauga);
    }
}