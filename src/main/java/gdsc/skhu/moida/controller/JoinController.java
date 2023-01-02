package gdsc.skhu.moida.controller;

import gdsc.skhu.moida.domain.DTO.JoinDTO;
import gdsc.skhu.moida.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDTO joinRequestDto) {
        memberService.join(joinRequestDto);
        return ResponseEntity.ok("join success");
    }
}
