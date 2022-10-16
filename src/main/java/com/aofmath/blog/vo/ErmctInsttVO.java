package com.aofmath.blog.vo;

import lombok.Data;

@Data
public class ErmctInsttVO {

	private String rnum	= "";					//일련번호
	private String dutyAddr = "";			//주소
	private String dutyEtc	= "";				//비고
	private String dutyInf = "";				//기관설명상세
	private String dutyMapimg = "";		//간이약도
	private String dutyName = "";			//기관명
	private String dutyTel1	 = "";				//대표전화1
	private String dutyTime1c = "";			//진료시간(월)C
	private String dutyTime2c = "";			//진료시간(화)C
	private String dutyTime3c = "";			//진료시간(수)C
	private String dutyTime4c = "";			//진료시간(목)C
	private String dutyTime5c = "";			//진료시간(금)C
	private String dutyTime6c = "";			//진료시간(토)C
	private String dutyTime7c = "";			//진료시간(일)C
	private String dutyTime8c = "";			//진료시간(공)C
	private String dutyTime1s = "";			//진료시간(월)S
	private String dutyTime2s = "";			//진료시간(화)S
	private String dutyTime3s = "";			//진료시간(수)S
	private String dutyTime4s = "";			//진료시간(목)S
	private String dutyTime5s = "";			//진료시간(금)S
	private String dutyTime6s = "";			//진료시간(토)S
	private String dutyTime7s = "";			//진료시간(일)S
	private String dutyTime8s = "";			//진료시간(공)S
	private String hpid = "";					//기관ID
	private String postCdn1 = "";			//우편번호1
	private String postCdn2 = "";			//우편번호2
	private String wgs84Lon = "";			//병원경도
	private String wgs84Lat = "";			//병원위도
}
