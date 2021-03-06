package com.andrea.com.popmovies.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrea.com.popmovies.Movie;
import com.andrea.com.popmovies.R;
import com.andrea.com.popmovies.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private Movie[] mData={};

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final clickHandler mClickHandler;

    /**
     * Creates a ForecastAdapter.
     *  @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     *
     *
     */
    public GridViewAdapter(clickHandler clickHandler) {

        this.mClickHandler = clickHandler;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface clickHandler{
        void onCLick (Movie movieSelected);
    }

    /**
     * This method is used to set the data on a GridViewAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new GridViewAdapter to display it.
     *
     * @param moviesData The new movies data to be displayed.
     */
    public void setData(Movie[] moviesData) {
        mData = moviesData;
        notifyDataSetChanged();
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context =viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutResources = R.layout.item_list;
        View view = inflater.inflate(layoutResources,viewGroup,false);

        return new ViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(NetworkUtilities.POSTER_PATH
                +NetworkUtilities.POSTER_SIZE+mData[position].getMurl());
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        if(mData == null){return 0;}
        return mData.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView posterHolder;

        ViewHolder(View itemView) {
            super(itemView);
            posterHolder = itemView.findViewById(R.id.iv_holder);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            Movie movieSelected = mData[adapterPosition];
            mClickHandler.onCLick(movieSelected);
        }

        void bind (String urlPoster){
            Picasso.get()
                    .load(urlPoster)
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .into(posterHolder);
        }
    }
}

