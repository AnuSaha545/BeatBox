package com.anusaha.beatbox.repository;

import com.anusaha.beatbox.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}