import 'flutter_ar_vr_platform_interface.dart';

class FlutterArVr {
  Future<String?> getPlatformVersion() {
    return FlutterArVrPlatform.instance.getPlatformVersion();
  }

  Future<void> initializeAR() {
    return FlutterArVrPlatform.instance.initializeAR();
  }

  Future<void> startARSession() {
    return FlutterArVrPlatform.instance.startARSession();
  }

  Future<void> stopARSession() {
    return FlutterArVrPlatform.instance.stopARSession();
  }
}
