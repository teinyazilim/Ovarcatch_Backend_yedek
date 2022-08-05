package com.tein.overcatchbackend.util;

import com.tein.overcatchbackend.domain.vm.PassTime;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author pc
 */
public class GlobalVariable {

    public static String nonGroupName = "Non Group";
    public static String userNamePwdError = "Wrong Name or Password";
    public static String systemError = "System Error";
    public static String lngEnglish = "English";
    public static String lngTurkish = "Turkish";
    public static String lngPortekiz = "Português";
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static String timeZoneDateString(Calendar cal, String gmt) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (gmt.equals("-")) {
            return dateFormat.format(cal.getTime());
        }
        TimeZone tz = cal.getTimeZone();
        Date dt = cal.getTime();
        int deger = tz.getOffset(dt.getTime());

        cal.add(Calendar.MILLISECOND, -deger);

        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone(gmt));
        tz = gmtCal.getTimeZone();
        dt = gmtCal.getTime();
        deger = tz.getOffset(dt.getTime());

        cal.add(Calendar.MILLISECOND, deger);

        return dateFormat.format(cal.getTime());
    }

    public static Long TimeZoneDate(Calendar cal, String gmt) {
        if (gmt.equals("-")) {
            return cal.getTime().getTime();
        }
        TimeZone tz = cal.getTimeZone();
        Date dt = cal.getTime();
        int deger = tz.getOffset(dt.getTime());

        cal.add(Calendar.MILLISECOND, -deger);

        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone(gmt));
        tz = gmtCal.getTimeZone();
        dt = gmtCal.getTime();
        deger = tz.getOffset(dt.getTime());

        cal.add(Calendar.MILLISECOND, deger);

        return cal.getTime().getTime();
    }


    public static String getRequest(String request) {
        URLConnection s;
        String line;
        String req = "";
        try {
            URL page = new URL(request);
            s = page.openConnection();
            s.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while ((line = in.readLine()) != null) {
                if (line.length() == 0) {
                    break;
                }
                req = line;
            }
            in.close();
        } catch (Exception ex) {
            req = ex.getMessage();
        }
        //} catch (MalformedURLException e) {
        //} catch (IOException e) {
        return req;
    }

    public static String getFullRequest(String request) {
        URLConnection s;
        String line;
        String req = "";
        try {
            URL page = new URL(request);
            s = page.openConnection();
            s.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while ((line = in.readLine()) != null) {
                if (line.length() == 0) {
                    break;
                }
                req += line;
            }
            in.close();
        } catch (Exception ex) {
            req = ex.getMessage();
        }
        //} catch (MalformedURLException e) {
        //} catch (IOException e) {
        return req;
    }

    public static String DateFormat(Date dttm) {
        if (dttm == null) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dttm);
        int y = c.get(Calendar.YEAR) - 1900;
        if (y == 0) {
            return "-";
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(dttm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }


    public static String getPassTime(Integer sec) {
        //var myYear=0;
        //var myMonth=0;
        if (sec == null) {
            return "-";
        }
        double mySecond = sec;
        int myDay = (int) (sec / (24 * 3600));
        int myHour = (int) ((mySecond - (myDay * 24 * 3600)) / 3600);
        int myMinute = (int) ((mySecond - ((myDay * 24 * 3600) + (myHour * 3600))) / 60);
        mySecond = (int) ((mySecond - ((myDay * 24 * 3600) + (myHour * 3600) + (myMinute * 60))));
        PassTime obj = new PassTime();
        obj.setDay(myDay);
        obj.setHour(myHour);
        obj.setMinute(myMinute);
        obj.setSecond((int) mySecond);
        return obj.getDay() + ":" + obj.getHour() + ":" + obj.getMinute() + ":" + obj.getSecond();
    }

    public static int convertHexToInt(String hexValue) {
        if (hexValue.indexOf("#") != -1) {
            hexValue = hexValue.replace("#", "");
        }
        Long d = Long.parseLong(hexValue, 16);
        return d.intValue();
    }

    public static String convertIntToHexString(int sayi, int digit) {
        String deger = Integer.toHexString(sayi);
        while (deger.length() < digit) {
            deger = "0" + deger;
        }
        return deger.toUpperCase();//autoPadOrShrink(Integer.toHexString(sayi), 4);
    }

    public static String autoPadOrShrink(String strForm, int numberOfRequestedDigits) {
        int len = strForm.length();

        if (len > numberOfRequestedDigits) {
            return strForm.substring(len - numberOfRequestedDigits);
        } else {
            if (strForm.length() < numberOfRequestedDigits) {
                for (int i = 0; i < numberOfRequestedDigits - len; i++) {
                    strForm = '0' + strForm;
                }
            }
        }

        return strForm;
    }

    static int get_crc_ccit(int crc_buff, int input) {
        int i;
        short inp1;
        char x16, crc_buff1; /* we?ll use this to hold the XOR mask */

        for (i = 0; i < 8; i++) {
            /* XOR current D0 and next input bit to determine x16 value */
            crc_buff1 = (char) (crc_buff & 0x0001);
            inp1 = (short) (input & 0x01);

            if ((crc_buff1 ^ inp1) > 0) {
                x16 = (char) 0x8408;
            } else {
                x16 = 0x0000;
            }
            /* shift crc buffer */
            crc_buff = (char) (crc_buff >> 1);
            /* XOR in the x16 value */
            crc_buff ^= x16;
            /* shift input for next iteration */
            input = (short) (input >> 1);
        }
        return crc_buff;
    }

    public static void addSettings(String setting, List<String> settingList) {
        if (settingList.size() > 0) {
            String lastValue = settingList.get(settingList.size() - 1);
            String buf1 = lastValue + setting;
            if (buf1.length() > 500) {
                settingList.add(setting);
            } else {
                settingList.set(settingList.size() - 1, buf1);
            }

        } else {
            settingList.add(setting);
        }
    }

    public static void addSettings(String setting, List<String> settingList, Boolean control) {
        if (settingList.size() > 0) {
            String lastValue = settingList.get(settingList.size() - 1);
            String buf1 = lastValue + setting;

            if (buf1.length() > 500 || (!control)) {
                settingList.add(setting);
            } else {
                settingList.set(settingList.size() - 1, buf1);
            }

        } else {
            settingList.add(setting);
        }
    }

    public static String getKmCrc(String distance) {
        String kmSetting = "";

        String set1 = "08" + addZero(Integer.parseInt(distance), 6);
        Integer packetCRC = getSettingPacketCRC(set1);
        kmSetting = "SET" + set1 + convertIntToHexString(packetCRC, 4) + "#";

        set1 = "76" + addZero(Integer.parseInt(distance), 6);
        packetCRC = getSettingPacketCRC(set1);
        kmSetting = kmSetting + "SET" + set1 + convertIntToHexString(packetCRC, 4) + "#";

//		String setting = "08" + addZero(Integer.parseInt(distance),6);
//		kmSetting = "SET"+setting+convertIntToHexString(calculateCRC((short)0,setting.getBytes()),4)+"#";
//
//		setting = "76" + addZero(Integer.parseInt(distance),6);
//		kmSetting = kmSetting + "SET"+setting+convertIntToHexString(calculateCRC((short)0,setting.getBytes()),4)+"#";

        return kmSetting;
    }

    public static String addZero(int number, int count) {
        String numberStr = "";
        numberStr = Integer.toString(number);
        if (numberStr.length() < count) {
            while (numberStr.length() != count) {
                numberStr = "0" + numberStr;
            }
        }
        return numberStr;
    }

    public static Integer getSettingPacketCRC(byte[] b) {
        return calculateCRC((short) 0, b);
    }

    public static Integer getSettingPacketCRC(String settings) {
        return calculateCRC((short) 0, settings.getBytes());
    }

    public static int calculateCRC(int initCrc, byte[] b) {
        /*short anUnsignedByte = 0;
         int anUnsignedShort = 0;
         int firstByte = 0;
         int secondByte = 0;
         byte []buf=new byte[2];

         for (int i=0;i<b.length;i++){
         if (i==0){
         buf=shortToByteArray(initCrc);
         firstByte = (0x000000FF & ((int)buf[0]));
         secondByte = (0x000000FF & ((int)buf[1]));
         anUnsignedShort  = (char) (firstByte << 8 | secondByte);
         }
         anUnsignedByte=(short)(0x000000FF & ((int)b[i]));
         if (anUnsignedByte<0)
         anUnsignedByte+=256;
         //System.out.print(anUnsignedByte+" ");
         anUnsignedShort=get_crc_ccit(anUnsignedShort,anUnsignedByte);
         }*/

        int resultcrc = initCrc;
        for (int i = 0; i < b.length; i++) {
            resultcrc = get_crc_ccit(resultcrc, b[i]);
        }

        return resultcrc;
    }

    public static String ReverseID(String id) {
        int i = -1, len = -1;
        String result = "";
        len = id.length();
        for (i = len; i >= 1; i--) {
            if (i == 1) break;
            if (i % 2 == 0) result = result + id.charAt(i - 2) + id.charAt(i - 1);
        }
        return result;
    }

    public static String getParam(HttpServletRequest request, String param, String def) {
        String parameter = request.getParameter(param);
        if (parameter == null || "".equals(parameter)) {
            return def;
        } else {
            return parameter;
        }
    }

    public static String ObjectControl(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }

    public static byte[] intToByteArray(int i) {
        //convert integer to byte array
        byte[] dword = new byte[4];
        dword[0] = (byte) (i & 0x00FF);
        dword[1] = (byte) ((i >> 8) & 0x000000FF);
        dword[2] = (byte) ((i >> 16) & 0x000000FF);
        dword[3] = (byte) ((i >> 24) & 0x000000FF);
        return dword;
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static Object getClientValueControl(String value, Class type) {
        if (type.getName().contains("Byte") && !value.toString().equals("")) {
            return Byte.parseByte(value);
        } else if (type.getName().contains("Short") && value != null && !value.toString().equals("")) {
            return Short.parseShort(value);
        } else if (type.getName().contains("Integer") && value != null && !value.toString().equals("")) {
            return Integer.parseInt(value);
        } else if (type.getName().contains("Long") && value != null && !value.toString().equals("")) {
            return Long.parseLong(value);
        } else if (type.getName().contains("Double") && value != null && !value.toString().equals("")) {
            return Double.parseDouble(value);
        }
        return null;

    }

    public static void logWrite(Class aClass, String logLevel, Throwable obj) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(aClass);
        if (logLevel.equals("TRACE")) {
            logger.trace(aClass.getName(), obj);
        } else if (logLevel.equals("WARN")) {
            logger.warn(aClass.getName(), obj);
        } else if (logLevel.equals("DEBUG")) {
            logger.debug(aClass.getName(), obj);
        } else {
            logger.warn(logLevel, obj);

        }

    }

  /*  public static boolean isUserAdmin(User user) {
        if (user != null && user.getUserType() != null
            && (user.getUserType().equals() )) {
            return true;
        }
        return false;
    }

    public static boolean isBasicUser(User user) {
        if (user != null && user.getUserType() != null
            && (user.getUserType().intValue() > 3)) {
            return true;
        }
        return false;
    }*/

    public static Throwable getRealError(Throwable error) {
        if (error != null && error.getCause() != null) {
            error = getRealError(error.getCause());
        }
        return error;
    }

    public static List<Integer> StringArrayToIntegerList(String[] strList) {
        List<Integer> result = new ArrayList<>();
        if (strList != null) {
            for (String str1 : strList) {
                result.add(Integer.parseInt(str1));
            }
        }
        return result;
    }

    public static List<String> StringArrayToStringList(String[] strList) {
        List<String> result = new ArrayList<>();
        if (strList != null) {
            for (String str1 : strList) {
                result.add(str1);
            }
        }
        return result;
    }

    public static Integer[] StringArrayToIntegerArray(String[] selectedDays) {
        Integer[] result = new Integer[selectedDays.length];
        for (int i = 0; i < selectedDays.length; i++) {
            result[i] = Integer.parseInt(selectedDays[i]);
        }
        return result;
    }

    public static String IntegerListToString(List<Integer> pointList) {
        String result = "";
        for (Integer deger : pointList) {
            result += deger + ",";
        }
        return result;
    }

    public static List<Integer> StringArrayToIntegerList(String strList) {
        List<Integer> result = new ArrayList<>();
        if (strList != null) {
            for (String str1 : strList.split(",")) {
                result.add(Integer.parseInt(str1));
            }
        } else
            return null;

        return result;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();

        } catch (IOException e) {

        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();

    }

    public static byte[] decompressBytes(byte[] data) throws Exception {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
    }

    public static String converSessiz(String text) {
         String metin=text.replaceAll(" ","").toUpperCase();
         List<String> sesli=Arrays.asList("Ö","Ü","İ","Ş","Ç","Ğ");
         List<String> sessiz=Arrays.asList("O","U","I","S","C","G");

        for (String i:sesli){
            if(metin.contains(i)){
                int getCount=sesli.stream().collect(Collectors.toList()).indexOf(i);
                metin=metin.replaceAll(i,sessiz.get(getCount));
            }
        }
        return metin;
    }

}
