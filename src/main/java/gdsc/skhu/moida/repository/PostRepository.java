package gdsc.skhu.moida.repository;

import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Post;
import gdsc.skhu.moida.domain.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Slice<Post> findByPostType(Pageable pageable, PostType postType);
    Slice<Post> findByMember(Pageable pageable, Member member);
}
