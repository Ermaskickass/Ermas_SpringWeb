package org.Ermas.letter;

import java.util.List;

import org.Ermas.book.chap11.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class LetterController {
	
	@Autowired
	LetterDao letterDao;

	
	// 보낸 목록
	@GetMapping("/letter/sendletter")
	public void listSend (@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("MEMBER") Member member, Model model) {

		final int ROWS_PER_PAGE = 20;
		int offset = (page - 1) * ROWS_PER_PAGE;

		List<Letter> letters = letterDao.listSendLetter(member.getMemberId(),
				offset, ROWS_PER_PAGE);
		int count = letterDao.countLettersSent(member.getMemberId());

		model.addAttribute("letters", letters);
		model.addAttribute("count", count);
	}
	
	//받은 목록
	@GetMapping("/letter/receive")
	public void listReceive(@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("MEMBER") Member member, Model model) {

		final int ROWS_PER_PAGE = 20;
		int offset = (page - 1) * ROWS_PER_PAGE;

		List<Letter> letters = letterDao.listLettersReceived(
				member.getMemberId(), offset, ROWS_PER_PAGE);
		int count = letterDao.countLettersReceived(member.getMemberId());

		model.addAttribute("letters", letters);
		model.addAttribute("count", count);
	}
	
	//조회
	@GetMapping("letter/letterview")
	public void LetterView(@RequestParam("letterId") String letterId,
			@SessionAttribute("MEMBER") Member member, Model model) {
		Letter letter = letterDao.getLetter(letterId, member.getMemberId());
		model.addAttribute("letter", letter); 
	}
	
	//쓰기
	@GetMapping("/letter/add")
	public String letterAdd() {
		return "letter/add";
	}

	@PostMapping("/letter/addsend")
	public String letterAddSend(Letter letter,
			@SessionAttribute("MEMBER") Member member) {
		letter.setSenderId(member.getMemberId());
		letter.setSenderName(member.getName());
		letterDao.addLetter(letter);
		return "redirect:/app/letter/sendletter";	
	}
	
	
	//삭제
	@GetMapping("/letter/delete")
	public String delete(@RequestParam(value = "mode", required = false) String mode,
			@RequestParam("letterId") String letterId,
			@SessionAttribute("MEMBER") Member member) {
		int updatedRows = letterDao.deleteLetter(letterId,
				member.getMemberId());
		if (updatedRows == 0)
			throw new RuntimeException("No Authority!");

		if ("SENT".equals(mode))
			return "redirect:/app/letter/sendletter";
		else
			return "redirect:/app/letter/receive";
	}
	
}