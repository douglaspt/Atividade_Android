package br.com.pch.livraria.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.pch.livraria.R;
import br.com.pch.livraria.listener.OnItemClickListener;
import br.com.pch.livraria.model.Livro;

/**
 * Created by douglas.teixeira on 15/03/2017.
 */

public class AndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Livro> data = Collections.emptyList();

    private OnItemClickListener clickListener;


    public AndroidAdapter(Context context, List<Livro> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_livro, parent, false);
        AndroidItemHolder holder = new AndroidItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AndroidItemHolder myHolder = (AndroidItemHolder) holder;
        Livro current = data.get(position);

        myHolder.tvId.setText(current.getId());
        myHolder.tvTitulo.setText(current.getTitulo());
        myHolder.tvAutor.setText(context.getString(R.string.label_autor)  + current.getAutor());
        myHolder.tvCategoria.setText(context.getString(R.string.label_categoria) + current.getCategoria().getNome());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Livro getItem(int position){
        return data.get(position);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    private class AndroidItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvId;
        TextView tvTitulo;
        TextView tvAutor;
        TextView tvCategoria;

        private AndroidItemHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvAutor = (TextView) itemView.findViewById(R.id.tvAutor);
            tvCategoria = (TextView) itemView.findViewById(R.id.tvCategoria);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }

    }
}
