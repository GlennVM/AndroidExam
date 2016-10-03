package com.example.glenn.diary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Glenn on 18/08/2016.
 */
public class DateFormatter {

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat hourFormat;

    public DateFormatter(){
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        hourFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String formatDate(Date date){
        StringBuilder builder = new StringBuilder();

        builder.append(dateFormat.format(date))
                .append(" - ")
                .append(hourFormat.format(date));

        return builder.toString();
    }
}
