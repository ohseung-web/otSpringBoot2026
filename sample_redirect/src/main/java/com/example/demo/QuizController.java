package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuizController {
	
	// 1. 입력 페이지 (시작점)
    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz-view"; // quiz-view.html을 보여줌
    }

    // 2. 정답 확인 로직
    @PostMapping("/check-quiz")
    public String checkQuiz(@RequestParam("pass") String pass, RedirectAttributes re) {
        
        if ("1234".equals(pass)) {
            // 정답이면 메인으로!
            return "redirect:/main";
        } else {
            // 틀리면 메시지를 담아서 다시 퀴즈 페이지로!
            re.addFlashAttribute("errorMsg", "비밀번호가 틀렸습니다. 다시 시도하세요!");
            return "redirect:/quiz";
        }
    }

    // 3. 메인 페이지 (정답 시 도착하는 곳)
    @GetMapping("/main")
    public String mainPage() {
        return "main-view"; // main-view.html을 보여줌
    }
    
}
