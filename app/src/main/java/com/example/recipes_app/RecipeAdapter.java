package com.example.recipes_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

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
        new DownloadImageTask(imiImagine).execute(reteta.getImageUrl());


        return convertView;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;
            try {
                // Crează conexiunea HTTP
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                // Citește InputStream-ul din conexiune
                InputStream input = connection.getInputStream();

                // Convertește InputStream-ul într-un Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Setează imaginea descărcată în ImageView
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }

}
