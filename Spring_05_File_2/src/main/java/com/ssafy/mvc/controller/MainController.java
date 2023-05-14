package com.ssafy.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {		
		return "home";
	}
	
	@GetMapping("regist")
	public String registForm() {
		return "regist";
	}
	
	//의존성 주입해서 servletContext 만들기
	@Autowired
	private ServletContext servletContext;
	
	@PostMapping("upload")
	public String upload(MultipartFile upload_file, Model model) {
		
		//실제 저장될 위치 가져와
		String uploadPath = servletContext.getRealPath("/upload");
		System.out.println(uploadPath);
		
		// 그런데 경로가 없을 수도 있음. 경로 중의 어떤 폴더가 없을 수도. 그럴 경우에 대비하여 다음과 같이 처리
		// 경로가 없을경우 해당경로를 만들어줌
		File folder = new File(uploadPath);
		if(!folder.exists())
			folder.mkdir(); //폴더 없으면 만들어
		
		// 실제 파일이름 가져와
		String fileName = upload_file.getOriginalFilename();
		
		// 파일 하나 만드는 로직임
		// uploadPath경로에 fileName이름으로 파일 하나 만들기
		File target = new File(uploadPath, fileName);
		
		// 파일을 해당 폴더에 저장하기
		// 저장 방법은 크게 2가지가 있다.
		// 1.FileCopyUtiles
//			- 이놈의 메서드 중 copy를 이용함.
//			- 첫번째 인자로 byte타입 경로를 넣고, 두번째 인자로 파일을 넣어야함
//			- 예외처리까지 해준다.
		// 2.File인스턴스를 이용
		
		try {
			FileCopyUtils.copy(upload_file.getBytes(), target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		// 바구니에 담아 보내기
		model.addAttribute("fileName", fileName);
		
		return "result";
	} 
	
	
	// resource 경로를 가져오기 위해서 사용.
	@Autowired
	private ResourceLoader resLoader;
	
	@PostMapping("upload2")
	public String upload2(MultipartFile[] upload_files, Model model) throws IOException {
		
		// 파일들의 이름을 저장할 리스트를 생성하자 (임시)
		List<String> list = new ArrayList<>();
		
		// 널이 아니면 이렇게 사전에 작업을 해주는게 사실 조금 더 안전함.
		// 널이 아닐때만 처리를 진행 하도록 함.
		// 파일 한개 업로드할때와 마찬가지로, 경로가 없을경우 경로를 만들어주는 로직.
		if(upload_files != null) {
			// "resources/upload"경로를 res로 잡고
			Resource res = resLoader.getResource("resources/upload");
			// 만약에 그 경로를 파일로 잡았을때 존재하지 않으면
			if(!res.getFile().exists())
				// 해당 경로의 폴더를 만든다
				res.getFile().mkdir();
			
			
			
			// 배열인자 for문 돌린다
			// 만약 파일이 존재하면 해당 파일을 서버에 업로드하고, 그리고 임시 list에 이름들을 저장할 거임.
			// 서버에 업로드할 때 transferTo 메서드를 이용함.
			for(MultipartFile mfile : upload_files) {
				if(mfile.getSize() > 0) {  // 파일이 있으면..
					// 파일을 res.getFile()경로에 원래이름으로 저장한다.
					mfile.transferTo(new File(res.getFile(), mfile.getOriginalFilename()));
					// 그리고 임시 리스트에 파일이름을 저장한다.
					list.add(mfile.getOriginalFilename());
				}
				
			}
		}
		
		
		model.addAttribute("list", list);
		
		
		return "result";
		
		
		//참고 : 지금은 저장경로같은거를 간략하게 하여 저장했지만, 
		// 폴더 구조를 조금더 세분화 할 필요가 있음
		// 파일 이름또한, OriginName뿐만아니라, 저장명도 만들어서 함께 저장함으로써 -> 파일명의 중복으로 인한 파일 덮어쓰기를 방지할 수 있도록 한다.
	}
	
	
	
	@GetMapping("download")
	public String download(Model model, String fileName) {
		// 파일 정보를 담을 맵 객체를 하나 만든다.
		Map<String, Object> fileInfo = new HashMap<>();
		
		// 맵객체에 key-value 데이터 쌍 저장
		fileInfo.put("fileName", fileName);
		
		// 바구니에 담기
		model.addAttribute("downloadFile", fileInfo);
		
		//
		return "fileDownLoadView";
	}
	

}
