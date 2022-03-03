package com.aofmath.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aofmath.blog.config.auth.PrincipalDetail;
import com.aofmath.blog.dto.ReplySaveRequestDto;
import com.aofmath.blog.dto.ResponseDto;
import com.aofmath.blog.model.Board;
import com.aofmath.blog.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "게시글API")
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	@ApiOperation(value = "게시글 등록", notes = "게시글 등록합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "board", value = "게시글 정보", required = true, dataType = "Board")})
	public ResponseDto<Integer> save(@RequestBody Board board) {
//	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		PrincipalDetail principal = (PrincipalDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@GetMapping("/api/board/{id}")
	@ApiOperation(value = "게시글 조회", notes = "게시글 조회합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "게시글 번호", required = true, dataType = "string", defaultValue = "1")})
	public ResponseDto<Board> get(@PathVariable int id){
		System.out.println("BoardApiController : update : id : "+id);
		return new ResponseDto<Board>(HttpStatus.OK.value(), boardService.글상세보기(id));
	}
	
	@PutMapping("/api/board/{id}")
	@ApiOperation(value = "게시글 수정", notes = "게시글 수정합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "게시글 번호", required = true, dataType = "string", defaultValue = "1"),
		@ApiImplicitParam(name = "board", value = "게시글 정보", required = true, dataType = "Board")
	})
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		System.out.println("BoardApiController : update : id : "+id);
		System.out.println("BoardApiController : update : board : "+board.getTitle());
		System.out.println("BoardApiController : update : board : "+board.getContent());
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	@ApiOperation(value = "게시글 삭제", notes = "게시글 삭제합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "게시글 번호", required = true, dataType = "string", defaultValue = "1")})
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	// 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
	// dto 사용하지 않은 이유는!! 
	@PostMapping("/api/board/{boardId}/reply")
	@ApiOperation(value = "댓글 등록", notes = "댓글 등록합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "replySaveRequestDto", value = "댓글 정보", required = true, dataType = "ReplySaveRequestDto")})
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	@ApiOperation(value = "댓글 삭제", notes = "댓글 삭제합니다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "replyId", value = "댓글ID", required = true, dataType = "string", defaultValue = "1")})
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
}



