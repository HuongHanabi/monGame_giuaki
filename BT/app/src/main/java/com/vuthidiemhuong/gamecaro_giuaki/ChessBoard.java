package com.vuthidiemhuong.gamecaro_giuaki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 4/13/2018.
 */

public class ChessBoard {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private int[][] board;
    private int player;
    private Context context;
    private int bitmapWidth;
    private int bitmapHeight;
    private int colQty;
    private int rowQty;
    private List<Line> listLine;

    private Bitmap bmTick;
    private Bitmap bmCross;

    public ChessBoard(Context context, int bitmapWidth, int bitmapHeight, int colQty, int rowQty)
    {
        this.context=context;
        this.bitmapWidth=bitmapWidth;
        this.bitmapHeight=bitmapHeight;
        this.colQty=colQty;
        this.rowQty=rowQty;
    }

    public void init(){
        bitmap= Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bitmap);
        paint=new Paint();
        int storkeWidth=2;
        paint.setStrokeWidth(storkeWidth);
        board=new int[rowQty][colQty];
        player=0;
        listLine=new ArrayList<>();

        bmCross= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher_background);
        bmTick=BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher_foreground);

        int cellWidth=bitmapWidth/colQty;

        for(int i=0;i<=colQty;i++) {
            boolean add = listLine.add(new Line(i * cellWidth, 0, cellWidth * i, bitmapHeight));
        }

        int cellHeight=bitmapHeight/rowQty;
        for (int i=0;i<=rowQty;i++)
        {
            listLine.add(new Line(0,i*cellHeight,bitmapWidth,i*cellHeight));
        }

        for (int i=0;i<rowQty;i++){
            for (int j = 0; colQty > j; j++) {
                board[i][j] = -1;
            }
        }
    }

    public Bitmap drawBoard(){
        Line line;
        for(int i=0;i<listLine.size();i++){
            line=listLine.get(i);
            canvas.drawLine(line.getStartX(),line.getStartY(),line.getStopX(),line.getStopY(),paint);
        }
        return bitmap;
    }

    public boolean onTouch(View v, MotionEvent event){

        int cellWidth=v.getWidth()/colQty;
        int cellHeight=v.getHeight()/rowQty;

        int cellWidthBm=bitmapWidth/colQty;;
        int cellHeightBm=bitmapHeight/rowQty;

        int columnIndex=(int) event.getX()/cellWidth;
        int rowIndex=(int) event.getY()/cellHeight;

        if(board[rowIndex][columnIndex]!=-1){
            return true;
        }
        board[rowIndex][columnIndex]=player;

        //Update Chessboard
        int padding=5;
        if(player==0){
            canvas.drawBitmap(bmCross,new Rect(0,0,bmCross.getWidth(),bmCross.getHeight()),new Rect(columnIndex*cellWidthBm+padding,
                    rowIndex*cellHeightBm+padding,
                    (columnIndex+1)*cellWidthBm-padding,(rowIndex+1)*cellHeightBm-padding),paint);
            player=1;}
        else
            player=0;

        //Toast.makeText(context, event.getX()+" "+event.getY(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, v.getWidth()+" "+v.getHeight(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, rowIndex+" "+columnIndex, Toast.LENGTH_SHORT).show();

        return true;
    }
}
