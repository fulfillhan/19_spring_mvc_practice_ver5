package com.application.practiceVer5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.practiceVer5.dao.BoardDAO;
import com.application.practiceVer5.dto.BoardDTO;
import com.application.practiceVer5.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/createBoard")
	public String createBoard() {
		return "board/createBoard";
	}

	@PostMapping("/createBoard")
	@ResponseBody
	public String createBoard(@ModelAttribute BoardDTO boardDTO) {
		boardService.createBoard(boardDTO);
		//System.out.println(boardDTO);
		//BoardDTO(boardId=0, passwd=$2a$10$IRx9BbRBaYylaaPDLFmjr..bEzrBrwGPSz4zkU1To7oyg31DlMqDS, writer=랑희, subject=자바, content=1, readCnt=0, enrollAt=null)
		String jsScript = """
				<script>
				alert('게시글이 작성되었습니다.');
				location.href='/board/boardList';
				</script>
				""";
		return jsScript;
	}
	
	@GetMapping("/boardList")
	public String boardList(Model model) {
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/boardList";
	}
	
	@GetMapping("/boardDetail")
	public String boardDetail(Model model,@RequestParam("boardId") long boardId) {
		 // boardService.getBoardDetail(boardId);
		  model.addAttribute("boardDTO", boardService.getBoardDetail(boardId));
		return "board/boardDetail";
	}
	
	@GetMapping("/authentication")
	public String authentication(Model model ,@RequestParam("boardId") long boardId, @RequestParam("menu") String menu) {
		model.addAttribute("boardDTO",boardService.getBoardDetail(boardId));
		
		return "board/authentication";
	}
	
	
	

}
