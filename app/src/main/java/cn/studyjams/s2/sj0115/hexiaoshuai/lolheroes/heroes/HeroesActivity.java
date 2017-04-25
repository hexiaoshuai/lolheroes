package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.heroes;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroesRepository;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local.HeroesLocalDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.ActivityUtils;

public class HeroesActivity extends AppCompatActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout drawerLayout;
    private HeroesPresenter heroesPresenter;
    private AppBarLayout appBarLayout;
    private MenuItem allHeroes;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setToolbarTitle(HeroFilterType.ALL);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            ViewGroup.LayoutParams params = navigationView.getLayoutParams();
            if (ActivityUtils.isScreenOrientationPortrait(getApplicationContext())) {
                params.width = getResources().getDisplayMetrics().widthPixels / 2;
            } else {
                params.width = getResources().getDisplayMetrics().widthPixels / 3;
            }
            navigationView.setLayoutParams(params);
            setupDrawerContent(navigationView);
            allHeroes = navigationView.getMenu().findItem(R.id.drawer_menu_all);
        }
        HeroesFragment mHeroesFragment =
                (HeroesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_layout);
        if (mHeroesFragment == null) {
            mHeroesFragment = HeroesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mHeroesFragment, R.id.content_frame_layout);
        }

        heroesPresenter = new HeroesPresenter(HeroesRepository.getInstance(HeroesLocalDataSource.getInstance(getApplicationContext())), mHeroesFragment);

        if (savedInstanceState != null) {
            HeroFilterType currentFiltering =
                    (HeroFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            if (allHeroes != null && currentFiltering != HeroFilterType.ALL) {
                allHeroes.setChecked(false);
            }
            setToolbarTitle(currentFiltering);
            heroesPresenter.setFiltering(currentFiltering);
        }
    }

    private void setToolbarTitle(HeroFilterType heroFilterType) {
        int strId = -1;
        if (toolbarTitle != null) {
            switch (heroFilterType) {
                case ALL:
                    strId = R.string.drawer_menu_all;
                    break;
                case FIGHTER:
                    strId = R.string.drawer_menu_fighter;
                    break;
                case MAGE:
                    strId = R.string.drawer_menu_mage;
                    break;
                case ASSASSIN:
                    strId = R.string.drawer_menu_assassin;
                    break;
                case TANK:
                    strId = R.string.drawer_menu_tank;
                    break;
                case MARKSMAN:
                    strId = R.string.drawer_menu_marksman;
                    break;
                case SUPPORT:
                    strId = R.string.drawer_menu_support;
                    break;
            }
            toolbarTitle.setText(getResources().getText(R.string.app_name) + " - "
                    + getResources().getText(strId));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToolBar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, heroesPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    private void showToolBar() {
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                heroesPresenter.setSearchText(newText);
                return false;
            }
        });
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_menu_all:
                                heroesPresenter.setFiltering(HeroFilterType.ALL);
                                break;
                            case R.id.drawer_menu_fighter:
                                heroesPresenter.setFiltering(HeroFilterType.FIGHTER);
                                break;
                            case R.id.drawer_menu_mage:
                                heroesPresenter.setFiltering(HeroFilterType.MAGE);
                                break;
                            case R.id.drawer_menu_assassin:
                                heroesPresenter.setFiltering(HeroFilterType.ASSASSIN);
                                break;
                            case R.id.drawer_menu_tank:
                                heroesPresenter.setFiltering(HeroFilterType.TANK);
                                break;
                            case R.id.drawer_menu_marksman:
                                heroesPresenter.setFiltering(HeroFilterType.MARKSMAN);
                                break;
                            case R.id.drawer_menu_support:
                                heroesPresenter.setFiltering(HeroFilterType.SUPPORT);
                                break;

                        }
                        setToolbarTitle(heroesPresenter.getFiltering());
                        if (allHeroes != null && allHeroes != menuItem) {
                            allHeroes.setChecked(false);
                        }
                        menuItem.setChecked(true);
                        showToolBar();
                        heroesPresenter.loadHeroes(false);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
