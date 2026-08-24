package com.anusaha.beatbox.controller;

import com.anusaha.beatbox.entity.Playlist;
import com.anusaha.beatbox.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {

        playlistService.deletePlaylist(id);

        return ResponseEntity.ok("Playlist deleted");
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public Playlist addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {

        return playlistService.addSongToPlaylist(playlistId, songId);
    }
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public Playlist removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {

        return playlistService.removeSongFromPlaylist(
                playlistId,
                songId
        );
    }
    @PutMapping("/{id}")
    public Playlist updatePlaylist(
            @PathVariable Long id,
            @RequestBody Playlist playlist) {

        return playlistService.updatePlaylist(id, playlist);
    }
    @GetMapping("/search")
    public List<Playlist> searchPlaylists(
            @RequestParam String name) {

        return playlistService.searchPlaylists(name);
    }
    @GetMapping("/paged")
    public Page<Playlist> getPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }

        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 50");
        }

        return playlistService.getPlaylists(page, size);
    }
}