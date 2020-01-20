package com.project.messagesend;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * @class ExampleSend
 * @brief This sample code demonstrate how to send sms through CoolSMS Rest API PHP
 */

public class CodeMessage {
    public static void sms_send(String phoneNum,String send_message) {
        String api_key = "NCSFAQPHTHUSHQL4";
    String api_secret = "IMP7IBZJK1SLFWA0EX1YZF3WQYHW4RGQ";
    Message coolsms = new Message(api_key, api_secret);

    // 4 params(to, from, type, text) are mandatory. must be filled
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("to", phoneNum);
    params.put("from", "010-9293-5618");
    params.put("type", "SMS");
    params.put("text", send_message);
    params.put("app_version", "test app 1.2"); // application name and version

    try {
      JSONObject obj = (JSONObject) coolsms.send(params);
      System.out.println(obj.toString());
    } catch (CoolsmsException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getCode());
    }
    }
}