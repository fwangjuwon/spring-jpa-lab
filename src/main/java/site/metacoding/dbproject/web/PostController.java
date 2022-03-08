package site.metacoding.dbproject.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.post.PostRepository;
import site.metacoding.dbproject.domain.user.User;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final HttpSession session; // null이다. final 붙이면 오류된다. @requiredargsconstructor 붙이면 final이 붙은 애들에 대한 생성자를
                                       // 만들어준다. 주입 안 받고 싶으면 final 떼면 됨.
    private final PostRepository postRepository;

    // 글쓰기 페이지 /post/writeForm- 인증 필요o, 권한 필요x
    @GetMapping("/s/post/writeForm")
    public String writeForm() {
        // 인증 체크
        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }
        return "post/writeForm"; // viewresolver도움 받음
    }

    // main 페이지 - 인증 필요x
    // 글 목록 페이지 /post/list, /
    @GetMapping({ "/", "/post/list" })
    public String list(Model model) {

        // 1. post repository의 findAll()호출

        // 2. 모델에 담기
        model.addAttribute("posts", postRepository.findAll());
        // 3. 머스태치로 뿌리면 됨

        return "post/list";
    }

    // 글 상세보기 페이지 /post/{id} (삭제버튼 -> 글목록으로 돌아오기 , 수정버튼->수정페이지 만들어 두기) 인증 필요x
    @GetMapping("/post/{id}") // get요청에 /post 만 제외 시키기 !! get은 일단 무사통과 시켜 !
    public String detail(@PathVariable Integer id) {
        return "post/detail";
    }

    // 글 수정 페이지 /post/{id}/updateForm 인증필요o
    @GetMapping("/s/post/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "post/updateForm";
    }

    // delete 글 삭제 /post/{id} -> 글 목록 페이지로 인증필요o
    @DeleteMapping("/s/post/{id}")
    public String delete(@PathVariable Integer id) {
        return "redirect:/";
    }

    // update 글 수정 /post/{id} -> 글상세보기 페이지 인증필요o
    @PutMapping("/s/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/post/" + id;
    }

    // post글 쓰기 /post -> 글목록 페이지로 인증필요o
    @PostMapping("/s/post")
    public String write(Post post) {

        // title, content 1.null검사, 2. 공백 검사, 3. 길이 검사 ....

        // 인증 체크
        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }

        User principal = (User) session.getAttribute("principal");
        post.setUser(principal);
        // insert into post(title, content, userId) values(사용자한테 받음, 사용자한테 받음, 세션오브젝트의
        // PK)

        postRepository.save(post);
        return "redirect:/"; // redirect: 다른 페이지로 이동시켜준다.
    }
}
