package com.desarrollo.laboratorio2unidad2.ACTIVIDADES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.laboratorio2unidad2.ADAPTADOR.RVAdapter;
import com.desarrollo.laboratorio2unidad2.ADAPTADOR.RVHistorialAdapter;
import com.desarrollo.laboratorio2unidad2.ASYNCTASK.ATConseguirLibro;
import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsHistorial;
import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsLibro;
import com.desarrollo.laboratorio2unidad2.FRAGMENTOS.HistorialFragment;
import com.desarrollo.laboratorio2unidad2.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.coloromatic.ColorOMaticDialog;
import es.dmoral.coloromatic.IndicatorMode;
import es.dmoral.coloromatic.OnColorSelectedListener;
import es.dmoral.coloromatic.colormode.ColorMode;

public class MainActivity extends AppCompatActivity {

    String nombreFichero = "historialBusqueda.txt";
    ArrayList<ClsHistorial> ListaHistorialLibros = new ArrayList<>();
    String contenido;

    public static String HistorialDescripcion = "";
    public static String HistorialTitulo = "";
    public static int verificador = 1;

    public String titulolibro = "";
    public String descripcionlibro = "";
    public String descripcionlibrolarga = "";

    private EditText mImputLibro;
    public ArrayList<ClsLibro> Libros;
    private TextView mTextoTitulo;
    private TextView mTextoDescripcion;
    private TextView mTextoDescripcionLarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImputLibro = (EditText) findViewById(R.id.ingresoLibro);
        mTextoTitulo = (TextView) findViewById(R.id.titulo);
        mTextoDescripcion = (TextView) findViewById(R.id.autorLibro);
        mTextoDescripcionLarga = (TextView) findViewById(R.id.deslibro);

        Entrada1PrimeraVez();

        VerificarExistenciaFichero();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Entrada1PrimeraVez();
        FrameLayout frameFragmento = findViewById(R.id.fragment_container);
        frameFragmento.setVisibility(View.INVISIBLE);

        ConstraintLayout contenedorPrincipal = findViewById(R.id.contenedorprincipal);
        contenedorPrincipal.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Entrada1PrimeraVez();
        SharedPreferences sharedPreferences =
                this.getPreferences(Context.MODE_PRIVATE);

        int color = sharedPreferences.getInt(getString(R.string.sp_color_bar), 0);

        if (color != 0) {
            cambiarColorBarra(color, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_temas) {
            CargarTemas();
            return true;
        } else if (id == R.id.action_historial) {
            CargarHistorial();
        }

        return super.onOptionsItemSelected(item);
    }

    private void CargarTemas() {
        new ColorOMaticDialog.Builder()
                .initialColor(Color.BLACK)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        SharedPreferences sharedPreferences =
                                MainActivity.this.getPreferences(Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.sp_color_bar), color);
                        editor.commit();

                        cambiarColorBarra(color, MainActivity.this);
                    }
                })
                .showColorIndicator(true)
                .create()
                .show(getSupportFragmentManager(), "ColorOMaticDialog");
    }

    private void CargarHistorial() {
        //CARGAR FRAGMENTO
        ConstraintLayout contenedorPrincipal = findViewById(R.id.contenedorprincipal);
        contenedorPrincipal.setVisibility(View.INVISIBLE);

        FrameLayout frameFragmento = findViewById(R.id.fragment_container);
        frameFragmento.setVisibility(View.VISIBLE);

        HistorialFragment historialFragment = new HistorialFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, historialFragment)
                .addToBackStack(null)
                .commit();
    }

    private void cambiarColorBarra(int color, Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams
                .FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setStatusBarColor(color);
        window.setNavigationBarColor(color);
    }


    public void MTDbuscarLibro(View view) {
        mImputLibro = (EditText) findViewById(R.id.ingresoLibro);
        String cadenaBusqueda = mImputLibro.getText().toString();

        if (!cadenaBusqueda.equals("")) {
            new ATConseguirLibro(mTextoTitulo, mTextoDescripcion, mTextoDescripcionLarga, this).execute(cadenaBusqueda);
            if (verificador == 1) {
                Button verresultado = findViewById(R.id.botonVerResultados);
                verresultado.setVisibility(View.VISIBLE);
                verificador = 0;
            }
        } else {
            Toast.makeText(getApplicationContext(), "No hay datos de entrada", Toast.LENGTH_SHORT).show();
        }
    }

    public void MTDVerResultados(View view) {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);
        initializaData();
        RVAdapter rvAdapter = new RVAdapter(Libros, this);
        rv.setAdapter(rvAdapter);
        Button verresultado = findViewById(R.id.botonVerResultados);
        verresultado.setVisibility(View.INVISIBLE);
    }

    private void initializaData() {
        Libros = new ArrayList<>();
        titulolibro = mTextoTitulo.getText().toString();
        descripcionlibro = mTextoDescripcion.getText().toString();
        descripcionlibrolarga = mTextoDescripcionLarga.getText().toString();

        if (!titulolibro.equals("")) {
            Libros.add(new ClsLibro(titulolibro, descripcionlibro, descripcionlibrolarga, R.drawable.ic_librofondo));
            crearLista(HistorialTitulo, HistorialDescripcion);
        } else {
            Toast.makeText(getApplicationContext(), "No hay datos a guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void crearLista(String titulolibro, String descripcionlibrolarga) {
        ListaHistorialLibros.add(new ClsHistorial(titulolibro, descripcionlibrolarga));
        crearFichero();
    }

    private void crearFichero() {
        FileOutputStream stream;

        try {
            stream = openFileOutput(nombreFichero, Context.MODE_PRIVATE);

            for (int i = 0; i < ListaHistorialLibros.size(); i++) {
                String datosfichero = ListaHistorialLibros.get(i).getTitulo() + ";" + ListaHistorialLibros.get(i).getContenido() + ";";
                stream.write(datosfichero.getBytes());
            }
            stream.close();
            Toast.makeText(getApplicationContext(), "Se guardo exitosamente", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void VerificarExistenciaFichero() {
        try {
            //LEER DATOS
            FileInputStream stream = openFileInput(nombreFichero);
            int contador = 0;
            String lectura = "";
            while ((contador = stream.read()) != -1) {
                lectura = lectura + Character.toString((char) contador);
            }

            contenido = lectura;
            ExtraerLlenadoDeDatos();

            Toast.makeText(getApplicationContext(), "Se cargo un fichero pasado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ExtraerLlenadoDeDatos() {
        String[] datos = contenido.split(";");
        for (int i = 0; i < datos.length; i++) {
            ListaHistorialLibros.add(new ClsHistorial(datos[i], datos[i+1]));
            i = i + 1;
        }
    }

    private void Entrada1PrimeraVez() {
        Button buscar = findViewById(R.id.botonBuscar);
        buscar.setVisibility(View.VISIBLE);

        Button verresultado = findViewById(R.id.botonVerResultados);
        verresultado.setVisibility(View.INVISIBLE);
    }
}