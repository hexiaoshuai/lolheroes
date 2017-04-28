package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;

public class HeroDetailFragment extends Fragment implements HeroDetailContract.View {

    private HeroDetailContract.Presenter heroDetailPresenter;
    private TextView toolbarTitle;

    public HeroDetailFragment() {

    }

    public static HeroDetailFragment newInstance() {
        return new HeroDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        heroDetailPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        return inflater.inflate(R.layout.fragment_heroes, container, false);
    }

    @Override
    public void showHeroDetail(HeroDetail heroDetail) {
        toolbarTitle.setText(heroDetail.getSpells().get(0).getToolTip());
    }

    @Override
    public String getHeroDetailId() {
        return getActivity().getIntent().getStringExtra("heroId");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(HeroDetailContract.Presenter presenter) {
        heroDetailPresenter = presenter;
    }
}

