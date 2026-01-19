package myMsg;

public class Mms  implements Message {

	@Override
	public void send() {
		System.out.println("MMS 전송 시작");
	}
  
}
