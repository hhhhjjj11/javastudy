package practice.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticeApplication {

	public static void main(String[] args) {

		Member member = new Member();
		member.setUsername("dddd");
		System.out.println(member.getUsername());
		SpringApplication.run(PracticeApplication.class, args);
	}

}
