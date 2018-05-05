package com.djes.game.my8puzzlegame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class PiecesAdapter  extends ArrayAdapter<Piece> {
    private GridView GV;
    private ArrayAdapter<Piece> ap;
    private ArrayList<Piece> list;
    private int lastPieceIndex=0;
    private int Moves=0;
    private TextView TxtMoves;
    private Game game;

    PiecesAdapter(Context context, ArrayList<Piece> objects, GridView gv, TextView txtMoves)
    {
        super(context,0,objects);
        this.GV=gv;
        this.ap=this;
        this.list=objects;
        this.TxtMoves = txtMoves;
        this.game = Game.GetInstance();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        Piece piece = getItem(position);
        lastPieceIndex = game.GetLastPieceIndex();
        Piece lastPiece = getItem(lastPieceIndex);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.piece,parent,false);
            convertView.setOnClickListener(v -> {

                if(!game.HasFinished()) {
                    if (piece != null && piece.image != null) {
                        int numPieces = list.size();
                        double base = Math.sqrt(numPieces);
                        int upPosition = (lastPieceIndex + 1) - (int) base;
                        int downPosition = (lastPieceIndex + 1) + (int) base;
                        int leftPosition = (lastPieceIndex + 1) - 1;
                        int rightPosition = (lastPieceIndex + 1) + 1;

                        if ((position + 1) == upPosition || (position + 1) == downPosition || ((position + 1) == leftPosition && (position + 1) % base != 0) || ((position + 1) == rightPosition) && (position + 1) % base != 1) {
                            Moves++;
                            int swapPosition1 = piece.position;
                            int swapPosition2 = lastPiece != null ? lastPiece.position : -1;
                            Drawable swapDrawable1 = Objects.requireNonNull(piece.image.getConstantState()).newDrawable();
                            piece.position = swapPosition2;
                            piece.image = null;
                            if (lastPiece != null) {
                                lastPiece.position = swapPosition1;
                                lastPiece.image = swapDrawable1;
                            }

                            game.RefreshPuzzle();
                            TxtMoves.setText(String.valueOf(Moves));
                            if (game.HasFinished()) {
                                Moves = 0;
                                Toast t = Toast.makeText(getContext(), "Ganaste", Toast.LENGTH_SHORT);
                                t.show();
                                game.StopGame();
                            }
                        } else {
                            Toast t = Toast.makeText(getContext(), "Jugada Invalida", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                }

            });
        }
        TextView pieceView = convertView.findViewById(R.id.piece);
        assert piece != null;
        pieceView.setText(String.valueOf(piece.position));
        pieceView.setBackground(piece.image);
        return convertView;
    }
}
