package reviewboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reviewboard.dto.ReviewBoardDTO;
import reviewboard.mapper.ReviewBoardMapper;

@Service
public class ReviewBoardService {

	@Autowired
	ReviewBoardMapper reviewboardmapper;
	
		//하나의 후기 추가
			public void addReview(ReviewBoardDTO rdto) {
				reviewboardmapper.addReview(rdto);
		}
		
		//전체후기출력
		public List<ReviewBoardDTO> allReview() {
			return reviewboardmapper.allReview();
		}
		
		//후기 하나 출력
		public ReviewBoardDTO getoneReview(int num) {
			//조회수 증가 메서드 추가하기
			reviewboardmapper.readCount(num);
			//조회수 증가 + 하나 게시글 검색
			return reviewboardmapper.oneReview(num);
		}
		
		//후기 수정
		public boolean modReview(ReviewBoardDTO rdto) {
			int result = reviewboardmapper.updateReview(rdto);
			if(result > 0) {
				System.out.println("리뷰 수정 완료");
				return true;
			}else {
				System.out.println("리뷰 수정 실패");
				return false;
			}
		}
		
		//후기 삭제
		public boolean deleteReview(int num) {
			int result = reviewboardmapper.deleteReview(num);
			if(result > 0) {
				System.out.println("리뷰 삭제 성공");
				return true;
			}else {
				System.out.println("리뷰 삭제 실패");
				return false;
			}
		}
	
		//전체 리뷰 수
		public int getAllcount() {
			return reviewboardmapper.allReviewCount();
		}
		
		//한 화면에서 뿌려지는 limit 구하는 메소드? startRow, pageSize 까지의 행 검색
		public List<ReviewBoardDTO> getPageList(int startRow, int pageSize){
			return reviewboardmapper.getPageList(startRow, pageSize);
		}
		
		//평균별점
		public double getAvgStar() {
		    return reviewboardmapper.avgStar();
		}
		
}
