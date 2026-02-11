package reviewboard.dto;

public class PageHandler {

	//1. 기본 변수
		private int totalCnt; //전체 게시글 게수
		private int pageNum; //현재 페이지 번호
		private int pageSize; //한 페이지에 보여줄 레코드(게시물) 개수
		private int pageBlock = 3; //한 화면에 페이지묶음 1~3페이지까지
		
		private int startRow; //DB의 시작 위치
		private int endRow;
		
		private int totalPage; //전체 페이지 수
		private int startPage; //블록페이지의 시작번호 
		private int endPage;
		
		private boolean prev; // 이전 
		private boolean next; // 다음
		
		public PageHandler(int totalCnt, int pageNum, int pageSize) {
			this.totalCnt = totalCnt;
			this.pageNum = pageNum;
			this.pageSize = pageSize;
			calcPaging();	
		}
			
		public void calcPaging() {
			totalPage = (int) Math.ceil(totalCnt/(double)pageSize);
			startRow = (pageNum-1)*pageSize;
			endRow = pageSize;
			startPage = ((pageNum - 1) / pageBlock)*pageBlock+1;
			endPage = startPage + (pageBlock - 1);
			if(endPage > totalPage) {
				endPage = totalPage;
			}
			prev = startPage > 1;
			next = endPage < totalPage;
			
		}
		public int getTotalCnt() {
			return totalCnt;
		}

		public void setTotalCnt(int totalCnt) {
			this.totalCnt = totalCnt;
		}

		public int getPageNum() {
			return pageNum;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getPageBlock() {
			return pageBlock;
		}

		public void setPageBlock(int pageBlock) {
			this.pageBlock = pageBlock;
		}

		public int getStartRow() {
			return startRow;
		}

		public void setStartRow(int startRow) {
			this.startRow = startRow;
		}

		public int getEndRow() {
			return endRow;
		}

		public void setEndRow(int endRow) {
			this.endRow = endRow;
		}

		public int getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}

		public int getStartPage() {
			return startPage;
		}

		public void setStartPage(int startPage) {
			this.startPage = startPage;
		}

		public int getEndPage() {
			return endPage;
		}

		public void setEndPage(int endPage) {
			this.endPage = endPage;
		}

		public boolean isPrev() {
			return prev;
		}

		public void setPrev(boolean prev) {
			this.prev = prev;
		}

		public boolean isNext() {
			return next;
		}

		public void setNext(boolean next) {
			this.next = next;
		}
}
