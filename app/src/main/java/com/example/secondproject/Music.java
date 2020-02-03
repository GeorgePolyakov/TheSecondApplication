package com.example.secondproject;

public class Music {
    private String authorName;
    private String songName;

    public String getAuthorName() {
        return authorName;
    }

    public String getSongName() {
        return songName;
    }

    public Music(String authorName, String songName){
        this.authorName=authorName;
        this.songName = songName;
    }

}
