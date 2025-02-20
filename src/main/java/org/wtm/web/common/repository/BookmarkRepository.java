package org.wtm.web.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wtm.web.bookmark.model.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
