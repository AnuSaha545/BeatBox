package com.anusaha.beatbox.controller;

import com.anusaha.beatbox.dto.FavoriteResponse;
import com.anusaha.beatbox.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{songId}")
    public FavoriteResponse addFavorite(
            @PathVariable Long songId) {

        return favoriteService.addFavorite(songId);
    }

    @GetMapping
    public List<FavoriteResponse> getFavorites() {

        return favoriteService.getFavorites();
    }

    @GetMapping("/{songId}")
    public FavoriteResponse getFavoriteBySong(
            @PathVariable Long songId) {

        return favoriteService.getFavoriteBySong(songId);
    }

    @GetMapping("/check/{songId}")
    public boolean isFavorite(
            @PathVariable Long songId) {

        return favoriteService.isFavorite(songId);
    }

    @DeleteMapping("/{songId}")
    public void removeFavorite(
            @PathVariable Long songId) {

        favoriteService.removeFavorite(songId);
    }
}