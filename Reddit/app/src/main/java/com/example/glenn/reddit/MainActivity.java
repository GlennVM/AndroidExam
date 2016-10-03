package com.example.glenn.reddit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.btnRefresh)
    Button btnRefresh;
    private String lastSubredditClicked = "frankocean";

    private ActionBarDrawerToggle toggle;
    private FragmentManager fragmentManager;
    private PostListFragment postListFragment;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.drawer_left)
    ListView drawerList;
    private String[] navigationDrawerSubRedditTitles;

    @OnClick(R.id.btnRefresh)
    public void refreshPosts(){
        postListFragment.loadSubreddit(lastSubredditClicked,true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Laad juiste view
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Haalt de toolbar op
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Voegt toolbar toe aan ActionBar
        setSupportActionBar(toolbar);


        //Haalt drawerLayout op
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Maakt nieuwe ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Haal navigationView op
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //setListener
        navigationView.setNavigationItemSelectedListener(this);

        //Haal fragmentManager op
        fragmentManager = getSupportFragmentManager();
        //Maak nieuw postListFragment
        postListFragment = new PostListFragment();
        //Voeg fragment toe aan scherm
        fragmentManager.beginTransaction().add(R.id.container, postListFragment, "list").commit();

        SharedPreferences prefs = getSharedPreferences("reddit",MODE_PRIVATE);
        String title = prefs.getString("lastVisitedSubreddit", "frankocean");
        // getSupportActionBar().setTitle(title);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates het menu, voegt menu toe aan de actionBar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handel clicks in het menu af
        //haalt id van aangeklikte actie op
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Handelt items selected af van drawer
        //Haalt id op van aangeklikte actie
        int id = item.getItemId();

        //vergelijkt id met id acties
        if (id == R.id.nav_main) {
            //Hierin wordt juiste actie uitgevoerd
        }

        //Haalt drawer op
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Sluit deze drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
