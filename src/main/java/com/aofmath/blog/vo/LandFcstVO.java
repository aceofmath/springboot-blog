package com.aofmath.blog.vo;

import lombok.Data;

@Data
public class LandFcstVO {

	private String announceTime	= "";	//발표시간(년월일시분)
	private String numEf = "";			//발효번호(발표시간기준)
	private String regId = "";			//예보구역코드
	private String rnSt = "";			//강수확률
	private String rnYn = "";			//강수형태(0 : 강수없음, 1 : 비, 2 : 비/눈, 3: 눈, 4: 소나기)
	private String ta = "";				//예상기온(℃)
	private String wd1 = "";			//풍향(1) ex. NW
	private String wd2 = "";			//풍향(2) ex. N
	private String wdTnd = "";			//풍향연결코드(1 : (-) 2 : 후)
	private String wf = "";				//날씨
	private String wfCd = "";			//날씨코드(하늘상태)(DB01 : 맑음, DB03 : 구름많음, DB04 : 흐림)
	private String wsIt = "";			//풍속 강도코드(1 : 약간 강, 2 : 강, 3 : 매우 강)
}
