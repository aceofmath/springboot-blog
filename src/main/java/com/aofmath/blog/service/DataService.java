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

import com.aofmath.blog.vo.LandFcstVO;
import com.aofmath.blog.vo.RestDeVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataService {
	
	//1.특일 정보제공
	private static String REST_DE_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
	
	//2.육상예보조회
	private static String LAND_FCST_URL = "http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst";
	
	private static String SERVICE_KEY = "4x6L44%2FdIc6Yu6RlJNVIO31KRHjZjuv4f5%2FokcbiCbqfNSUrL98tRmKOry%2FvAZCk53LR%2FHLcbMEbhEF5ccpByw%3D%3D";
	
	@SuppressWarnings("null")
	@Transactional
	public List<RestDeVO> restDeInfo(String yyyy)  throws Exception { // title, content
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// 서비스명 : 특일 정보제공 서비스 > 일반 인증키 재발급 받을 것
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(REST_DE_URL); /* URL */
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
	    
	    List<RestDeVO> list = new ArrayList<RestDeVO>();
	    Gson gson = new Gson();
	    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<RestDeVO>>() {}.getType());
	    
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
	
	@Transactional
	public List<LandFcstVO> landFcst(String regId)  throws Exception { // title, content
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// 서비스명 : 육상예보조회 서비스 > 일반 인증키 재발급 받을 것
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(LAND_FCST_URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /* return 타입 JSON */
		urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(regId, "UTF-8")); /*  */

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
	    
	    obj = parser.parse(sb.toString());
	    
	    log.info("landFcst : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>obj.toString():{}", obj.toString());
	    
	    // json 데이터 response > body > items > item(list) 형식으로 되어있음
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
	    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("body");
	    JSONObject jsonObj4 = (JSONObject) jsonObj3.get("items");
	    JSONArray jsonArray = (JSONArray) jsonObj4.get("item");
	    
	    List<LandFcstVO> list = new ArrayList<LandFcstVO>();
	    Gson gson = new Gson();
	    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<LandFcstVO>>() {}.getType());
		
		log.info("landfcst : 육상예보조회>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>list:{}", list);

		return list;
	}
	
}
