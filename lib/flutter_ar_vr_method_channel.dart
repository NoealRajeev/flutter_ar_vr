import 'package:flutter/services.dart';

import 'flutter_ar_vr_platform_interface.dart';

class MethodChannelFlutterArVr extends FlutterArVrPlatform {
  final methodChannel = const MethodChannel('flutter_ar_vr');

  @override
  Future<void> initializeAR() async {
    await methodChannel.invokeMethod('initializeAR');
  }

  @override
  Future<void> startARSession() async {
    await methodChannel.invokeMethod('startARSession');
  }

  @override
  Future<void> stopARSession() async {
    await methodChannel.invokeMethod('stopARSession');
  }
}
