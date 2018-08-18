package com.baidu.translate.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5缂栫爜鐩稿叧鐨勭被
 * 
 * @author wangjingtao
 * 
 */
public class MD5 {
    // 棣栧厛鍒濆鍖栦竴涓瓧绗︽暟缁勶紝鐢ㄦ潵瀛樻斁姣忎釜16杩涘埗瀛楃
    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * 鑾峰緱涓�涓瓧绗︿覆鐨凪D5鍊�
     * 
     * @param input 杈撳叆鐨勫瓧绗︿覆
     * @return 杈撳叆瀛楃涓茬殑MD5鍊�
     * @throws UnsupportedEncodingException 
     * 
     */
    public static String md5(String input) throws UnsupportedEncodingException {
        if (input == null)
            return null;

        try {
            // 鎷垮埌涓�涓狹D5杞崲鍣紙濡傛灉鎯宠SHA1鍙傛暟鎹㈡垚鈥漇HA1鈥濓級
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 杈撳叆鐨勫瓧绗︿覆杞崲鎴愬瓧鑺傛暟缁�
            byte[] inputByteArray = input.getBytes("utf-8");
            // inputByteArray鏄緭鍏ュ瓧绗︿覆杞崲寰楀埌鐨勫瓧鑺傛暟缁�
            messageDigest.update(inputByteArray);
            // 杞崲骞惰繑鍥炵粨鏋滐紝涔熸槸瀛楄妭鏁扮粍锛屽寘鍚�16涓厓绱�
            byte[] resultByteArray = messageDigest.digest();
            // 瀛楃鏁扮粍杞崲鎴愬瓧绗︿覆杩斿洖
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 鑾峰彇鏂囦欢鐨凪D5鍊�
     * 
     * @param file
     * @return
     */
    public static String md5(File file) {
        try {
            if (!file.isFile()) {
                System.err.println("鏂囦欢" + file.getAbsolutePath() + "涓嶅瓨鍦ㄦ垨鑰呬笉鏄枃浠�");
                return null;
            }

            FileInputStream in = new FileInputStream(file);

            String result = md5(in);

            in.close();

            return result;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String md5(InputStream in) {

        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                messagedigest.update(buffer, 0, read);
            }

            in.close();

            String result = byteArrayToHex(messagedigest.digest());

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String byteArrayToHex(byte[] byteArray) {
        // new涓�涓瓧绗︽暟缁勶紝杩欎釜灏辨槸鐢ㄦ潵缁勬垚缁撴灉瀛楃涓茬殑锛堣В閲婁竴涓嬶細涓�涓猙yte鏄叓浣嶄簩杩涘埗锛屼篃灏辨槸2浣嶅崄鍏繘鍒跺瓧绗︼紙2鐨�8娆℃柟绛変簬16鐨�2娆℃柟锛夛級
        char[] resultCharArray = new char[byteArray.length * 2];
        // 閬嶅巻瀛楄妭鏁扮粍锛岄�氳繃浣嶈繍绠楋紙浣嶈繍绠楁晥鐜囬珮锛夛紝杞崲鎴愬瓧绗︽斁鍒板瓧绗︽暟缁勪腑鍘�
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 瀛楃鏁扮粍缁勫悎鎴愬瓧绗︿覆杩斿洖
        return new String(resultCharArray);

    }

}
