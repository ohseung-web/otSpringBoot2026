package replyBoard.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import replyBoard.dto.ReplyBoardDTO;
import replyBoard.service.ReplyBoardService;

@Controller
public class ReplyBoardController {

	@Autowired
	ReplyBoardService replyBoardservice;

	// 게시글 목록으로 이동하는 컨트롤러
	@GetMapping("/board/list")
	public String boardList(Model model) {
		System.out.println("ReplyBoardController boardList() 호출");
		List<ReplyBoardDTO> replyList = replyBoardservice.getAllReplyBoard();
		
		model.addAttribute("rlist",replyList);
		
		return "/replyBoard/replyboardList";
	}
	
	// 1. 글쓰기 폼으로 이동하는 컨트롤러
	@GetMapping("/board/writer")
	public String boardWriterForm() {
		System.out.println("ReplyBoardController boardWriterForm() 호출" );
		String nextPage ="replyBoard/replyboardWrite_Form";
		return nextPage;
	}
	
	// 2. 글쓰기를 처리하는 컨트롤러
//	@GetMapping("/board/writerPro")
//	public String boardWriterPro(Model model,ReplyBoardDTO rdto) {
//		System.out.println("ReplyBoardController boardWriterPro() 호출 함");
//		replyBoardservice.insertReplyBoard(rdto);
//		return "redirect:/board/list";
//	}
	
	// 이미지 업로드 하는 코드 수정 부분 -----------------------
	/**
	 * @PostMapping: HTML의 <form enctype="multipart/form-data">를 통해 들어온 데이터를 처리합니다.
	 * @RequestParam("file1") MultipartFile: <input type="file" name="file1">에 담긴 파일을 받는 파라미터입니다.
	 */	
	// 파일업로드는 PostMapping()이다.
		@PostMapping("/board/writerPro")
		public String boardWriterPro(Model model,ReplyBoardDTO rdto,
				@RequestParam("file1") MultipartFile upload1,
		        @RequestParam("file2") MultipartFile upload2
				) throws IllegalStateException, IOException {
			
			System.out.println("ReplyBoardController boardWriterPro() 호출 함");	
			
			// 1. 파일을 저장할 실제 하드디스크의 위치를 지정한다.
		    // WebConfig에서 설정한 'file:///d:/upload/' 경로와 반드시 일치해야 웹에서 불러올 수 있다.
		    String savePath = "d:/upload/";
		    
			
			// 스프링 부트는 기본적으로 파일 업로드 크기 제한이 매우 작게(보통 1MB) 설정되어 있다.
			// 프로젝트의 src/main/resources/application.properties 파일에 아래 내용을 추가하여 허용 용량을 늘려주세요. 
			// (이미 설정되어 있다면 값을 키워주세요.)
			//  # 파일 하나의 최대 사이즈 (예: 10MB)
//			spring.servlet.multipart.max-file-size=10MB
//
//					# 한 번의 요청(파일 여러개 포함)의 전체 최대 사이즈 (예: 20MB)
//					spring.servlet.multipart.max-request-size=20MB
		
		    
		    // 2. [안전장치] 만약 'd:/upload/' 폴더가 없으면 프로그램을 통해 자동으로 생성한다.
		    File saveDir = new File(savePath);
		    if (!saveDir.exists()) {
		    	saveDir.mkdirs(); // mkdirs()는 상위 폴더가 없어도 한꺼번에 다 만들어준다
		    }
			
			
		     // ===== 3. 첫 번째 이미지(upload1) 업로드 처리 =====
		    if (!upload1.isEmpty()) { // 사용자가 파일을 실제로 선택해서 보냈는지 확인한다.
		    	
		    	// 사용자가 올린 원래 파일명 (예: "my_car.jpg")을 가져온다.
		        String originalName1 = upload1.getOriginalFilename();
		        
		        // [중복 방지] 파일명이 겹치지 않게 UUID를 생성한다.
		        // substring(0, 4)를 사용해 36자리 중 앞 4자리만 가져와서 파일명을 짧게 만든다. (예: "a1b2_my_car.jpg")
		        String saveName1 = UUID.randomUUID().toString().substring(0, 4) + "_" + originalName1;
		        
		        // savePath(경로)와 saveName1(이름)을 합쳐서 실제 저장될 파일 객체를 생성한다.
		        File file1 = new File(savePath + saveName1);
		        // File file1 = new File("c:/upload/" + saveName1);
		        
		        // transferTo(): 이 명령어가 실행되는 순간 서버의 메모리에 있던 파일이 
		        //               실제 하드디스크(D:/upload/)로 복사된다.
		        upload1.transferTo(file1);

		        // 👉 DB에 저장할 파일명 DTO에 세팅
		        // [핵심] 하드디스크에 저장된 '새 파일명'을 DTO 객체에 담는다. 
		        // 그래야 나중에 DB의 upload1 컬럼에 이 이름이 기록된다.
		        rdto.setUpload1(saveName1);
		    }

		    // ===== 4. 두 번째 이미지(upload2) 업로드 처리 (방식은 위와 동일) =====
		    if (!upload2.isEmpty()) {
		        String originalName2 = upload2.getOriginalFilename();
		        String saveName2 =  originalName2;
		        //String saveName2 = UUID.randomUUID().toString().substring(0, 4) + "_" + originalName2;

		        File file2 = new File(savePath + saveName2);
		        upload2.transferTo(file2);

		        rdto.setUpload2(saveName2);
		    }
		   
		    // 5. 게시글 내용(제목, 작성자 등)과 함께 위에서 세팅한 '파일명'들을 DB에 최종 저장합니다.
			replyBoardservice.insertReplyBoard(rdto);
			// 6. 모든 작업이 끝나면 게시글 목록 페이지로 화면을 이동시킵니다.
			return "redirect:/board/list";
		}
		
		//---------- 이미지 업로드를 React와 연결하는 방법 2가지 설명
		// 예시: 내 컴퓨터의 실제 React 프로젝트 경로로 설정
		//String savePath = "D:/work/react-pjt/public/img/";
//		2. 더 똑똑한 방법: 심볼릭 링크(Symbolic Link) 사용
//		윈도우의 '바로가기' 같은 개념을 폴더 수준에서 적용하는 것입니다. 컴퓨터 시스템에게 **"C:/upload 폴더는 사실 React의 public/img 폴더와 같은 곳이야"**라고 알려주는 방식입니다.
//
//		기존 public/img 폴더를 유지합니다.
//
//		명령 프롬프트(CMD)를 관리자 권한으로 실행합니다.
//
//		다음 명령어를 입력합니다:
//
//		DOS

//		결과: 스프링부트 코드는 수정할 필요 없이 C:/upload에 파일을 저장하면, 실제로는 React의 public/img 폴더에 파일이 저장됩니다.
//
//		이점: 스프링부트 코드(C:/upload)와 React 코드(public/img)를 둘 다 수정하지 않고 연결할 수 있습니다.
//	
	//-----------------------------------------------------
	
	
	// 3. 하나의 게시글 정보로 이동하는 컨트롤러
	@GetMapping("/board/detail")
	public String getOneBoard(@RequestParam("num") int num, Model model) {
		System.out.println("ReplyBoardController getOneBoard() 호출 함");
		
		ReplyBoardDTO oneList = replyBoardservice.getOneBoard(num);
		model.addAttribute("onelist",oneList);
		
		return "/replyBoard/replyboardDetail";
	}
	
	// 4. 답글 작헝하는 폼으로 이동하는 컨트롤러
	@GetMapping("/board/reply")
	public String reWriteForm(Model model, @RequestParam("num") int num,
			@RequestParam("ref") int ref,
			@RequestParam("re_step") int re_step,
			@RequestParam("re_level") int re_level
			) {
		
		model.addAttribute("num",num);
		model.addAttribute("ref",ref);
		model.addAttribute("re_step",re_step);
		model.addAttribute("re_level",re_level);
		
		return "/replyBoard/replyboardReWrite_Form";
	}
	
	// 5. 답글 작성을 처리하는 컨트롤러
	@PostMapping("/board/reWritePro")
	public String reWritePro(ReplyBoardDTO rdto) {
		replyBoardservice.replyProcess(rdto);
		return "redirect:/board/list";
	}
}
