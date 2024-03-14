package controller.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.ActionForward;
import model.dto.AddressDTO;
import model.dto.BuyProductDTO;

public class MailSendAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();

		String to=(String)request.getParameter("email");
		String subject= "구매해 주셔서 감사합니다 - WEAVE GLOW";
		String text="상품내역은 생략하겠습니다. 구매해주셔서 감사합니다";
		
		AddressDTO aDTO = (AddressDTO)request.getAttribute("aDTO");
		ArrayList<BuyProductDTO> datas = (ArrayList<BuyProductDTO>)request.getAttribute("bdatas");
		int totalPrice = 0;
			// 구매한 상품들을 html형식으로 나타내기위한 문자열
		String html = "<h3>구매해 주셔서 감사합니다</h3><br><span><b>주문번호 : "+datas.get(0).getSpk()+"</b></span><br><span><b>주문날짜 : "+ datas.get(0).getRegdate()+"</b></span><br><span><b>배송지 : ["+aDTO.getZonecode()+"] " + aDTO.getRoadaddress()+" " + aDTO.getDetail() + "</b></span> <br><br>"
				+ "<div class=\"table-responsive\" id=\"orderList\">\r\n"
				+ "					<table class=\"table\" style=\"border-spacing:0px; border-collapse: collapse;\">\r\n"
				+ "						<colgroup>\r\n"
				+ "							<col width=\"40%\" />\r\n"
				+ "							<col width=\"20%\" />\r\n"
				+ "							<col width=\"20%\" />\r\n"
				+ "							<col width=\"20%\" />\r\n"
				+ "						</colgroup>\r\n"
				+ "						<thead>\r\n"
				+ "							<tr>\r\n"
				+ "								<th scope=\"col\" style=\"border:2px black solid; text-align:center; border-collapse: collapse; background-color: navajowhite; padding:5px; \">상품명</th>\r\n"
				+ "								<th scope=\"col\" style=\"border:2px black solid; text-align:center; border-collapse: collapse; background-color: navajowhite; padding:5px; \">상품가격</th>\r\n"
				+ "								<th scope=\"col\" style=\"border:2px black solid; text-align:center; border-collapse: collapse; background-color: navajowhite; padding:5px; \">상품수량</th>\r\n"
				+ "								<th scope=\"col\" style=\"border:2px black solid; text-align:center; border-collapse: collapse; background-color: navajowhite; padding:5px; \">합계</th>\r\n"
				+ "							</tr>\r\n"
				+ "						</thead>\r\n"
				+ "						<tbody>\r\n";
		
				// 출력할 구매내역을 for문을 통해 구매한 상품을 반복하여출력
				for(BuyProductDTO data : datas) {
						html += "									<tr>\r\n"
							+ "										<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
							+ "											<p>"+data.getPname()+"</p>\r\n"
							+ "										</td>\r\n"
							+ "										<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
							+ "											<p>"+data.getPrice()+"원</p>\r\n"
							+ "										</td>\r\n"
							+ "										<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
							+ "											<p>"+data.getCnt()+"개</p>\r\n"
							+ "										</td>\r\n"
							+ "										<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
							+ "											<p><span class=\"productPrice\">"+data.getPrice()*data.getCnt()+"</span>원</p>\r\n"
							+ "										</td>\r\n"
							+ "									</tr>\r\n";
							// 구매한 상품들의 총가격을 나타내기위해 구매한 상품의 가격을 총가격에 더하여 저장
							totalPrice += data.getPrice()*data.getCnt();
				}
				
				html += "							<tr>\r\n"
				+ "								<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
				+ "									<h4>총 결제 금액</h4>\r\n"
				+ "								</td>\r\n"
				+ "								<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
				+ "									<h5>-</h5>\r\n"
				+ "								</td>\r\n"
				+ "								<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
				+ "									<h5>-</h5>\r\n"
				+ "								</td>\r\n"
				+ "								<td style=\"border:2px black solid; text-align:center; border-collapse: collapse; padding:5px;\">\r\n"
				+ "									<h4>"+totalPrice+"원</h4>\r\n"
				+ "								</td>\r\n"
				+ "							</tr>\r\n"
				+ "						</tbody>\r\n"
				+ "					</table>\r\n"
				+ "				</div>";
		
		String user = "wgw1008@gmail.com"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
		String password = "bkrcumodnhxdcfjc"; // 패스워드 bkrcumodnhxdcfjc
		
		// SMTP 서버 정보를 설정
		Properties prop = new Properties(); 
		prop.put("mail.smtp.host", "smtp.gmail.com"); // 메일 서버 주소
		prop.put("mail.smtp.starttls.enable", "true"); // 두 컴퓨터 사이의 연결 암호화 표준 기술 : TLS라는 보안인증서 활성화
		prop.put("mail.smtp.ssl.protocols","TLSv1.2"); // SSL 프로토콜을 TLS1.2로 설정
		prop.put("mail.smtp.auth", "true"); // 계정과 비밀번호 넣는 부분으로 사용하겠다는 뜻
		prop.put("mail.smtp.port", "587"); // TLS를 통신할 포트 : gmail이므로 587포트
		
		
		
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));   // 발신자메일주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //수신자메일주소
			// Subject
			message.setSubject(subject); //메일 제목을 입력
			// Text
//			message.setText(text);    //메일 내용을 입력
			// HTML
			 message.setContent(html, "text/html; charset=UTF-8");
			
			// send the message
			Transport.send(message); //// Transport.send()를 통해 메세지 전송
			System.out.println("message sent successfully...");
			forward.setPath("checkoutSuccess.jsp");
			forward.setRedirect(false);
			
		} catch (AddressException e) {
			forward.setPath("checkoutSuccess.jsp");
			forward.setRedirect(false);
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
			forward.setPath("checkoutSuccess.jsp");
			forward.setRedirect(false);
		}
		return forward;
	}

}
