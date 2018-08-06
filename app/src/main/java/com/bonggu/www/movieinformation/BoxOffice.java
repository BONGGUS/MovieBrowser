package com.bonggu.www.movieinformation;

public class BoxOffice {
    String image;
    String title;
    String grade;
    String opendt;
    String advence;
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOpendt() {
        return opendt;
    }

    public void setOpendt(String opendt) {
        this.opendt = opendt;
    }

    public String getAdvence() {
        return advence;
    }

    public void setAdvence(String advence) {
        this.advence = advence;
    }
}