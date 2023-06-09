package com.kimyayd.stage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kimyayd.stage.create.CreateActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryFragment";

    private static final int NUM_GRID_COLUMNS = 3;


    private GridView gridView;
    private ImageView galleryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;

    private ArrayList<String> directories;
    private String mAppend = "file:/";
    private String mSelectedImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

        galleryImage = view.findViewById(R.id.galleryImageView);
        gridView =  view.findViewById(R.id.gridView);
        directorySpinner = view.findViewById(R.id.spinnerDirectory);
        mProgressBar =  view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();
        Log.d(TAG, "onCreateView: started.");

        ImageView shareClose =  view.findViewById(R.id.ivCloseShare);
        shareClose.setOnClickListener(v -> {
            Log.d(TAG, "onClick: closing the gallery fragment.");
            getActivity().finish();
        });


        TextView nextScreen = view.findViewById(R.id.tvNext);
        nextScreen.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating to the final share screen.");
            if(isRootTask()){
                Intent intent = new Intent(getActivity(), CreateActivity.class);
                intent.putExtra("selected_image", mSelectedImage);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                intent.putExtra("selected_image", mSelectedImage);
                intent.putExtra("return_to_fragment", "Edit Profile");
                startActivity(intent);
                getActivity().finish();
            }

        });

        init();

        return view;
    }
    private boolean isRootTask(){
        if(((AddActivity)getActivity()).getTask() == 0){
            return true;
        }
        else{
            return false;
        }
    }


    private void init() {
        FilePaths filePaths = new FilePaths();

        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null) {
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }
        directories.add(filePaths.CAMERA);

        ArrayList<String> directoryNames = new ArrayList<>();
        for (int i = 0; i < directories.size(); i++) {
            Log.d(TAG, "init: directory: " + directories.get(i));
            int index = directories.get(i).lastIndexOf("/");
            String string = directories.get(i).substring(index);
            directoryNames.add(string);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected: " + directories.get(position));
                setupGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setupGridView(String selectedDirectory) {
        Log.d(TAG, "setupGridView: directory chosen: " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);
        if(imgURLs.size()==0){

        }
        if(imgURLs.size()!=0) {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

            int gridWidth = getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth / NUM_GRID_COLUMNS;
            gridView.setColumnWidth(imageWidth);

            GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
            gridView.setAdapter(adapter);

            try {
                if (imgURLs.size() != 0) {
                    ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

                    setImage(imgURLs.get(0), galleryImage, mAppend);
                    mSelectedImage = imgURLs.get(0);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "setupGridView: ArrayIndexOutOfBoundsException: " + e.getMessage());
            }

            gridView.setOnItemClickListener((parent, view, position, id) -> {
                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

                Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(position));

                setImage(imgURLs.get(position), galleryImage, mAppend);
                mSelectedImage = imgURLs.get(position);
            });
        }

    }


    private void setImage(String imgURL, ImageView image, String append) {
        Log.d(TAG, "setImage: setting image");
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}


