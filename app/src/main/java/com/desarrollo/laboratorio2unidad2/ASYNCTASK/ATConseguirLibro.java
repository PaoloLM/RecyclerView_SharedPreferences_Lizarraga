package com.desarrollo.laboratorio2unidad2.ASYNCTASK;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.laboratorio2unidad2.ACTIVIDADES.MainActivity;
import com.desarrollo.laboratorio2unidad2.ADAPTADOR.RVAdapter;
import com.desarrollo.laboratorio2unidad2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ATConseguirLibro extends AsyncTask<String, Integer, String> {

    private TextView mTextoTitulo;
    private TextView mTextoDescripcion;
    private TextView mTextoDescripcionLarga;
    private Context context;
    private ProgressDialog proceso;
    private String DescripcionLarga;
    private String Descripcion;

    public ATConseguirLibro(TextView mTextoTitulo, TextView mTextoDescripcion, TextView mTextoDescripcionLarga, Context context) {
        this.mTextoTitulo = mTextoTitulo;
        this.mTextoDescripcion = mTextoDescripcion;
        this.mTextoDescripcionLarga = mTextoDescripcionLarga;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (UtilidadesRed.obtenerInformacionLibro(strings[0]) != null) {
            return UtilidadesRed.obtenerInformacionLibro(strings[0]);
        } else {
            Toast.makeText(context.getApplicationContext(), "Error en la busqueda", Toast.LENGTH_LONG).show();
        }
        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        proceso = new ProgressDialog(context);
        proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proceso.setMessage("Buscando Libro");
        proceso.setCancelable(false);
        proceso.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray Jitems = jsonObject.getJSONArray("items");
            int i = 0;

            String titulo = null;
            String autores = null;
            String subtitle = null;
            String publisher = null;
            String datepublisher = null;
            String largedescription = null;

            /*******************************************OBTENIENDO DATOS**********************************************/

            while ( i < Jitems.length() && titulo == null) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    titulo = volumenInfo.getString("title");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }

            while ( i < Jitems.length() && largedescription == null) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    largedescription = volumenInfo.getString("description");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            while ( i < Jitems.length() && autores == null) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    autores = volumenInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }

            while ( i < Jitems.length() && subtitle == null ) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    subtitle = volumenInfo.getString("subtitle");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }

            while ( i < Jitems.length() && publisher == null) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    publisher = volumenInfo.getString("publisher");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            while ( i < Jitems.length() && datepublisher == null) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    datepublisher = volumenInfo.getString("publishedDate");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            while ( i < Jitems.length() && largedescription == null) {
                JSONObject libro = Jitems.getJSONObject(i);
                JSONObject volumenInfo = libro.getJSONObject("volumeInfo");

                try {
                    largedescription = volumenInfo.getString("description");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            if (titulo != null || autores != null) {
                mTextoTitulo.setText(titulo);
                mTextoDescripcion.setText(FormaDescripcion(subtitle, autores, publisher, datepublisher));
                mTextoDescripcionLarga.setText(FormaDescripcionLarga(titulo, largedescription));
                proceso.dismiss();
                Toast.makeText(context.getApplicationContext(), "Se encontro el libro", Toast.LENGTH_SHORT).show();
                MainActivity.verificador = 1;

            } else {
                proceso.dismiss();
                Toast.makeText(context.getApplicationContext(), "No se encontro el libro", Toast.LENGTH_SHORT).show();
                mTextoTitulo.setText("No se encontro el Libro");
                mTextoDescripcion.setText("");
                mTextoDescripcionLarga.setText("");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String FormaDescripcionLarga(String titulo, String largedescription) {
        DescripcionLarga = "DESCRIPCION:" + "\n\n" + largedescription;
        MainActivity.HistorialTitulo = titulo;
        MainActivity.HistorialDescripcion = largedescription;
        return DescripcionLarga;
    }

    private String ExtraerAutor(String autores) {
        return autores.substring(2, autores.length() - 2);
    }

    private String FormaDescripcion(String subtitle , String autores, String publisher, String datepublisher){
        Descripcion = subtitle
                + "\n\nAutor: " + ExtraerAutor(autores)
                + "\n\nEditorial: " + publisher
                + "\n\nFecha de Publicacion: " + datepublisher;
        return Descripcion;
    }
}

