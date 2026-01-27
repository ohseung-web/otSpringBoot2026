package com.green.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
   
	@Autowired
	private DataSource dataSource;
	
	//하나의 게시글을 추가하는 메소드----------------------------------
	public void insertBoard(BoardDTO bdto) {
		System.out.println("BoardDAO insertBoard()메소드 확인");
		
		String sql = "INSERT INTO board(writer,subject,writerPw,content) VALUES(?,?,?,?)";
		try(
				Connection conn =  dataSource.getConnection(); 
				PreparedStatement  pstmt =  conn.prepareStatement(sql);
				  ){
			  		pstmt.setString(1, bdto.getWriter());
			        pstmt.setString(2, bdto.getSubject());
			        pstmt.setString(3, bdto.getWriterPw());
			        pstmt.setString(4, bdto.getContent());
			        pstmt.executeUpdate();
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
	}
	
	// 전체 게시글을 검색하는 메소드----------------------------
	public List<BoardDTO> getAllBoard(){
      
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		String sql = "SELECT * FROM board ORDER BY num DESC";
		try(
				Connection conn =  dataSource.getConnection(); 
				PreparedStatement  pstmt =  conn.prepareStatement(sql);
				){
			    ResultSet rs = pstmt.executeQuery();
			    while(rs.next()) {
			    	BoardDTO bdto = new BoardDTO();
			    	bdto.setNum(rs.getInt("num"));
			    	bdto.setWriter(rs.getString("writer"));
			    	bdto.setSubject(rs.getString("subject"));
			    	bdto.setWriterPw(rs.getString("writerPw"));
			    	bdto.setReg_date(rs.getString("Reg_date").toString());
			    	bdto.setReadcount(rs.getInt("readcount"));
			    	bdto.setContent(rs.getString("content"));
			    	
			    	boardList.add(bdto);
			    	
			    }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}
	
	//하나의 게시글 검색 ---------------------------------------
	// 하나의 게시글을 리턴받는 메소드 작성
    public BoardDTO getOneBoard(int num) {
 
    	BoardDTO bdto = new BoardDTO();
    	String countsql = "UPDATE board SET readcount = readcount + 1 WHERE num=?";
        String sql = "SELECT * FROM board WHERE num=?";

        // Connection은 하나만 열어서 공유합니다.
        try (Connection conn = dataSource.getConnection()) {
            
            // 1. 조회수 증가 처리
            try (PreparedStatement pstmt = conn.prepareStatement(countsql)) {
                pstmt.setInt(1, num);
                pstmt.executeUpdate();
            }

            // 2. 게시글 정보 가져오기
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, num);
                ResultSet rs = pstmt.executeQuery();
//                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        bdto.setNum(rs.getInt("num"));
                        bdto.setWriter(rs.getString("writer"));
                        bdto.setSubject(rs.getString("subject"));
                        bdto.setWriterPw(rs.getString("writerPw"));
                        bdto.setReg_date(rs.getString("Reg_date").toString());
                        bdto.setReadcount(rs.getInt("readcount"));
                        bdto.setContent(rs.getString("content"));
                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bdto;
    }
    
    // 하나의 게시글 수정하는 메소드 ---------------------------------------
    public int updateBoard(BoardDTO bdto) {
        System.out.println("BoardDAO updateBoard() 메소드 확인");
        
        int result = 0;
        // 제목과 내용만 수정 가능하도록 설정 (비밀번호 확인 로직은 보통 서비스나 쿼리 조건에 추가)
        String sql = "UPDATE board SET subject=?, content=? WHERE num=? AND writerPw=?";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, bdto.getSubject());
            pstmt.setString(2, bdto.getContent());
            pstmt.setInt(3, bdto.getNum());
            pstmt.setString(4, bdto.getWriterPw());
            
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    // 하나의 게시글을 삭제하는 메소드 ---------------------------------------
    public int deleteBoard(int num, String writerPw) {
        System.out.println("BoardDAO deleteBoard() 메소드 확인");
        
        int result = 0;
        // 번호와 비밀번호가 일치해야 삭제
        String sql = "DELETE FROM board WHERE num=? AND writerPw=?";
        
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, num);
            pstmt.setString(2, writerPw);
            
             result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
	//-----------------------------------------------------------------
}
