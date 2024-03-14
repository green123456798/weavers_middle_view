package controller.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.dao.AddressDAO;
import model.dao.MemberDAO;
import model.dto.AddressDTO;
import model.dto.MemberDTO;

public class RegisterSuccessAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입 완료 페이지로 이동하는 기능의 클래스
		
		ActionForward forward = new ActionForward();
		
		// Date 객체를 선언하고 초기화
		Date birth = null;
		
		// "yyyy-MM-dd" 형식의 날짜 문자열을 처리하기 위한 formatter 생성
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// request.getParameter("birth")로부터 전달된 생년월일 문자열을 Date 객체로 변환
			birth = formatter.parse(request.getParameter("birth"));			
		} catch (Exception e ) {
			// 예외가 발생한 경우
			e.printStackTrace();
		}
		
		// 사용자로부터 전달받은 마케팅 동의 여부를 변수에 저장
		String marketingString = request.getParameter("marketing");
		// 변수 초기화
		int marketing;
		
		// 사용자로부터 마케팅 동의를 받지 않은 경우
		if(marketingString == null) {
			//변수의 값 = 0
			marketing = 0;
		}
		// 사용자로부터 마케팅 동의를 받은 경우
		else {
			//변수의 값 = 1
			marketing = 1;
		}
		
		// MemberDTO와 MemberDAO 인스턴스 생성
     	// DB에서 사용자정보를 다루기 위해 필요한 객체들을 생성
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		// mDTO에 사용자로부터 전달받은 회원 정보 저장
		// 사용자 ID, 비밀번호, 이름, 전화번호, 닉네임, 이메일주소, 마케팅 동의 여부
		mDTO.setMid((String)request.getParameter("mid"));		
		mDTO.setMpw((String)request.getParameter("mpw"));
		mDTO.setName((String)request.getParameter("name"));		
		mDTO.setBirth(birth);
		mDTO.setPhone((String)request.getParameter("phone"));
		mDTO.setNickname((String)request.getParameter("nickname"));
		mDTO.setEmail((String)request.getParameter("email"));
		mDTO.setMarketing(marketing);
		
		// 디버깅을 위한 출력
		System.out.println("로그111"+mDTO);
		
		// MemberDAO의 insert 기능을 통해 전달받은 값을 flag에 저장
		boolean flag = mDAO.insert(mDTO);
		
		// 입력받은 정보의 추가가 된 경우
		if(flag) {
			forward.setPath("registerSuccess.jsp");
			forward.setRedirect(true);
		}
		// 입력받은 정보의 추가가 안된 경우
		else { 
			forward.setPath("register.jsp");
			forward.setRedirect(false);
		}
		
		// AddressDTO와 AddressDAO 인스턴스 생성
     	// DB에서 사용자 주소정보를 다루기 위해 필요한 객체들을 생성
		AddressDTO aDTO = new AddressDTO();
		AddressDAO aDAO = new AddressDAO();
		
		// aDTO에 사용자로부터 전달받은 주소정보를 저장
		aDTO.setMid((String)request.getParameter("mid"));
		aDTO.setZonecode(request.getParameter("zonecode"));
		aDTO.setJibunaddress(request.getParameter("jibunAddress"));
		aDTO.setRoadaddress(request.getParameter("roadAddress"));
		aDTO.setAname("기본배송지");
		String detail = request.getParameter("detailAddress");
		
		// 상세주소를 입력하지 않은 경우
		if(detail == null) {
			detail = "";
		}
		aDTO.setDetail(detail);
		
		
		flag = aDAO.insert(aDTO);
		// 전달받은 주소의 정보가 저장된 경우
		if(flag) {
			forward.setPath("registerSuccess.jsp");
			forward.setRedirect(true);
		}
		// 전달받은 주소의 정보가 저장되지 않은 경우
		else {
			forward.setPath("register.jsp");
			forward.setRedirect(false);
		}

		return forward;
	}
}
