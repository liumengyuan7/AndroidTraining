package com.entity;

/**
 * @auther angel
 * @description as
 * @date 2019/11/26 16:52
 */

public class Interest {

    private int id;
    private int userId;
    private int outdoor;
    private int music;
    private int film;
    private int society;
    private int delicacy;
    private int science;
    private int star;
    private int comic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(int outdoor) {
        this.outdoor = outdoor;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getFilm() {
        return film;
    }

    public void setFilm(int film) {
        this.film = film;
    }

    public int getSociety() {
        return society;
    }

    public void setSociety(int society) {
        this.society = society;
    }

    public int getDelicacy() {
        return delicacy;
    }

    public void setDelicacy(int delicacy) {
        this.delicacy = delicacy;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getComic() {
        return comic;
    }

    public void setComic(int comic) {
        this.comic = comic;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", userId=" + userId +
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
