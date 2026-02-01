package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String boardWritePro(BoardDTO bdto, HttpSession session) {
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
        
        // id가 포함된 bdto를 서비스로 넘겨 addBoard 호출하여 DB 저장
        boardService.addBoard(bdto);
        
        // 저장 후에는 '목록' 페이지로 이동 (Redirect)
        return "redirect:/board/list";
    }
	
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
		@GetMapping("/board/list")
		public String boardList(Model model,
		        @RequestParam(value="searchType", required=false) String searchType,
		        @RequestParam(value="searchKeyword", required=false) String searchKeyword,
		        @RequestParam(value="page", required=false, defaultValue="1") int page // 1. 페이지 번호 파라미터 추가
		        ) {
		    System.out.println("1)BoardController boardList()메소드 호출");
		    
		    // 2. 전체 게시글 개수 가져오기
		    int totalCnt = boardService.getAllcount();
		    
		    // 3. PageHandler 생성 (현재 페이지와 전체 게시글 수 전달)
		    // pageSize를 10으로 설정하는 생성자를 사용하거나, 기본 생성자 후 설정하세요.
		    PageHandler ph = new PageHandler(totalCnt, page, 10); 
		    
		    List<BoardDTO> listboard;  
		    
		    // 4. (참고) 실제 페이징 쿼리를 적용하려면 MyBatis 쿼리에 LIMIT을 추가하고 
		    // 아래 메서드들에 시작 번호(offset) 등을 넘겨주어야 하지만, 
		    // 우선 현재 구조에서 PageHandler를 모델에 담는 로직을 완성합니다.
		    if(searchType != null && !searchKeyword.trim().isEmpty() ) {
		        listboard = boardService.searchBoard(searchType, searchKeyword);
		    } else {
		    	// [수정] 전체 조회가 아닌, 현재 페이지(page)에 맞는 10개 데이터만 가져오도록 변경
		        listboard = boardService.getPageList(page, 10);
		        //listboard = boardService.allBoard();
		    }
		    
		    // 5. 뷰(HTML)에서 사용할 리스트와 페이징 정보를 모델에 담기
		    model.addAttribute("list", listboard);
		    model.addAttribute("ph", ph); // 화면에서 ph.beginPage, ph.endPage 등을 사용합니다.
		    
		    String nextPage = "board/boardList";
		    return nextPage;
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
		
	    
	    
}
