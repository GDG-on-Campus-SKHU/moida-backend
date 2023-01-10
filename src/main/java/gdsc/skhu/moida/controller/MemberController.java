package gdsc.skhu.moida.controller;

import gdsc.skhu.moida.domain.DTO.MemberDTO;
import gdsc.skhu.moida.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/user")
    public ResponseEntity<MemberDTO> userPage(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Principal principal) {
        MemberDTO memberDTO = memberService.user(pageable, principal);
        return ResponseEntity.ok(memberDTO);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminPage() {
        return ResponseEntity.ok("admin page access success");
    }
}
