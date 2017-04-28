package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.data;

public class Spells {
    private String id;
    private String name;
    private String description;
    private Image image;
    private String toolTip;
    private LevelTip levelTip;

    public Spells() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public LevelTip getLevelTip() {
        return levelTip;
    }

    public void setLevelTip(LevelTip levelTip) {
        this.levelTip = levelTip;
    }
}
