package org.Ermas.letter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LetterDao {
	
	//보낸 목록
	static final String LIST_SEND = "select letterId,title,receiverId,receiverName,left(cdate,19) cdate from letter where senderId=? order by letterId desc limit ?,?";
	
	static final String COUNT_LETTERS_SEND = "select count(letterId) from letter where senderId=?";
	//받은 목록
	static final String LIST_RECEIVER = "select letterId,title,senderId,senderName,left(cdate,19) cdate from letter where receiverId=? order by letterId desc limit ?,?";
	
	static final String COUNT_LETTERS_RECEIVE = "select count(letterId) from letter where receiverId=?";
	// 조회
	static final String GET_LETTER = "select letterId,title,content,senderId,senderName,receiverId,receiverName,left(cdate,19) cdate from letter where letterId=? and (senderId=? or receiverId=?)";
	// 쓰기
	static final String ADD_LETTER = "insert letter(title,content,senderId,senderName,receiverId,receiverName) values(?,?,?,?,?,?)";
	// 삭제
	static final String DELETE_LETTER = "delete from letter where letterId=? and (senderId=? or receiverId=?)";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Letter> letterRowMapper = new BeanPropertyRowMapper<>(Letter.class);
	
	//받은 목록
	public List<Letter> listLettersReceived(String receiverId, int offset,
			int count) {
		return jdbcTemplate.query(LIST_RECEIVER, letterRowMapper, receiverId, offset, count);
	}
		
	public int countLettersReceived(String receiverId) {
		return jdbcTemplate.queryForObject(COUNT_LETTERS_RECEIVE, Integer.class, receiverId);
	}
		
	//보낸 목록
	public List<Letter> listSendLetter(String senderId, int offset, int count) {
		return jdbcTemplate.query(LIST_SEND, letterRowMapper, senderId, offset, count);
	}
	
	public int countLettersSent(String senderId) {
		return jdbcTemplate.queryForObject(COUNT_LETTERS_SEND, Integer.class, senderId);
	}
	
	// 조회
	public Letter getLetter(String letterId, String memberId) {
		return jdbcTemplate.queryForObject(GET_LETTER, letterRowMapper, letterId, memberId, memberId);
	}
	
	// 쓰기
	public int addLetter(Letter letter) {
		return jdbcTemplate.update(ADD_LETTER, letter.getTitle(),
				letter.getContent(), letter.getSenderId(), letter.getSenderName()
				, letter.getReceiverId(), letter.getReceiverName());
	} 
	
	//삭제
	public int deleteLetter(String letterId, String memberId) {
		return jdbcTemplate.update(DELETE_LETTER, letterId, memberId, memberId);
	}
	
}
