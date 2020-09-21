package com.example.photoalbum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 pictureSelection;
    private ArrayList<Fragment> fragment_list;
    private ArrayList<Integer> imageId;
    private ArrayList<Integer> descriptionId;
    private ListView titleList;
    private Button backButton,frontButton;
    private FragmentStateAdapter adapter;
    private ArrayAdapter<String> titleAdapter;

    // Question: How can I remove the title in the actionbar so thet I can use the entire actionbar
    // ViewPager is deprecated as of 1st of April 2020 to replace ViewPager2.
    // Read here for more info https://developer.android.com/jetpack/androidx/releases/viewpager2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Code for ListView initialisation
        initListView();

        //Code for Fragment initialisation
        fragment_list = new ArrayList<Fragment>();
        initFragmentAdapter();
        fillDataToAdapter();
        initViewPager();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.image_scroller,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.back:
                if(pictureSelection.getCurrentItem()!=0){
                    pictureSelection.setCurrentItem(pictureSelection.getCurrentItem()-1);
                }
                break;
            case R.id.next:
                if(pictureSelection.getCurrentItem()!=pictureSelection.getScrollBarSize()){
                    pictureSelection.setCurrentItem(pictureSelection.getCurrentItem()+1);
                }
                break;
        }

        return true;
    }

    private void initListView(){
        titleList =(ListView) findViewById(R.id.title_list);
        titleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.picture_title_list));
        titleList.setAdapter(titleAdapter);
        titleList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        titleList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View choice,int pos, long id){
                pictureSelection.setCurrentItem(pos);
            }
        });
    }

    private void initViewPager(){
        pictureSelection = (ViewPager2)findViewById(R.id.pager);
        pictureSelection.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        pictureSelection.setAdapter(adapter);;
    }

    private void initFragmentAdapter(){
        adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if(position<fragment_list.size()) fragment_list.set(position, ImageDisplay.newInstance(imageId.get(position), descriptionId.get(position)));
                else fragment_list.add(ImageDisplay.newInstance(imageId.get(position), descriptionId.get(position)));
                return fragment_list.get(position);
            }

            @Override
            public int getItemCount() {
                return fragment_list.size();
            }
        };
    }
    /*
    Fragment 1: dog staring out
    Fragment 2: Mountain view
    Fragment 3: Pets
    Fragment 4: Friends
     */
    private void fillDataToAdapter(){//This solution uses hot coded images for now. Might modify to read from album later
        imageId = new ArrayList<Integer>();
        imageId.add(R.drawable.doggo);
        imageId.add(R.drawable.mountains);
        imageId.add(R.drawable.pets);
        imageId.add(R.drawable.friends);

        descriptionId = new ArrayList<Integer>();
        descriptionId.add(R.string.dog_picture_description);
        descriptionId.add(R.string.mountain_view_picture_description);
        descriptionId.add(R.string.cat_dog_picture_description);
        descriptionId.add(R.string.friend_picture_description);

        adapter.createFragment(0);
        adapter.createFragment(1);
        adapter.createFragment(2);
        adapter.createFragment(3);

    }
}