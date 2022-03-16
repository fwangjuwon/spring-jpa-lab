package site.metacoding.dbproject.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.post.PostRepository;
import site.metacoding.dbproject.domain.user.User;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> 글목록보기(Integer page) {
        PageRequest pq = PageRequest.of(page, 3);
        return postRepository.findAll(pq);

    }

    // 글 상세보기, 글 수정페이지
    public Post 글상세보기(Integer id) {

        Optional<Post> postOp = postRepository.findById(id);

        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            return postEntity;
        } else {
            return null;
        } // try-catch로 하는게 더 좋다.
    }

    @Transactional // 이거 걸리면 끝날 때 커밋된다. 하나의 트랜잭션이 끝날 때
    public void 글수정하기() {

    }

    @Transactional // select하는거는 필요 없고 write하는 거만 필요하다.
    public void 글삭제하기() {

    }

    @Transactional // 어떤 예외가 걸렸을 때 롤백할건지는 괄호 안에 넣으면 된다.
    public void 글쓰기(Post post, User principal) {

        post.setUser(principal); // user forieng key 추가
        postRepository.save(post);
    }
}