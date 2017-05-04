package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.herodetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.R;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.HeroDetail;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.LevelTip;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data.Spells;
import cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util.AssetsUtil;

public class HeroDetailFragment extends Fragment implements HeroDetailContract.View {
    private HeroDetailContract.Presenter heroDetailPresenter;
    private TextView loreTextView, allyTipsTextView, enemyTipsTextView, tv_spell_name, tv_spell_type, tv_spell;
    private ImageView iv_b, iv_q, iv_w, iv_e, iv_r;
    private HeroDetail heroDetail;

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
        View root = inflater.inflate(R.layout.fragment_hero_detail, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        loreTextView = (TextView) root.findViewById(R.id.text_view_lore);
        allyTipsTextView = (TextView) root.findViewById(R.id.text_view_allytips);
        enemyTipsTextView = (TextView) root.findViewById(R.id.text_view_enemytips);
        iv_b = (ImageView) root.findViewById(R.id.iv_b);
        iv_q = (ImageView) root.findViewById(R.id.iv_q);
        iv_w = (ImageView) root.findViewById(R.id.iv_w);
        iv_e = (ImageView) root.findViewById(R.id.iv_e);
        iv_r = (ImageView) root.findViewById(R.id.iv_r);
        tv_spell_name = (TextView) root.findViewById(R.id.tv_spell_name);
        tv_spell_type = (TextView) root.findViewById(R.id.tv_spell_type);
        tv_spell = (TextView) root.findViewById(R.id.tv_spell);
    }

    @Override
    public void showHeroDetail(HeroDetail heroDetail) {
        this.heroDetail = heroDetail;
        ((HeroDetailActivity) getActivity()).updateCollapsingToolbar(heroDetail);
        loreTextView.setText(heroDetail.getLore());
        allyTipsTextView.setText(tipToString(heroDetail.getAllyTips()));
        enemyTipsTextView.setText(tipToString(heroDetail.getEnemyTips()));
        iv_b.setImageBitmap(AssetsUtil.getImage(getContext(), "passive/" + heroDetail.getPassive().getImage().getFull()));
        iv_q.setImageBitmap(AssetsUtil.getImage(getContext(), "spell/" + heroDetail.getSpells().get(0).getImage().getFull()));
        iv_w.setImageBitmap(AssetsUtil.getImage(getContext(), "spell/" + heroDetail.getSpells().get(1).getImage().getFull()));
        iv_e.setImageBitmap(AssetsUtil.getImage(getContext(), "spell/" + heroDetail.getSpells().get(2).getImage().getFull()));
        iv_r.setImageBitmap(AssetsUtil.getImage(getContext(), "spell/" + heroDetail.getSpells().get(3).getImage().getFull()));
        iv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_spells(view, R.string.tv_b, -1);
            }
        });
        iv_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_spells(view, R.string.tv_q, 0);
            }
        });
        iv_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_spells(view, R.string.tv_w, 1);
            }
        });
        iv_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_spells(view, R.string.tv_e, 2);
            }
        });
        iv_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_spells(view, R.string.tv_r, 3);
            }
        });
        update_spells(iv_b, R.string.tv_b, -1);
    }

    private void update_spells(View view, int typeId, int spellsIndex) {
        iv_b.setAlpha(0.3f);
        iv_q.setAlpha(0.3f);
        iv_w.setAlpha(0.3f);
        iv_e.setAlpha(0.3f);
        iv_r.setAlpha(0.3f);
        view.setAlpha(1f);
        if (view == iv_b) {
            tv_spell_name.setText(heroDetail.getPassive().getName());
        } else {
            tv_spell_name.setText(heroDetail.getSpells().get(spellsIndex).getName());
        }
        tv_spell_type.setText(getResources().getText(typeId));
        tv_spell.setText(getSpell(heroDetail, spellsIndex));
    }

    private String replaceString(String str) {
        return str.replace("<br>", "\n\u3000\u3000").replaceAll("</?[^>]+>", "");
    }

    private String getSpell(HeroDetail heroDetail, int spellsIndex) {
        String string = "";
        if (spellsIndex == -1) {
            string = "\u3000\u3000" + heroDetail.getPassive().getDescription() + "\n";
        } else {
            Spells spells = heroDetail.getSpells().get(spellsIndex);
            LevelTip levelTip = spells.getLevelTip();
            string += "\u3000\u3000" + spells.getToolTip() + "\n\n";
            List<String> label = levelTip.getLabel();
            List<String> effect = levelTip.getEffect();
            for (int i = 0; i < label.size(); i++) {
                string += label.get(i) + ":" + effect.get(i) + "\n";
            }
        }

        return replaceString(string);
    }

    private String tipToString(List<String> tip) {
        String string = "";
        for (String str : tip) {
            string += "\u3000\u3000" + str + "\n";
        }
        return string;
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

