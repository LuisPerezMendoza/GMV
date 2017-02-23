package com.guma.desarrollo.gmv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by maryan.espinoza on 23/02/2017.
 */

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.PedidosViewHolder>{

    List<pedido> pedidos;

    public AdapterPedidos(List<pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        PedidosViewHolder holder = new PedidosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder holder, int position) {
        pedido pdd = pedidos.get(position);
        holder.name.setText(pdd.getIdClilente());
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public static class PedidosViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public PedidosViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name_ART);

        }
    }
}
