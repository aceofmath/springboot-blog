package com.aofmath.blog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aofmath.blog.dto.ResponseDto;
import com.aofmath.blog.service.DataService;
import com.aofmath.blog.vo.LandFcstVO;
import com.aofmath.blog.vo.RestDeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "공공데이터포털API")
@Slf4j
@RestController
public class DataApiController {
	
	@Autowired
	private DataService dataService;
	
	@GetMapping("/data/restDeInfo")
	@ApiOperation(value = "공휴일 조회", notes = "공휴일 조회합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "yyyy", value = "해당년", required = true, dataType = "string", defaultValue = "2022")})
	@ResponseBody
	public ResponseDto<List<RestDeVO>> restDeInfo(@RequestParam("yyyy") String yyyy) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>{}", yyyy);
		
		return new ResponseDto<List<RestDeVO>>(HttpStatus.OK.value(), dataService.restDeInfo(yyyy));
	}
	
	@GetMapping("/data/landFcst")
	@ApiOperation(value = "육상예보조회", notes = "육상예보 조회합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "regId", value = "서울(11B10101)", required = true, dataType = "string", defaultValue = "11B10101")})
	@ResponseBody
	public ResponseDto<List<LandFcstVO>> landFcst(@RequestParam("regId") String regId) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>regId{}", regId);
		
		return new ResponseDto<List<LandFcstVO>>(HttpStatus.OK.value(), dataService.landFcst(regId));
	}
}
