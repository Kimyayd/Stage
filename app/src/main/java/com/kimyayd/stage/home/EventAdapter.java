package com.kimyayd.stage.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kimyayd.stage.R;
import com.kimyayd.stage.models.Event;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventAdapter";
    private LayoutInflater mInflater;
    private List<Event> mEvents = null;
    private int layoutResource;
    private Context mContext;

    public EventAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Event> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mEvents = objects;
    }

    private static class ViewHolder{
        TextView name, place,date;
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.place = (TextView) convertView.findViewById(R.id.place);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.image = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position).getName());
        holder.place.setText(getItem(position).getPlace());
        holder.date.setText(getItem(position).getDate());
        Context context = getContext();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(getItem(position).getPhoto(),
                holder.image);

        return convertView;
    }

}
