package com.anusaha.beatbox.service;

import com.anusaha.beatbox.dto.SongResponse;
import com.anusaha.beatbox.entity.Song;
import com.anusaha.beatbox.exception.ResourceNotFoundException;
import com.anusaha.beatbox.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public SongResponse addSong(Song song) {

        Song savedSong = songRepository.save(song);

        return toResponse(savedSong);
    }

    public List<SongResponse> getAllSongs() {

        return songRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public SongResponse getSongById(Long id) {

        Song song = songRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + id));

        return toResponse(song);
    }

    public SongResponse updateSong(
            Long id,
            Song updatedSong) {

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

        Song savedSong =
                songRepository.save(existingSong);

        return toResponse(savedSong);
    }

    public void deleteSong(Long id) {

        Song song = songRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Song not found with id: " + id));

        songRepository.delete(song);
    }

    private SongResponse toResponse(Song song) {

        return new SongResponse(
                song.getId(),
                song.getTitle(),
                song.getArtist(),
                song.getAlbum(),
                song.getGenre(),
                song.getDuration(),
                song.getAudioUrl()
        );
    }
    public List<SongResponse> searchByTitle(String title) {

        return songRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SongResponse> searchByArtist(String artist) {

        return songRepository
                .findByArtistContainingIgnoreCase(artist)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SongResponse> searchByGenre(String genre) {

        return songRepository
                .findByGenreContainingIgnoreCase(genre)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public Page<SongResponse> getSongs(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return songRepository
                .findAll(pageable)
                .map(this::toResponse);
    }
}