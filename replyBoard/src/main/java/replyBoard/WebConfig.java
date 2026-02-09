package replyBoard;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Configuration: 이 클래스가 스프링 부트의 설정 파일임을 나타낸다.
 * 스프링이 시작될 때 이 클래스를 읽어서 웹 관련 설정을 적용한다.
 * WebMvcConfigurer 인터페이스를 상속받아 스프링 MVC의 기능을 확장한다.
 */
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
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///d:/upload/");
        
        /**
         * [정리]
         * 만약 사용자가 <img src="/upload/abc.jpg"> 라고 코드를 짜면,
         * 서버는 "D드라이브의 upload 폴더 안에 있는 abc.jpg" 파일을 꺼내서 화면에 보여준다.
         */
        
    }
	
}
