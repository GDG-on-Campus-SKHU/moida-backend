package gdsc.skhu.moida.domain.DTO;

import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Role;
import lombok.Data;

@Data
public class JoinDTO {
    private String username;
    private String password;
    private String repeatedPassword;
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }
}
