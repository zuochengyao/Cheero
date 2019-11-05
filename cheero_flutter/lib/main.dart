import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:cheero_flutter/widget/basic.dart';
import 'package:cheero_flutter/widget/random_words.dart';

/// main 函数入口
/// => 符号, 这是Dart中单行函数或方法的简写
void main() => runApp(_widgetForRoute(window.defaultRouteName));

Widget _widgetForRoute(String route) {
  switch (route) {
    case 'randomRoute':
      return new MyApp(homeWidget: new RandomWords());
    case 'basicRoute':
      return new MyApp(homeWidget: new Basic());
    default:
      return new MyApp(
          homeWidget: Center(
        child: Text('Unknown route: $route', textDirection: TextDirection.ltr),
      ));
  }
}

class MyApp extends StatelessWidget {
  final Widget homeWidget;

  const MyApp({this.homeWidget});

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Startup Name Generator1',
      home: this.homeWidget,
      theme: new ThemeData(
        primaryColor: Colors.white,
      ),
    );
  }
}
