package com.desarrollo.laboratorio2unidad2.ASYNCTASK;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilidadesRed {

    private static final String LOG_TAG = UtilidadesRed.class.getSimpleName();
    private static final String URL_LIBRO = "https://www.googleapis.com/books/v1/volumes?";
    private static final String PARAM = "q";
    private static final String LIMIT= "maxResults";
    private static final String PRINT_TYPE = "printType";


    static String obtenerInformacionLibro(String cadenaConsulta) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String libroJSON = null;

        try {
            Uri construirURI = Uri.parse(URL_LIBRO).buildUpon()
                    .appendQueryParameter(PARAM, cadenaConsulta)
                    .appendQueryParameter(LIMIT, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL peticionURL = new URL(construirURI.toString());

            urlConnection = (HttpURLConnection) peticionURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String linea;

            while ((linea = reader.readLine()) != null) {
                builder.append(linea);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            libroJSON = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, libroJSON);

        return libroJSON;
    }
}
