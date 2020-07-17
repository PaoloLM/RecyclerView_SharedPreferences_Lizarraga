package com.desarrollo.laboratorio2unidad2.ADAPTADOR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.laboratorio2unidad2.ACTIVIDADES.MainActivity;
import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsLibro;
import com.desarrollo.laboratorio2unidad2.R;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.LibroViewHolder> implements View.OnClickListener {

    ArrayList<ClsLibro> ListaLibro;
    private View.OnClickListener listener;
    Context context;
    String titulolibro;
    String contenidolibro;

    public RVAdapter(ArrayList<ClsLibro> listaLibro, Context context) {
        this.ListaLibro = listaLibro;
        this.context = context;
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {

        TextView edtTitulo, edtDescripcion, edtDescripcionLarga;
        ImageView imgfoto;

        public LibroViewHolder(View itemView) {
            super(itemView);
            edtTitulo = (TextView) itemView.findViewById(R.id.tituloLibro);
            edtDescripcion = (TextView) itemView.findViewById(R.id.descripcionLibro);
            edtDescripcionLarga = (TextView) itemView.findViewById(R.id.descripcionLibroLarga);
            imgfoto = (ImageView) itemView.findViewById(R.id.fotolibro);
        }
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public RVAdapter.LibroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.libro, null, false);
        view.setOnClickListener(this);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter.LibroViewHolder holder, int position) {
        holder.edtTitulo.setText(ListaLibro.get(position).getTitulo());
        holder.edtDescripcion.setText(ListaLibro.get(position).getDescripcion());
        holder.edtDescripcionLarga.setText(ListaLibro.get(position).getDescripcionlarga());
        holder.imgfoto.setImageResource(ListaLibro.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return ListaLibro.size();
    }
}
