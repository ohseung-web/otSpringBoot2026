package myCalculator;

public class MyCalculator {

	// 이메소드 하나로 +, -, *, / 모두 계산 가능하다.
	public void cal(int num1, int num2, ICal ical) {
		int result = ical.doOper(num1, num2);
		System.out.println("reslut:"+result);
	}
	
	
	
/*  다 같은 인터페이스를 사용하기때문에 하나로 빼서 사요할 수 있다.
// 외부에서 CalAdd를 주입해주면 나는 그대로 받겠다	
 public void calAdd(int fNum, int sNum, CalAdd caladd) {
	 //부품화 한다.
	 //CalAdd caladd = new CalAdd();
	 //인터페이스를 이용해 설계하고 나면 모든 내부 구조가 모두 같다
	 // 외부에서 주입받으면 내가 직접 관리할 필요가 없기때문에 아래 코드는 필요가 없다.
	 //ICal calculator = new CalAdd(); 
	 //int result = calculator.doOper(fNum, sNum);
	 //외부에서 주는대로 받아서 사용만 하면된다.
	 int result = caladd.doOper(fNum, sNum);
	 System.out.println("result:"+ result);
 }
 public void calsub(int fNum, int sNum, CalAsub calsub) {
	 //ICal calculator = new CalAsub();
	 //int result = calculator.doOper(fNum, sNum);
	 int result = calsub.doOper(fNum, sNum);
	 System.out.println("result:"+ result);
 }
 public void calMul(int fNum, int sNum, CalAmul calmul) {
	 //ICal calculator = new CalAmul();
	 //int result = calculator.doOper(fNum, sNum);
	 int result = calmul.doOper(fNum, sNum);
	 System.out.println("result:"+ result);
 }
 public void calDiv(int fNum, int sNum, CalDiv caldiv) {
	 //ICal calculator = new CalDiv();
	 //int result = calculator.doOper(fNum, sNum);
	 int result = caldiv.doOper(fNum, sNum);
	 System.out.println("result:"+ result);
 }
  */
	
	
}
