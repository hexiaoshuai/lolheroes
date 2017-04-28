package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data;

import java.util.List;

public class HeroDetail {
    private String id;
    private int key;
    private String name;
    private String title;
    private List<String> tags;
    private Image image;
    private List<Skin> skins;
    private Info info;
    private List<Spells> spells;
    private Passive passive;
    private String lore;
    private List<String> allyTips;
    private List<String> enemyTips;

    public HeroDetail() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Skin> getSkins() {
        return skins;
    }

    public void setSkins(List<Skin> skins) {
        this.skins = skins;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Spells> getSpells() {
        return spells;
    }

    public void setSpells(List<Spells> spells) {
        this.spells = spells;
    }

    public Passive getPassive() {
        return passive;
    }

    public void setPassive(Passive passive) {
        this.passive = passive;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public List<String> getAllyTips() {
        return allyTips;
    }

    public void setAllyTips(List<String> allyTips) {
        this.allyTips = allyTips;
    }

    public List<String> getEnemyTips() {
        return enemyTips;
    }

    public void setEnemyTips(List<String> enemyTips) {
        this.enemyTips = enemyTips;
    }
}
