package com.desarrollo.laboratorio2unidad2.ADAPTADOR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsHistorial;
import com.desarrollo.laboratorio2unidad2.ENTIDAD.ClsLibro;
import com.desarrollo.laboratorio2unidad2.FRAGMENTOS.HistorialFragment;
import com.desarrollo.laboratorio2unidad2.R;

import java.util.ArrayList;

public class RVHistorialAdapter extends RecyclerView.Adapter<RVHistorialAdapter.HistorialViewHolder> implements View.OnClickListener {

    ArrayList<ClsHistorial> HistorialLibro;
    private View.OnClickListener listener;
    Context context;

    public RVHistorialAdapter(ArrayList<ClsHistorial> HistorialLibro) {
        this.HistorialLibro = HistorialLibro;
    }

    public class HistorialViewHolder extends RecyclerView.ViewHolder {

        TextView edtContenido, edtTitulo;

        public HistorialViewHolder(View itemView) {
            super(itemView);
            edtTitulo = (TextView) itemView.findViewById(R.id.HistorialNombreLibro);
            edtContenido = (TextView) itemView.findViewById(R.id.HistorialDescripcionLibro);
        }
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @Override
    public RVHistorialAdapter.HistorialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historial, null, false);
        view.setOnClickListener(this);
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialViewHolder holder, int position) {
        holder.edtTitulo.setText(HistorialLibro.get(position).getTitulo());
        holder.edtContenido.setText(HistorialLibro.get(position).getContenido());

    }

    @Override
    public int getItemCount() {
        return HistorialLibro.size();
    }
}
