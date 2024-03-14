package controller.action;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import controller.Action;
import controller.ActionForward;
import model.dao.ReviewDAO;
import model.dto.ReviewDTO;

public class ReviewUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 이동할 페이지의 값을 담을 forward 객체를 생성
		ActionForward forward = new ActionForward();
		
		// 상품의 정보를 담을 pDTO 객체와, 상품테이블에 기능을 수행할 pDAO 객체를 생성
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();
				
		String path = "D:\\JY_java\\workspace\\weaveGlow_ver0.3.6\\src\\main\\webapp\\uploadimg";// 본인 uploadimg 폴더 경로 지정
		int size = 10 * 1024 * 1024; // 10MB
		
		// 파일을 멀티파트로 인코딩해서 받아오기 때문에 멀티파트 객체를 생성해서 변수로 저장
		MultipartRequest multipart = new MultipartRequest(request, path, size, "UTF-8", new DefaultFileRenamePolicy());
		rDTO.setRpk(Integer.parseInt(multipart.getParameter("rpk"))); // request 대신 multipart를 사용하여 rpk(리뷰PK)파라미터를 받아와서 rDTO에 저장
		
		// 리뷰의 내용(content)을 multipart 객체를 통해서 받음.
		String content = multipart.getParameter("content");
		if(content == null) { // 만약 받아온 값이 null이라면 값을 ""로 지정
			content = "";
		}
		rDTO.setContent(content); // rDTO에 content값 저장
		
		// 리뷰의 별점(scope)를 multipart 객체를 통해서 받음
		int scope = Integer.parseInt(multipart.getParameter("scope"));
	      if(scope == 0) { // 만약 받아온 값이 0이라면 값을 "1"로 지정 (scope의 default값은 1, 만약을 대비한 유효성검사)
	    	  scope = 1;
	      }
	      rDTO.setScope(scope); // rDTO에 별점값을 저장
		
		String img = multipart.getFilesystemName("file"); // 리뷰에서 새로 업로드된 사진파일의 이름을 multipart객체를 통해 받음
		String prevImg = multipart.getParameter("prevImg"); // 수정전에 존재하던 사진파일의 이름을 multipart객체를 통해 받음
		if(img == null) { // 만약 이미지가 새로 업로드 되지 않았다면
			img = prevImg; // 기존의 이미지를 사용
		}
		else { // 이미지가 새로 업로드 되었다면
			// 수정전의 이미지의 경로를 Path객체에 저장
			Path filePath = Paths.get(path+"\\"+prevImg);
			// Path directoryPath = Paths.get("d:\\example"); <= 폴더를 삭제할경우 해당 방식을 사용
			try {            
				// 파일 삭제            
				Files.delete(filePath);  // 파일객체의 delete메소드를 사용하여 위에서 초기화한 이미지경로를 사용          
				// 디렉토리 삭제            
				// Files.delete(directoryPath); <= 디렉토리 삭제는 좌측의 방식을 사용
			} 
			catch (NoSuchFileException e) { // 삭제하려는 파일이 존재하지 않는다면    
				System.out.println("삭제하려는 파일이 없습니다"); // 삭제 실패문구 출력하고
				e.printStackTrace(); // 해당 오류를 출력
			}
			catch (IOException e) { // 그외의 입출력과정에서 오류가 발생한다면
				e.printStackTrace(); // 해당오류를 출력
			}
		}
		rDTO.setImg(img); // 위에서 설정된 img의 값을 rDTO에 저장
		System.out.println(rDTO);
		boolean flag = rDAO.update(rDTO); // 리뷰관련정보를 담고있는 rDTO를 rDAO에서 수정기능인 U(update)기능을 사용하여 리뷰테이블의 데이터를 수정
		if(flag) { // 만약 리뷰테이블의 데이터수정이 정상적으로 작동했으면
			forward.setPath("reviewList.do"); // reviewList.do로 경로를 지정 
	          forward.setRedirect(true);
		}
		else {
			forward.setPath("error.jsp"); // 만약 리뷰테이블의 데이터 수정이 실패했다면 에러페이지로 이동
	          forward.setRedirect(true);
		}
		return forward;
	}
}
