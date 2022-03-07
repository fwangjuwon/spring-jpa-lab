package site.metacoding.dbproject.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@Controller
public class UserController {

    // 컴포지션(의존성 연결) : 컨트롤러는 레파지토리에 의존해야해!
    private UserRepository userRepository;
    private HttpSession session;

    // DI 받는 코드!!
    public UserController(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    // 회원가입 페이지 (정적) - 인증(로그인) X
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // username=ssar&password=1234&email=ssar@nate.com (x-www 타입)
    // 회원가입 INSERT - 인증(로그인) X
    @PostMapping("/join")
    public String join(User user) { // 행위, 페이지 아님
        // 1. username, password, email check -> choin form 에서 던진다. 문제!!서버 실행해보면 패스워드
        // 안넣어도 회원가입이 돼버림
        // -> 프론트에서 먼저 막아야한다. -일반인 (required 속성 걸기)
        // -> 백엔드에서 막기 - 공격자(body로 넘어온 값을 확인: null check, 공백 check, @없는거 체크, 비밀번호 길이
        // 체크,비번이 한글인지,,,,) -> null과 공백만 체크!!
        // username=ssar&password=&email=ssar@nate.com 패스워드 공백
        // username=ssar&email=ssar passwordnull
        // user.getpassword==null

        // 1. username, password, email -> null check, 공백 check
        // try-catch로 잡는다. if로 하면 최악이다 하나하나 다해야되
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return "redirect:/joinForm"; // 문제! 새로고침됨
        }
        if (user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("")) {
            return "redirect:/joinForm";
        }

        // 2. 핵심 로직
        User userEntity = userRepository.save(user);
        System.out.println("userEntity : " + userEntity);
        // redirect는 GetMapping 주소!! redirect:매핑주소
        return "redirect:/loginForm"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인 페이지 (정적) - 인증(로그인) X
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 로그인 SELECT * FROM user WHERE username=? AND password=? -> 이런 메서드는 없으니까 직접
    // 만들어!
    // 원래 SELECT는 무조건 GET요청
    // 근데 로그인만 예외! POST요청
    // 이유 : 주소에 패스워드를 남길 수 없으니까!! 보안을 위해!!
    // 로그인 - 인증(로그인) X
    @PostMapping("/login")
    public String login(HttpServletRequest request, User user) {
        // HttpSession session = request.getSession(); // 쿠키에 JSESSIONID를 85로 가져오면
        // session의 자기 공간을 가리킴

        // 1. DB연결해서 username, password 있는지 확인
        User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());

        // 2. 있으면 session 영역에 인증됨이라고 메시지 하나 넣어두자
        if (userEntity == null) {
            System.out.println("아이디 혹은 패스워드가 틀렸습니다.");
        } else {
            System.out.println("로그인 되었습니다.");
            // 세션에 옮겨담자, request는 사라졌지만 세션영역에 보관
            session.setAttribute("principal", userEntity); // principal 인증된 주체 -> 로그인
        }

        return "redirect:/";
    }

    // http://localhost:8080/user/1
    // 유저 상세 페이지 (동적 -> DB연동 필요) - 인증(로그인) O
    @GetMapping("/user/{id}")
    public String detail(@PathVariable int id, Model model) {

        // 유효성 검사하기 (수십개... 엄청 많음)
        User principal = (User) session.getAttribute("principal");

        // 1. 인증 체크
        if (principal == null) {
            return "error/page1";
        }

        // 2. 권한 체크
        if (principal.getId() != id) {
            return "error/page1";
        }
        // 3. 핵심 로직
        Optional<User> userOp = userRepository.findById(id); // 유저정보

        if (userOp.isPresent()) { // 박스안에 뭐가 있으면
            User userEntity = userOp.get();
            model.addAttribute("user", userEntity);
            return "user/detail";
        } else { // 없으면 == isEmpty
            return "error/page1";
        }

        // db에 로그 남기기(로그인한 아이디)
    }

    // 유저 수정 페이지 - 인증(로그인) O
    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    // 유저 수정 - 인증(로그인) O
    @PutMapping("/user/{id}")
    public String update(@PathVariable int id) {
        return "redirect:/user/" + id;
    }

    // 로그아웃 - 인증(로그인) O
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // session 다 날림
        return "redirect:/loginForm"; // PostController 만들고 수정하자
    }
}