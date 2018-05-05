package com.djes.game.my8puzzlegame;

import android.graphics.drawable.Drawable;

public class Piece {
    public int position;
    public Drawable image;
    public Boolean showLabel;

    public Piece(int position)
    {
        this.position=position;
        this.showLabel=false;
    }

    public Piece(int position,Drawable image, Boolean showLabel)
    {
        this.position=position;
        this.image=image;
        this.showLabel=showLabel;
    }
}
