package com.example.recipes_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityLauncher;
    private ArrayList<Recipe> listaRetete=new ArrayList<>();
    private ListView lvRecipes;
    private ArrayAdapter<Recipe> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Recipe r1=new Recipe("Tiramisu","Prajitura de casa tiramisu",false,TipReteta.Gustare,"https://culinary.ro/wp-content/uploads/2023/05/Reteta-spaghete-bolognese.jpg");
        Recipe r2=new Recipe("Omleta","Omleta simpla cu bacon",false,TipReteta.Mic_dejun,null);
        Recipe r3=new Recipe("Paste","Paste cu sos de rosii",false,TipReteta.Cina,null);
        listaRetete.add(r1);
        listaRetete.add(r2);
        listaRetete.add(r3);

        Log.e("MainActivity",listaRetete.toString());

        lvRecipes=findViewById(R.id.lvRecipes);
        adapter=new RecipeAdapter(this,R.layout.lv_item_recipe,listaRetete);
        lvRecipes.setAdapter(adapter);


        activityLauncher=registerForActivityResult(new ActivityResultContract<Intent, Recipe>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Intent intent) {
                return intent;
            }

            @Override
            public Recipe parseResult(int i, @Nullable Intent intent) {
                if (i==RESULT_OK && intent!=null){
                    return intent.getParcelableExtra("recipeKey");
                }
                return null;
            }
        }, new ActivityResultCallback<Recipe>() {
            @Override
            public void onActivityResult(Recipe o) {
                if (o==null){
                    Log.e("MainActivity","REZULTATUL ESTE NULL");
                }
                else {
                    Log.e("MainActivity","REZULTAT: "+o.toString());
                    if (!listaRetete.contains(o)){
                        listaRetete.add(o);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        lvRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item_adauga){
            Intent intent=new Intent(getApplicationContext(), RecipeActivity.class);
            activityLauncher.launch(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}