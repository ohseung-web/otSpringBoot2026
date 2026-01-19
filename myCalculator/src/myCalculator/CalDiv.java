package myCalculator;

public class CalDiv implements ICal {
@Override
  public int doOper(int num1, int num2) {
	  int result = (num2 !=0)? num1 / num2:0;
	  return result;
  }
}
