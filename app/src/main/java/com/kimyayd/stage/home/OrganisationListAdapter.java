package com.kimyayd.stage.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kimyayd.stage.R;
import com.kimyayd.stage.models.Organisation;
import com.kimyayd.stage.utils.UniversalImageLoader;

import java.security.AccessControlContext;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrganisationListAdapter extends ArrayAdapter<Organisation> {

    private static final String TAG = "UserListAdapter";
    private LayoutInflater mInflater;
    private List<Organisation> mUsers = null;
    private int layoutResource;
    private Context mContext;

    public OrganisationListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Organisation> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mUsers = objects;
    }


    private static class ViewHolder {
        TextView name, adress;
        CircleImageView profileImage;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.adress = (TextView) convertView.findViewById(R.id.adress);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.profile_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.name.setText(getItem(position).getFullname());
        holder.adress.setText(getItem(position).getAdress());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Organisations")
                .orderByChild("user_id")
                .equalTo(getItem(position).getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    if (singleSnapshot.getValue(Organisation.class).getProfile_photo().equals("default")) {
                        holder.profileImage.setImageResource(R.drawable.ic_organisation);
                    } else {
                        UniversalImageLoader.setImage(singleSnapshot.getValue(Organisation.class).getProfile_photo(), holder.profileImage, null, "");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return convertView;
    }
}