package id.keepfight.favoritemovie;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.keepfight.favoritemovie.databinding.MovieFavoriteListBinding;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.Holder> {

    private Cursor cursor;
    private static RvClickListener rvClickListener;
    private LayoutInflater mInflater;

    public interface RvClickListener {
        void onItemClick(View v, int position);
    }

    public MovieFavoriteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    void setOnItemClickListener(MovieFavoriteAdapter.RvClickListener rvClickListener) {
        MovieFavoriteAdapter.rvClickListener = rvClickListener;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        MovieFavoriteListBinding viewBinding;
         viewBinding = DataBindingUtil.inflate(mInflater, R.layout.movie_favorite_list, viewGroup, false);

        return new Holder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.movieFavoriteListBinding.setMovie(getMovieItem(i));
        holder.movieFavoriteListBinding.layoutListContainer.setOnClickListener(v -> rvClickListener.onItemClick(v, i));
    }

    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        else return 0;
    }
    void setMovies(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }
    public Movie getMovieItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new Movie(cursor);
    }


    public class Holder extends RecyclerView.ViewHolder {
        private final MovieFavoriteListBinding movieFavoriteListBinding;

        public Holder(@NonNull MovieFavoriteListBinding viewBinding) {
            super(viewBinding.getRoot());
            this.movieFavoriteListBinding = viewBinding;
        }
    }
}
