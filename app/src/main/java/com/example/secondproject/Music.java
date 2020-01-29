package com.example.secondproject;

public class Music {
    public String getAuthorName() {
        return authorName;
    }

    public String getSongName() {
        return songName;
    }

    private String authorName;
    private String songName;

    public Music(String authorName, String songName){
        this.authorName=authorName;
        this.songName = songName;
    }

}
