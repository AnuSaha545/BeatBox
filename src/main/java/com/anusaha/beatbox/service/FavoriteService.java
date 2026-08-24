package com.anusaha.beatbox.service;

import com.anusaha.beatbox.entity.Favorite;
import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.entity.User;
import com.anusaha.beatbox.exception.ResourceNotFoundException;
import com.anusaha.beatbox.repository.FavoriteRepository;
import com.anusaha.beatbox.repository.SongRepository;
import com.anusaha.beatbox.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public FavoriteService(
            FavoriteRepository favoriteRepository,
            UserRepository userRepository,
            SongRepository songRepository) {

        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public Favorite addFavorite(Long songId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + songId));

        if (favoriteRepository.existsByUserIdAndSongId(
                user.getId(), songId)) {

            throw new IllegalArgumentException(
                    "Song is already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setSong(song);

        return favoriteRepository.save(favorite);
    }

    public List<Favorite> getFavorites() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return favoriteRepository.findByUserId(user.getId());
    }
    public void removeFavorite(Long songId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Favorite favorite = favoriteRepository
                .findByUserIdAndSongId(user.getId(), songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song is not in favorites"));

        favoriteRepository.delete(favorite);
    }
}