package com.smallpigeon.entity;

public class Interest {
    private Integer id;
    private Integer user_id;
    private Integer outdoor;
    private Integer music;
    private Integer film;
    private Integer society;
    private Integer delicacy;
    private Integer science;
    private Integer star;
    private Integer comic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(Integer outdoor) {
        this.outdoor = outdoor;
    }

    public Integer getMusic() {
        return music;
    }

    public void setMusic(Integer music) {
        this.music = music;
    }

    public Integer getFilm() {
        return film;
    }

    public void setFilm(Integer film) {
        this.film = film;
    }

    public Integer getSociety() {
        return society;
    }

    public void setSociety(Integer society) {
        this.society = society;
    }

    public Integer getDelicacy() {
        return delicacy;
    }

    public void setDelicacy(Integer delicacy) {
        this.delicacy = delicacy;
    }

    public Integer getScience() {
        return science;
    }

    public void setScience(Integer science) {
        this.science = science;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getComic() {
        return comic;
    }

    public void setComic(Integer comic) {
        this.comic = comic;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", outdoor=" + outdoor +
                ", music=" + music +
                ", film=" + film +
                ", society=" + society +
                ", delicacy=" + delicacy +
                ", science=" + science +
                ", star=" + star +
                ", comic=" + comic +
                '}';
    }
}
