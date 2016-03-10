/**
 * Created by Fabrice Armisen (farmisen@gmail.com) on 1/3/16.
 */

package com.lwansbrough.RCTCamera;

import java.util.List;

import android.content.Context;
import android.graphics.*;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.util.Log;
import android.support.annotation.Nullable;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.*;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.*;

public class RCTCameraView extends ViewGroup {
	private ThemedReactContext mContext;
	private BarcodeView mScanner;

	private void sendEvent(String eventName,
												 @Nullable WritableMap params) {
		mContext
				.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
				.emit(eventName, params);
	}

	private BarcodeCallback callback = new BarcodeCallback() {
		@Override
		public void barcodeResult(BarcodeResult result) {
			if (result.getText() != null) {
				Log.d("BARCODE", result.getText());
				WritableMap params = Arguments.createMap();
				params.putString("code", result.getText());
				sendEvent("CameraBarCodeRead", params);
			}
		}

		@Override
		public void possibleResultPoints(List<ResultPoint> resultPoints) {
		}
	};

  public RCTCameraView(ThemedReactContext context) {
    super(context);
		mContext = context;
		mScanner = new BarcodeView(mContext);
		addView(mScanner);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		this.mScanner.layout(
			0, 0, right - left, bottom - top
		);
  }

  @Override
  protected void onAttachedToWindow() {
		mScanner.resume();
		mScanner.decodeContinuous(callback);
  }

  @Override
  protected void onDetachedFromWindow() {
		mScanner.pause();
    mScanner.stopDecoding();
  }

  public void setAspect(int aspect) {
  }

  public void setCameraType(final int type) {
  }

  public void setTorchMode(int torchMode) {
    if (this.mScanner != null) {
      this.mScanner.setTorch(torchMode == 1);
    }
  }

  public void setFlashMode(int flashMode) {
      //this._flashMode = flashMode;
      //if (this._viewFinder != null) {
          //this._viewFinder.setFlashMode(flashMode);
      //}
  }

  public void setOrientation(int orientation) {
      //RCTCamera.getInstance().setOrientation(orientation);
      //if (this._viewFinder != null) {
          //layoutViewFinder();
      //}
  }
}
