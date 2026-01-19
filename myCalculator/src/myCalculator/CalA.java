package myCalculator;

public class CalA {
  //IoC 컨테이너를 만들어 보기
  
  MyCalculator mycal;
  CalAdd caladd;
  CalAmul calmul;
  CalAsub calsub;
  CalDiv caldiv;
  
  //생성자가 초기화될떄 객체가 생성
  public CalA() {
	  //각각의 부품들이 초기화 됨
	  this.mycal = new MyCalculator();
	  this.caladd = new CalAdd();
	  this.calmul = new CalAmul();
	  this.caldiv = new CalDiv();
	  this.calsub = new CalAsub();
	  assembler();
  }
  
  //메소드
  public void assembler() {
	  mycal.cal(20, 10, caladd);
	  mycal.cal(20, 10, calmul);
	  mycal.cal(20, 10, caldiv);
	  mycal.cal(20, 10, calsub);
  }
  
  
  
  
  
}
