package com.nsetec.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nsetec.board.model.BoardVO;
import com.nsetec.board.model.PageVo;
import com.nsetec.board.model.PostVO;
import com.nsetec.board.model.UserVO;
import com.nsetec.board.repository.BoardDao;
import com.nsetec.board.web.BoardController;

@Service("boardService")
public class BoardService implements BoardServiceI {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	@Resource(name = "boardDao")
	private BoardDao boardDao;

	//로그인
	@Override
	public int checkLogin(UserVO userVo) {
		// TODO Auto-generated method stub
		return boardDao.checkLogin(userVo);
	}

	//게시판 생성
	@Override
	public int insertBoard(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return boardDao.insertBoard(boardVo);
	}

	//게시판 전체목록조회
	@Override
	public List<BoardVO> selectBoardList() {
		// TODO Auto-generated method stub
		return boardDao.selectBoardList();
	}

	@Override
	public List<UserVO> selectUserList() {
		// TODO Auto-generated method stub
		return boardDao.selectUserList();
	}

	@Override
	public int insertPost(PostVO postVo) {
		// TODO Auto-generated method stub
		return boardDao.insertPost(postVo);
	}

	@Override
	public UserVO selectUser(String user_id) {
		// TODO Auto-generated method stub
		return boardDao.selectUser(user_id);
	}

	//게시글 전체조회
	@Override
	public Map<String, Object> selectPostList(PageVo pageVo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		int postListCnt = boardDao.postListCnt(pageVo);
		logger.debug("****************************************************");
		logger.debug("****************************************************");
		logger.debug("****************************************************");
		logger.debug("****************************************************");
		logger.debug("postListCnt : {}" ,postListCnt);
		
		map.put("postList", boardDao.selectPostList(pageVo));
		map.put("postListCnt", postListCnt);
		map.put("pagination", (int)Math.ceil( (double)postListCnt / pageVo.getPageSize()));
		map.put("pageVo", pageVo);
		
		return map;
	}
	
	//게시글 상세조회
	@Override
	public PostVO postView(int post_no) {
		// TODO Auto-generated method stub
		boardDao.viewsPlus(post_no);
		return boardDao.postView(post_no);
	}

	@Override
	public int insertReply(PostVO postVo) {
		// TODO Auto-generated method stub
		return boardDao.insertReply(postVo);
	}

	@Override
	public int registUser(UserVO userVo) {
		// TODO Auto-generated method stub
		return boardDao.registUser(userVo);
	}

	@Override
	public int checkUserId(String user_id) {
		// TODO Auto-generated method stub
		return boardDao.checkUserId(user_id);
	}
	
}
