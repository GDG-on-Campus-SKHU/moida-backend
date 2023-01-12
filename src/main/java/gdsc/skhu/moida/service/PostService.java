package gdsc.skhu.moida.service;

import gdsc.skhu.moida.domain.DTO.PostDTO;
import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Post;
import gdsc.skhu.moida.domain.PostType;
import gdsc.skhu.moida.repository.MemberRepository;
import gdsc.skhu.moida.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @Transactional
    public void write(Principal principal, PostDTO postDTO) {
        String username = principal.getName();
        postDTO.setAuthor(username);
        if(postDTO.getTitle().isEmpty()) {
            throw new IllegalStateException("Title not found");
        }
        if(postDTO.getContext().isEmpty()) {
            throw new IllegalStateException("Context not found");
        }
        postRepository.save(Post.builder()
                .member(memberRepository.findByUsername(username).get())
                .title(postDTO.getTitle())
                .postType(PostType.getType(postDTO.getType().toUpperCase()))
                .context(postDTO.getContext())
                .build());
    }

    @Transactional
    public void edit(PostDTO postDTO) {
        Member writer = memberRepository.findByUsername(postDTO.getAuthor())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Post oldPost = postRepository.findById(postDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        if(postDTO.getTitle().isEmpty()) {
            throw new IllegalStateException("Title not found");
        }
        if(postDTO.getContext().isEmpty()) {
            throw new IllegalStateException("Context not found");
        }
        postRepository.save(Post.builder()
                .id(postDTO.getId())
                .member(writer)
                .title(postDTO.getTitle())
                .postType(PostType.getType(postDTO.getType().toUpperCase()))
                .context(postDTO.getContext())
                .comments(oldPost.getComments())
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
                        .nickname(post.getMember().getNickname())
                        .title(post.getTitle())
                        .type(post.getPostType().toString())
                        .context(post.getContext())
                        .comments(commentService.findByPostId(post.getId()))
                        .createdDate(post.getCreatedDate())
                        .modifiedDate(post.getModifiedDate())
                        .build());
    }

    @Transactional(readOnly = true)
    public PostDTO findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        return PostDTO.builder()
                .id(post.getId())
                .author(post.getMember().getUsername())
                .nickname(post.getMember().getNickname())
                .title(post.getTitle())
                .type(post.getPostType().toString())
                .context(post.getContext())
                .comments(commentService.findByPostId(post.getId()))
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

    @Transactional(readOnly = true)
    public Slice<PostDTO> findByTypeWithPaging(Pageable pageable, String type) {
        PostType postType = PostType.getType(type.toUpperCase());
        return postRepository.findByPostType(pageable, postType)
                .map(post -> PostDTO.builder()
                        .id(post.getId())
                        .author(post.getMember().getUsername())
                        .nickname(post.getMember().getNickname())
                        .title(post.getTitle())
                        .type(post.getPostType().toString())
                        .context(post.getContext())
                        .comments(commentService.findByPostId(post.getId()))
                        .createdDate(post.getCreatedDate())
                        .modifiedDate(post.getModifiedDate())
                        .build());
    }
}