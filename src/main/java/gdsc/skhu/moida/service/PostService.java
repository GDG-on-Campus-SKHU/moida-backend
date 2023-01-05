package gdsc.skhu.moida.service;

import gdsc.skhu.moida.domain.DTO.PostDTO;
import gdsc.skhu.moida.domain.Post;
import gdsc.skhu.moida.repository.MemberRepository;
import gdsc.skhu.moida.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void write(Principal principal, PostDTO postDTO) {
        String username = principal.getName();
        postDTO.setAuthor(username);
        if(postDTO.getTitle().isEmpty()) {
            throw new IllegalStateException("제목을 입력해주세요.");
        }
        if(postDTO.getContext().isEmpty()) {
            throw new IllegalStateException("내용을 입력해주세요.");
        }
        postRepository.save(Post.builder()
                .member(memberRepository.findByUsername(username).get())
                .title(postDTO.getTitle())
                .type(postDTO.getType())
                .context(postDTO.getContext())
                .build());
    }

    @Transactional
    public void edit(PostDTO postDTO) {
        if(postDTO.getTitle().isEmpty()) {
            throw new IllegalStateException("제목을 입력해주세요.");
        }
        if(postDTO.getContext().isEmpty()) {
            throw new IllegalStateException("내용을 입력해주세요.");
        }
        postRepository.save(Post.builder()
                .id(postDTO.getId())
                .member(memberRepository.findByUsername(postDTO.getAuthor()).get())
                .title(postDTO.getTitle())
                .type(postDTO.getType())
                .context(postDTO.getContext())
                .build());
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<PostDTO> findAllWithPaging(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> PostDTO.builder()
                        .id(post.getId())
                        .author(post.getMember().getUsername())
                        .title(post.getTitle())
                        .type(post.getType())
                        .context(post.getContext())
                        .build());
    }

    @Transactional(readOnly = true)
    public PostDTO findById(Long id) {
        Post post = postRepository.findById(id).get();
        return PostDTO.builder()
                .id(post.getId())
                .author(post.getMember().getUsername())
                .title(post.getTitle())
                .type(post.getType())
                .context(post.getContext())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<PostDTO> findByTypeWithPaging(Pageable pageable, String type) {
        return postRepository.findByType(pageable, type)
                .map(post -> PostDTO.builder()
                        .id(post.getId())
                        .author(post.getMember().getUsername())
                        .title(post.getTitle())
                        .type(post.getType())
                        .context(post.getContext())
                        .build());
    }
}