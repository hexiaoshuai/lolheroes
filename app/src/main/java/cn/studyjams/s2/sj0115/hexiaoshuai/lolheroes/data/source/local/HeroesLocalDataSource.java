package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.local;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Hero;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroesDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.AssetsUtil;

public class HeroesLocalDataSource implements HeroesDataSource {
    private static HeroesLocalDataSource INSTANCE;

    private List<Hero> heroList;
    private String json;

    private static final String HEROES_JSON_FILE = "json/heroes.json";

    private HeroesLocalDataSource(Context context) {
        this.json = AssetsUtil.getJson(context, HEROES_JSON_FILE);
    }

    public static HeroesLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new HeroesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getHeroes(LoadHeroesCallback callback) {
        heroList = getHeroList(json);
        if (heroList.size() == 0) {
            callback.onDataNotAvailable();
        } else {
            callback.onHeroesLoaded(heroList);
        }
    }

    @Override
    public void getHero(int key, GetHeroCallback callback) {
        Hero hero = getHeroWithKey(key);
        if (hero == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onTaskLoaded(hero);
        }
    }

    @Override
    public void refreshHeroes() {

    }

    private List<Hero> getHeroList(String json) {
        List<Hero> heroList = new ArrayList<>();
        TreeMap treeMap = JSON.parseObject(json, TreeMap.class);
        for (Object o : treeMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Hero hero = JSON.parseObject(entry.getValue().toString(), Hero.class);
            heroList.add(hero);
        }
        return heroList;
    }

    private Hero getHeroWithKey(int key) {
        for (Iterator<Hero> iterator = heroList.iterator(); iterator.hasNext(); ) {
            Hero hero = iterator.next();
            if (hero.getKey() == key) {
                return hero;
            }
        }
        return null;
    }
}
