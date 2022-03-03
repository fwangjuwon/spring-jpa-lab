package site.metacoding.dbproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    // 회원가입 페이지 (정적) - 누구나 들어갈 수 있다. (로그인 필요 없음)
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // 회원가입 -> 어떤 액션이 일어난다. (로그인x)
    @PostMapping("/join") // user이런거 적지도 않고 예외적으로 그냥 join
    public String join() {
        return "redirect:/user/loginForm"; // login페이지 이동해주는 컨트롤러 메소드를 재활용
    }

    // 로그인 페이지 (정적) (로그인x)
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // SELECT * FROM user WHERE username =? and password=?
    // 원래 select는 무조건 get요청
    // 근데 로그인만 예외! post!! -> 이유: 주소에 패스워드를 남길 수 없다. **기억해!! 로그인만 예외로 post를 쓴다.

    @PostMapping("/login") // user이런거 적지도 않고 예외적으로 그냥 login (로그인x)
    public String login() {
        return "메인페이지를 돌려주면 됨"; // Post Controller만들고 수정하자
    }

    // 유저 상세 페이지 (동적) -> 값마다 데이터가 달라짐 -> db연동이 필요하다. (로그인o->인증이 필요)
    @GetMapping("/user/{id}")
    public String detail(@PathVariable int id) {
        return "/user/detail";
    }

    // 유저수정 페이지 (동적) -> 로그인o
    @GetMapping("/user/{id}/undateForm")
    public String updateForm() {
        return "/user/updateForm";
    }

    // 유저정보 수정 페이지(로그인o ->인증이 필요)
    @PutMapping("/user/{id}")
    public String update(@PathVariable int id) {
        return "redirect:/user/" + id;
    }

    // 로그아웃 (로그인o)
    @GetMapping("/logout")
    public String logout() {
        return "main페이지를 돌려주면 됨 "; // postcontroller만들어서 수정하기
    }
}
