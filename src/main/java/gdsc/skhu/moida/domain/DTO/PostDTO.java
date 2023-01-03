package gdsc.skhu.moida.domain.DTO;

import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Post;
import gdsc.skhu.moida.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Member author;
    private String title;
    private String type;
    private String context;
    private final MemberService memberService;

    public Post toEntity() {
        return Post.builder()
                .member(author)
                .title(title)
                .type(type)
                .context(context)
                .build();
    }
}
