package site.metacoding.dbproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import site.metacoding.dbproject.domain.post.Post;

@Controller
public class PostController {

    // 글쓰기 페이지 /post/writeForm
    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm"; // viewresolver도움 받음
    }

    // main 페이지
    // 글 목록 페이지 /post/list, /
    @GetMapping({ "/", "/post/list" })
    public String list() {
        return "post/list";
    }

    // 글 상세보기 페이지 /post/{id} (삭제버튼 -> 글목록으로 돌아오기 , 수정버튼->수정페이지 만들어 두기)
    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id) {
        return "post/detail";
    }

    // 글 수정 페이지 /post/{id}/updateForm
    @GetMapping("/post/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "post/updateForm";
    }

    // delete 글 삭제 /post/{id} -> 글 목록 페이지로
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Integer id) {
        return "redirect:/";
    }

    // update 글 수정 /post/{id} -> 글상세보기 페이지
    @PutMapping("/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/post/" + id;
    }

    // post글 쓰기 /post -> 글목록 페이지로
    @PostMapping("/post")
    public String write() {
        return "redirect:/"; // redirect: 다른 페이지로 이동시켜준다.
    }
}
