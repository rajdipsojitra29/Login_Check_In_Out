package com.login_check_in_out.NativeClasses.a4VideoRecord;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.login_check_in_out.NativeClasses.a2Utility.UtilityJava.UtilityJava;
import com.login_check_in_out.NativeClasses.a4VideoRecord.encoder.MediaAudioEncoder;
import com.login_check_in_out.NativeClasses.a4VideoRecord.encoder.MediaEncoder;
import com.login_check_in_out.NativeClasses.a4VideoRecord.encoder.MediaMuxerWrapper;
import com.login_check_in_out.NativeClasses.a4VideoRecord.encoder.MediaVideoEncoder;
import com.login_check_in_out.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CameraFragment extends Fragment {
	private static final boolean DEBUG = false;	// TODO set false on release
	private static final String TAG = "CameraFragment";

	/**
	 * for camera preview display
	 */
	private CameraGLView mCameraView;
	/**
	 * for scale mode display
	 */
//	private TextView mScaleModeView;
	/**
	 * button for start/stop recording
	 */
	private ImageButton mRecordButton;
	/**
	 * muxer for audio/video recording
	 */
	private MediaMuxerWrapper mMuxer;

	public CameraFragment() {
		super();
		// need default constructor
	}

	String stringFilePath = "";











	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		mCameraView = (CameraGLView)rootView.findViewById(R.id.cameraView);
//		mCameraView.setVideoSize(1280, 720);
//		mCameraView.setVideoSize(640, 360);
		mCameraView.setOnClickListener(mOnClickListener);
//		mScaleModeView = rootView.findViewById(R.id.scalemode_textview);
		updateScaleModeText();
		mRecordButton = rootView.findViewById(R.id.record_button);
		mRecordButton.setOnClickListener(mOnClickListener);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (DEBUG) Log.v(TAG, "onResume:");
		mCameraView.onResume();
	}

	@Override
	public void onPause() {
		if (DEBUG) Log.v(TAG, "onPause:");
		stopRecording(new CameraFragment_SR_Interface() {
			@Override
			public void callBackStopRecording(String stringBase64) {
			}
		});
		mCameraView.onPause();
		super.onPause();
	}

	/**
	 * method when touch record button
	 */
	private final OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(final View view) {
			switch (view.getId()) {
			case R.id.cameraView:
//				final int scale_mode = (mCameraView.getScaleMode() + 1) % 4;
//				mCameraView.setScaleMode(scale_mode);
//				updateScaleModeText();
				break;
			case R.id.record_button:
//				if (mMuxer == null)
//					startRecording();
//				else
//					stopRecording();
				break;
			}
		}
	};

	private void updateScaleModeText() {
		final int scale_mode = mCameraView.getScaleMode();
//		mScaleModeView.setText(
//			scale_mode == 0 ? "scale to fit"
//			: (scale_mode == 1 ? "keep aspect(viewport)"
//			: (scale_mode == 2 ? "keep aspect(matrix)"
//			: (scale_mode == 3 ? "keep aspect(crop center)" : ""))));
	}

	/**
	 * start resorcing
	 * This is a sample project and call this on UI thread to avoid being complicated
	 * but basically this should be called on private thread because prepareing
	 * of encoder is heavy work
	 */
	public void startRecording() {
		if (DEBUG) Log.v(TAG, "startRecording:");
		try {
			mRecordButton.setColorFilter(0xffff0000);	// turn red
			mMuxer = new MediaMuxerWrapper(".mp4");	// if you record audio only, ".m4a" is also OK.
			Log.d("",mMuxer.getOutputPath());
			stringFilePath = mMuxer.getOutputPath();
			if (true) {
				// for video capturing
				new MediaVideoEncoder(mMuxer, mMediaEncoderListener, mCameraView.getVideoWidth(), mCameraView.getVideoHeight());
			}
			if (true) {
				// for audio capturing
				new MediaAudioEncoder(mMuxer, mMediaEncoderListener);
			}
			mMuxer.prepare();
			mMuxer.startRecording();
		} catch (final IOException e) {
			mRecordButton.setColorFilter(0);
			Log.e(TAG, "startCapture:", e);
		}
	}

	/**
	 * request stop recording
	 */
	public void stopRecording(final CameraFragment_SR_Interface cameraFragment_sr_interface) {
		if (DEBUG) Log.v(TAG, "stopRecording:mMuxer=" + mMuxer);
		mRecordButton.setColorFilter(0);
		if (mMuxer != null) {
			mMuxer.stopRecording();
			mMuxer = null;
			final Handler handler = new Handler(Looper.getMainLooper());
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					String base64 = "";
					try {
						File file = new File(stringFilePath);
						byte[] buffer = new byte[(int) file.length() + 100];
						int length = new FileInputStream(file).read(buffer);
						base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
						cameraFragment_sr_interface.callBackStopRecording(base64);
					} catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						Log.d("",stringFilePath);

						 Uri uri = Uri.fromFile(new File(stringFilePath));
							Log.d("", String.valueOf(uri));

						File fdelete = new File(uri.getPath());
						if (fdelete.exists()) {
							if (fdelete.delete()) {
								System.out.println("file Deleted :" + uri.getPath());
							} else {
								System.out.println("file not Deleted :" + uri.getPath());
							}
						}

						stringFilePath = "";
					}
				}
			}, 1000);
		}
	}

	/**
	 * callback methods from encoder
	 */
	private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new MediaEncoder.MediaEncoderListener() {
		@Override
		public void onPrepared(final MediaEncoder encoder) {
			if (DEBUG) Log.v(TAG, "onPrepared:encoder=" + encoder);
			if (encoder instanceof MediaVideoEncoder)
				mCameraView.setVideoEncoder((MediaVideoEncoder)encoder);
		}

		@Override
		public void onStopped(final MediaEncoder encoder) {
			if (DEBUG) Log.v(TAG, "onStopped:encoder=" + encoder);
			if (encoder instanceof MediaVideoEncoder)
				mCameraView.setVideoEncoder(null);
		}
	};

}
