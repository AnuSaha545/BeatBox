package com.anusaha.beatbox.repository;

import com.anusaha.beatbox.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    // Existing method — needed by getAllPlaylists()
    List<Playlist> findByUserId(Long userId);

    // Pagination method
    Page<Playlist> findByUserId(Long userId, Pageable pageable);

    Optional<Playlist> findByIdAndUserId(Long id, Long userId);

    List<Playlist> findByUserIdAndNameContainingIgnoreCase(
            Long userId,
            String name
    );
}