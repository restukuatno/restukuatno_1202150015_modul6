package com.example.android.restukuatno_1202150015_modul6;
import com.google.firebase.firestore.IgnoreExtraProperties;
/**
 * Created by user on 01-Apr-18.
 */

@IgnoreExtraProperties
public class database1 {
    String commenters, comments, commentedImage;

    //method kosong untuk membaca data
    public database1(){
    }

    //konstruktor
    public database1(String commenters, String comments, String commentedImage) {
        this.commenters = commenters;
        this.comments = comments;
        this.commentedImage = commentedImage;
    }

    //method getter
    public String getCommenters() {
        return commenters;
    }

    public String getComments() {
        return comments;
    }

    public String getCommentedImage() {
        return commentedImage;
    }
}

