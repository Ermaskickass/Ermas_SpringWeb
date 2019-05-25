package org.Ermas.article;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Ermas.book.chap11.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ArticleController {

	@Autowired
	ArticleDao articleDao;

	Logger logger = LogManager.getLogger();

	@GetMapping("/article/list")
	public void articleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Article> articleList = articleDao.listArticles(offset, COUNT);
		int totalCount = articleDao.getArticlesCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleList", articleList);
	}

	@GetMapping("/article/view")
	public void articleView(@RequestParam("articleId") String articleId,
			Model model) {
		Article article = articleDao.getArticle(articleId);
		model.addAttribute("article", article);
	}

	@GetMapping("/article/addForm")
	public String articleAddForm(HttpSession session) {
		return "article/addForm";
	}

	@PostMapping("/article/add")
	public String articleAdd(Article article,
			@SessionAttribute("MEMBER") Member member) {
		article.setUserId(member.getMemberId());
		article.setName(member.getName());
		articleDao.addArticle(article);
		return "redirect:/app/article/list";	
	}	
	
	@PostMapping("/article/up")
    public String up(Article article,
            @RequestParam("articleId") String articleId,
            @SessionAttribute("MEMBER") Member member) {
        article.setArticleId(articleId);
        articleDao.updateArticle(article);
        return "redirect:/app/article/list";
    }

	@GetMapping("/article/update")
    public String update(@RequestParam("articleId") String articleId,
        @SessionAttribute("MEMBER") Member member,
            Model model) {
        Article article = articleDao.getArticle(articleId);
        if(!member.getMemberId().equals(article.getUserId()))
            return "redirect:/app/article/view?articleId="+articleId;

        model.addAttribute("article",article);
        return "article/update";
	}
    
	@GetMapping("/article/delete")
	public String deleteArticle(@RequestParam("articleId") String articleId,
			@SessionAttribute("MEMBER") Member member)
	{
			Article article = articleDao.getArticle(articleId);
			if(!member.getMemberId().equals(article.getUserId()))
				return "redirect:/app/article/view?articleId="+articleId;
			
			articleDao.deleteArticle(article);
			return "redirect:/app/article/list";
	}
}


