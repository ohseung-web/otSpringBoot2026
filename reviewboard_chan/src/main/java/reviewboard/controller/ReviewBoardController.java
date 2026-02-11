package reviewboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reviewboard.dto.PageHandler;
import reviewboard.dto.ReviewBoardDTO;
import reviewboard.service.ReviewBoardService;


@Controller
public class ReviewBoardController {

	@Autowired
	ReviewBoardService reviewboardservice;
	
	@GetMapping("/")
	public String boardList(Model model,
			@RequestParam(value="page",defaultValue = "1") int page,
			@RequestParam(value="pageSize",defaultValue = "5") int pageSize
			) {
		int totalCnt;
		//totalCnt를 조건에 만족하는 값으로 저장되도록 지정하는 부분
			totalCnt = reviewboardservice.getAllcount();
		
		//4.PageHandler 클래스 접근하기 위해 인스턴스화 한다.
		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
		
		List<ReviewBoardDTO> list;

			list = reviewboardservice.getPageList(ph.getStartRow(), pageSize);
			
		double avgStar = reviewboardservice.getAvgStar();
		
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("avgStar", avgStar);
		model.addAttribute("list",list);
		model.addAttribute("ph",ph);
		String nextPage = "main";
		return nextPage;
	}
	
	@GetMapping("/write")
	public String writeForm() {
		String nextPage = "write";
		return nextPage;
		}
	
	@PostMapping("/writePro")
	public String writePro(ReviewBoardDTO rdto) {
		reviewboardservice.addReview(rdto);
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public String boardInfo(@RequestParam("num") int num,Model model) {
		
		ReviewBoardDTO onereview = reviewboardservice.getoneReview(num);
		model.addAttribute("onereview",onereview);	
		String nextPage="detail";
		return nextPage;
	}
	
	@GetMapping("/update")
	public String boardUpdateForm(@RequestParam("num") int num,Model model) {
		ReviewBoardDTO onereview = reviewboardservice.getoneReview(num);
		model.addAttribute("onereview",onereview);
		String nextPage = "update";
		return nextPage;
	}
	
	@PostMapping("/updatePro")
	public String boardUpdatePro(ReviewBoardDTO rdto, Model model) {
		boolean isSuccess = reviewboardservice.modReview(rdto);
		if(isSuccess) {
			return "redirect:/";
		}else {
			//수정 실패시 현재 url에 머무르기
			return "redirect:/update?num="+rdto.getNum();
		}
	}
	
	@GetMapping("/deletePro")
	public String boardDeletePro(
			@RequestParam("num") int num
			) {
		boolean isSuccess = reviewboardservice.deleteReview(num);
		if(isSuccess) {
			return "redirect:/";
		}else { //삭제 실패 시 페이지 머무르기
			return "redirect:/detail?num="+num;
		}
	}
	
	
	
	
}
