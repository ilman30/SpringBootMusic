/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilman.music.impl;

import com.ilman.music.model.Albums;
import com.ilman.music.model.Artis;
import com.ilman.music.model.DataTablesRequest;
import com.ilman.music.model.Genre;
import com.ilman.music.model.LablesRekaman;
import com.ilman.music.model.Lagu;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ilman
 */
@Repository
public class KoneksiJdbc {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //Lables Rekaman
    
    public List<LablesRekaman> getLablesRekaman(){
        String SQL = "SELECT id_label as idLabel, nama_labels as namaLabels, alamat, "
                   + "no_telp as noTelp, contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman";
        List<LablesRekaman> lbls = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(LablesRekaman.class));
        return lbls;
    }
        
    public Optional<LablesRekaman> getLablesRekamanById(int id){
        String SQL = "SELECT nama_labels as namaLabels, alamat, no_telp as noTelp, "
                   + "contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman where id_label = ? ";
        Object param[] = {id};
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(LablesRekaman.class)));
        }catch(Exception e){
            return Optional.empty();
        }

    }

    public void insertLables(LablesRekaman lablesRekaman){
        String sql = "insert into lables_rekaman (id_label,nama_labels,alamat,no_telp,contact_person,url_website) values (?,?,?,?,?,?)";
        Object param[] = {lablesRekaman.getIdLabel(),lablesRekaman.getNamaLabels(),lablesRekaman.getAlamat(),
                          lablesRekaman.getNoTelp(), lablesRekaman.getContactPerson(), lablesRekaman.getUrlWebsite()};
        jdbcTemplate.update(sql, param);
    }

    public void updateLables(LablesRekaman lablesRekaman){
        String sql = "update lables_rekaman set nama_labels=?, alamat=?, no_telp=?, contact_person=?, url_website=? where id_label=?";
        Object param[] = {lablesRekaman.getNamaLabels(),lablesRekaman.getAlamat(), lablesRekaman.getNoTelp(), 
                          lablesRekaman.getContactPerson(), lablesRekaman.getUrlWebsite(), lablesRekaman.getIdLabel()};
        jdbcTemplate.update(sql, param);
    }

    public void insertOrUpdateLables (LablesRekaman lablesRekaman){
        Optional<LablesRekaman> data= getLablesRekamanById(lablesRekaman.getIdLabel());
        if(data.isPresent()){
            updateLables(lablesRekaman);
        }else{
            insertLables(lablesRekaman);
        }
    }

    public Integer getBanyakLablesRekaman(DataTablesRequest req){
        String query = "SELECT count(id_label) as banyak FROM lables_rekaman";
        if(!req.getExtraParam().isEmpty()){
            String nama_labels = (String) req.getExtraParam().get("namaLabels");
            query = " SELECT count(id_label) as banyak FROM lables_rekaman WHERE  nama_labels like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_labels);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }

    }

    public List<LablesRekaman> getListLablesRekaman(DataTablesRequest req){
        String SQL = "SELECT id_label as idLabel, nama_labels as namaLabels, alamat, no_telp as noTelp, "
                   + "contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman"
                   + " order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
        if(!req.getExtraParam().isEmpty()){
            String namaLabels = (String) req.getExtraParam().get("namaLabels");
            SQL = "SELECT id_label as idLabel, nama_labels as namaLabels, alamat, no_telp as noTelp, "
                + "contact_person as contactPerson, url_website as urlWebsite FROM lables_rekaman "
                + "WHERE nama_labels like concat('%',?,'%') "
                + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(LablesRekaman.class), namaLabels, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(LablesRekaman.class), req.getLength(), req.getStart());
        }

    }
        
    //Artis
        
    public List<Artis> getArtis(){
        String SQL = "select id_artis as idArtis, nama_artis as namaArtis, foto, "
                + "url_website as urlWebsite, keterangan FROM artis";
        List<Artis> art = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Artis.class));
        return art;
    }
    
    public Optional<Artis> getArtisById(int id){
        String SQL = "select id_artis as idArtis, nama_artis as namaArtis, foto, "
            + "url_website as urlWebsite, keterangan FROM artis where id_artis = ? ";
        Object param[] = {id};
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Artis.class)));
        }catch(Exception e){
            return Optional.empty();
        }

    }
        
    public void insertArtis(Artis artis){
        String sql = "insert into artis (id_artis,nama_artis,foto,url_website,keterangan) values (?,?,?,?,?)";
        Object param[] = {artis.getIdArtis(),artis.getNamaArtis(),artis.getFoto(),artis.getUrlWebsite(), artis.getKeterangan()};
        jdbcTemplate.update(sql, param);
    }

    public void updateArtis(Artis artis){
        String sql = "update artis set nama_artis=?, foto=?, url_website=?, keterangan=? where id_artis=?";
        Object param[] = {artis.getNamaArtis(),artis.getFoto(),artis.getUrlWebsite(), artis.getKeterangan(),artis.getIdArtis()};
        jdbcTemplate.update(sql, param);
    }

    public void insertOrUpdateArtis (Artis artis){
        Optional<Artis> data= getArtisById(artis.getIdArtis());
        if(data.isPresent()){
            updateArtis(artis);
        }else{
            insertArtis(artis);
        }
    }

    public Integer getBanyakArtis(DataTablesRequest req){
        String query = "SELECT count(id_artis) as banyak FROM artis";
        if(!req.getExtraParam().isEmpty()){
            String nama_artis = (String) req.getExtraParam().get("namaArtis");
            query = " SELECT count(id_artis) as banyak FROM artis WHERE  nama_artis like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_artis);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }

    }

    public List<Artis> getListArtis(DataTablesRequest req){
        String SQL = "SELECT id_artis as idArtis, nama_artis as namaArtis, foto, "
                   + "url_website as urlWebsite, keterangan FROM artis "
                   + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
        if(!req.getExtraParam().isEmpty()){
            String namaArtis = (String) req.getExtraParam().get("namaArtis");
            SQL = "SELECT id_artis as idArtis, nama_artis as namaArtis, foto, "
                + "url_website as urlWebsite, keterangan FROM artis "
                + "WHERE nama_artis like concat('%',?,'%') "
                + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Artis.class), namaArtis, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Artis.class), req.getLength(), req.getStart());
        }

    }
        
    //Genre
    
    public List<Genre> getGenre(){
        String SQL = "select id_genre as idGenre, nama_genre as namaGenre FROM genre";
        List<Genre> gen = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Genre.class));
        return gen;
    }
    
    public Optional<Genre> getGenreById(int id){
        String SQL = "select id_genre as idGenre, nama_genre as namaGenre FROM genre where id_genre = ? ";
        Object param[] = {id};
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Genre.class)));
        }catch(Exception e){
            return Optional.empty();
        }

    }
        
    public void insertGenre(Genre genre){
        String sql = "insert into genre (id_genre,nama_genre) values (?,?)";
        Object param[] = {genre.getIdGenre(),genre.getNamaGenre()};
        jdbcTemplate.update(sql, param);
    }

    public void updateGenre(Genre genre){
        String sql = "update genre set nama_genre=? where id_genre=?";
        Object param[] = {genre.getNamaGenre(),genre.getIdGenre()};
        jdbcTemplate.update(sql, param);
    }

    public void insertOrUpdateGenre (Genre genre){
        Optional<Genre> data= getGenreById(genre.getIdGenre());
        if(data.isPresent()){
            updateGenre(genre);
        }else{
            insertGenre(genre);
        }
    }

    public Integer getBanyakGenre(DataTablesRequest req){
        String query = "SELECT count(id_genre) as banyak FROM genre";
        if(!req.getExtraParam().isEmpty()){
            String nama_genre = (String) req.getExtraParam().get("namaGenre");
            query = " SELECT count(id_genre) as banyak FROM genre WHERE  nama_genre like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_genre);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }

    }

    public List<Genre> getListGenre(DataTablesRequest req){
        String SQL = "SELECT id_genre as idGenre, nama_genre as namaGenre FROM genre "
                   + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
        if(!req.getExtraParam().isEmpty()){
            String namaGenre = (String) req.getExtraParam().get("namaGenre");
            SQL = "SELECT id_genre as idGenre, nama_genre as namaGenre FROM genre "
                + "WHERE nama_genre like concat('%',?,'%') "
                + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Genre.class), namaGenre, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Genre.class), req.getLength(), req.getStart());
        }

    }
    
    public void deleteGenre(Integer id){
        String SQL = "delete from genre where id_genre=?";
        Object parameters[] = {id};
        
        jdbcTemplate.update(SQL, parameters);
    }
    
    //Albums
    
    public List<Albums> getAlbums(){
        String SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums,\n" +
                     "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, "
                   + "al.keterangan, ar.id_artis as idArtis, \n" +
                     "la.id_label as idLabel from Albums al join Artis ar on \n" +
                     "al.id_artis = ar.id_artis join lables_rekaman la on al.id_labels = la.id_label";
        List<Albums> al = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Albums.class));
        return al;
    }
    
    public Optional<Albums> getAlbumsById(int id){
        String SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums,\n" +
                     "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, al.keterangan, ar.id_artis as idArtis, \n" +
                     "la.id_label as idLabel from Albums al join Artis ar on \n" +
                     "al.id_artis = ar.id_artis join lables_rekaman la on al.id_labels = la.id_label where id_albums = ? ";
        Object param[] = {id};
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Albums.class)));
        }catch(Exception e){
            return Optional.empty();
        }

    }
        
    public void insertAlbums(Albums albums){
        String sql = "insert into albums (id_album,nama_albums,id_labels,id_artis,foto_cover,keterangan) values (?,?,?,?,?,?)";
        Object param[] = {albums.getIdAlbum(),albums.getNamaAlbums(),albums.getIdLabel(),
                          albums.getIdArtis(),albums.getFotoCover(),albums.getKeterangan()};
        jdbcTemplate.update(sql, param);
    }

    public void updateAlbums(Albums albums){
        String sql = "update albums set nama_albums=?, id_labels=?, id_artis=?, foto_cover=?, keterangan=? where id_albums=?";
        Object param[] = {albums.getNamaAlbums(),albums.getIdLabel(),albums.getIdArtis(),
                          albums.getFotoCover(),albums.getKeterangan(),albums.getIdAlbum()};
        jdbcTemplate.update(sql, param);
    }

    public void insertOrUpdateAlbums (Albums albums){
        Optional<Albums> data= getAlbumsById(albums.getIdAlbum());
        if(data.isPresent()){
            updateAlbums(albums);
        }else{
            insertAlbums(albums);
        }
    }

    public Integer getBanyakAlbums(DataTablesRequest req){
        String query = "SELECT count(id_album) as banyak FROM albums";
        if(!req.getExtraParam().isEmpty()){
            String nama_albums = (String) req.getExtraParam().get("namaAlbums");
            query = " SELECT count(id_album) as banyak FROM albums WHERE  nama_albums like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_albums);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }

    }

    public List<Albums> getListAlbums(DataTablesRequest req){
        String SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums,\n" +
                     "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, al.keterangan, ar.id_artis as idArtis, \n" +
                     "la.id_label as idLabel from Albums al join Artis ar on \n" +
                     "al.id_artis = ar.id_artis join lables_rekaman la on al.id_labels = la.id_label "
                   + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
        if(!req.getExtraParam().isEmpty()){
            String namaAlbums = (String) req.getExtraParam().get("namaAlbums");
            SQL = "select al.id_album as idAlbum, al.nama_albums as namaAlbums,\n" +
                     "al.id_labels as idLabel, al.id_artis as idArtis, al.foto_cover as fotoCover, al.keterangan, ar.id_artis as idArtis, \n" +
                     "la.id_label as idLabel from Albums al join Artis ar on \n" +
                     "al.id_artis = ar.id_artis join lables_rekaman la on al.id_labels = la.id_label "
                + "WHERE nama_albums like concat('%',?,'%') "
                + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Albums.class), namaAlbums, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Albums.class), req.getLength(), req.getStart());
        }

    }
    
    //Lagu
    
    public List<Lagu> getLagu(){
        String SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album";
        List<Lagu> la = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Lagu.class));
        return la;
    }
    
    public Optional<Lagu> getLaguById(int id){
        String SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album where id_lagu = ? ";
        Object param[] = {id};
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL, param, BeanPropertyRowMapper.newInstance(Lagu.class)));
        }catch(Exception e){
            return Optional.empty();
        }

    }
        
    public void insertLagu(Lagu lagu){
        String sql = "insert into lagu (id_lagu,judul,durasi,id_genre,id_artis,id_album,file_lagu) values (?,?,?,?,?,?,?)";
        Object param[] = {lagu.getIdLagu(),lagu.getJudul(),lagu.getDurasi(),lagu.getIdGenre(),
                          lagu.getIdArtis(),lagu.getIdAlbum(),lagu.getFileLagu()};
        jdbcTemplate.update(sql, param);
    }

    public void updateLagu(Lagu lagu){
        String sql = "update lagu set judul=?, durasi=?, id_genre=?, id_artis=?, id_album=?, file_lagu=? where id_lagu=?";
        Object param[] = {lagu.getJudul(),lagu.getDurasi(),lagu.getIdGenre(),
                          lagu.getIdArtis(),lagu.getIdAlbum(),lagu.getFileLagu(),lagu.getIdLagu()};
        jdbcTemplate.update(sql, param);
    }

    public void insertOrUpdateLagu (Lagu lagu){
        Optional<Lagu> data= getLaguById(lagu.getIdLagu());
        if(data.isPresent()){
            updateLagu(lagu);
        }else{
            insertLagu(lagu);
        }
    }

    public Integer getBanyakLagu(DataTablesRequest req){
        String query = "SELECT count(id_lagu) as banyak FROM lagu";
        if(!req.getExtraParam().isEmpty()){
            String nama_lagu = (String) req.getExtraParam().get("namaLagu");
            query = " SELECT count(id_lagu) as banyak FROM lagu WHERE  judul like concat('%',?,'%')";
            return jdbcTemplate.queryForObject(query, Integer.class, nama_lagu);
        }else{
            return this.jdbcTemplate.queryForObject(query, null, Integer.class);
        }

    }

    public List<Lagu> getListLagu(DataTablesRequest req){
        String SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album "
                   + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
        if(!req.getExtraParam().isEmpty()){
            String namaLagu = (String) req.getExtraParam().get("namaLagu");
            SQL = "select la.id_lagu as idLagu, la.judul, la.durasi, la.id_genre as idGenre, \n" +
                     "la.id_artis as idArtis, la.id_album as idAlbum, la.file_lagu as fileLagu, g.id_genre as idGenre, \n" +
                     "ar.id_artis as idArtis, al.id_album as idAlbum from lagu la join genre g on la.id_genre = g.id_genre "
                   + "join artis ar on la.id_artis = ar.id_artis join albums al on la.id_album = al.id_album "
                + "WHERE nama_albums like concat('%',?,'%') "
                + "order by " +(req.getSortCol()+1)+ "  " +req.getSortDir()  +" limit ? offset ? ";
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Lagu.class), namaLagu, req.getLength(), req.getStart());
        }else{
            return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Lagu.class), req.getLength(), req.getStart());
        }

    }
    
        
}