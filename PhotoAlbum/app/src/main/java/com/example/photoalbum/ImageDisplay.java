package com.example.photoalbum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageDisplay extends Fragment {

    public ImageDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ImageViewer.
     */
    public static ImageDisplay newInstance(int drawable, int contentDescriptor) {
        ImageDisplay fragment = new ImageDisplay();
        Bundle args = new Bundle();
        args.putInt("image", drawable);
        args.putInt("description",contentDescriptor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ImageView image = (ImageView) getView().findViewById(R.id.image_display);
        TextView description = (TextView) getView().findViewById(R.id.description_text);
        image.setImageResource((int) getArguments().get("image"));
        image.setContentDescription(getResources().getString((int) getArguments().get("description")));
        description.setText(getResources().getString((int) getArguments().get("description")));
        System.out.println(getResources().getString((int) getArguments().get("description")));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (ViewGroup)inflater.inflate(R.layout.fragment_image_viewer, container, false);
    }

}