package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.heroes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Hero;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail.HeroDetailActivity;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.ActivityUtils;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.AssetsUtil;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class HeroesFragment extends Fragment implements HeroesContract.View {

    private HeroesContract.Presenter heroesPresenter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    private static final String URL_HEAD = "champion/";

    public HeroesFragment() {

    }

    public static HeroesFragment newInstance() {
        return new HeroesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        heroesPresenter.start();
    }

    @Override
    public void setPresenter(HeroesContract.Presenter presenter) {
        heroesPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_heroes, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.heroes_recycler_view);
        gridLayoutManager = new GridLayoutManager(getContext(), getSpanCount());
        recyclerView.setLayoutManager(gridLayoutManager);
        setHasOptionsMenu(true);
        return root;
    }

    private int getSpanCount() {
        int spanCount;
        if (ActivityUtils.isScreenOrientationPortrait(getContext())) {
            spanCount = 4;
        } else {
            spanCount = 6;
        }
        return spanCount;
    }

    @Override
    public void showHeroes(List<Hero> heroList) {
        HeroRecyclerAdapter heroRecyclerAdapter = new HeroRecyclerAdapter(heroList);
        heroRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Hero hero, int position) {
                heroesPresenter.openHeroDetails(hero);
                heroesPresenter.setPosition(position);
            }
        });

        MyAnimationAdapter animationAdapter = new MyAnimationAdapter(heroRecyclerAdapter);
        animationAdapter.setFirstOnly(false);
        animationAdapter.setInterpolator(new OvershootInterpolator());
        animationAdapter.setDuration(800);
        recyclerView.setAdapter(animationAdapter);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    @Override
    public void showHeroDetailsUi(String id) {
        Intent intent = new Intent(getActivity(), HeroDetailActivity.class);
        intent.putExtra("heroId", id);
        startActivity(intent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private class HeroViewHolder extends RecyclerView.ViewHolder {
        HeroViewHolder(View itemView) {
            super(itemView);
        }
    }

    interface OnItemClickListener {
        void onItemClick(Hero hero, int position);
    }

    private class MyAnimationAdapter extends ScaleInAnimationAdapter {
        private RecyclerView.Adapter adapter;

        MyAnimationAdapter(RecyclerView.Adapter adapter) {
            super(adapter);
            this.adapter = adapter;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            adapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    private class HeroRecyclerAdapter extends RecyclerView.Adapter<HeroViewHolder> {
        private List<Hero> list;
        private OnItemClickListener onItemClickListener;
        static final int TYPE_HEADER = 0;
        static final int TYPE_NORMAL = 1;

        void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        HeroRecyclerAdapter(List<Hero> list) {
            this.list = list;
        }

        @Override
        public HeroesFragment.HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_hero, parent, false);
            if (viewType == TYPE_HEADER) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_header, parent, false);
            }
            return new HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HeroViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_HEADER) return;
            final Hero hero = list.get(position);
            View cv_hero = holder.itemView.findViewById(R.id.item_card_view_hero);
            ImageView iv_hero = (ImageView) holder.itemView.findViewById(R.id.item_image_view_hero);
            TextView tv_hero = (TextView) holder.itemView.findViewById(R.id.item_text_view_hero);
            iv_hero.setImageBitmap(AssetsUtil.getImage(getContext(), URL_HEAD + hero.getImage().getFull()));
            tv_hero.setText(hero.getName());
            if (onItemClickListener != null) {
                cv_hero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(hero, gridLayoutManager.findFirstCompletelyVisibleItemPosition());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_NORMAL;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == TYPE_HEADER
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }
    }
}

