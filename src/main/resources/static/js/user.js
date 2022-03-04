let index = {
		init: function(){
			$("#btn-save").on("click", ()=>{ // function(){} , ()=>{} this를 바인딩하기 위해서!! 
				this.save();
			});
			$("#btn-update").on("click", ()=>{ // function(){} , ()=>{} this를 바인딩하기 위해서!! 
				this.update();
			});
			$("#btn-postcode").on("click", ()=>{ // function(){} , ()=>{} this를 바인딩하기 위해서!! 
				this.postcode();
			});
			
		},

		save: function(){
			//alert('user의 save함수 호출됨');
			let data = {
					username: $("#username").val(),
					password: $("#password").val(),
					email: $("#email").val()
			};
			
			if($.trim(data.username) == ''){
				alert('ID를 입력하세요.');
				return;
			}
			
			if($.trim(data.password) == ''){
				alert('비밀번호를 입력하세요.');
				return;
			}
			
			if($.trim(data.email) == ''){
				alert('이메일을 입력하세요.');
				return;
			}
			
			
			
			//console.log(data);
			
			// ajax호출시 default가 비동기 호출
			// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
			// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요.
			$.ajax({ 
				type: "POST",
				url: "/auth/joinProc",
				data: JSON.stringify(data), // http body데이터
				contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
			}).done(function(resp){
				if(resp.status === 500){
					alert("회원가입에 실패하였습니다.");
				}else{
					alert("회원가입이 완료되었습니다.");
					location.href = "/";
				}

			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
			
		},
		
		update: function(){
			//alert('user의 save함수 호출됨');
			let data = {
					id: $("#id").val(),
					username: $("#username").val(),
					password: $("#password").val(),
					email: $("#email").val(),
					postcode: $("#postcode").val(),
					address: $("#address").val(),
					detailAddress: $("#detailAddress").val()
			};
			
			if($('#h_provider').val() == ''){
				if($.trim(data.password) == ''){
					alert('비밀번호를 입력하세요.');
					return;
				}
			}
			
			$.ajax({ 
				type: "PUT",
				url: "/user",
				data: JSON.stringify(data), // http body데이터
				contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
			}).done(function(resp){
				alert("회원수정이 완료되었습니다.");
				//console.log(resp);
				location.href = "/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
			
		},
		
		postcode: function(){
			new daum.Postcode ({
			    oncomplete : function (data) {
			    	// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
			
			        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
			        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			        var addr = ''; // 주소 변수
			        var extraAddr = ''; // 참고항목 변수
			
			      	//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
			            addr = data.roadAddress;
			        } else { // 사용자가 지번 주소를 선택했을 경우(J)
			            addr = data.jibunAddress;
			        }
			
			     	// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			        if(data.userSelectedType === 'R'){
			            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
			            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
			            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
			                extraAddr += data.bname;
			            }
			            // 건물명이 있고, 공동주택일 경우 추가한다.
			            if(data.buildingName !== '' && data.apartment === 'Y'){
			                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
			            }
			            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
			            if(extraAddr !== ''){
			                extraAddr = ' (' + extraAddr + ')';
			            }
			            // 조합된 참고항목을 해당 필드에 넣는다.
			            //document.getElementById("sample6_extraAddress").value = extraAddr;
			        
			        } else {
			            //document.getElementById("sample6_extraAddress").value = '';
			        }

			     	// 우편번호와 주소 정보를 해당 필드에 넣는다.
			        $('#postcode').val(data.zonecode);
			        $('#address').val(addr+extraAddr);
			        // 커서를 상세주소 필드로 이동한다.
			        $('#detailAddress').focus();
			    }
			}).open();
		},
		
}

index.init();
