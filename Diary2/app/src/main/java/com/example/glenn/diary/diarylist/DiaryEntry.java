package com.example.glenn.diary.diarylist;

/**
 * Created by Glenn on 18/08/2016.
 */
public class DiaryEntry {
    private String title;
    private String content;
    private long recordDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(long recordDate) {
        this.recordDate = recordDate;
    }
}
