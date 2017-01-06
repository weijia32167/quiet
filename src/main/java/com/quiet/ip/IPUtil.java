package com.quiet.ip;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2015/12/25
 * Desc   :
 */
public final class IPUtil {


    public static final long Start_A_Network = 16777217L;    //1.0.0.1
    public static final long END_A_Network = 2147483646L;    //127.255.255.254

    public static final long Start_B_Network = 2147483649L;    //128.0.0.1
    public static final long END_B_Network = 3221225470L;     //191.255.255.254

    public static final long Start_C_Network = 3221225473L;    //192.0.0.1
    public static final long END_C_Network = 3758096382L;    //223.255.255.254

    public static final long Start_A_PrivateNetwork = 167772160L;   //10.0.0.0
    public static final long End_A_PrivateNetwork = 184549375L;     //10.255.255.255

    public static final long Start_B_PrivateNetwork = 2886729728L;  // 172.16.0.0
    public static final long End_B_PrivateNetwork = 2887778303L;    // 172.16.0.0-172.31.255.255

    public static final long Start_C_PrivateNetwork = 3232235520L;   //192.168.0.0
    public static final long End_C_PrivateNetwork = 3232301055L;     //192.168.255.255

    private static final long Local_Loopback = 2130706433L;          // 127.0.0.1

    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    /**
     * IPv4 ---> Number
     * It's used by compare
     * example:
     *      10.10.121.234  ---> 2814749934909930
     */
    public static long convert(String clientIP) {
        String[] ipBits = clientIP.split("\\.");
        long result = 0;
        result  += (Long.parseLong(ipBits[0])<<24);
        result  += (Long.parseLong(ipBits[1])<<16);
        result  += (Long.parseLong(ipBits[2])<<8);
        result  += Long.parseLong(ipBits[3]);
        return result;
    }

    /**
     * Number---> IpV4
     * 右移24位，右移时高位补0，得到的数字即为第一段IP
     * 右移16位，右移时高位补0，得到的数字即为第二段IP
     * 右移8位，右移时高位补0，得到的数字即为第三段IP
     * 最后一段的为第四段IP
     * example:
     *     2814749934909930  --->  10.10.121.234
     */
    public static String convert(long ipNumber) {
        return ((ipNumber>>24) & 0xFF) + "." + ((ipNumber>>16) & 0xFF) + "." + ((ipNumber>>8) & 0xFF) + "." + (ipNumber & 0xFF);
    }

    /**
     * 判断IPv4地址是否为私有网络地址
     */
    public static boolean isPrivateNetwork(String clientIP) throws IllegalArgumentException{
        checkIPv4(clientIP);
        long clientIPValue = convert(clientIP);
        if(Start_A_PrivateNetwork<=clientIPValue&&clientIPValue<=End_A_PrivateNetwork ||
           Start_B_PrivateNetwork<=clientIPValue&&clientIPValue<=End_B_PrivateNetwork ||
           Start_C_PrivateNetwork<=clientIPValue&&clientIPValue<=End_C_PrivateNetwork ){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 判断IPv4地址是否为本地回环地址(127.0.0.1)
     */
    public static boolean isLocalLoopback(String clientIP) throws IllegalArgumentException{
        checkIPv4(clientIP);
        long clientIPValue = convert(clientIP);
        if(Local_Loopback == clientIPValue){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 判断当前地址是否为IPv4地址。
     * 如果是，正常返回；
     * 否则返回false
     */
    public static boolean isIPv4(String clientIP) throws IllegalArgumentException{
        Matcher ipv4Matcher = IPV4_PATTERN.matcher(clientIP);
        return ipv4Matcher.matches();
    }
    /**
     * 判断当前地址是否为IPv4地址。
     * 如果是，正常返回；
     * 否则抛出IllegalArgumentException异常
     */
    public static void checkIPv4(String clientIP) throws IllegalArgumentException{
         if(!isIPv4(clientIP)){
             throw new IllegalArgumentException("clientIP is not IPV4!");
         }
    }

    /**
     * 随机生成IPV4公网地址
     * */
    public static String randomPublicNetworkIPv4(){
        int type = ThreadLocalRandom.current().nextInt(0,3);
        String result = null;
        switch (type){
            case 0: //A端公网
                result = randomPublicNetworkIPv4ForA();
                break;
            case 1://B端公网
                result = randomPublicNetworkIPv4ForB();
                break;
            case 2://C端公网
                result = randomPublicNetworkIPv4ForC();
                break;
        }
        return result;
    }
    public static String randomPublicNetworkIPv4ForA(){
        long value = ThreadLocalRandom.current().nextLong(Start_A_Network,END_A_Network+1L);
        String result = convert(value);
        if(isLocalLoopback(result) || isPrivateNetwork(result)){
           return randomPublicNetworkIPv4ForA();
        }
        return result;
    }
    public static String randomPublicNetworkIPv4ForB(){
        long value = ThreadLocalRandom.current().nextLong(Start_B_Network,END_B_Network+1L);
        String result = convert(value);
        if(isPrivateNetwork(result)){
            return randomPublicNetworkIPv4ForB();
        }
        return convert(value);
    }
    public static String randomPublicNetworkIPv4ForC(){
        long value = ThreadLocalRandom.current().nextLong(Start_C_Network,END_C_Network+1L);
        String result = convert(value);
        if(isPrivateNetwork(result)){
           return randomPublicNetworkIPv4ForC();
        }
        return convert(value);
    }






}
