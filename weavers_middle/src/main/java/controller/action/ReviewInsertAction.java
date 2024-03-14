package controller.action;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import controller.Action;
import controller.ActionForward;
import model.dao.ReviewDAO;
import model.dto.ReviewDTO;

public class ReviewInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 리뷰 작성(추가)을 수행하는 기능의 클래스
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 리뷰의 정보를 담을 rDTO 객체와, 리뷰테이블에 기능을 수행할 rDAO 객체를 생성
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();
		
		// 세션타입의 변수에 request로 세션을 저장
		HttpSession session = request.getSession();
		
		
		String path = "D:\\JY_java\\workspace\\weaveGlow_ver0.3.6\\src\\main\\webapp\\uploadimg";// 본인 uploadimg 폴더 경로 지정
		int size = 10 * 1024 * 1024; // 10MB 
		
		// 파일을 멀티파트로 인코딩해서 받아오기 때문에 멀티파트 객체를 생성해서 변수로 저장
		MultipartRequest multipart = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
		
		rDTO.setMid((String)session.getAttribute("sessionMid")); // 세션에서 사용자의 id 값을 받아와서 rDTO에 저장
		rDTO.setBpk(Integer.parseInt((String)multipart.getParameter("bpk"))); 
		// multipart객체를 사용하여 bpk(구매한상품의PK번호 <= 리뷰를 남길 상품은 어떤상품인지)를 받아와서 rDTO에 저장
		
		String content = multipart.getParameter("content"); // multipart객체를 사용하여 리뷰내용을 받아옴
		if(content == null) { // 만약 리뷰 내용이 없을 경우
			content = ""; // 리뷰를 빈칸을 지정
		}
		rDTO.setContent(content); // rDTO에 위에서 초기화한 리뷰내용을 저장
      
		int scope = Integer.parseInt(multipart.getParameter("scope")); // multipart 객체를 사용하여 별점(scope)값을 받아옴
		if(scope == 0) { // 만약 별점이 0점이라면 (별점의 default=1로 지정, 만약을 위한 유효성검사)
			scope = 1; // 별점을 1로 설정
		}
		rDTO.setScope(scope); // rDTO에 초기화한 별점값을 저장

		String img = multipart.getFilesystemName("file"); // multipart객체를 사용하여 파일명을 받아옴
		if(img == null) { // 만약 파일명이 존재하지 않을 경우
			img = ""; // 파일명을 빈칸으로 지정
		}
		rDTO.setImg(img); // rDTO에 초기화한 파일명을 저장
		System.out.println(rDTO);
		boolean flag = rDAO.insert(rDTO); // rDTO를 사용하여 rDAO의 추가기능인 C(insert)기능을 사용하여 리뷰테이블에 리뷰데이터를 추가
		if(flag) { // 만약 리뷰테이블에 리뷰데이터를 성공적으로 추가했다면
			forward.setPath("reviewList.do"); // checkoutList.do로 경로를 지정 => 구매내역으로 페이지를 이동하는 기능을 수행하는 액션으로 경로지정
			forward.setRedirect(true); // request에 저장된 값이 존재하지 않으므로 리다이렉트 방식으로 지정
		}
		else {
			forward.setPath("error.jsp"); // 만약 리뷰테이블에 리뷰데이터 추가를 실패했다면 에러페이지로 이동
			forward.setRedirect(true);
		}
		return forward;
	}
}
