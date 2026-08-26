package com.anusaha.beatbox.dto;

import java.util.List;

public class PlaylistResponse {

    private Long id;
    private String name;
    private List<SongResponse> songs;

    public PlaylistResponse() {
    }

    public PlaylistResponse(
            Long id,
            String name,
            List<SongResponse> songs) {

        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SongResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongResponse> songs) {
        this.songs = songs;
    }
}