package com.green.board;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.member.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	
	//BoardServicelmpl 말고 BoardService 인터페이스를 주입받는것이
	// 실무 관례이다.
	@Autowired
	BoardService boardService;

	// 1. 게시글 작성 폼
	@GetMapping("/board/write")
	public String boardWriteForm() {
		System.out.println("BoardController boardWriteForm() 호출");
		String nextPage = "board/boardWrite_form";
		return nextPage;
	}
	
	// 2. 게시글 저장 처리 (신규 추가 중요!)
    @PostMapping("/board/writePro")
    public String boardWritePro(BoardDTO bdto, HttpSession session,
    		@RequestParam("file") MultipartFile file
    		) {
        System.out.println("BoardController boardWritePro() 호출");
        
        //1. 세션에서 로그인된 회원정보(MemberDTO)를 가져온다.
        //다운캐스팅한다.
        MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginedMember");
       
        // 2. 로그인 정보가 있는지 반드시 체크합니다. (데이터 누락 방지)
        if (loginedMember != null) {
            // [중요] 세션에서 꺼낸 id를 DTO에 수동으로 넣어줘야 MyBatis가 인식합니다.
            bdto.setId(loginedMember.getId());
            System.out.println("DB에 저장될 ID 확인: " + loginedMember.getId());
        } else {
            System.out.println("로그인 세션이 없습니다!");
            return "redirect:/member/login";
        }
        
        /* ===============================
        2. 이미지 업로드 처리
        =============================== */

         // 파일이 선택되었는지 확인
	     if (!file.isEmpty()) {
	
	         // 업로드 폴더 (수업용: 고정 경로)
	         String uploadPath = "d:/upload/";
	
	         // 원본 파일명
	         String fileName = file.getOriginalFilename();
	         System.out.println("업로드 파일명: " + fileName);
	
	         try {
	             // 실제 파일 저장
	             file.transferTo(new File(uploadPath + fileName));
	
	             // ⭐ DB에는 파일명만 저장
	             bdto.setImage(fileName);
	
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	     }

        
        // id가 포함된 bdto를 서비스로 넘겨 addBoard 호출하여 DB 저장
        boardService.addBoard(bdto);
        
        // 저장 후에는 '목록' 페이지로 이동 (Redirect)
        return "redirect:/board/list";
    }
	
    // 이미지 출력용 메소드
//    @GetMapping("/upload/{filename}")
//    @ResponseBody
//    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
//
//        // 실제 이미지가 저장된 경로
//    	 Path filePath = Paths.get("d:/upload/").resolve(filename);
//    	    Resource resource = new UrlResource(filePath.toUri());
//
//    	    return ResponseEntity.ok()
//    	            .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
//    	            .body(resource);
//    }
    
	// 3. 게시글 목록 보기 핸들러
//	@GetMapping("/board/list")
//	public String boardList(Model model) {
//		System.out.println("BoardController boardList() 호출");
//		
//	    List<BoardDTO> listboard =  boardService.allBoard();
//	    model.addAttribute("list",listboard);
//	    String nextPage = "board/boardList";
//		return nextPage;
//	}
	
	// /board/list 커스텀마이징 하는 부분 ----------------------------
	
	// 검색을 위한 board/list 커스텀 하기
//		@GetMapping("/board/list")
//		public String boardList(Model model,
//				@RequestParam(value="searchType", required=false) String searchType,
//				@RequestParam(value="searchKeyword", required=false) String searchKeyword
//				) {
//			System.out.println("1)BoardController boardList()메소드 호출");
//			
//			List<BoardDTO> listboard;  
//			
//			// 검색 종료 후 => 검색내용이 list나오기
//			if(searchType != null && !searchKeyword.trim().isEmpty() ) {
//				//boarDAO에 검색메소드 getSearchBoard()메소드 호출한다.
//				// service에서 serchBoard()메소드 호출한다.
//				listboard = boardService.searchBoard(searchType, searchKeyword);
//			}else {
//				// 검색하지 않고 전체보기 list나오기
//				listboard = boardService.allBoard();
//			}
//			
//			model.addAttribute("list",listboard);
//			String nextPage = "board/boardList";
//			return nextPage;
//		}
		
		// 게시판 페이징 하기 ------------------------------------------------------------------------
		// 검색을 위한 board/list 커스텀 하기
//		@GetMapping("/board/list")
//		public String boardList(Model model,
//		        @RequestParam(value="searchType", required=false) String searchType,
//		        @RequestParam(value="searchKeyword", required=false) String searchKeyword,
//		         // 1. 페이지 번호 => 1부터 시작으로 초기값 지정하는 파라미터 추가
//		        @RequestParam(value="page", required=false, defaultValue="1") int page, 
//		        //  2. 페이지 사이즈 => 한 화면에 보여지는 게시글의 개수를 5개로 초기화하는 파라미터 추가
//		        @RequestParam(value="pageSize", required=false, defaultValue="5") int pageSize
//				) {
//		    System.out.println("1)BoardController boardList()메소드 호출");
//		    
//		    // 3. 전체 게시글 개수 가져오기
//		    int totalCnt = boardService.getAllcount();
//		    
//		    // 4. PageHandler 생성 (현재 페이지와 전체 게시글 수 전달)
//		    PageHandler ph = new PageHandler(totalCnt, page, pageSize); 
//		 
//		    List<BoardDTO> listboard;  
//		    
//		    if(searchType != null && !searchKeyword.trim().isEmpty() ) {
//		        listboard = boardService.searchBoard(searchType, searchKeyword);
//		    } else {	    	
//		    	// 5 핵심: PageHandler가 계산한 값으로 DB 조회
//		        listboard = boardService.getPageList( ph.getStartRow(),ph.getEndRow() );
//		    }
//		    
//		    // 6. 뷰(HTML)에서 사용할 리스트와 페이징 정보를 모델에 담기
//		    model.addAttribute("list", listboard);
//		    model.addAttribute("ph", ph); // 화면에서 ph.beginPage, ph.endPage 등을 사용.
//		    
//		    String nextPage = "board/boardList";
//		    return nextPage;
//		}
//		
    
        // 검색 + 페이징 
    @GetMapping("/board/list")
    public String boardList(Model model, HttpSession session,
            @RequestParam(value="searchType", required=false) String searchType,
            @RequestParam(value="searchKeyword", required=false) String searchKeyword,
            @RequestParam(value="page", defaultValue="1") int page,
            @RequestParam(value="pageSize", defaultValue="5") int pageSize) {

    	// 1. 세션에서 로그인 아이디 가져오기
        MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginedMember");
        if (loginedMember == null) {
            return "redirect:/member/login";
        }
        String loginId = loginedMember.getId();
        
        boolean isSearch = (searchType != null && searchKeyword != null && !searchKeyword.trim().isEmpty());

        int totalCnt;
        List<BoardDTO> list;

        if (isSearch) {
            totalCnt = boardService.getSearchCount(searchType, searchKeyword);
        } else {
            totalCnt = boardService.getAllcount();
        }

        PageHandler ph = new PageHandler(totalCnt, page, pageSize);

        if (isSearch) {
            list = boardService.getSearchPageList(
                    searchType,
                    searchKeyword,
                    ph.getStartRow(),
                    ph.getEndRow()
            );
        } else {
            list = boardService.getPageList(ph.getStartRow(), ph.getEndRow());
        }

        model.addAttribute("list", list);
        model.addAttribute("ph", ph);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        return "board/boardList";
    }

    
		
		//-----------------------------------------------------------------------------------------------------
	
	
	// 4. 하나의 게시글 상세 정보 보기 핸들러 
		@GetMapping("/board/boardInfo")
		public String boardInfo(Model model, @RequestParam("num") int num) {
			System.out.println("BoardController boardInfo()메소드 호출"+ num);
			BoardDTO oneboardInfo = boardService.oneBoard(num);
			
			model.addAttribute("oneboard",oneboardInfo);
			String nextPage = "board/boardInfo";
			return nextPage;
		}
		
		
		// 5. 게시글 수정 폼 이동 (기존 데이터를 가지고 이동)
	    @GetMapping("/board/update")
	    public String boardUpdateForm(@RequestParam("num") int num, Model model) {
	        System.out.println("BoardController boardUpdateForm() 호출: " + num);
	        
	        // 기존 데이터를 불러와서 수정 폼에 채워주기 위해 상세정보 조회와 동일한 로직 사용
	        BoardDTO oneboardInfo = boardService.oneBoard(num);
	        model.addAttribute("oneboard", oneboardInfo);
	        String nextPage = "board/boardUpdate_form";
	        
	        return nextPage; // 수정 폼 HTML 파일명
	    }

	    // 6. 게시글 수정 처리
	    @PostMapping("/board/updatePro")
	    public String boardUpdatePro(BoardDTO bdto, Model model) {
	        System.out.println("BoardController boardUpdatePro() 호출");
	        
	        boolean isSuccess = boardService.modifyBoard(bdto);
	        
	        if(isSuccess) {
	            // 수정 성공 시 목록으로 이동
	            return "redirect:/board/list";
	        } else {
	            // 수정 실패 시 (비밀번호 틀림 등) 메시지 처리나 이전 페이지 유도가 필요할 수 있음
	            // 여기서는 일단 다시 상세페이지로 리다이렉트
	            return "redirect:/board/boardInfo?num=" + bdto.getNum();
	        }
	    }

	    // 7. 게시글 삭제 처리 (보통 삭제 전용 폼이나 상세페이지의 비밀번호 입력값 이용)
	    @GetMapping("/board/deletePro")
	    public String boardDeletePro(@RequestParam("num") int num, 
	                                @RequestParam("writerPw") String writerPw) {
	        System.out.println("BoardController boardDeletePro() 호출");
	        
	        boolean isSuccess = boardService.removeBoard(num, writerPw);
	        
	        if(isSuccess) {
	            return "redirect:/board/list";
	        } else {
	            // 삭제 실패 시 상세페이지로 복귀
	            return "redirect:/board/boardInfo?num=" + num;
	        }
	    }		
		
	    //---------  마이페이지
	    @GetMapping("/member/mypage")
	    public String myPostList(Model model, HttpSession session,
	            @RequestParam(value="page", defaultValue="1") int page) {

	        // 1. 세션에서 로그인한 정보 가져오기
	        MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginedMember");
	        if (loginedMember == null) return "redirect:/member/login";
	        
	        String loginId = loginedMember.getId();
	        int pageSize = 5; // 마이페이지는 간단하게 5개씩

	        // 2. 내 글이 총 몇 개인지 조회 (페이징용)
	        int totalCnt = boardService.getMyBoardCount(loginId);
	        
	        // 3. 페이징 계산기 생성
	        PageHandler ph = new PageHandler(totalCnt, page, pageSize);

	        // 4. [핵심] JOIN을 사용해 내 글 목록만 가져오기
	        List<BoardDTO> mylist = boardService.getMyBoardList(loginId, ph.getStartRow(), ph.getPageSize());

	        model.addAttribute("list", mylist);
	        model.addAttribute("ph", ph);
	        
	        return "member/mypage"; // 새로 만들 마이페이지 HTML
	    }
	    
}
