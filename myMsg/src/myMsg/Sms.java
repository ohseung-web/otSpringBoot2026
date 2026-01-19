package myMsg;

public class Sms implements Message{

	@Override
	public void send() {
		System.out.println("SMS 전송시작");
	}
	
}
