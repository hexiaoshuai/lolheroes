package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.heroes;

import java.util.List;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.BasePresenter;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.BaseView;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Hero;

interface HeroesContract {
    interface View extends BaseView<Presenter> {
        void showHeroes(List<Hero> heroList);

        void showHeroDetailsUi(String id);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void loadHeroes(boolean forceUpdate);

        void openHeroDetails(Hero hero);

        void setFiltering(HeroFilterType requestType);

        HeroFilterType getFiltering();

        void setSearchText(String search);

        void setPosition(int position);

        int getPosition();
    }
}
