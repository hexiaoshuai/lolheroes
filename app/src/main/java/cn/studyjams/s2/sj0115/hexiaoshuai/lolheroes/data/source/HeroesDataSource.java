package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source;

import java.util.List;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Hero;

public interface HeroesDataSource {

    interface LoadHeroesCallback {

        void onHeroesLoaded(List<Hero> heroList);

        void onDataNotAvailable();
    }

    interface GetHeroCallback {

        void onTaskLoaded(Hero hero);

        void onDataNotAvailable();
    }

    void getHeroes(LoadHeroesCallback callback);

    void getHero(int key, GetHeroCallback callback);

    void refreshHeroes();
}
