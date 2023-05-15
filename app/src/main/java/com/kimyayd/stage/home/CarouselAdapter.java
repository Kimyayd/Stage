package com.kimyayd.stage.home;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kimyayd.stage.R;
import com.kimyayd.stage.models.Event;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    private List<Event> items;


    public CarouselAdapter(List<Event> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_carrousel, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event item = items.get(position);
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.place.setText(item.getPlace());
        Context context =holder.date.getContext(); ;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(item.getPhoto(),
                holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,date,place,join;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            place = itemView.findViewById(R.id.place);
            join = itemView.findViewById(R.id.join);
            image = itemView.findViewById(R.id.image);
        }
    }
}
