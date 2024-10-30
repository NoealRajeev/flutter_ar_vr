package in.ind.breakthrough.flutter_ar_vr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Config;
import com.google.ar.core.Session;

public class FlutterArVrPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private MethodChannel channel;
    private Activity activity; // Store the Activity reference
    private Context context;
    private Session arSession; // Declare the AR session variable

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        context = binding.getApplicationContext();
        channel = new MethodChannel(binding.getBinaryMessenger(), "flutter_ar_vr");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        this.activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        // Handle detachment
        activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        this.activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        // Clean up when detached from the activity
        activity = null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "initializeAR":
                initializeAR(result);
                break;
            case "startARSession":
                startARSession(result);
                break;
            case "stopARSession":
                stopARSession(result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private void initializeAR(Result result) {
        try {
            if (activity != null) { // Ensure activity is not null
                ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(activity);
                if (availability == ArCoreApk.Availability.SUPPORTED_INSTALLED) {
                    arSession = new Session(activity);
                    Config config = new Config(arSession);
                    config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
                    arSession.configure(config);
                    result.success(null);
                } else if (availability == ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED) {
                    result.error("AR_ERROR", "AR is supported but not installed. Please install ARCore.", null);
                    promptUserToInstallArCore();
                } else {
                    result.error("AR_ERROR", "AR is not supported on this device", null);
                }
            } else {
                result.error("AR_ERROR", "Failed to get current activity", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.error("AR_ERROR", "Exception during AR initialization: " + e.getMessage(), null);
        }
    }

    private void promptUserToInstallArCore() {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.ar.core"));
            activity.startActivity(intent);
        }
    }

    private void startARSession(Result result) {
        if (activity != null && arSession != null) {
//            setupSurfaceView(); // Setup SurfaceView to display the camera feed
            result.success(null);
        } else {
            result.error("AR_ERROR", "AR session is not initialized or activity is null", null);
        }
    }

    private void stopARSession(Result result) {
        if (arSession != null) {
            arSession.close();
            arSession = null;
//            if (surfaceView != null) {
//                activity.runOnUiThread(() -> {
//                    ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
//                    surfaceView = null;
//                });
//            }
        }
        result.success(null);
    }

//    private void setupSurfaceView() {
//        activity.runOnUiThread(() -> {
//            surfaceView = new GLSurfaceView(activity);
//            surfaceView.setEGLContextClientVersion(2); // Set the OpenGL ES version
//            surfaceView.setRenderer(new ARRenderer(arSession)); // Custom renderer for AR
//
//            activity.addContentView(surfaceView, new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        });
//    }
}
