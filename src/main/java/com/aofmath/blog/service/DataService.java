package com.aofmath.blog.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aofmath.blog.vo.DataVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataService {
	
	private static String URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
	
	private static String SERVICE_KEY = "";
	
	@SuppressWarnings("null")
	@Transactional
	public List<DataVO> restDeInfo(String yyyy)  throws Exception { // title, content
		
		String result = "";
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// 서비스명 : 특일 정보제공 서비스 > 일반 인증키 재발급 받을 것
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(yyyy, "UTF-8")); /* 연 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* return 타입 json */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("99999", "UTF-8")); /* 해당 년에 모든값을 불러오기 위해 */

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		rd.close();
		conn.disconnect();
		////////////// apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api end ///////////////////

		/////////////	json 데이터 가공 start	////////////////////
	    JSONParser parser = new JSONParser();
	    Object obj = null;
	    // 1월1일 , 기독탄신일 > 신년,성탄절로 치환
	    obj = parser.parse(sb.toString().replace("1월1일", "신년").replace("기독탄신일", "성탄절"));
	    
	    log.info("restDeInfo : 공공데이터포털 휴일>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>obj:{}", obj.toString());
	    
	    // json 데이터 response > body > items > item(list) 형식으로 되어있음
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
	    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("body");
	    JSONObject jsonObj4 = (JSONObject) jsonObj3.get("items");
	    JSONArray jsonArray = (JSONArray) jsonObj4.get("item");
//	    JSONObject resultJsonObj = new JSONObject();
	    
	    log.info("restDeInfo : 공공데이터포털 휴일>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>jsonArray:{}", jsonArray.toString());
	    
	    List<DataVO> list = new ArrayList<DataVO>();
	    Gson gson = new Gson();
	    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<DataVO>>() {}.getType());
	    
//	    DataVO dataVO = null;
//	    List<DataVO> list = new ArrayList<DataVO>();
//	    Gson gson = new Gson();
//
//	    for (int i = 0; i < jsonArray.size(); i++) {
//	    	resultJsonObj = (JSONObject) jsonArray.get(i);
//	    	dataVO = gson.fromJson(resultJsonObj.toString(), DataVO.class);
//	    	list.add(dataVO);
//		}
	    
		
		log.info("restDeInfo : 공공데이터포털 휴일>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>list:{}", list);

		return list;
	}
}
