package com.anusaha.beatbox.service;

import com.anusaha.beatbox.dto.PlaylistResponse;
import com.anusaha.beatbox.dto.SongResponse;
import com.anusaha.beatbox.entity.Playlist;
import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.entity.User;
import com.anusaha.beatbox.exception.ResourceNotFoundException;
import com.anusaha.beatbox.repository.PlaylistRepository;
import com.anusaha.beatbox.repository.SongRepository;
import com.anusaha.beatbox.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlaylistService(
            PlaylistRepository playlistRepository,
            UserRepository userRepository,
            SongRepository songRepository) {

        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public PlaylistResponse createPlaylist(Playlist playlist) {

        User user = getCurrentUser();

        playlist.setUser(user);

        Playlist savedPlaylist =
                playlistRepository.save(playlist);

        return toResponse(savedPlaylist);
    }

    public List<PlaylistResponse> getAllPlaylists() {

        User user = getCurrentUser();

        return playlistRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PlaylistResponse getPlaylistById(Long id) {

        User user = getCurrentUser();

        Playlist playlist = playlistRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + id));

        return toResponse(playlist);
    }

    public void deletePlaylist(Long id) {

        User user = getCurrentUser();

        Playlist playlist = playlistRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + id));

        playlistRepository.delete(playlist);
    }

    public PlaylistResponse addSongToPlaylist(
            Long playlistId,
            Long songId) {

        User user = getCurrentUser();

        Playlist playlist = playlistRepository
                .findByIdAndUserId(
                        playlistId,
                        user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: "
                                        + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: "
                                        + songId));

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
        }

        Playlist savedPlaylist =
                playlistRepository.save(playlist);

        return toResponse(savedPlaylist);
    }

    public PlaylistResponse removeSongFromPlaylist(
            Long playlistId,
            Long songId) {

        User user = getCurrentUser();

        Playlist playlist = playlistRepository
                .findByIdAndUserId(
                        playlistId,
                        user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: "
                                        + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: "
                                        + songId));

        playlist.getSongs().remove(song);

        Playlist savedPlaylist =
                playlistRepository.save(playlist);

        return toResponse(savedPlaylist);
    }

    public Page<PlaylistResponse> getPlaylists(
            int page,
            int size) {

        User user = getCurrentUser();

        Pageable pageable =
                PageRequest.of(page, size);

        return playlistRepository
                .findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }

    private PlaylistResponse toResponse(
            Playlist playlist) {

        List<SongResponse> songs = playlist.getSongs()
                .stream()
                .map(song -> new SongResponse(
                        song.getId(),
                        song.getTitle(),
                        song.getArtist(),
                        song.getAlbum(),
                        song.getGenre(),
                        song.getDuration(),
                        song.getAudioUrl()
                ))
                .toList();

        return new PlaylistResponse(
                playlist.getId(),
                playlist.getName(),
                songs
        );
    }
}