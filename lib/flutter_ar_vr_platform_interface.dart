import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_ar_vr_method_channel.dart';

abstract class FlutterArVrPlatform extends PlatformInterface {
  FlutterArVrPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterArVrPlatform _instance = MethodChannelFlutterArVr();

  static FlutterArVrPlatform get instance => _instance;

  static set instance(FlutterArVrPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('getPlatformVersion() has not been implemented.');
  }

  // Initialize AR
  Future<void> initializeAR() {
    throw UnimplementedError('initializeAR() has not been implemented.');
  }

  // Add placeholders for additional AR functionalities
  Future<void> startARSession() {
    throw UnimplementedError('startARSession() has not been implemented.');
  }

  Future<void> stopARSession() {
    throw UnimplementedError('stopARSession() has not been implemented.');
  }
}

