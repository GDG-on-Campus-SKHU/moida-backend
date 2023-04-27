package gdsc.skhu.moida.domain.DTO;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Builder
@Getter
public class MemberDTO {
    private String username;
    private String nickname;
    private Slice<PostDTO> posts;
    private Slice<CommentDTO> comments;
}