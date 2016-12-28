package org.yanzi.camera.preview;

import org.yanzi.camera.CameraInterface;
import org.yanzi.playcamera.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import static android.R.attr.bitmap;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback,
		Camera.PreviewCallback{
	private static final String TAG = "yanzi";
	CameraInterface mCameraInterface;
	Context mContext;
	SurfaceHolder mSurfaceHolder;
	Camera camera;
	Bitmap bm;

	public CameraSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);//translucent��͸�� transparent͸��
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceHolder.addCallback(this);
	}



	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.i(TAG, "surfaceCreated...");
		camera = CameraInterface.getInstance().doOpenCamera(null, CameraInfo.CAMERA_FACING_BACK);
		camera.setPreviewCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.i(TAG, "surfaceChanged...");
		CameraInterface.getInstance().doStartPreview(mSurfaceHolder, 1.333f);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.i(TAG, "surfaceDestroyed...");
		CameraInterface.getInstance().doStopCamera();

	}
	public SurfaceHolder getSurfaceHolder(){
		return mSurfaceHolder;
	}


	public void saveScreenshot(Activity activity, ImageView iv) {
		Log.d("Test", "2222222");

		if(bm != null){
			Log.d("Test", "3333333333");

			iv.setImageBitmap(bm);
		}
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		Camera.Parameters params = camera.getParameters();
		int w = params.getPreviewSize().width;
		int h = params.getPreviewSize().height;
		int format = params.getPreviewFormat();
		YuvImage image = new YuvImage(data, format, w, h, null);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Rect area = new Rect(0, 0, w, h);
		image.compressToJpeg(area, 100, out);
		Bitmap bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size());
		Matrix matrix = new Matrix();
		matrix.postRotate(270);
		bm = Bitmap.createBitmap(bitmap, 0, 0,w, h, matrix, true);
	}
}
