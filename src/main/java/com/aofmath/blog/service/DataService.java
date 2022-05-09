package com.aofmath.blog.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aofmath.blog.util.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataService {
	
	private static String URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
	
	private static String SERVICE_KEY = "";
	
	@Transactional
	public String restDeInfo()  throws Exception { // title, content
		
		String result = "";
		
		//////////////apis.data.go.kr http 통신 /////////////////
		////////////// 공공데이터 포럼 api start /////////////////
		// api 키 갱신은 2년 주기로 되며 (https://www.data.go.kr/) 에서 발급 받을 수 있음.
		// 서비스명 : 특일 정보제공 서비스 > 일반 인증키 재발급 받을 것
		// id와 pw는 문의
		StringBuilder urlBuilder = new StringBuilder(URL); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(DateUtil.getToday("yyyy"), "UTF-8")); /* 연 */
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
		
		result = sb.toString();
		
		result = result.replace("1월1일", "신년").replace("기독탄신일", "성탄절");

		System.out.println("restDeInfo : 공공데이터포털 휴일>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + result);

		return result;
	}
}
