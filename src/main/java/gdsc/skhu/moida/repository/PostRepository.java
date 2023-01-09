package gdsc.skhu.moida.repository;

import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByType(Pageable pageable, String type);
    Page<Post> findByMember(Pageable pageable, Member member);
}
