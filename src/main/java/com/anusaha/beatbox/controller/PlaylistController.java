package com.anusaha.beatbox.controller;

import com.anusaha.beatbox.dto.PlaylistResponse;
import com.anusaha.beatbox.entity.Playlist;
import com.anusaha.beatbox.service.PlaylistService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PlaylistResponse createPlaylist(
            @RequestBody Playlist playlist) {

        return playlistService.createPlaylist(playlist);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PlaylistResponse> getAllPlaylists() {

        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PlaylistResponse getPlaylistById(
            @PathVariable Long id) {

        return playlistService.getPlaylistById(id);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<PlaylistResponse> getPlaylists(
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

        return playlistService.getPlaylists(page, size);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PlaylistResponse addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {

        return playlistService.addSongToPlaylist(
                playlistId,
                songId);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PlaylistResponse removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {

        return playlistService.removeSongFromPlaylist(
                playlistId,
                songId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void deletePlaylist(
            @PathVariable Long id) {

        playlistService.deletePlaylist(id);
    }
}