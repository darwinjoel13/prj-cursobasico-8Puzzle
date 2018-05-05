package com.djes.game.my8puzzlegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

class Game {

    private static Game gameInstance;
    private TextView TxtMoves;
    private Chronometer ChmTime;
    private TextView BtnReset;
    private GridView GridPuzzle;
    private int NumPieces;
    private Bitmap ImagePuzzle;
    private Resources AppResources;
    private ArrayList<Piece> PiecesArray =  new ArrayList<>();
    private ArrayList<Bitmap> BitmapArray = new ArrayList<>();
    private PiecesAdapter PAdapter;

    private Game()
    { }

    public static Game GetInstance()
    {
        if(gameInstance==null)
        {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public void SetChronometer(Chronometer _chronometer)
    {
        this.ChmTime=_chronometer;
    }
    public void SetTextMoves(TextView _moves)
    {
        this.TxtMoves=_moves;
    }
    public void SetGrid(GridView _grid)
    {
        this.GridPuzzle=_grid;
    }
    public void SetBtnReset(TextView _reset)
    {
        this.BtnReset=_reset;
    }
    public void SetNumPieces(int _numPieces)
    {
        this.NumPieces=_numPieces;
    }

    public void SetResources(Resources _resources)
    {
        this.AppResources=_resources;
    }

    public void SetImageResource(int idResource)
    {
        this.ImagePuzzle= BitmapFactory.decodeResource(AppResources,idResource);
    }

    public void CreateGame(Context _context)
    {
        PiecesArray = new ArrayList<>();
        BitmapArray = new ArrayList<>();
        for(int i=0;i<NumPieces;i++)
        {
            PiecesArray.add(new Piece(i+1));
        }
        BitmapArray = SplitImage(ImagePuzzle,NumPieces);
        ShuffleImages(PiecesArray,BitmapArray,AppResources);
        PAdapter = new PiecesAdapter(_context,PiecesArray,GridPuzzle,TxtMoves);
        GridPuzzle.setAdapter(PAdapter);
        ChmTime.start();

        BtnReset.setOnClickListener(view->{
            ChmTime.setBase(SystemClock.elapsedRealtime());
            ChmTime.start();
            TxtMoves.setText("0");
            ShuffleImages(PiecesArray,BitmapArray,AppResources);
            PAdapter = new PiecesAdapter(_context,PiecesArray,GridPuzzle,TxtMoves);
            GridPuzzle.setAdapter(PAdapter);
        });
    }

    public void RefreshPuzzle()
    {
        GridPuzzle.setAdapter(PAdapter);
    }

    public int GetLastPieceIndex()
    {
        for(int i=0;i<NumPieces;i++)
        {
            if(PiecesArray.get(i).position==9)
            {
                return i;
            }
        }
        return -1;
    }

    public boolean HasFinished()
    {
            int numPieces= PiecesArray.size();
            for(int i=0;i<numPieces;i++)
            {
                if(!((i+1)==PiecesArray.get(i).position))
                {
                    return false;
                }
            }
            return true;
    }

    public void StopGame()
    {
        ChmTime.stop();
    }




    private void ShuffleImages(ArrayList<Piece> pieces, ArrayList<Bitmap>bitmaps, Resources resources)
    {
        Collections.shuffle(pieces);
        for (Piece piece:pieces) {
            if(piece.position!=9)
                piece.image =new BitmapDrawable(resources,bitmaps.get(piece.position-1));
        }
    }

    private ArrayList<Bitmap> SplitImage(Bitmap bmp, int smallImage_Numbers) {

        int rows,cols;

        int smallImage_Height,smallImage_Width;

        ArrayList<Bitmap> smallImages = new ArrayList<>(smallImage_Numbers);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), true);

        rows = cols = (int) Math.sqrt(smallImage_Numbers);
        smallImage_Height = bmp.getHeight()/rows;
        smallImage_Width = bmp.getWidth()/cols;


        //xCo and yCo are the pixel positions of the image smallimage_s

        int yCo = 0;

        for(int x=0; x<rows; x++){
            int xCo = 0;
            for(int y=0; y<cols; y++){
                smallImages.add(Bitmap.createBitmap(scaledBitmap, xCo, yCo, smallImage_Width, smallImage_Height));
                xCo += smallImage_Width;
            }
            yCo+= smallImage_Height;
        }
        return smallImages;
    }
}
