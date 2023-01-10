package gdsc.skhu.moida.service;

import gdsc.skhu.moida.domain.DTO.*;
import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.repository.CommentRepository;
import gdsc.skhu.moida.repository.MemberRepository;
import gdsc.skhu.moida.repository.PostRepository;
import gdsc.skhu.moida.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinDTO joinRequestDTO) {
        if(memberRepository.findByUsername(joinRequestDTO.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        if(!joinRequestDTO.getPassword().equals(joinRequestDTO.getRepeatedPassword())) {
            throw new IllegalStateException("재입력 비밀번호가 일치하지 않습니다.");
        }
        joinRequestDTO.setPassword(passwordEncoder.encode(joinRequestDTO.getPassword()));
        memberRepository.save(joinRequestDTO.toEntity());
    }

    @Transactional
    public TokenDTO login(LoginDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.createToken(authentication);
    }

    @Transactional
    public MemberDTO user(Pageable pageable, Principal principal) {
        String username = principal.getName();
        Member member = memberRepository.findByUsername(username).orElseThrow();
        Page<PostDTO> posts = postRepository.findByMember(pageable, member)
                .map(post -> PostDTO.builder()
                        .id(post.getId())
                        .author(post.getMember().getUsername())
                        .title(post.getTitle())
                        .type(post.getType())
                        .context(post.getContext())
                        .createdDate(post.getCreatedDate())
                        .modifiedDate(post.getModifiedDate())
                        .build()
                );
        Page<CommentDTO> comments = commentRepository.findByWriter(pageable, member)
                .map(comment -> CommentDTO.builder()
                        .id(comment.getId())
                        .postId(comment.getPost().getId())
                        .writer(comment.getWriter().getUsername())
                        .context(comment.getContext())
                        .createdDate(comment.getCreatedDate())
                        .modifiedDate(comment.getModifiedDate())
                        .build()
                );
        return MemberDTO.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .posts(posts)
                .comments(comments)
                .build();
    }
}
