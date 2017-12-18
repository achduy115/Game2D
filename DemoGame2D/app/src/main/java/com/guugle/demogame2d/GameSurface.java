package com.guugle.demogame2d;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.graphics.Bitmap;

/**
 * Created by Yuu on 12/16/2017.
 */

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    private ChibiCharacter chibi1;

    public GameSurface(Context context) {
        super(context);

        // Đảm bảo Game Surface có thể forcus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Set đặt các sự kiện liên quan đến game.
        this.getHolder().addCallback(this);
    }

    public void update() {
        this.chibi1.update();
    }

    // Vẽ màn hình
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.chibi1.draw(canvas);
    }

    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Thêm nhân vật vào trò chơi
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        // Set vị trí của nhân vật
        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,700,400);

        // Tạo một luồng
        this.gameThread = new GameThread(this, holder);
        // Set luồng chạy
        this.gameThread.setRunning(true);
        // Bắt đầu luồng
        this.gameThread.start();
    }

    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);


                // Luồng cha, cần phải tạm dừng chờ GameThread kết thúc.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}
