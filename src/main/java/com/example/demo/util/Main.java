package com.example.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dateFormat = formatter.format(new Date());

        System.out.println(dateFormat);
    }
}
