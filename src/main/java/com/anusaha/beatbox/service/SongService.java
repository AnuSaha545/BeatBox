package com.anusaha.beatbox.service;

import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.repository.SongRepository;
import org.springframework.stereotype.Service;
import com.anusaha.beatbox.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found with id: " + id));
    }
    public Song updateSong(Long id, Song updatedSong) {

        Song existingSong = songRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + id));

        existingSong.setTitle(updatedSong.getTitle());
        existingSong.setArtist(updatedSong.getArtist());
        existingSong.setAlbum(updatedSong.getAlbum());
        existingSong.setGenre(updatedSong.getGenre());
        existingSong.setDuration(updatedSong.getDuration());
        existingSong.setAudioUrl(updatedSong.getAudioUrl());

        return songRepository.save(existingSong);
    }
    public void deleteSong(Long id) {

        Song song = songRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + id));

        songRepository.delete(song);
    }
}