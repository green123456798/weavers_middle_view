package controller.listener;

import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.dao.ProductDAO;
import model.dto.ProductDTO;
import model.util.Crawling;

@WebListener
public class Crawlinglistener implements ServletContextListener {


    public Crawlinglistener() {
    }
    
    public void contextDestroyed(ServletContextEvent sce)  { 
    }
    
    public void contextInitialized(ServletContextEvent sce)  {
       ProductDTO pDTO = new ProductDTO();
       ProductDAO pDAO = new ProductDAO();
       pDTO.setSearchCondition("판매순");
       ArrayList<ProductDTO> datas = pDAO.selectAll(pDTO);
       if(datas.size() <= 0) {
          Crawling crawling = new Crawling(); // 크롤링 객체를 생성
          crawling.crawlingStart(); // 크롤링객체의 메서드를 실행하여 웹에서 제품을 크롤링하고 DB의 product(제품)테이블에 저장          
       }
    }
   
}