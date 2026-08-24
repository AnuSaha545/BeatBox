package com.anusaha.beatbox.service;

import com.anusaha.beatbox.entity.Playlist;
import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.entity.User;
import com.anusaha.beatbox.exception.ResourceNotFoundException;
import com.anusaha.beatbox.repository.PlaylistRepository;
import com.anusaha.beatbox.repository.SongRepository;
import com.anusaha.beatbox.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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

    public Playlist createPlaylist(Playlist playlist) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        playlist.setUser(user);

        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return playlistRepository.findByUserId(user.getId());
    }

    public Playlist getPlaylistById(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return playlistRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + id));
    }

    public void deletePlaylist(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Playlist playlist = playlistRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + id));

        playlistRepository.delete(playlist);
    }

    public Playlist addSongToPlaylist(Long playlistId, Long songId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Playlist playlist = playlistRepository
                .findByIdAndUserId(playlistId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + songId));

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
        }

        return playlistRepository.save(playlist);
    }

    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Playlist playlist = playlistRepository
                .findByIdAndUserId(playlistId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + songId));

        if (!playlist.getSongs().contains(song)) {
            throw new ResourceNotFoundException(
                    "Song is not present in playlist");
        }

        playlist.getSongs().remove(song);

        return playlistRepository.save(playlist);
    }
    public Playlist updatePlaylist(Long id, Playlist updatedPlaylist) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Playlist playlist = playlistRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Playlist not found with id: " + id));

        playlist.setName(updatedPlaylist.getName());

        return playlistRepository.save(playlist);
    }
    public List<Playlist> searchPlaylists(String name) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return playlistRepository
                .findByUserIdAndNameContainingIgnoreCase(
                        user.getId(),
                        name
                );
    }
    public Page<Playlist> getPlaylists(int page, int size) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size);

        return playlistRepository.findByUserId(
                user.getId(),
                pageable
        );
    }
}