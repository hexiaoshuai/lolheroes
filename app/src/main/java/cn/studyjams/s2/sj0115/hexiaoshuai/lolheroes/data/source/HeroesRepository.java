package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Hero;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local.HeroesLocalDataSource;

public class HeroesRepository implements HeroesDataSource {
    private static HeroesRepository INSTANCE = null;

    private final HeroesDataSource heroesLocalDataSource;
    private Map<String, Hero> cachedHeroes;
    private boolean cacheIsDirty = false;

    private HeroesRepository(HeroesDataSource heroesLocalDataSource) {
        this.heroesLocalDataSource = heroesLocalDataSource;
    }

    public static HeroesRepository getInstance(HeroesLocalDataSource heroesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new HeroesRepository(heroesLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getHeroes(final LoadHeroesCallback callback) {
        if (cachedHeroes != null && !cacheIsDirty) {
            callback.onHeroesLoaded(new ArrayList<>(cachedHeroes.values()));
            return;
        }
        heroesLocalDataSource.getHeroes(new LoadHeroesCallback() {
            @Override
            public void onHeroesLoaded(List<Hero> heroList) {
                refreshCache(heroList);
                callback.onHeroesLoaded(new ArrayList<>(cachedHeroes.values()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getHero(int key, GetHeroCallback callback) {
        Hero cachedHero = getHeroWithKey(String.valueOf(key));
        if (cachedHero != null) {
            callback.onTaskLoaded(cachedHero);
            return;
        }

        heroesLocalDataSource.getHero(key, new GetHeroCallback() {
            @Override
            public void onTaskLoaded(Hero hero) {
                if (cachedHeroes == null) {
                    cachedHeroes = new LinkedHashMap<>();
                }
                cachedHeroes.put(String.valueOf(hero.getKey()), hero);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void refreshHeroes() {
        cacheIsDirty = true;
    }

    private void refreshCache(List<Hero> heroList) {
        if (cachedHeroes == null) {
            cachedHeroes = new LinkedHashMap<>();
        }
        cachedHeroes.clear();
        for (Hero hero : heroList) {
            cachedHeroes.put(String.valueOf(hero.getKey()), hero);
        }
        cacheIsDirty = false;
    }

    private Hero getHeroWithKey(String key) {
        if (cachedHeroes == null || cachedHeroes.isEmpty()) {
            return null;
        } else {
            return cachedHeroes.get(key);
        }
    }
}
