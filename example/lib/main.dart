import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_ar_vr/flutter_ar_vr.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _flutterArVrPlugin = FlutterArVr();

  @override
  void initState() {
    super.initState();
    _initializeAR();
  }

  Future<void> _initializeAR() async {
    try {
      await _flutterArVrPlugin.initializeAR();
      await _flutterArVrPlugin.startARSession();
    } catch (e) {
      print('Error initializing AR: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('AR Test App')),
        body: const Center(child: Text('AR session initialized')),
      ),
    );
  }
}
