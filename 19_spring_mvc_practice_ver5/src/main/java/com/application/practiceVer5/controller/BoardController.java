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
		model.addAttribute("menu", menu);// 잊지말기!
		return "board/authentication";
	}
	
	@PostMapping("/authentication")
	@ResponseBody
	public String authentication(@ModelAttribute BoardDTO boardDTO, @RequestParam("menu") String menu) {

		boolean isAuthorized = boardService.isAuthorized(boardDTO);
		String jsScript = "";
		if (isAuthorized) {
			if(menu.equals("delete")) {
				jsScript="<script>";
				jsScript += "location.href='/board/deleteBoard?boardId="+boardDTO.getBoardId()+"';";
				jsScript += "</script>";
				
			}else if(menu.equals("update")) {
				jsScript="<script>";
				jsScript += "location.href='/board/updateBoard?boardId="+boardDTO.getBoardId()+"';";
				jsScript += "</script>";
			}
		} else {
			jsScript = """
					<script>
					alert('패스워드를 확인하세요');
					history.go(-1);
					</script>
					""";
		}
		return jsScript;

	}
	
	@GetMapping("/updateBoard")
	public String updateBoard(Model model, @RequestParam("boardId") long boardId) {
		model.addAttribute("boardDTO", boardService.getBoardDetail(boardId));
		
		return "board/updateBoard";
	}

	@PostMapping("/updateBoard")
	@ResponseBody
	public String updateBoard(@ModelAttribute BoardDTO boardDTO) {
		boardService.updateBoard(boardDTO);
		String jsScript = """
				<script>
					alert('게시글이 수정되었습니다.');
					location.href='/board/boardList';
					</script>
				""";
		return jsScript;
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(Model model, @RequestParam("boardId") long boardId) {
		model.addAttribute("boardId", boardId);
		//System.out.println(boardId);
		
		return "board/deleteBoard";
	}

	@PostMapping("/deleteBoard")
	@ResponseBody
	public String deleteBoard(@RequestParam("boardId") long boardId) {
		boardService.deleteBoard(boardId);
		String jsScript = """
				<script>
				alert('게시글이 삭제되었습니다..');
				location.href='/board/boardList';
				</script>
				""";
		return jsScript;
	}
}


