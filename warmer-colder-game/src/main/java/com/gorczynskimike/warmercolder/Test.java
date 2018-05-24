package com.gorczynskimike.warmercolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {

    public static void main(String[] args) {
        DateTimeFormatter formatter1 = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate localDate1 = LocalDate.parse("19920202", formatter1);

        DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate localDate2 = LocalDate.parse("1992-02-02", formatter2);

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy && MM && dd");
        LocalDate localDate3 = LocalDate.parse("1992 && 02 && 02", formatter3);

        System.out.println(localDate1);
        System.out.println(localDate2);
        System.out.println(localDate3);
    }


}
