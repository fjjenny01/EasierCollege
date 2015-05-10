package com.example.jinfang.easiercollege;

import java.util.Date;

/**
 * Created by jinfang on 4/21/15.
 */
public class Note {
    private int id;
    private String title = "";
    private String text_content = "";
    private String pic_content = "";  // need to be changed
    private Date time_created;
    private Date time_last_saved;

    //Default Constructor
    public Note()
    {
        this.time_created = new Date();
        this.time_last_saved = new Date();
    }

    //explicit Constructor
    public Note(int id, String title, String text_content, String pic_content) {
        this.id = id;
        this.title = title;
        this.text_content = text_content;
        this.pic_content = pic_content;
        this.time_created = new Date();
        this.time_last_saved = new Date();
    }


    //explicit Constructor
    public Note(String title, String text_content, String pic_content, Date time_created, Date time_last_saved) {
        this.title = title;
        this.text_content = text_content;
        this.pic_content = pic_content;
        this.time_created = time_created;
        this.time_last_saved = time_last_saved;
    }

    public int getId() {return id;}
    public String getTitle() {
        return title;
    }

    public String getText_content() {
        return text_content;
    }

    public String getPic_content() {
        return pic_content;
    }

    public Date getTime_created() {
        return time_created;
    }

    public Date getTime_last_saved() {
        return time_last_saved;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public void setPic_content(String pic_content) {
        this.pic_content = pic_content;
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
    }

    public void setTime_last_saved(Date time_last_saved) {
        this.time_last_saved = time_last_saved;
    }

    public void setId(int id) {
        this.id = id;
    }
}
