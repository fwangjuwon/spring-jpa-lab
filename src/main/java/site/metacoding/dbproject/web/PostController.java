package site.metacoding.dbproject.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.post.PostRepository;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.service.PostService;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final HttpSession session;
    private final PostService postService;
    private final PostRepository postRepository; // 나중에 지워야함

    // 글쓰기 페이지 /post/writeForm- 인증 필요o, 권한 필요x
    @GetMapping("/s/post/writeForm")
    public String writeForm() {
        // 인증 체크
        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }
        return "post/writeForm"; // viewresolver도움 받음
    }

    // 이사!!
    // main 페이지 - 인증 필요x
    // 글 목록 페이지 /post/list, /
    @GetMapping({ "/", "/post/list" })
    public String list(@RequestParam(defaultValue = "0") Integer page, Model model) {

        Page<Post> pagePosts = postService.글목록보기(page);

        model.addAttribute("posts", pagePosts);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("prevPage", page - 1);

        // 3. 머스태치로 뿌리면 됨

        return "post/list";
    }

    // @GetMapping("/test/post/list")
    // public @ResponseBody Page<Post> listTest(@RequestParam(defaultValue = "0")
    // Integer page) {
    // PageRequest pq = PageRequest.of(page, 3);
    // return postRepository.findAll(pq);
    // }

    // 이사!!
    // 글 상세보기 페이지 /post/{id} (삭제버튼 -> 글목록으로 돌아오기 , 수정버튼->수정페이지 만들어 두기) 인증 필요x
    @GetMapping("/post/{id}") // get요청에 /post 만 제외 시키기 !! get은 일단 무사통과 시켜 !
    public String detail(@PathVariable Integer id, Model model) {

        Post postEntity = postService.글상세보기(id);

        Optional<Post> postOp = postRepository.findById(id);

        if (postEntity == null) {
            return "error/page1";
        } else {
            model.addAttribute("post", postEntity);
            return "post/detail";
        }
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

    // 이사!
    // post글 쓰기 /post -> 글목록 페이지로 인증필요o
    @PostMapping("/s/post")
    public String write(Post post) {

        // title, content 1.null검사, 2. 공백 검사, 3. 길이 검사 ....

        // 인증 체크
        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }

        User principal = (User) session.getAttribute("principal");
        postService.글쓰기(post, principal);
        return "redirect:/"; // redirect: 다른 페이지로 이동시켜준다.
    }
}
