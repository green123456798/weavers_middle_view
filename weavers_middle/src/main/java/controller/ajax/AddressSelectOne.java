package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.dao.AddressDAO;
import model.dto.AddressDTO;

@WebServlet("/addressSelectOne.asy")
public class AddressSelectOne extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddressSelectOne() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// 요청에 대한 응답을 보내기위해 printWriter 객체 생성
		PrintWriter out = response.getWriter();
		
		// 주소의 정보를 담을 aDTO 객체와, 주소테이블에 기능을 수행할 aDAO 객체를 생성
		AddressDTO aDTO = new AddressDTO();
		AddressDAO aDAO = new AddressDAO();
		
		// aDTO에 파라미터로 받아온 apk(주소의 PK번호)값을 저장
		aDTO.setApk(Integer.parseInt(request.getParameter("apk")));
		aDTO = aDAO.selectOne(aDTO); // aDAO의 검색기능인 R(selectOne)기능을 사용하여 주소 테이블에 해당 주소가 존재하는지 확인
		System.out.println(aDTO);
		if(aDTO == null) { // 주소테이블에 해당 주소가 존재하지 않는다면
			out.print("[]"); // 빈 배열을 반환
		}
		else { // 주소테이블에 해당 주소가 존재한다면 else의 실행문을 실행
			if(aDTO.getJibunaddress() == null) { // 해당 주소의 지번주소가 null일경우
				aDTO.setJibunaddress(""); // 해당 주소의 지번주소를 빈칸으로 수정
			}
			if(aDTO.getDetail() == null) { // 해당 주소의 상세주소가 null일 경우
				aDTO.setDetail(""); // 해당 주소의 상세주소를 빈칸으로 수정
			}
			// Gson 라이브러리를 사용하여 객체를 JSON 문자열로 변환
			Gson gson = new Gson(); // Gson객체 생성
			String json = gson.toJson(aDTO); // aDTO객체를 json타입으로 변환하여 문자열로 저장
			out.print(json); // 문자열로 변환된 aDTO를 출력
			System.out.println(json);
		}
	}

}
