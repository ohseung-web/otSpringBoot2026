package com.green.board;

public class PageHandler {
	private int totalCnt; //총 게시물 갯수
	  private int pageSize; // 한 페이지의 크기
	  private int naviSize = 10; // 페이지 네비게이션의 크기
	  private int totalPage; //전체 페이지의 갯수
	  private int page; // 현재 페이지
	  private int beginPage; // 네비게이션의 첫번째 페이지
	  private int endPage; // 네비게이션의 마지막 페이지
	  private  boolean showPrev; // 이전 페이지로 이동하는 링크를 보여줄것인지 여부
	  private boolean showNext; // 다음 페이지로 이동하는 링크를 보여줄겻인지 여부
	   
	   public PageHandler(int totalCnt, int page) {
		 //  this(totalCnt,page, pageSize=10);
		   this.totalCnt = 10;
		   this.page = 10;
	   }
	   public PageHandler(int totalCnt, int page, int pageSize) {
		   this.totalCnt = totalCnt;
		   this.page = page;
		   this.pageSize = pageSize;
		   
		   // 원인: totalCnt와 pageSize가 모두 정수(int)라 
		   // 소수점이 버려진 채로 Math.ceil에 전달됩니다. (예: 25 / 10 = 2.0 → 올림해도 2가 됨)
		   // totalPage =(int)Math.ceil( totalCnt / pageSize );
		   
		   totalPage = (int)Math.ceil(totalCnt / (double)pageSize);
		   beginPage = page / naviSize * naviSize + 1;
		   endPage = Math.min(beginPage + naviSize - 1, totalPage) ; //둘 중에 작은 값을 출력한다.
		   
		   // 이전으로 이동하는 페이지는 beginPage가 1페이지만 아니면 됨
		   showPrev = beginPage != 1;
		   // 다음으로 이동하는 페이지는 endPage가 totalPage만 아니면 됨
		   showNext = endPage != totalPage;
	   }
	   
	  public void print() {
		  System.out.println("page = " + page);
		  System.out.println(showPrev ? "[PREV]": "");
		  for(int i=beginPage; i<=endPage ; i++) {
			  System.out.println(i+" ");
		  }
		  System.out.println(showNext ? "[NEXT]":"");
	  }
	  
	  public int getTotalCnt() {
		return totalCnt;
		}
		public void setTotalCnt(int totalCnt) {
			this.totalCnt = totalCnt;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getNaviSize() {
			return naviSize;
		}
		public void setNaviSize(int naviSize) {
			this.naviSize = naviSize;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getBeginPage() {
			return beginPage;
		}
		public void setBeginPage(int beginPage) {
			this.beginPage = beginPage;
		}
		public int getEndPage() {
			return endPage;
		}
		public void setEndPage(int endPage) {
			this.endPage = endPage;
		}
		public boolean isShowPrev() {
			return showPrev;
		}
		public void setShowPrev(boolean showPrev) {
			this.showPrev = showPrev;
		}
		public boolean isShowNext() {
			return showNext;
		}
		public void setShowNext(boolean showNext) {
			this.showNext = showNext;
		}
	@Override
	  public String toString() {
		  return "PageHandler{" +
	              ", totalCnt=" + totalCnt +
	              ", showNext=" + showNext +
	              ", beginPage=" + beginPage +
	              ", naviSize=" + naviSize +
	              ", totalPage=" + totalPage +
	              ", endPage=" + endPage +
	              ", showPrev=" + showPrev +
	              '}';
	  }
}
