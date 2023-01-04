package gdsc.skhu.moida.service;

import gdsc.skhu.moida.domain.DTO.PostDTO;
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
        postDTO.setAuthor(memberRepository.findByUsername(username).get());
        if(postDTO.getTitle().isEmpty()) {
            throw new IllegalStateException("제목을 입력해주세요.");
        }
        if(postDTO.getContext().isEmpty()) {
            throw new IllegalStateException("내용을 입력해주세요.");
        }
        postRepository.save(postDTO.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<PostDTO> findAllWithPaging(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDTO::toDTO);
    }
}
