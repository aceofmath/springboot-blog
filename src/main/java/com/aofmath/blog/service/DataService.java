package com.aofmath.blog.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aofmath.blog.vo.DgsbjtInfoVO;
import com.aofmath.blog.vo.EqpInfoVO;
import com.aofmath.blog.vo.ErmctInsttVO;
import com.aofmath.blog.vo.HospAsmVO;
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
	
	//3.국립중앙의료원_전국 약국 정보 조회 서비스
	private static String ERMCT_INSTT_URL = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown";
	
	//4.건강보험심사평가원_병원평가정보서비스
	private static String HOSP_ASM_URL = "http://apis.data.go.kr/B551182/hospAsmInfoService/getHospAsmInfo";
		
	//5-1.건강보험심사평가원_의료기관별상세정보서비스(시설정보)
	private static String EQP_INFO_URL = "http://apis.data.go.kr/B551182/MadmDtlInfoService/getEqpInfo";
	
	//5-2.건강보험심사평가원_의료기관별상세정보서비스(진료과목정보)
	private static String DGSBJT_INFO_URL = "http://apis.data.go.kr/B551182/MadmDtlInfoService/getDgsbjtInfo";
		
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
	    
	    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sb.toString():{}", sb.toString());
	    
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
	
	
	@Transactional
	public List<ErmctInsttVO> ermctInstt(String pageNo, String numOfRows)  throws Exception { // title, content
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// 서비스명 : 육상예보조회 서비스 > 일반 인증키 재발급 받을 것
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(ERMCT_INSTT_URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		System.out.println("Response code: " + conn.getResponseCode());

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
		
//		log.info("ermctInstt : >>>>>>>>>>>>>{}", sb.toString());
		
		//XML to JSONObject 변환
		org.json.JSONObject json = XML.toJSONObject(sb.toString());
		
		/////////////	json 데이터 가공 start	////////////////////
		JSONParser parser = new JSONParser();
	    Object obj = null;
	    
	    obj = parser.parse(json.toString());
	    
	    // json 데이터 response > body > items > item(list) 형식으로 되어있음
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
	    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("body");
	    JSONObject jsonObj4 = (JSONObject) jsonObj3.get("items");
	    JSONArray jsonArray = (JSONArray) jsonObj4.get("item");
	    
	    List<ErmctInsttVO> list = new ArrayList<ErmctInsttVO>();
	    Gson gson = new Gson();
	    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<ErmctInsttVO>>() {}.getType());
		
		log.info("ermctInstt : 전국약국정보>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>list:{}", list);

		return list;
	}
	
	
	@Transactional
	public List<HospAsmVO> hospAsm(String pageNo, String numOfRows)  throws Exception { // title, content
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(HOSP_ASM_URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		System.out.println("Response code: " + conn.getResponseCode());

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
		
		//XML to JSONObject 변환
		org.json.JSONObject json = XML.toJSONObject(sb.toString());
		
		/////////////	json 데이터 가공 start	////////////////////
		JSONParser parser = new JSONParser();
	    Object obj = null;
	    
	    obj = parser.parse(json.toString());
	    
	    // json 데이터 response > body > items > item(list) 형식으로 되어있음
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
	    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("body");
	    JSONObject jsonObj4 = (JSONObject) jsonObj3.get("items");
	    JSONArray jsonArray = (JSONArray) jsonObj4.get("item");
	    
	    List<HospAsmVO> list = new ArrayList<HospAsmVO>();
	    Gson gson = new Gson();
	    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<HospAsmVO>>() {}.getType());
		
//		log.info("hospAsm : 병원평가상세등급조회>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>list:{}", list);

		return list;
	}
	
	@Transactional
	public List<EqpInfoVO> eqpInfo(String ykiho)  throws Exception {
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(EQP_INFO_URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("ykiho", "UTF-8") + "=" + URLEncoder.encode(ykiho, "UTF-8")); /*암호화된 요양기호*/
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* return 타입 JSON */

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
	    
	    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sb.toString():{}", sb.toString());
	    
	    obj = parser.parse(sb.toString());
	    
	    log.info("eqpInfo : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>obj.toString():{}", obj.toString());
	    
	    // json 데이터 response > body > items > item(list) 형식으로 되어있음
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
	    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("body");
	    JSONObject jsonObj4 = (JSONObject) jsonObj3.get("items");
	    long totalCount = (long)jsonObj3.get("totalCount");
	    List<EqpInfoVO> list = new ArrayList<EqpInfoVO>();
	    Gson gson = null;
	    EqpInfoVO eqpInfo = null;
	    
	    log.info("eqpInfo : 의료기관별상세정보서비스(시설정보)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>totalCount:{}", totalCount);
	    
	    if( 1 < totalCount) {
	    	JSONArray jsonArray = (JSONArray) jsonObj4.get("item");	    
		    gson = new Gson();
		    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<EqpInfoVO>>() {}.getType());
	    } else {
	    	JSONObject jsonObj5 = (JSONObject) jsonObj4.get("item");
	    	eqpInfo = new EqpInfoVO();
	    	gson = new Gson();
	    	eqpInfo = gson.fromJson(jsonObj5.toString(), new TypeToken<EqpInfoVO>() {}.getType());
	    	list.add(eqpInfo);
	    }
	    
		
		log.info("eqpInfo : 의료기관별상세정보서비스(시설정보)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>list:{}", list);

		return list;
	}
	
	@Transactional
	public List<DgsbjtInfoVO> dgsbjtInfo(String ykiho)  throws Exception {
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(DGSBJT_INFO_URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("ykiho", "UTF-8") + "=" + URLEncoder.encode(ykiho, "UTF-8")); /*암호화된 요양기호*/
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* return 타입 JSON */

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
	    
	    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sb.toString():{}", sb.toString());
	    
	    obj = parser.parse(sb.toString());
	    
	    log.info("dgsbjtInfo : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>obj.toString():{}", obj.toString());
	    
	    // json 데이터 response > body > items > item(list) 형식으로 되어있음
	    JSONObject jsonObj = (JSONObject) obj;
	    JSONObject jsonObj2 = (JSONObject) jsonObj.get("response");
	    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("body");
	    JSONObject jsonObj4 = (JSONObject) jsonObj3.get("items");
	    long totalCount = (long)jsonObj3.get("totalCount");
	    List<DgsbjtInfoVO> list = new ArrayList<DgsbjtInfoVO>();
	    Gson gson = null;
	    DgsbjtInfoVO dgsbjtInfoVO = null;
	    
	    log.info("dgsbjtInfo : 의료기관별상세정보서비스(진료과목정보)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>totalCount:{}", totalCount);
	    
	    if( 1 < totalCount) {
	    	JSONArray jsonArray = (JSONArray) jsonObj4.get("item");	    
		    gson = new Gson();
		    list = gson.fromJson(jsonArray.toString(), new TypeToken<List<DgsbjtInfoVO>>() {}.getType());
	    } else {
	    	JSONObject jsonObj5 = (JSONObject) jsonObj4.get("item");
	    	dgsbjtInfoVO = new DgsbjtInfoVO();
	    	gson = new Gson();
	    	dgsbjtInfoVO = gson.fromJson(jsonObj5.toString(), new TypeToken<DgsbjtInfoVO>() {}.getType());
	    	list.add(dgsbjtInfoVO);
	    }
		
		log.info("dgsbjtInfo : 의료기관별상세정보서비스(진료과목정보)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>list:{}", list);

		return list;
	}
	
}
