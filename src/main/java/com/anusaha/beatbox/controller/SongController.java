package com.anusaha.beatbox.controller;

import com.anusaha.beatbox.dto.SongResponse;
import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<SongResponse> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public SongResponse getSongById(
            @PathVariable Long id) {

        return songService.getSongById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SongResponse addSong(
            @Valid @RequestBody Song song) {

        return songService.addSong(song);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SongResponse updateSong(
            @PathVariable Long id,
            @Valid @RequestBody Song song) {

        return songService.updateSong(id, song);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSong(
            @PathVariable Long id) {

        songService.deleteSong(id);

        return ResponseEntity.ok(
                "Song deleted successfully");
    }
    @GetMapping("/search/title")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<SongResponse> searchByTitle(
            @RequestParam String title) {

        return songService.searchByTitle(title);
    }

    @GetMapping("/search/artist")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<SongResponse> searchByArtist(
            @RequestParam String artist) {

        return songService.searchByArtist(artist);
    }

    @GetMapping("/search/genre")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<SongResponse> searchByGenre(
            @RequestParam String genre) {

        return songService.searchByGenre(genre);
    }
    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<SongResponse> getSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative");
        }

        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 50");
        }

        return songService.getSongs(page, size);
    }
}