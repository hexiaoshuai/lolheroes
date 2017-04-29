package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroDetailRepository;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local.HeroDetailLocalDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.ActivityUtils;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.AssetsUtil;


public class HeroDetailActivity extends AppCompatActivity {
    private static final String SKINS_IMG_URI_BIG = "http://ossweb-img.qq.com/images/lol/web201310/skin/big";

    private HeroDetailPresenter heroDetailPresenter;
    private HeroDetailFragment heroDetailFragment;
    private ImageView skinImageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton floatingActionButton;

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
        }
        initAppLayout();
        heroDetailFragment = (HeroDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_layout);
        if (heroDetailFragment == null) {
            heroDetailFragment = HeroDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), heroDetailFragment, R.id.content_frame_layout);
        }
        heroDetailPresenter = new HeroDetailPresenter(HeroDetailRepository.getInstance(HeroDetailLocalDataSource.getInstance(getApplicationContext())), heroDetailFragment);
    }

    private void initAppLayout() {
        skinImageView = (ImageView) findViewById(R.id.image_view_skin);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void updateCollapsingToolbar(HeroDetail heroDetail) {
        setSkin(heroDetail.getSkins().get(0).getId());
        collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.CENTER);
        collapsingToolbar.setTitle(heroDetail.getName());
    }

    private void setSkin(int SkinId) {
        setSkinImageView(SkinId, skinImageView);
        //Bitmap bitmap = ImageUtils.drawCircleView(AssetsUtil.getImage(HeroDetailActivity.this, "skin/small" + SkinId + ".jpg"));
        //floatingActionButton.setImageBitmap(bitmap);
        //floatingActionButton.setSize(FloatingActionButton.SIZE_MINI);
    }

    private void setSkinImageView(int skinId, ImageView skinImageView) {
        Bitmap skinBitmap = AssetsUtil.getImage(getApplicationContext(), "skin/big" + skinId + ".jpg");
        if (skinBitmap == null) {
            Picasso.with(getApplicationContext()).load(SKINS_IMG_URI_BIG +
                    skinId + ".jpg").placeholder(android.R.color.black).into(skinImageView);
        } else {
            skinImageView.setImageBitmap(skinBitmap);
        }
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
