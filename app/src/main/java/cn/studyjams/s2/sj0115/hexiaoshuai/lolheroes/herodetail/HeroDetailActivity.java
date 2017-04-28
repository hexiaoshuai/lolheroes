package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroDetailRepository;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local.HeroDetailLocalDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.ActivityUtils;


public class HeroDetailActivity extends AppCompatActivity {

    private HeroDetailPresenter heroDetailPresenter;
    private HeroDetailFragment heroDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        heroDetailFragment = (HeroDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_layout);
        if (heroDetailFragment == null) {
            heroDetailFragment = HeroDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), heroDetailFragment, R.id.content_frame_layout);
        }
        heroDetailPresenter = new HeroDetailPresenter(HeroDetailRepository.getInstance(HeroDetailLocalDataSource.getInstance(getApplicationContext())), heroDetailFragment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
