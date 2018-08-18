package com.imooc.test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.baidu.translate.demo.TransApi;
import com.imooc.translate.Result;
import com.imooc.translate.TransResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TranslateTest {

	 // ��ƽ̨�����APP_ID ��� http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20180303000130609";
    private static final String SECURITY_KEY = "IlSpHO1Vh_yMDL75vyhp";
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "do";
        System.out.println(api.getTransResult(query, "auto", "auto"));
        JSONObject jsonObject=JSONObject.fromObject(api.getTransResult(query, "auto", "auto"));
        String result="";
        if(jsonObject!=null){
        	if(jsonObject.has("error_code")){
        		result = "������룺"+jsonObject.getString("error_code")+"������ʾ��"+jsonObject.getString("error_msg");        		
        	}else{
        		Result res=(Result) JSONObject.toBean(jsonObject,Result.class);
                result="Ŀ���������ͣ�"+res.getFrom()+"\n"+"���������ͣ�"+res.getTo();
//                TransResult[] transResults=(TransResult[]) JSONArray.toArray(JSONArray.fromObject(res.getTrans_result()),TransResult.class);
//                for(TransResult transResult:transResults){
//                	result+=("Ŀ�����ԣ�"+transResult.getSrc()+"\n"+"��������"+transResult.getDst()+"\n");
//                }
                for(TransResult transResult:res.getTrans_result()){
                	result+=("Ŀ�����ԣ�"+transResult.getSrc()+"\n"+"��������"+transResult.getDst()+"\n");
                }
        	}
        }
      
        System.out.println(result);
        
	}
}
