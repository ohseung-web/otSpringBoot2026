package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.member.MemberDTO;

@Controller
public class BoardController {
	
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
    public String boardWritePro(BoardDTO bdto) {
        System.out.println("BoardController boardWritePro() 호출");
        
        // 서비스의 addBoard 호출하여 DB 저장
        boardService.addBoard(bdto);
        
        // 저장 후에는 '목록' 페이지로 이동 (Redirect)
        return "redirect:/board/list";
    }
	
	// 3. 게시글 목록 보기 핸들러
	@GetMapping("/board/list")
	public String boardList(Model model) {
		System.out.println("BoardController boardList() 호출");
		
	    List<BoardDTO> listboard =  boardService.allBoard();
	    model.addAttribute("list",listboard);
	    String nextPage = "board/boardList";
		return nextPage;
	}
	
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
