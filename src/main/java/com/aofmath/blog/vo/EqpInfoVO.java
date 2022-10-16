package com.aofmath.blog.vo;

import lombok.Data;

@Data
public class EqpInfoVO {

	private String yadmNm = "";    						//병원명(병원명)
	private String clCd = "";    							//종별 코드(종별 코드)
	private String clCdNm = "";    						//종별 코드명(종별 코드명)
	private String orgTyCd = "";    						//설립 구분(설립 구분)
	private String orgTyCdNm = "";    					//설립 구분명(설립 구분명)
	private String sidoCd = "";    						//시도코드(시도코드)
	private String sidoCdNm = "";    					//시도명(시도명)
	private String sgguCd = "";    						//시군구코드(시군구코드)
	private String sgguCdNm = "";    					//시군구명(시군구명)
	private String emdongNm = "";    					//읍면동명(읍면동명)
	private String postNo = "";    						//우편번호(우편번호)
	private String addr = "";    							//주소(주소)
	private String telno = "";    							//전화번호(전화번호)
	private String hghrSickbdCnt = "";    				//일반입원실상급병상수(일반입원실상급병상수)
	private String stdSickbdCnt = "";    				//일반입원실일반병상수(일반입원실일반병상수)
	private String aduChldSprmCnt = "";  			//성인중환자병상수(성인중환자병상수)
	private String nbySprmCnt = "";    				//신생아중환자병상수(신생아중환자병상수)
	private String partumCnt = "";    					//분만실병상수(분만실병상수)
	private String soprmCnt = "";    					//수술실병상수(수술실병상수)
	private String emymCnt = "";    					//응급실병상수(응급실병상수)
	private String ptrmCnt = "";    						//물리치료실병상수(물리치료실병상수)
	private String hospUrl = "";    						//홈페이지(홈페이지)
	private String estbDd = "";    						//개설일자(개설일자)
	private String chldSprmCnt = "";    				//소아중환자병상수(소아중환자병상수)
	private String psydeptClsHigSbdCnt = "";    	//정신과폐쇄상급병상수(정신과폐쇄상급병상수)
	private String psydeptClsGnlSbdCnt = "";    	//정신과폐쇄일반병상수(정신과폐쇄일반병상수)
	private String isnrSbdCnt = "";    					//격리실병상수(격리실병상수)
	private String anvirTrrmSbdCnt = "";    			//무균치료실병상수(무균치료실병상수)

}
