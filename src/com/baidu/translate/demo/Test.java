package com.baidu.translate.demo;

import java.io.UnsupportedEncodingException;

public class Test {
	 // ��ƽ̨�����APP_ID ��� http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20180303000130609";
    private static final String SECURITY_KEY = "IlSpHO1Vh_yMDL75vyhp";

    public static void main(String[] args) throws UnsupportedEncodingException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "do";
        System.out.println(api.getTransResult(query, "auto", "auto"));
    }
}
