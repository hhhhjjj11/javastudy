package jpashop.jpabook;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpabookApplication {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("dddd");
		System.out.println("hello = " + hello);
		String aa = hello.getData();
		System.out.println("hello = " + aa);
		// 여기서 getData()로 얻은다음에 찍는게아니라, 바로 hello를 콘솔에 찍으면 인스턴스의 문자열 표현으로 나타난다...
		// 이경우 @ToString까지 추가해줘야함

		SpringApplication.run(JpabookApplication.class, args);
	}
}
