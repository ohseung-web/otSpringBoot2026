package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {
	// 메모리에 주소 데이터를 담을 리스트 (서버가 켜져 있는 동안 유지)
    private List<AddressDTO> addressList = new ArrayList<>();
    
 // 1. 주소록 목록 화면
    @GetMapping("/addresses")
    public String listPage(Model model) {
        model.addAttribute("list", addressList); // 리스트 전달
        return "address-list";
    }

    // 2. 주소 등록 처리
    @PostMapping("/add-address")
    public String addAddress(AddressDTO dto) { // @RequestParam 대신 DTO로 바로 받기
        addressList.add(dto);
        return "redirect:/addresses";
    }
    
}
