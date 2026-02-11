package reviewboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import reviewboard.dto.ReviewBoardDTO;

@Mapper
public interface ReviewBoardMapper {

	//리뷰추가
	public void addReview(ReviewBoardDTO bdto);
	
	//리뷰전체출력
	public List<ReviewBoardDTO> allReview();
	
	//하나리뷰상세보기
	public ReviewBoardDTO oneReview(int num);
	
	//readcount 누적하여 조회수 증가하는 메소드
	public int readCount(int num);
	
	//하나의 후기 수정
	public int updateReview(ReviewBoardDTO bdto);
	
	//하나의 후기 삭제
	public int deleteReview(int num);
	
	//전체 리뷰 수 구하기
	public int allReviewCount();
	
	//전체 게시글의 시작 (startRow),몇개의 행(pageSize) 만큼 보는 메소드
	public List<ReviewBoardDTO> getPageList(@Param("startRow") int startRow,
									  		@Param("pageSize") int pageSize);
	
	//평균별점
	public double avgStar();
	
	
	
	
	
}
