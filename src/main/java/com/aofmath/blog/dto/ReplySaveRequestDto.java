package com.aofmath.blog.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRequestDto {
	@ApiModelProperty(name="사용자ID", example = "1")
	private int userId;
	
	@ApiModelProperty(name="게시글ID", example = "1")
	private int boardId;
	
	@ApiModelProperty(name="댓글", example = "댓글")
	private String content;
}
