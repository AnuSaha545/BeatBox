package com.anusaha.beatbox.repository;

import com.anusaha.beatbox.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByTitleContainingIgnoreCase(String title);

    List<Song> findByArtistContainingIgnoreCase(String artist);

    List<Song> findByGenreContainingIgnoreCase(String genre);

    Page<Song> findAll(Pageable pageable);
}