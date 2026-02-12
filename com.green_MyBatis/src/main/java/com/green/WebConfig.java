package com.green;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Configuration: 이 클래스가 스프링 부트의 설정 파일임을 나타낸다.
 * 스프링이 시작될 때 이 클래스를 읽어서 웹 관련 설정을 적용한다.
 * WebMvcConfigurer 인터페이스를 상속받아 스프링 MVC의 기능을 확장한다.
 */
// 프로젝트 우클릭 → Properties → Resource 확인한다.
@Configuration
public class WebConfig implements WebMvcConfigurer{

	/**
     * addResourceHandlers: 정적 리소스(이미지, CSS, JS 등)를 관리하는 메서드이다.
     * 외부의 물리적인 경로를 웹에서 사용하는 URL 주소로 매핑하는 설정을 담당한다.
     */
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// 1. addResourceHandler("/upload/**")
        // - 브라우저 주소창에 "http://localhost:8080/upload/파일명" 이라고 입력했을 때를 의미한다.
        // - "/upload/"로 시작하는 모든 요청(**)을 이 설정이 가로챈다.
        
        // 2. addResourceLocations("file:///d:/upload/")
        // - 위에서 가로챈 요청을 실제로 어느 폴더에서 찾을지 지정한다.
        // - 반드시 "file:///" 라는 프로토콜을 붙여야 내 컴퓨터 하드디스크의 실제 경로를 인식한다.
        // - 뒤에 오는 "d:/upload/"는 실제로 파일이 저장되어 있는 물리적인 경로이다.
 
       // React public 폴더 경로 (본인 프로젝트 경로에 맞게 수정)
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///D:/Spring_Boot/pjt/com.green_MyBatis/frontend/public/img/");
  
    }
	
}
