package gdsc.skhu.moida.service;

import gdsc.skhu.moida.domain.DTO.JoinDTO;
import gdsc.skhu.moida.domain.DTO.LoginDTO;
import gdsc.skhu.moida.domain.DTO.TokenDTO;
import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.repository.MemberRepository;
import gdsc.skhu.moida.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
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
}
