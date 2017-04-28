package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroDetailDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.AssetsUtil;

public class HeroDetailLocalDataSource implements HeroDetailDataSource {
    private static HeroDetailLocalDataSource INSTANCE;

    private Context context;

    private HeroDetailLocalDataSource(Context context) {
        this.context = context;
    }

    public static HeroDetailLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new HeroDetailLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getHeroDetail(String heroId, LoadHeroDetailCallback callback) {
        String json = AssetsUtil.getJson(context, "json/hero_" + heroId + ".json");
        HeroDetail heroDetail = JSON.parseObject(json, HeroDetail.class);
        if (heroDetail == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onHeroDetailLoaded(heroDetail);
        }

    }

    @Override
    public void refreshHeroDetail() {

    }
}
