package com.anusaha.beatbox.service;

import com.anusaha.beatbox.entity.Playlist;
import com.anusaha.beatbox.entity.User;
import com.anusaha.beatbox.repository.PlaylistRepository;
import com.anusaha.beatbox.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistService(
            PlaylistRepository playlistRepository,
            UserRepository userRepository) {

        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    public Playlist createPlaylist(Playlist playlist) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        playlist.setUser(user);

        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return playlistRepository.findByUserId(user.getId());
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Playlist not found with id: " + id));
    }
}