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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroDetailRepository;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local.HeroDetailLocalDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.ActivityUtils;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.AssetsUtil;


public class HeroDetailActivity extends AppCompatActivity {
    private static final String SKINS_IMG_URI_BIG = "http://ossweb-img.qq.com/images/lol/web201310/skin/big";

    private ImageView skinImageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView text_view_name, text_view_tags, text_view_attack,
            text_view_magic, text_view_defense, text_view_difficulty;
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
        HeroDetailFragment heroDetailFragment = (HeroDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_layout);
        if (heroDetailFragment == null) {
            heroDetailFragment = HeroDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), heroDetailFragment, R.id.content_frame_layout);
        }
        new HeroDetailPresenter(HeroDetailRepository.getInstance(HeroDetailLocalDataSource.getInstance(getApplicationContext())), heroDetailFragment);
    }

    private void initAppLayout() {
        skinImageView = (ImageView) findViewById(R.id.image_view_skin);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        text_view_name = (TextView) findViewById(R.id.text_view_name);
        text_view_tags = (TextView) findViewById(R.id.text_view_tags);
        text_view_attack = (TextView) findViewById(R.id.text_view_attack);
        text_view_magic = (TextView) findViewById(R.id.text_view_magic);
        text_view_defense = (TextView) findViewById(R.id.text_view_defense);
        text_view_difficulty = (TextView) findViewById(R.id.text_view_difficulty);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void updateCollapsingToolbar(HeroDetail heroDetail) {
        setSkin(heroDetail, 0);
        collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
        collapsingToolbar.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.CENTER);
        text_view_name.setText(heroDetail.getTitle());
        text_view_tags.setText(tagToString(heroDetail.getTags()));
        text_view_attack.setText(getResources().getText(R.string.attack) + ": " + heroDetail.getInfo().getAttack());
        text_view_magic.setText(getResources().getText(R.string.magic) + ": " + heroDetail.getInfo().getMagic());
        text_view_defense.setText(getResources().getText(R.string.defense) + ": " + heroDetail.getInfo().getDefense());
        text_view_difficulty.setText(getResources().getText(R.string.difficulty) + ": " + heroDetail.getInfo().getDifficulty());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void showHeroSkinListDialog(){

    }
    private String tagToString(List<String> tags) {
        String string = "";
        for (String tag : tags) {
            switch (tag) {
                case "Fighter":
                    string += getResources().getString(R.string.drawer_menu_fighter) + " ";
                    break;
                case "Mage":
                    string += getResources().getString(R.string.drawer_menu_mage) + " ";
                    break;
                case "Assassin":
                    string += getResources().getString(R.string.drawer_menu_assassin) + " ";
                    break;
                case "Tank":
                    string += getResources().getString(R.string.drawer_menu_tank) + " ";
                    break;
                case "Marksman":
                    string += getResources().getString(R.string.drawer_menu_marksman) + " ";
                    break;
                case "Support":
                    string += getResources().getString(R.string.drawer_menu_support) + " ";
                    break;
            }
        }
        return string;
    }

    private void setSkin(HeroDetail heroDetail, int position) {
        setSkinImageView(heroDetail.getSkins().get(position).getId(), skinImageView);
        if (position == 0) {
            collapsingToolbar.setTitle(heroDetail.getName());
        }else {
            collapsingToolbar.setTitle(heroDetail.getSkins().get(position).getName());
        }
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
