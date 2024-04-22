package org.example.seminar2.homework;

import org.example.seminar2.rand.RandomDate;
import org.example.seminar2.rand.RandomDateProcessor;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class Homework {

  /**
   *  1. Создать аннотацию RandomDate со следующими возможностями:
   *  1.1 Если параметры не заданы, то в поле должна вставляться рандомная дата в диапазоне min, max.
   *  1.2 Аннотация должна работать с полем типа java.util.Date.
   *  1.3 Должна генерить дату в диапазоне [min, max)
   *  1.4 ** Научиться работать с полями LocalDateTime, LocalDate, Instant, ... (классы java.time.*)
   *
   *  Реализовать класс RandomDateProcessor по аналогии с RandomIntegerProcessor, который обрабатывает аннотацию.
   */

  private static class Gusik {
    @RandomDate(min = 1704056400000L, max = 1735592400000L)
    private Date date;
    @RandomDate()
    private LocalDateTime localDateTime;
    //@RandomDate
    @RandomDate()
    private LocalDate localDate;
    @RandomDate()
    private Instant instant;

    public String getDate() {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return simpleDateFormat.format(date);
    }

    public String getLocalDateTime() {
      Date dateformat = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return simpleDateFormat.format(dateformat);
    }

    public String getLocalDate() {
      Date dateformat = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return simpleDateFormat.format(dateformat);
    }

    public String getInstant() {
      Date dateformat = Date.from(instant.atZone(ZoneId.systemDefault()).toInstant());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return simpleDateFormat.format(dateformat);
    }
  }

  public static void main(String[] args) {
    Gusik gusik = new Gusik();
    RandomDateProcessor.ProcessRandomDate(gusik);

//    System.out.println(gusik.date);
//    System.out.println(gusik.localDate);
//    System.out.println(gusik.localDateTime);
//    System.out.println(gusik.instant);


    System.out.println(gusik.getDate() + " Date");
    System.out.println(gusik.getLocalDate() + " LocalDate");
    System.out.println(gusik.getLocalDateTime() + " LocalDateTime");
    System.out.println(gusik.getInstant() + " Instant");


  }
}
