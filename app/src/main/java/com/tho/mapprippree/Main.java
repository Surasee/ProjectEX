package com.tho.mapprippree;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class Main extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;
    ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
    private int intPage =3; // Menu myPosition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        itemTitle = activityTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1fb08d")));
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        items.add(new DrawerItem("Profile Menu")); // add a header
        items.add(new DrawerItem("Edit Profile", R.drawable.edit_user,false));
        items.add(new DrawerItem("Map Menu"));
        items.add(new DrawerItem("My Position  ", R.drawable.my_posiotion,true));
        items.add(new DrawerItem("Track Place", R.drawable.track_place,false));
        items.add(new DrawerItem("List Place", R.drawable.list_place, false));
        items.add(new DrawerItem("Map Style", R.drawable.mapstyle, false));
        items.add(new DrawerItem("Other Menu"));
        items.add(new DrawerItem("About", R.drawable.about, false));
        drawerList.setAdapter(new DrawerListAdapter(this, R.layout.drawer_list_item, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
               // getSupportActionBar().setTitle(itemTitle);
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
              //  getSupportActionBar().setTitle(activityTitle);
              //  invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        if (savedInstanceState == null) {
            if (items.get(0).getTitle() != null) {
                SelectItem(intPage);
            } else {
                SelectItem(0);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (  items.get(position).isChecked == false){
                    for(int j = 0; j < items.size();j++){
                        items.get(j).setChecked(false);
                    }
                    if (items.get(position).getTitle() == null) {
                        SelectItem(position);
                        items.get(position).setChecked(true);
                    }

                }else{
                    drawerLayout.closeDrawer(drawerList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 1:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());
                break;
            case 3:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());
                break;
            case 4:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());
                break;
            case 5:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());
                break;
            case 6:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());
                break;
            case 8:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());
                break;

            default:
                break;
        }
        fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        drawerList.setItemChecked(possition, true);
        setTitle(items.get(possition).getItemName());
        drawerLayout.closeDrawer(drawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getSupportActionBar().setTitle(itemTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}