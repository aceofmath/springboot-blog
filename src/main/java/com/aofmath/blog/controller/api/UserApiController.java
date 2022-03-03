package com.aofmath.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aofmath.blog.config.auth.PrincipalDetail;
import com.aofmath.blog.dto.ResponseDto;
import com.aofmath.blog.model.User;
import com.aofmath.blog.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "회원API")
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	@ApiOperation(value = "회원 등록", notes = "회원 등록합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "user", value = "회원 정보", required = true, dataType = "User")})
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
	}
	
	@GetMapping("/user")
	@ApiOperation(value = "회원 조회", notes = "회원 조회합니다.")
	public ResponseDto<User> get() { // key=value, x-www-form-urlencoded
		PrincipalDetail principal = (PrincipalDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		return new ResponseDto<User>(HttpStatus.OK.value(), userService.회원찾기(principal.getUser().getUsername()));
	}

	@PutMapping("/user")
	@ApiOperation(value = "회원 수정", notes = "회원 수정합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "user", value = "회원 정보", required = true, dataType = "User")})
	public ResponseDto<Integer> update(@RequestBody User user) { // key=value, x-www-form-urlencoded
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
		// 세션 등록

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}
