package com.eltendawy.anamuslim.Model;

/**
 * Created by Islam_Assem on 23-09-18.
 */

public class Hadith {
    String Title;
    String Content;

    public Hadith(String title,String content) {
        Title = title;
        Content=content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
