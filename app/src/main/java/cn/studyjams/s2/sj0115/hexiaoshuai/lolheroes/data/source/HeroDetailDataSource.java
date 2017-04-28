package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;

public interface HeroDetailDataSource {

    interface LoadHeroDetailCallback {

        void onHeroDetailLoaded(HeroDetail heroDetail);

        void onDataNotAvailable();
    }

    void getHeroDetail(String heroId, LoadHeroDetailCallback callback);

    void refreshHeroDetail();
}
