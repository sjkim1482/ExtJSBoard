package com.nsetec.board.web;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nsetec.board.model.BoardVO;
import com.nsetec.board.model.PageVo;
import com.nsetec.board.model.PostVO;
import com.nsetec.board.model.UserVO;
import com.nsetec.board.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	//시큐리티 pw암호화
	@Inject
	BCryptPasswordEncoder pwdEncode;
	
	
	@Resource(name = "boardService")
	private BoardService boardService;
	
	//로그인
	@RequestMapping(path = "checkLogin")
	public String selectEmpList(UserVO userVo,HttpSession session, HttpServletRequest request, Model model) {
		
		UserVO dbUser = boardService.selectUser(userVo.getUser_id());
		boolean pwdMatch = false;
		if(dbUser != null) {
			pwdMatch = pwdEncode.matches(userVo.getPass(), dbUser.getPass());
		}
		logger.debug("================================");
		logger.debug("로그인 컨트롤러 접속");
		logger.debug("userVo : {}", userVo );
		logger.debug("================================");
//		int check = boardService.checkLogin(userVo);
		int check = 0;
		if(dbUser != null && pwdMatch == true) {
			check = 1;
//			UserVO joinUserVo = boardService.selectUser(userVo.getUser_id());
			session.setAttribute("S_USER", dbUser);
			model.addAttribute("userVo", dbUser);
		}
		model.addAttribute("check", check);
		
		return "jsonView";
	}
	
	//게시판 생성
	@RequestMapping(path = "insertBoard")
	public String insertBoard(BoardVO boardVo, Model model) {
		
		logger.debug("================================");
		logger.debug("게시판 생성 컨트롤러 접속");
		logger.debug("boardVo : {}", boardVo );
		logger.debug("================================");
		model.addAttribute("insertCnt", boardService.insertBoard(boardVo));
		
		return "jsonView";
	}
	
	//게시판 전체목록조회
	@RequestMapping(path = "selectBoardList")
	public String selectBoardList(Model model) {
		
		logger.debug("================================");
		logger.debug("게시판 전체목록조회 컨트롤러 접속");
		logger.debug("boardList : {}", boardService.selectBoardList());
		logger.debug("================================");
		model.addAttribute("boardList", boardService.selectBoardList());
		
		return "userListJson";
	}
	
	//사용자 전체목록조회
	@RequestMapping(path = "selectUserList")
	public String selectUserList(Model model) {
		
		logger.debug("================================");
		logger.debug("게시판 전체목록조회 컨트롤러 접속");
		logger.debug("userList : {}", boardService.selectUserList());
		logger.debug("================================");
		model.addAttribute("userList", boardService.selectUserList());
		
		return "jsonView";
	}
	
	//게시글 등록
	@RequestMapping(path = "insertPost")
	public String insertPost(PostVO postVo,HttpSession session, HttpServletRequest request, Model model) {
		UserVO userVo = (UserVO)(request.getSession().getAttribute("S_USER"));
		postVo.setUser_id(userVo.getUser_id());
		logger.debug("================================");
		logger.debug("게시글 등록 컨트롤러 접속");
		logger.debug("postVo : {}", postVo);
		logger.debug("================================");
		model.addAttribute("insertCnt", boardService.insertPost(postVo));
		
		return "jsonView";
	}
	
	
	//게시글 전체조회
	@RequestMapping(path = "selectPostList")
	public String selectPostList(PageVo pageVo, Model model) {

		logger.debug("================================");
		logger.debug("게시글 조회 컨트롤러 접속");
		logger.debug("pageVo : {}", pageVo);
		logger.debug("postList : {}", boardService.selectPostList(pageVo));
		logger.debug("================================");
		model.addAllAttributes(boardService.selectPostList(pageVo));
		
		return "jsonView";
	}
	
	
	//게시글 상세조회 postView
	@RequestMapping(path = "postView")
	public String postView(int post_no,HttpServletRequest request, Model model) {

		logger.debug("================================");
		logger.debug("게시글 상세조회 컨트롤러 접속");
		logger.debug("post_no : {}", post_no);
		logger.debug("================================");
		PostVO postVo = boardService.postView(post_no);
		model.addAttribute("post",postVo);
//		UserVO userVo = (UserVO)request.getSession().getAttribute("S_USER");
//		int writer = 0;
//		if(postVo.getUser_id().equals(userVo.getUser_id())) {
//			writer = 1;
//		}
//		model.addAttribute("writer",writer);
		
		return "jsonView";
	}
	
	//답글 등록
	@RequestMapping(path = "insertReply")
	public String insertReply(PostVO postVo,HttpSession session, HttpServletRequest request, Model model) {
		UserVO userVo = (UserVO)(request.getSession().getAttribute("S_USER"));
		postVo.setUser_id(userVo.getUser_id());
		logger.debug("================================");
		logger.debug("게시글 등록 컨트롤러 접속");
		logger.debug("postVo : {}", postVo);
		logger.debug("================================");
		model.addAttribute("insertCnt", boardService.insertReply(postVo));
		
		return "jsonView";
	}
	
	//유저 등록
	@RequestMapping(path = "registUser")
	public String registUser(UserVO userVo, Model model) {
		
		logger.debug("================================");
		logger.debug("유저 등록 컨트롤러 접속");
		String inputPass = userVo.getPass();
		String pwd = pwdEncode.encode(inputPass);
		userVo.setPass(pwd);
		logger.debug("userVo : {}", userVo);
		logger.debug("================================");
		model.addAttribute("insertCnt", boardService.registUser(userVo));
		
		return "jsonView";
	}
	
	//아이디 중복검사
	@RequestMapping(path = "checkUserId")
	public String checkUserId(String user_id, Model model) {
		
		logger.debug("================================");
		logger.debug("아이디 중복검사 컨트롤러 접속");
		logger.debug("user_id : {}", user_id);
		logger.debug("================================");
		model.addAttribute("check", boardService.checkUserId(user_id));
		
		return "jsonView";
	}
	
	
	
	
}
