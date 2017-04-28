package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local.HeroDetailLocalDataSource;

public class HeroDetailRepository implements HeroDetailDataSource {
    private static HeroDetailRepository INSTANCE = null;

    private final HeroDetailDataSource heroDetailDataSource;
    private HeroDetail cachedHeroDetail;
    private boolean cacheIsDirty = false;

    private HeroDetailRepository(HeroDetailDataSource heroDetailDataSource) {
        this.heroDetailDataSource = heroDetailDataSource;
    }

    public static HeroDetailRepository getInstance(HeroDetailLocalDataSource heroDetailLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new HeroDetailRepository(heroDetailLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getHeroDetail(String heroId, final LoadHeroDetailCallback callback) {
        if (cachedHeroDetail != null && !cacheIsDirty) {
            callback.onHeroDetailLoaded(cachedHeroDetail);
            return;
        }
        heroDetailDataSource.getHeroDetail(heroId, new LoadHeroDetailCallback() {
            @Override
            public void onHeroDetailLoaded(HeroDetail heroDetail) {
                refreshCache(heroDetail);
                callback.onHeroDetailLoaded(cachedHeroDetail);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void refreshHeroDetail() {
        cacheIsDirty = true;
    }

    private void refreshCache(HeroDetail heroDetail) {
        cachedHeroDetail = heroDetail;
        cacheIsDirty = false;
    }
}
