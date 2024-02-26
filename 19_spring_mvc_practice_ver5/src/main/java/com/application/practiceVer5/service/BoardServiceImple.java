package com.application.practiceVer5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.practiceVer5.dao.BoardDAO;
import com.application.practiceVer5.dto.BoardDTO;

@Service
public class BoardServiceImple implements BoardService {
 
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void createBoard(BoardDTO boardDTO) {
		boardDTO.setPasswd(passwordEncoder.encode(boardDTO.getPasswd()));
		boardDAO.createBoard(boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardList() {
		
		return boardDAO.getBoardList();
	}

	@Override
	public BoardDTO getBoardDetail(long boardId) {
		
		return boardDAO.getBoardDetail(boardId);
	}

	@Override
	public boolean isAuthorized(BoardDTO boardDTO) {

		boolean isCheck = false;
		// 암호화된 패스워드 가져오기
		String encodedPasswd = boardDAO.getEncodedPasswd(boardDTO.getBoardId());

		if (passwordEncoder.matches(boardDTO.getPasswd(), encodedPasswd)) {
			isCheck = true;
		}

		return isCheck;
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) {
	boardDAO.updateBoard(boardDTO);
	}

	@Override
	public void deleteBoard(long boardId) {
		boardDAO.deleteBoard(boardId);
	}

	
}
