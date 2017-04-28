package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroDetailDataSource;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.source.HeroDetailRepository;

class HeroDetailPresenter implements HeroDetailContract.Presenter {
    private boolean firstLoad = true;
    private final HeroDetailRepository heroDetailRepository;
    private final HeroDetailContract.View heroDetailView;

    HeroDetailPresenter(HeroDetailRepository heroDetailRepository, HeroDetailContract.View heroDetailView) {
        this.heroDetailRepository = heroDetailRepository;
        this.heroDetailView = heroDetailView;
        this.heroDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHeroDetail(heroDetailView.getHeroDetailId(), false);
    }

    @Override
    public void loadHeroDetail(String heroId, boolean forceUpdate) {
        if (forceUpdate || firstLoad) {
            heroDetailRepository.refreshHeroDetail();
        }
        heroDetailRepository.getHeroDetail(heroId, new HeroDetailDataSource.LoadHeroDetailCallback() {
            @Override
            public void onHeroDetailLoaded(HeroDetail heroDetail) {
                heroDetailView.showHeroDetail(heroDetail);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        firstLoad = false;
    }
}
