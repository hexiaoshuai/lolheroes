package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.heroes;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Hero;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroesDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroesRepository;

class HeroesPresenter implements HeroesContract.Presenter {
    private boolean firstLoad = true;
    private final HeroesRepository heroesRepository;
    private final HeroesContract.View heroesView;

    private HeroFilterType mCurrentFiltering = HeroFilterType.ALL;
    private String searchText = null;
    private int position = 0;

    HeroesPresenter(HeroesRepository heroesRepository, HeroesContract.View heroesView) {
        this.heroesRepository = heroesRepository;
        this.heroesView = heroesView;
        this.heroesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHeroes(false);
    }

    @Override
    public void loadHeroes(boolean forceUpdate) {
        if (forceUpdate || firstLoad) {
            heroesRepository.refreshHeroes();
        }
        heroesRepository.getHeroes(new HeroesDataSource.LoadHeroesCallback() {
            @Override
            public void onHeroesLoaded(List<Hero> heroList) {
                List<Hero> heroShowList = new ArrayList<>();
                heroShowList.add(new Hero());
                for (Hero hero : heroList) {
                    if (searchText == null || searchText.equals("")) {
                        switch (mCurrentFiltering) {
                            case ALL:
                                heroShowList.add(hero);
                                break;
                            case FIGHTER:
                                for (String tag : hero.getTags()) {
                                    if (tag.equals("Fighter")) {
                                        heroShowList.add(hero);
                                    }
                                }
                                break;
                            case MAGE:
                                for (String tag : hero.getTags()) {
                                    if (tag.equals("Mage")) {
                                        heroShowList.add(hero);
                                    }
                                }
                                break;
                            case ASSASSIN:
                                for (String tag : hero.getTags()) {
                                    if (tag.equals("Assassin")) {
                                        heroShowList.add(hero);
                                    }
                                }
                                break;
                            case TANK:
                                for (String tag : hero.getTags()) {
                                    if (tag.equals("Tank")) {
                                        heroShowList.add(hero);
                                    }
                                }
                                break;
                            case MARKSMAN:
                                for (String tag : hero.getTags()) {
                                    if (tag.equals("Marksman")) {
                                        heroShowList.add(hero);
                                    }
                                }
                                break;
                            case SUPPORT:
                                for (String tag : hero.getTags()) {
                                    if (tag.equals("Support")) {
                                        heroShowList.add(hero);
                                    }
                                }
                                break;
                        }
                    } else {
                        if(hero.getName().contains(searchText)
                                || hero.getTitle().contains(searchText)
                                || hero.getId().contains(searchText)){
                            heroShowList.add(hero);
                        }
                    }

                }

                if (!heroesView.isActive()) {
                    return;
                }

                heroesView.showHeroes(heroShowList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        firstLoad = false;
    }

    @Override
    public void openHeroDetails(Hero hero) {
        heroesView.showHeroDetailsUi(hero.getId());
    }

    @Override
    public void setFiltering(HeroFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public HeroFilterType getFiltering() {
        return mCurrentFiltering;
    }

    @Override
    public void setSearchText(String search) {
        this.searchText = search;
        loadHeroes(false);
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
