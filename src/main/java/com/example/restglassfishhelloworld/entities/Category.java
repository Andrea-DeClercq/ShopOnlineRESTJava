package com.example.restglassfishhelloworld.entities;

public class Category {
    private int id;
    private String title;
    private String webTitle;
    private int parent;
    private int level;

    public Category() {
    }

    public Category(int id, String title, String webTitle, int parent, int level) {
        this.id = id;
        this.title = title;
        this.webTitle = webTitle;
        this.parent = parent;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
