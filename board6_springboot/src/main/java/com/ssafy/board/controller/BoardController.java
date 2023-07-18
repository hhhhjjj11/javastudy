package com.ssafy.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.member.model.MemberDto;
import com.ssafy.util.PageNavigation;

@Controller
@RequestMapping("/board")
public class BoardController {

	// 메서드 6개!! ( rest)
	
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private ServletContext servletContext;

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		logger.info("BoardController 생성자 호출!!!!");
		this.boardService = boardService;
	}

	// view리턴이라 필요 없음.
	@GetMapping("/write")
	public String write(@RequestParam Map<String, String> map, Model model) {
		logger.debug("write call parameter {}", map);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/write";
	}

	@PostMapping("/write")
	public String write(@Value("${file.path.upload-files}") String filePath , BoardDto boardDto, @RequestParam("upfile") MultipartFile[] files, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("write boardDto : {}", boardDto);
		
		// 1. 세션에서 유저정보 꺼내서 boardDto에 설정해준다.
		
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		boardDto.setUserId(memberDto.getUserId());

		// 2. FileUpload 관련 설정.
		/*
		 * MultipartFile은 Spring에서 제공하는 파일 업로드를 위한 인터페이스임.
		 * 파일들을 리스트 형태로 담아줌.
		 * step0.
		 * 일단 비어있는지 아닌지 확인을 한담에
		 * step1.
		 * 저장할 폴더부터 정해준다. 
		 * 저장할 폴더는 각 날짜별로 생성하기로하자. 
		 * 그래서 일단 폴더 경로를 만든담에 -> 해당 경로의 폴더가 이미 있는경우 이미 폴더가 잇는것이므로 패스. 
		 * 해당 경로에 폴더가 없으면 폴더를 새로 만들어준다.
		 * step2.
		 * 그다음에 for문을 돌린다.
		 * 기억해야할 것은 db에 저장할 데이터를 설정하구 또 실제로 메서드를 이용해서 정한 폴더 경로에 파일을 저장해주는 작업은 별개라는 점이다.
		 * db에 저장해야하는 필드는 세개였음. -> 저장폴더 / 원래파일명 / 저장파일명
		 * 이거를 fileInfodto에 설정
		 * */
		logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		if (!files[0].isEmpty()) {
//			String realPath = servletContext.getRealPath("/upload");
//			String realPath = servletContext.getRealPath("/resources/img");
			String today = new SimpleDateFormat("yyMMdd").format(new Date()); //저장폴더 경로 만들기. 날짜별로 만들거니까. 오늘날짜에 해당하는 문자열 만든다.
			String saveFolder = filePath + File.separator + today; // 저장폴더경로			
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);	
			if (!folder.exists())	
				folder.mkdirs();		// 폴더가 없으면 만든다.
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();  // 파일 정보들을 담기위한 바구니 배열
			for (MultipartFile mfile : files) {  
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();	// getOriginalFilename은 multipartFile의 내장메서드인듯. 
				if (!originalFileName.isEmpty()) {
					String saveFileName = System.nanoTime()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));   // 실제로 해당 파일에 저장하는 로직임 .transferTo 메스드를 쓴다..
				}
				fileInfos.add(fileInfoDto);  // 정보 배열에 파일 정보를 추가해준다.
			}
			boardDto.setFileInfos(fileInfos);  // boardDto에 파일정보들을 넣어준다..
		}
		boardService.writeArticle(boardDto);
		redirectAttributes.addAttribute("pgno", "1");
		redirectAttributes.addAttribute("key", "");
		redirectAttributes.addAttribute("word", "");
		return "redirect:/board/list";
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("list parameter : {}", map);
		ModelAndView mav = new ModelAndView();
//		try {
		List<BoardDto> list = boardService.listArticle(map);
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		mav.addObject("articles", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("board/list");
		return mav;
//		} catch (Exception e) {
//			e.printStackTrace();
//			mav.addObject("msg", "글작성 처리중 문제발생!!!");
//			mav.setViewName("error/error");
//			return mav;
//		}
	}

	// 상세보기....인듯
	@GetMapping("/view")
	public String view(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
			throws Exception {
		logger.debug("view articleNo : {}", articleNo);
		BoardDto boardDto = boardService.getArticle(articleNo);
		boardService.updateHit(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/view";
	}

	
	// view라 필요 없음.
	@GetMapping("/modify")
	public String modify(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
			throws Exception {
		logger.debug("modify articleNo : {}", articleNo);
		BoardDto boardDto = boardService.getArticle(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/modify";
	}

	@PostMapping("/modify")
	public String modify(BoardDto boardDto, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("modify boardDto : {}", boardDto);
		boardService.modifyArticle(boardDto);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/board/list";
	}


	@GetMapping("/delete")
	public String delete(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("delete articleNo : {}", articleNo);
		boardService.deleteArticle(articleNo, servletContext.getRealPath("/upload"));
		//servletContext.getRealPath("/upload")는 웹 애플리케이션의 루트 디렉토리에 있는 upload 폴더의 실제 파일 시스템 경로를 반환
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/board/list";
	}

	// sfolder = saveFolder
	// sfile = saveFile
	// ofile = originalFile
	@GetMapping(value = "/download")
	public ModelAndView downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
			@RequestParam("sfile") String sfile, HttpSession session) {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if (memberDto != null) {
			Map<String, Object> fileInfo = new HashMap<String, Object>();
			fileInfo.put("sfolder", sfolder);
			fileInfo.put("ofile", ofile);
			fileInfo.put("sfile", sfile);

			return new ModelAndView("fileDownLoadView", "downloadFile", fileInfo);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
}