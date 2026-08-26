package com.anusaha.beatbox.dto;

public class FavoriteResponse {

    private Long id;
    private Long songId;
    private String songTitle;
    private String artist;

    public FavoriteResponse() {
    }

    public FavoriteResponse(
            Long id,
            Long songId,
            String songTitle,
            String artist) {

        this.id = id;
        this.songId = songId;
        this.songTitle = songTitle;
        this.artist = artist;
    }

    public Long getId() {
        return id;
    }

    public Long getSongId() {
        return songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getArtist() {
        return artist;
    }
}