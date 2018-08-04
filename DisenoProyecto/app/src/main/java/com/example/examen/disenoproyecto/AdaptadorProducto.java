package com.example.examen.disenoproyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AdaptadorProducto extends ArrayAdapter<Producto>{

    public AdaptadorProducto(@NonNull Context context, int resource, @NonNull List<Producto> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Producto producto=getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_productos,parent, false);
        }
        ImageView imageView=(ImageView) convertView.findViewById(R.id.imgprod);

        TextView titulo=(TextView) convertView.findViewById(R.id.titulo);
        TextView descripcion=(TextView) convertView.findViewById(R.id.descripcion);
        TextView precio=(TextView) convertView.findViewById(R.id.preciocustom);

        titulo.setText(producto.getNombre());
        descripcion.setText(producto.getDescripcion());
        precio.setText("$"+producto.getPrecio());
        Picasso.with(convertView.getContext())
                .load(producto.getFoto())
                .error(R.mipmap.foto)
                .fit()
                .centerInside()
                .into(imageView);
       // downloadFile("http://jonsegador.com/wp-content/apezz.png",imageView);

        return convertView;
    }

    void downloadFile(String imageHttpAddress, ImageView imageView) {
        Bitmap loadedImage;
        URL imageUrl = null;
        try {

            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            imageView.setImageBitmap(loadedImage);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
