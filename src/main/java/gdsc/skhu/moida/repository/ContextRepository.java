package gdsc.skhu.moida.repository;

import gdsc.skhu.moida.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContextRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
}
