package co.infinum.rxpokemon.ui.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.infinum.mjolnirrecyclerview.MjolnirRecyclerAdapter;
import co.infinum.mjolnirrecyclerview.MjolnirViewHolder;
import co.infinum.rxpokemon.R;
import co.infinum.rxpokemon.data.model.Pokemon;

public class PokemonListAdapter extends MjolnirRecyclerAdapter<Pokemon> {

    private ClickListener<Pokemon> clickListener;


    public PokemonListAdapter(Context context, Collection<Pokemon> list, ClickListener<Pokemon> clickListener) {
        super(context, list);
        this.clickListener = clickListener;
    }

    @Override
    protected MjolnirViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_pokemon, parent, false));
    }

    protected class ViewHolder extends MjolnirViewHolder<Pokemon> {

        @BindView(R.id.tv_pokemon_name)
        protected TextView tvPokemonName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (clickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(get(getAdapterPosition()));
                    }
                });
            }

        }

        @Override
        protected void bind(Pokemon item, int position, List<Object> payloads) {
            tvPokemonName.setText(item.getName());
        }
    }

}
