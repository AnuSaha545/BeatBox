package com.anusaha.beatbox.dto;

public class SongResponse {

    private Long id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer duration;
    private String audioUrl;

    public SongResponse() {
    }

    public SongResponse(
            Long id,
            String title,
            String artist,
            String album,
            String genre,
            Integer duration,
            String audioUrl) {

        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.audioUrl = audioUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getAudioUrl() {
        return audioUrl;
    }
}