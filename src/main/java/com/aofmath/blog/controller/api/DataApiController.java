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
import com.aofmath.blog.vo.DgsbjtInfoVO;
import com.aofmath.blog.vo.EqpInfoVO;
import com.aofmath.blog.vo.ErmctInsttVO;
import com.aofmath.blog.vo.HospAsmVO;
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
	
	@GetMapping("/data/ermctInstt")
	@ApiOperation(value = "전국약국정보조회", notes = "전국약국정보조회")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "pageNo", value = "1", required = true, dataType = "string", defaultValue = "1"),
			@ApiImplicitParam(name = "numOfRows", value = "100", required = true, dataType = "string", defaultValue = "100")})
	@ResponseBody
	public ResponseDto<List<ErmctInsttVO>> ermctInstt(@RequestParam("pageNo") String pageNo, @RequestParam("numOfRows") String numOfRows) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>pageNo{}", pageNo);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>numOfRows{}", numOfRows);
		
		return new ResponseDto<List<ErmctInsttVO>>(HttpStatus.OK.value(), dataService.ermctInstt(pageNo, numOfRows));
	}
	
	@GetMapping("/data/hospAsm")
	@ApiOperation(value = "병원평가상세등급조회", notes = "병원평가상세등급조회")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "pageNo", value = "1", required = true, dataType = "string", defaultValue = "1"),
			@ApiImplicitParam(name = "numOfRows", value = "100", required = true, dataType = "string", defaultValue = "100")})
	@ResponseBody
	public ResponseDto<List<HospAsmVO>> hospAsm(@RequestParam("pageNo") String pageNo, @RequestParam("numOfRows") String numOfRows) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>pageNo{}", pageNo);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>numOfRows{}", numOfRows);
		
		return new ResponseDto<List<HospAsmVO>>(HttpStatus.OK.value(), dataService.hospAsm(pageNo, numOfRows));
	}
	
	@GetMapping("/data/eqpInfo")
	@ApiOperation(value = "의료기관별상세정보서비스(시설정보)", notes = "의료기관별상세정보서비스(시설정보)")
	@ApiImplicitParams({@ApiImplicitParam(name = "ykiho", value = "암호화된 요양기호", required = true, dataType = "string", defaultValue = "JDQ4MTYyMiM1MSMkMiMkMCMkMDAkNDgxOTYxIzMxIyQxIyQ3IyQ4MiQ0NjEwMDIjODEjJDEjJDYjJDgz")})
	@ResponseBody
	public ResponseDto<List<EqpInfoVO>> eqpInfo(@RequestParam("ykiho") String ykiho) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ykiho{}", ykiho);
		
		return new ResponseDto<List<EqpInfoVO>>(HttpStatus.OK.value(), dataService.eqpInfo(ykiho));
	}
	
	@GetMapping("/data/dgsbjtInfo")
	@ApiOperation(value = "의료기관별상세정보서비스(진료과목정보)", notes = "의료기관별상세정보서비스(진료과목정보)")
	@ApiImplicitParams({@ApiImplicitParam(name = "ykiho", value = "암호화된 요양기호", required = true, dataType = "string", defaultValue = "JDQ4MTYyMiM1MSMkMiMkMCMkMDAkNDgxOTYxIzMxIyQxIyQ3IyQ4MiQ0NjEwMDIjODEjJDEjJDYjJDgz")})
	@ResponseBody
	public ResponseDto<List<DgsbjtInfoVO>> dgsbjtInfo(@RequestParam("ykiho") String ykiho) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>ykiho{}", ykiho);
		
		return new ResponseDto<List<DgsbjtInfoVO>>(HttpStatus.OK.value(), dataService.dgsbjtInfo(ykiho));
	}
}
