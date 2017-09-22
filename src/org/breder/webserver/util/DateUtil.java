package org.breder.webserver.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Classe com um conjunto de m�todos para tratamento de datas.
 */
public abstract class DateUtil {

  /** Constante que define um dia em milisegundos */
  public static final long DAY_IN_MILLIS = 24 * 1000 * 60 * 60;

  /**
   * Fuso hor�rio original da JVM antes de ser modificado pela aplica��o
   */
  private static TimeZone originalTimeZone = TimeZone.getDefault();

  private static DateFormat defaultDateFormat = new SimpleDateFormat(
    "dd/MM/yyyy hh:mm:ss");

  public static String toString(Date date) {
    return defaultDateFormat.format(date);
  }

  /**
   * Retorna o �ltimo dia do m�s, com hora, minuto, segundo e milisegundos
   * iguais a zero.
   * 
   * Permite a utiliza��o de Datas como chaves de hashtables.
   * 
   * @param date o m�s
   * 
   * @return a data correspondente ao �ltimo dia do m�s
   */
  public static Date getLastDayOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    cal.set(Calendar.DAY_OF_MONTH, lastDay);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * Retorna o primeiro dia do m�s, com hora, minuto, segundo e milisegundos
   * iguais a zero.
   * 
   * Permite a utiliza��o de Datas como chaves de hashtables.
   * 
   * @param date o m�s
   * 
   * @return a data correspondente ao �ltimo dia do m�s
   */
  public static Date getFirstDayOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * Retorna o dia da semana de uma data passada como argumento.
   * 
   * @param date o m�s a data
   * 
   * @return um n�mero indicativo do dia da semana
   * 
   * @see Calendar#SUNDAY
   * @see Calendar#MONDAY
   * @see Calendar#TUESDAY
   * @see Calendar#WEDNESDAY
   * @see Calendar#THURSDAY
   * @see Calendar#FRIDAY
   * @see Calendar#SATURDAY
   */
  public static int getDayOfWeek(final Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.DAY_OF_WEEK);
  }

  /**
   * Obt�m a data correspondente ao dia da semana, considerando a data
   * especificada.
   * 
   * @param date uma data qualquer
   * @param dayOfWeek o dia da semana
   * @return a data correspondente ao dia da semana
   */
  public static Date getDayOfWeek(final Date date, int dayOfWeek) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DAY_OF_MONTH, dayOfWeek - cal.get(Calendar.DAY_OF_WEEK));
    return cal.getTime();
  }

  /**
   * Retorna o dia do m�s da data passada. O primeiro dia do m�s tem valor 1.
   * 
   * @param date a data
   * 
   * @return o novo dia do m�s
   */
  public static int getDay(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Retorna uma nova data igual a date exceto pelo m�s.
   * 
   * @param date a data
   * @param month o novo m�s, sendo 0=janeiro, 1=fevereiro, etc.
   * 
   * @return a nova data
   * 
   * @throws ArrayIndexOutOfBoundsException se o m�s n�o existir.
   */
  public static Date setMonth(Date date, int month) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.MONTH, month);
    return c.getTime();
  }

  /**
   * Retorna uma nova data igual a date exceto pelo dia.
   * 
   * @param date a data
   * @param day o novo dia
   * 
   * @return a nova data
   * 
   * @throws ArrayIndexOutOfBoundsException se o dia n�o existir para o m�s e
   *         ano de date.
   */
  public static Date setDay(Date date, int day) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, day);
    return c.getTime();
  }

  /**
   * Retorna uma nova data igual a date exceto pelo ano.
   * 
   * @param date a data
   * @param year o novo ano
   * 
   * @return a nova data
   * 
   * @throws ArrayIndexOutOfBoundsException se o ano n�o existir.
   */
  public static Date setYear(Date date, int year) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.YEAR, year);
    return c.getTime();
  }

  /**
   * Retorna o m�s da data passada, come�ando por 0 (0=janeiro, 1=fevereiro,
   * etc).
   * 
   * @param date a data
   * 
   * @return o m�s da data
   */
  public static int getMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(Calendar.MONTH);
  }

  /**
   * Retorna o m�s da data passada, come�ando por 0 (0=janeiro, 1=fevereiro,
   * etc).
   * 
   * @param time a data
   * 
   * @return o m�s da data
   */
  public static int getMonth(long time) {
    Calendar c = Calendar.getInstance();
    c.setTime(DateUtil.getDate(time));
    return c.get(Calendar.MONTH);
  }

  /**
   * Retorna o ano da data passada.
   * 
   * @param date a data
   * 
   * @return o ano da data
   */
  public static int getYear(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(Calendar.YEAR);
  }

  /**
   * Calcula uma nova data correspondente a data atual mais n meses.
   * 
   * @param date a data base
   * @param months o numero de meses a serem somados � data base
   * 
   * @return a data correspondente ao resultado da soma
   */
  public static Date addMonths(Date date, int months) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.MONTH, months);
    return c.getTime();
  }

  /**
   * Calcula uma nova data correspondente a data atual mais n anos.
   * 
   * @param date a data base
   * @param years o numero de anos a serem somados � data base
   * 
   * @return a data correspondente ao resultado da soma
   */
  public static Date addYears(Date date, int years) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.YEAR, years);
    return c.getTime();
  }

  /**
   * Calcula uma nova data correspondente a data atual mais n dias.
   * 
   * @param date a data base
   * @param days o numero de dias a serem somados a data base
   * 
   * @return a data correspondente ao resultado da soma
   */
  public static Date addDays(Date date, int days) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_MONTH, days);
    return c.getTime();
  }

  /**
   * Calcula uma nova data correspondente a data atual mais n horas.
   * 
   * @param date a data base
   * @param hours o numero de horas a serem somados a data base
   * 
   * @return a data correspondente ao resultado da soma
   */
  public static Date addHours(Date date, int hours) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.HOUR, hours);
    return c.getTime();
  }

  /**
   * Calcula uma nova data correspondente a data passada como par�metro mais N
   * milisegundos.
   * 
   * @param date a data base
   * @param milliseconds o numero de milisegundos a serem somados a data base
   * 
   * @return a data correspondente ao resultado da soma
   */
  public static Date addMillisecond(Date date, int milliseconds) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.MILLISECOND, milliseconds);
    return c.getTime();
  }

  /**
   * N�mero de dias entre a data corrente e uma outra data.
   * 
   * @param date a outra data
   * 
   * @return o n�mero de dias entre as duas datas
   */
  public static int getElapsedDays(Date date) {
    return elapsed(date, Calendar.DATE);
  }

  /**
   * N�mero de dias entre duas datas.
   * 
   * @param d1 a primeira data
   * @param d2 a segunda data
   * 
   * @return o n�mero de dias entre as duas datas
   */
  public static int getElapsedDays(Date d1, Date d2) {
    return elapsed(d1, d2, Calendar.DATE);
  }

  /**
   * N�mero de meses entre a data corrente e uma outra data.
   * 
   * @param date a outra data
   * 
   * @return o n�mero de meses entre as duas datas
   */
  public static int getElapsedMonths(Date date) {
    return elapsed(date, Calendar.MONTH);
  }

  /**
   * N�mero de meses entre duas datas.
   * 
   * @param d1 a primeira data
   * @param d2 a segunda data
   * 
   * @return o n�mero de meses entre as duas datas
   */
  public static int getElapsedMonths(Date d1, Date d2) {
    return elapsed(d1, d2, Calendar.MONTH);
  }

  /**
   * N�mero de anos entre a data corrente e uma outra data.
   * 
   * @param date a outra data
   * 
   * @return o n�mero de anos entre as duas datas
   */
  public static int getElapsedYears(Date date) {
    return elapsed(date, Calendar.YEAR);
  }

  /**
   * N�mero de anos entre duas datas.
   * 
   * @param d1 a primeira data
   * @param d2 a segunda data
   * 
   * @return o n�mero de anos entre as duas datas
   */
  public static int getElapsedYears(Date d1, Date d2) {
    return elapsed(d1, d2, Calendar.YEAR);
  }

  /**
   * Calcula todas as diferen�as entre duas datas
   * 
   * @param g1 o primeiro calend�rio
   * @param g2 o segundo calend�rio
   * @param type um valor de Calendar.FIELD_NAME
   * 
   * @return o valor resultado de acordo com o tipo
   */
  private static int elapsed(GregorianCalendar g1, GregorianCalendar g2,
    int type) {
    GregorianCalendar gc1;
    GregorianCalendar gc2;
    int elapsed = 0;

    // Cria as c�pias
    if (g2.after(g1)) {
      gc2 = (GregorianCalendar) g2.clone();
      gc1 = (GregorianCalendar) g1.clone();
    }
    else {
      gc2 = (GregorianCalendar) g1.clone();
      gc1 = (GregorianCalendar) g2.clone();
    }
    if ((type == Calendar.MONTH) || (type == Calendar.YEAR)) {
      gc1.clear(Calendar.DATE);
      gc2.clear(Calendar.DATE);
    }
    if (type == Calendar.YEAR) {
      gc1.clear(Calendar.MONTH);
      gc2.clear(Calendar.MONTH);
    }
    while (gc1.before(gc2)) {
      gc1.add(type, 1);
      elapsed++;
    }
    return elapsed;
  }

  /**
   * Calcula todas as diferen�as entre a data corrente e uma outra data
   * 
   * @param date a outra data
   * @param type um valor de Calendar.FIELD_NAME
   * 
   * @return o valor resultado de acordo com o tipo
   */
  private static int elapsed(Date date, int type) {
    return elapsed(date, new Date(), type);
  }

  /**
   * Calcula todas as diferen�as entre duas datas
   * 
   * @param d1 a primeira data
   * @param d2 a segunda data
   * @param type um valor de Calendar.FIELD_NAME
   * 
   * @return o valor resultado de acordo com o tipo
   */
  private static int elapsed(Date d1, Date d2, int type) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(d1);
    GregorianCalendar g1 =
      new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
        cal.get(Calendar.DATE));
    cal.setTime(d2);
    GregorianCalendar g2 =
      new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
        cal.get(Calendar.DATE));
    return elapsed(g1, g2, type);
  }

  /**
   * Transforma uma data e hora recebidas em milisegundos na mesma data e zero
   * hora tamb�m em milisegundos.
   * 
   * @param time uma data em milisegundos.
   * 
   * @return um valor em milisegundos que representa a mesma data recebida mas
   *         no hor�rio zero hora.
   */
  public static long getZeroHourDate(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    return getZeroHourDate(calendar);
  }

  /**
   * Transforma uma data e hora recebidas em milisegundos na mesma data e no
   * ultimo minuto do dia tamb�m em milisegundos.
   * 
   * @param time uma data em milisegundos.
   * 
   * @return um valor em milisegundos que representa a mesma data recebida mas
   *         no hor�rio 23:59.
   */
  public static long getLastHourDate(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    return getLastHourDate(calendar);
  }

  /**
   * Informa a hora corrente em millisegundos.
   * 
   * @return hora corrente.
   */
  public static long getCurrentInMillis() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    return calendar.getTimeInMillis();
  }

  /**
   * Transforma uma data em milisegundos para o formato Date.
   * 
   * @param time uma data.
   * 
   * @return mesma data no formato Date.
   */
  public static Date getDate(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    return calendar.getTime();
  }

  /**
   * Cria uma data (java.util.Date) para representar uma data/hora que �
   * inst�ncia java.sql.Timestamp.
   * 
   * @param timestamp a data/hora em java.sql.Timestamp
   * @return a data/hora representada em java.util.Date.
   */
  public static Date getDate(java.sql.Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }
    return new Date(timestamp.getTime());
  }

  /**
   * Cria uma data (java.util.Date) para representa um dia, m�s e ano.
   * 
   * @param day o dia
   * @param month o m�s
   * @param year o ano
   * @return a data (dia/m�s/ano) representada em java.util.Date.
   */
  public static Date getDate(int day, int month, int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.YEAR, year);
    return calendar.getTime();
  }

  /**
   * Informa a hora corrente.
   * 
   * @return hora corrente.
   */
  public static Date getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    return calendar.getTime();
  }

  /**
   * Informa a hora corrente em um determinado fuso hor�rio.
   * 
   * @param timezone o fuso hor�rio
   * @return hora corrente.
   */
  public static Date getCurrentDate(TimeZone timezone) {
    Calendar mbCal = new GregorianCalendar(timezone);
    mbCal.setTimeInMillis(System.currentTimeMillis());
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
    cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
    cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
    cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
    cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
    cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));
    return cal.getTime();
  }

  /**
   * Informa o dia corrente a zero hora.
   * 
   * @return dia corrente a zero hora.
   */
  public static Date getZeroHourCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    return getZeroHour(calendar);
  }

  /**
   * Transforma uma data e hora recebidas em milisegundos na mesma data e zero
   * hora tamb�m em milisegundos.
   * 
   * @param cal uma data representada como inst�ncia da classe
   *        <code>Calendar</code>.
   * 
   * @return um valor em milisegundos que representa a mesma data recebida mas
   *         no hor�rio zero hora.
   */
  public static long getZeroHourDate(Calendar cal) {
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTimeInMillis();
  }

  /**
   * Transforma uma data e hora recebidas na mesma data com hora, minuto,
   * segundo e milisegundo zerados.
   * 
   * @param date uma data.
   * 
   * @return uma data que representa a mesma data recebida mas no hor�rio
   *         zerado.
   */
  public static Date getZeroHourDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * Transforma uma data e hora recebidas em milisegundos na mesma data e zero
   * hora tamb�m em milisegundos.
   * 
   * @param cal uma data representada como inst�ncia da classe
   *        <code>Calendar</code>.
   * 
   * @return um valor em milisegundos que representa a mesma data recebida mas
   *         no hor�rio zero hora.
   */
  public static Date getZeroHour(Calendar cal) {
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * Transforma uma data e hora recebidas em milisegundos na mesma data e no
   * ultimo minuto do dia tamb�m em milisegundos.
   * 
   * @param cal uma data representada como inst�ncia da classe
   *        <code>Calendar</code>.
   * 
   * @return um valor em milisegundos que representa a mesma data recebida mas
   *         no hor�rio 23:59.
   */
  public static long getLastHourDate(Calendar cal) {
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTimeInMillis();
  }

  /**
   * Estabelece como default de <code>TimeZone</code> o fuso hor�rio GMT-03, que
   * n�o usa hor�rio de ver�o.
   */
  public static void setDefaultTimeZoneWithoutDST() {
    setDefaultTimeZone(TimeZone.getTimeZone("GMT-03"));
  }

  /**
   * Estabelece como default de <code>TimeZone</code> o fuso hor�rio recebido
   * como par�metro.
   * 
   * @param defaultTimeZone o fuso hor�rio a ser adotado como default.
   */
  public static void setDefaultTimeZone(TimeZone defaultTimeZone) {
    TimeZone.setDefault(defaultTimeZone);
  }

  /**
   * Obt�m o fuso hor�rio original da JVM antes de ser modificado pela
   * aplica��o.
   * 
   * @return o fuso hor�rio original
   */
  public static TimeZone getOriginalTimeZone() {
    return originalTimeZone;
  }

  /**
   * Informa o n�mero de dias corridos de um determinado m�s.
   * 
   * @param date o m�s do qual se quer saber o n�mero de dias
   * @return o n�mero de dias corridos.
   */
  public static int getDaysInMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getLastDayOfMonth(date));
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Informa o n�mero de dias corridos de um determinado m�s.
   * 
   * @param month o m�s do qual se quer saber o n�mero de dias
   * @return o n�mero de dias corridos.
   */
  public static int getDaysInMonth(Integer month) {
    Calendar cal = Calendar.getInstance();
    Date date = getCurrentDate();
    date = getDate(1, month, getYear(date));
    cal.setTime(getLastDayOfMonth(date));
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Obt�m o primeiro dia do m�s ap�s uma data.
   * 
   * @param date data a partir da qual queremos saber o primeiro dia do m�s.
   * @return primeiro dia do m�s ap�s uma data.
   */
  public static long getFirstDayOfMonthAfter(long date) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date);
    cal.add(Calendar.MONTH, 1);
    return getFirstDayOfMonth(cal.getTime()).getTime();
  }

  /**
   * Obt�m a hora corrente com precis�o de segundos e com os milisegundos
   * gerados. Este m�todo � �til para gerar horas a serem salvas no banco, pois
   * o banco n�o armazena os milisegundos.
   * 
   * @return hora corrente com precis�o de segundos.
   */
  public static long getCurrentTimeWithSecondPrecision() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(DateUtil.getCurrentInMillis());
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTimeInMillis();
  }

  /**
   * Retorna uma descri��o textual do tempo fornecido. Esse texto usa a grandeza
   * mais adequada ao tempo em quest�o, entre milisegundos, segundos, minutos,
   * horas e dias. Exemplos de retorno desse m�todo s�o: "234 milisegundos",
   * "3.5 segundos", "3 segundos", "10 horas", "2.41 dias".
   * 
   * @param t o tempo, em ms, a ser formatado.
   * 
   * @return Um texto que descreve o tempo fornecido.
   */
  public static String printTime(long t) {
    if (t < 1000) {
      return t + " milisegundo" + (t == 1 ? "" : "s");
    }
    float time = t / 1000F;
    if (time < 60) {
      return time + " segundo" + (time == 1 ? "" : "s");
    }
    time /= 60;
    if (time < 60) {
      return time + " minuto" + (time == 1 ? "" : "s");
    }
    time /= 60;
    if (time < 24) {
      return time + " hora" + (time == 1 ? "" : "s");
    }
    time /= 24;
    return time + " dia" + (time == 1 ? "" : "s");
  }

  /**
   * Obt�m o pr�ximo dia a zero hora a partir desta data.
   * 
   * @param date data a partir da qual obteremos o pr�ximo dia a zero hora.
   * 
   * @return pr�ximo dia a zero hora a partir desta data.
   */
  public static long getNextZeroHourDate(long date) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date);
    if (cal.get(Calendar.HOUR) == 0 && cal.get(Calendar.MINUTE) == 0
      && cal.get(Calendar.SECOND) == 0) {
      return getZeroHourDate(date);
    }
    return getZeroHourDate(date + DAY_IN_MILLIS);
  }

  /**
   * Indica se a data especificada est� compreendida no per�odo especificado (
   * 
   * @param date data a ser verificada.
   * @param pStart data de in�cio do per�odo.
   * @param pEnd data de fim do per�odo.
   * 
   * @return true se a data estiver compreendida no per�odo.
   */
  public static boolean isDateWithinPeriod(Date date, Date pStart, Date pEnd) {
    return date.compareTo(pStart) >= 0 && date.compareTo(pEnd) <= 0;
  }

  /**
   * Indica se os dois per�odos especificados possuem alguma interse��o.
   * 
   * @param p1Start data de in�cio do per�odo 1.
   * @param p1End data de fim do per�odo 1.
   * @param p2Start data de in�cio do per�odo 2.
   * @param p2End data de fim do per�odo 2.
   * 
   * @return true se o per�odo 1 tiver alguma interse��o (mesmo que seja apenas
   *         um dia) com o per�odo 2.
   */
  public static boolean periodsIntersect(Date p1Start, Date p1End,
    Date p2Start, Date p2End) {
    return (p1End.compareTo(p2Start) >= 0 && p1Start.compareTo(p2End) <= 0);

  }

}
