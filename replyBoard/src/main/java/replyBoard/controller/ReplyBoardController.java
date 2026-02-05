package replyBoard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@GetMapping("/board/writerPro")
	public String boardWriterPro(Model model,ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardController boardWriterPro() 호출 함");
		replyBoardservice.insertReplyBoard(rdto);
		return "redirect:/board/list";
	}
	
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
