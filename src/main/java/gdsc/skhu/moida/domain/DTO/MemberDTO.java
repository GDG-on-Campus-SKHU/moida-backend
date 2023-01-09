package gdsc.skhu.moida.domain.DTO;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class MemberDTO {
    private String username;
    private String nickname;
    private Page<PostDTO> posts;
    private Page<CommentDTO> comments;
}