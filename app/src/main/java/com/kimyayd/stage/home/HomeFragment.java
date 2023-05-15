package com.kimyayd.stage.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kimyayd.stage.R;
import com.kimyayd.stage.models.Event;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private List<Event> mEvents;
    private EventAdapter adapter;
    private Carousel carousel;
    private ListView listView;
    private ImageView search;
    private ViewPager2 viewPager;
    private CarouselAdapter adapter2;
    private List<String> items;
    private Timer timer;
    private int currentPage = 0;
    private final long DELAY_MS = 2000; // Delay in milliseconds before swiping to the next item
    private final long PERIOD_MS = 5000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        search=view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(),SearchActivity.class));
            }
        });
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("events");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Event event = new Event();
                    Map<String, Object> objectMap = (HashMap<String, Object>) ds.getValue();

                    event.setId(objectMap.get("id").toString());
                    event.setName(objectMap.get("name").toString());
                    event.setDescription(objectMap.get("description").toString());
                    event.setDate(objectMap.get("date").toString());
                    event.setPlace(objectMap.get("place").toString());
                    event.setPhoto(objectMap.get("profile_photo").toString());
                    event.setOrganisation_id(objectMap.get("organisation_id").toString());

                    mEvents.add(event);
                    if (mEvents != null) {
                        try {
                            Collections.sort(mEvents, new Comparator<Event>() {
                                public int compare(Event o1, Event o2) {
                                    return o2.getDate().compareTo(o1.getDate());
                                }
                            });
                            adapter = new EventAdapter(getContext(),R.layout.event_item,mEvents);
                            adapter2 = new CarouselAdapter(mEvents);
                            listView = view.findViewById(R.id.eventlists);
                            viewPager=view.findViewById(R.id.viewPager);
                            viewPager.setAdapter(adapter2);
                            listView.setAdapter(adapter);
                            startAutoSwiping();

                        } catch (IndexOutOfBoundsException e) {
                            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException:" + e.getMessage());
                        } catch (NullPointerException e) {
                            Log.e(TAG, "displayPhotos: NullPointerException:" + e.getMessage());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
    private void startAutoSwiping() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == items.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancel the timer when the activity is destroyed to avoid memory leaks
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
