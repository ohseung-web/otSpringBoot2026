package myCalculator;

public class CalAmul implements ICal{
	@Override
  public int doOper(int num1, int num2) {
	  int result = num1 * num2;
	  return result;
  }
}
