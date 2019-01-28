package qibee.id.sub1dicoding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import qibee.id.sub1dicoding.databinding.MovieListBinding;
import qibee.id.sub1dicoding.model.Movie;


public class MovieUpcomingAdapter extends RecyclerView.Adapter<MovieUpcomingAdapter.ViewHolder> implements Constants{

    private LayoutInflater mInflater;
    private List<Movie> movieList;
    private static RvClickListener rvClickListener;

    public MovieUpcomingAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MovieListBinding listBinding;

        ViewHolder(MovieListBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        MovieListBinding movieListBinding = DataBindingUtil.inflate(mInflater, R.layout.movie_list,
                parent, false);

        return new ViewHolder(movieListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (movieList != null) {
            Movie current = movieList.get(position);
            holder.listBinding.setMovie(current);
            holder.listBinding.layoutListContainer.setOnClickListener(v -> {
                if (rvClickListener != null) {
                    rvClickListener.onItemClick(v, position);
                }
            });
        }

    }


    void setMovies(List<Movie> movies) {
        if (movieList!=null) movieList.clear();
        movieList = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (movieList != null)
            return movieList.size();
        else return 0;
    }


    Movie getResultAtPosition(int position) {
        return movieList.get(position);
    }

    void setOnItemClickListener(RvClickListener rvClickListener) {
        MovieUpcomingAdapter.rvClickListener = rvClickListener;
    }

    public interface RvClickListener {
        void onItemClick(View v, int position);
//        void onItemClick(Movie movie);
    }


}
