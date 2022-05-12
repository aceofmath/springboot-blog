package com.aofmath.blog.vo;

import lombok.Data;

@Data
public class DataVO {

	private String locdate		= "";		//날짜
	private String seq		= "";			//순번
	private String dateKind		= "";		//종류
	private String isHoliday		= "";	//공공기관 휴일여부
	private String dateName		= "";		//명칭
	private String numOfRows		= "";	//페이지당항목수
	private String pageNo		= "";		//페이지
	private String totalCount		= "";	//모든항목수
}
