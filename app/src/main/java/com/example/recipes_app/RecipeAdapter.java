package com.example.recipes_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private final Context context;
    private final int resource;
    private final ArrayList<Recipe> listaRetete;

    public RecipeAdapter(Context context, int resource, ArrayList<Recipe> listaRetete) {
        super(context,resource,listaRetete);
        this.context = context;
        this.resource = resource;
        this.listaRetete = listaRetete;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(resource,parent,false);
        }

        Recipe reteta=listaRetete.get(position);

        TextView etiNume=convertView.findViewById(R.id.etiNume);
        TextView etiDescriere=convertView.findViewById(R.id.etiDescriere);
        ImageView imiImagine=convertView.findViewById(R.id.iviReteta);
        Button btnEdit=convertView.findViewById(R.id.btnEdit);

        etiNume.setText(reteta.getNume());
        etiDescriere.setText(reteta.getDescriere());


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Editeaza o reteta", Toast.LENGTH_LONG).show();
            }
        });

        //IMAGINE
        Runnable downloadImageRunnable = new Runnable() {
            @Override
            public void run() {
                // Descarcă imaginea pe thread-ul secundar
                Bitmap bitmap = downloadImage(reteta.getImageUrl());

                MainActivity context1 = (MainActivity) context;
                context1.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imiImagine.setImageBitmap(bitmap);
                    }
                });            }
        };

        Thread downloadThread = new Thread(downloadImageRunnable);
        downloadThread.start();


        return convertView;
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }



}
