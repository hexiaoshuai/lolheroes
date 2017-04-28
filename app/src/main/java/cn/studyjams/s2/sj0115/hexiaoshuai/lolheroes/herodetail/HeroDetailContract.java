package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.BasePresenter;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.BaseView;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;

interface HeroDetailContract {
    interface View extends BaseView<Presenter> {

        void showHeroDetail(HeroDetail heroDetail);

        String getHeroDetailId();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadHeroDetail(String heroId, boolean forceUpdate);

    }
}
