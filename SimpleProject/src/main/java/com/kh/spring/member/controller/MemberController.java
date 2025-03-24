package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.MemberInsertFailException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // lombok 기능 
@Controller
@RequiredArgsConstructor // 의존성 주입 생성자를 생성해주는 애노테이션(롬복에서 해주는 거임 생성자 만들어주는 거임)
public class MemberController {
	
	/* 
	 * @RequiredArgsConstructor 를 쓰면 final 이 있는 필드에 Constructor Injection 이 되게 해줌.
	 * @Autowired 안써도됨
	 */
	
	// DI 란? 객체 간의 의존 관계를 외부에서 주입하여 결합도를 낮춰 주는 것.
	
	/* 1번 방법(Field Injection)
	 @Service를 사용해서 bean으로 추가된 memeberService 를 상속받은 애가 @Autowired를 사용하면 연결되어짐.
	 이걸 외부에서 의존성을 주입해준다 해서 '의존성 주입(Dependency Injection)' 이라 한다.
	 */
//	@Autowired
	private final MemberService memberService;

	/* 2번 방법(Setter Injection)
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	/* 3번 방법(Constructor Injection) 3번 방법이 권장됨!!!
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	// 1번 방법
	/*
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		log.info("id : {}, pw : {}", id, pw);
		
		return "main_page";
	}
	*/
	
	// 2번 방법
	/*
	@RequestMapping("login")
	public String login(@RequestParam(value="id", defaultValue="혹시 값이 안넘어 오면 기본 값으로 셋팅") String id,
						@RequestParam(value="pw") String pw) {
		log.info("두 번째 방법으로 뽑기 id : {}, pw : {}",id, pw);
		
		return "main_page";
	}
	*/
	
	// 3번 방법
	/*
	@PostMapping("login")
	public String login(String id, String pw) {
		
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		return "main_page";
	}
	*/
	// 3번이 편하지만 2번이 권장된다.
	
	// 4번
	/**
	 * 커맨드 객체 방식
	 * 
	 * 전제 조건
	 * 1. 매개 변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키 값과 객체의 필드명이 동일할 것
	 * 3. setter 메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter 메서드를 찾아서 요청 시 전달값을 해당 필드에 대입해줌
	 * (Setter Injection)
	 */
	// 이 메서드 하나 하나를 리퀘스트 핸들러 라고 한다.
//	@PostMapping("login")
//	public String login(MemberDTO member, HttpSession session, Model model) {
//		
//		/* 컨트롤러 역할
//		 * 1. 데이터 가공 => 스프링이 해줌
//		 * 2. 요청처리 => 서비스에서 함
//		 * 3. 응답화면 지정 
//		 */
//		
//		// new MemberServiceImpl().login(member);
//		// 이제 이렇게 직접 생성 안하고 bean 에 등록돼 있는 외부에서 씀
//		// => 의존성 주입
//		MemberDTO loginMember = memberService.login(member);
//		
//		if (loginMember != null) { // 성공
//			session.setAttribute("loginMember", loginMember);
//			
////			return "main_page";
//// 			=> 이건 포워딩 방식이다.
//			return "redirect:/";
////			=> spring 에서 SendRedirect 하는 방법
//		} else { // 실패
//			// requestScope에 에러문구를 담아서 포워딩
//			// spring 에서는 Model 객체를 이용해서 RequestScope 에 값을 담음
//			model.addAttribute("message", "로그인 실패데스");
//			
//			// fowarding
//			// viewresolver 는 
//			// /WEB-INF/views/ 까지만 붙여준다.
//			// error_page 는 include 밑에 있음
//			// 이런 문자열을 가지고 찾아가는 방식을 논리적인 경로를 가지고 물리적인 경로를 찾아간다고 한다.
//			return "include/error_page";
//		}
//	}
	
	// 두 번째 방법 반환타입 ModelAndView로 돌아가기
	@PostMapping("login")
	public ModelAndView login(HttpSession session, MemberDTO member, ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if (loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("message", "로그인 실패").setViewName("include/error_page");
		}
		
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mv) {
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("signup-form")
	public String signupForm() {
		return "member/signup-form";
	}
	
	/**
	 * @param memberId, memberPw, memberName, email
	 * 
	 * @return 성공 시 웰컴페이지 실패 시 에러페이지
	 */
	@PostMapping("signup")
	public String signup(MemberDTO member) {
		memberService.signUp(member);
		
		return "main_page";
	}
	
	@GetMapping("my-page")
	public String myPage() {
		return "member/my_page";
	}
	
	@PostMapping("update-member")
	public String update(MemberDTO member, HttpSession session) {
		
		// 1. Controller 에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		/*
		 * 1_1) 404 발생 : mapping 값 잘못 적음
		 * org.springframework.web.servlet.PageNotFound
		 * - No mapping for POST/ spring/update-member
		 * 
		 * 1_2) 405 발생 : 앞단에선 GET / POST 로 요청을 보내놓고 메서드와 맞지 않는 애너테이션을 사용했을 때
		 * Request method 'POST' not suppoted
		 * 
		 * 1_3) 필드에 값이 들어오지 않는 경우
		 */
		log.info("사용자가 입력한 값 : {}", member);
		
		// 2. 이번에 실행할 SQL 문을 생각
		// UPDATE문
		// ID, PW, MAME, EMAIL, DATE
		// 2_1) 매개변수 MemberDTO 타입의 memberId 필드값
		// 2_2) SessionScope에 loginMember 키값에 memberId 필드값
		// 넘겨 주어야 겠구나 +
		
		// 값들이 유효한 값인지 체크하기
		// MemberId가 존재하는 아이디인지 체크하기
		
		// UPDATE KH_MEMBER SET MEMBER_NAME = 사용자가 입력한 이름, EMAIL = 사용자가 일벽한 이메일 WHERE MEMBER_ID = 사용자가 입력한 아이디
		
		// UPDATE 수행의 결과 => PK 를 조건으로 수행함 => 0 / 1
		
		// 수행에 성공했을 경우 =>
		// my-page.jsp 로 이동 + 갱신된 회원의 정보 출력
		
		// 수행에 실패했을 경우 =>
		// message를 담아서 error_page로 포워딩
		// 예외 발생 => 예외 처리기로 위임
		
		memberService.update(member, session);
		
		return "redirect:my-page";
	}
	
	@PostMapping("deleteMember")
	public String deleteMember(MemberDTO member, HttpSession session) {
		
		member.setMemberId(((MemberDTO)session.getAttribute("loginMember")).getMemberId());
		int result = memberService.delete(member);
		
		if(result > 0) {
			session.setAttribute("message", "삭제 성공~~~~");
		}else {
			new MemberInsertFailException("회원 삭제에 실패했어유");
		}
		
		return "redirect:logout";
	}
	
	@ResponseBody
	@GetMapping("id-check")
	public String idCheck(@RequestParam(name="memberId") String memberId) {
		
		memberService.idCheck(memberId);
		
		return memberService.idCheck(memberId);
	}
	
	
}
