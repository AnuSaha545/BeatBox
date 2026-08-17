package com.anusaha.beatbox.controller;

import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.service.SongService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

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
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Song addSong(@Valid @RequestBody Song song) {
        return songService.addSong(song);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Song updateSong(
            @PathVariable Long id,
            @Valid @RequestBody Song song) {

        return songService.updateSong(id, song);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {

        songService.deleteSong(id);

        return ResponseEntity.ok("Song deleted successfully");
    }
}