package com.desarrollo.laboratorio2unidad2.FRAGMENTOS;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.laboratorio2unidad2.ADAPTADOR.RVHistorialAdapter;
import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsHistorial;
import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsLibro;
import com.google.android.material.snackbar.Snackbar;

import com.desarrollo.laboratorio2unidad2.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class HistorialFragment extends Fragment {

    RecyclerView RecyclerHistorial;
    String contenido;
    public ArrayList<ClsHistorial> HistorialLibros;
    String titulo;
    String descripcion;

    public HistorialFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_historial, container, false);

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String nombreFichero = "historialBusqueda.txt";

        try {
            //LEER DATOS
            FileInputStream stream = getContext().openFileInput(nombreFichero);
            int contador = 0;
            String lectura = "";
            while ((contador = stream.read()) != -1) {
                lectura = lectura + Character.toString((char) contador);
            }

            contenido = lectura;
            ExtraerLlenadoDeDatos();
            RecyclerHistorial = ((RecyclerView)getActivity().findViewById(R.id.rvfragmento));
            RecyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext()));

            RVHistorialAdapter adapter = new RVHistorialAdapter(HistorialLibros);
            RecyclerHistorial.setAdapter(adapter);

            ((Button)getActivity().findViewById(R.id.HistorialBorrar)).setVisibility(View.VISIBLE);

            Snackbar.make(getView(), "Fichero Cargado \n" + contenido, Snackbar.LENGTH_SHORT).show();

        } catch (Exception e) {
            ((Button)getActivity().findViewById(R.id.HistorialBorrar)).setVisibility(View.INVISIBLE);
            Snackbar.make(getView(), "No hay datos de su historial", Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        ((Button)getActivity().findViewById(R.id.HistorialBorrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    (getActivity()).deleteFile(nombreFichero);
                    Snackbar.make(getView(), "Historial Eliminado", Snackbar.LENGTH_SHORT).show();
                    ((Button)getActivity().findViewById(R.id.HistorialBorrar)).setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void ExtraerLlenadoDeDatos() {
        HistorialLibros = new ArrayList<>();
        String[] datos = contenido.split(";");
        for (int i = 0; i < datos.length; i++) {
            HistorialLibros.add(new ClsHistorial(datos[i], datos[i+1]));
            i = i + 1;
        }
    }
}