package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GradeDAO {
	
	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL = "";
	private static final String SELECTONE = "";

	private static final String INSERT = "";
	private static final String UPDATE = "";
	private static final String DELETE = "";

}
