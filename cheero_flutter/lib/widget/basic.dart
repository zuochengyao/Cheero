import 'package:flutter/material.dart';
import 'package:cheero_flutter/model/app_bar_choice.dart';
import 'package:flutter/services.dart';

const List<AppBarChoice> choices = const <AppBarChoice>[
  const AppBarChoice(title: 'Car', icon: Icons.directions_car),
  const AppBarChoice(title: 'Bicycle', icon: Icons.directions_bike),
  const AppBarChoice(title: 'Boat', icon: Icons.directions_boat),
  const AppBarChoice(title: 'Bus', icon: Icons.directions_bus),
  const AppBarChoice(title: 'Train', icon: Icons.directions_railway),
  const AppBarChoice(title: 'Subway', icon: Icons.directions_subway),
  const AppBarChoice(title: 'Car', icon: Icons.directions_car),
  const AppBarChoice(title: 'Walk', icon: Icons.directions_walk)
];

class Basic extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _BasicState();
  }
}

class _BasicState extends State<Basic> {
  static const toAndroidPlugin =
      const MethodChannel('com.icheero.app.activity/fta');
  static const fromAndroidPlugin =
      const EventChannel('com.icheero.app.activity/atf');

  AppBarChoice _choice = choices[0];

  @override
  void initState() {
    super.initState();
    _fromAndroid();
  }

  void setChoice(AppBarChoice choice) {
    setState(() {
      this._choice = choice;
    });
  }

  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      _toAndroid(index);
    });
  }

  // 发送数据给Android端
  Future<Null> _toAndroid(int index) async {
    String result = await toAndroidPlugin.invokeMethod(index.toString());
    print(result);
  }

  var _fromAndroidSub;
  var _nativeParams;

  void _fromAndroid() {
    if (_fromAndroidSub == null) {
      _fromAndroidSub = fromAndroidPlugin
          .receiveBroadcastStream()
          .listen(_onFromAndroidEvent, onError: _onFromAndroidError);
    }
  }

  void _onFromAndroidEvent(Object event) {
    setState(() {
      _nativeParams = event;
      print(_nativeParams);
    });
  }

  void _onFromAndroidError(Object error) {
    setState(() {
      _nativeParams = "error";
      print(error);
    });
  }

  @override
  Widget build(BuildContext context) {
    var basicAppBar = new AppBar(
      title: const Text("Basic AppBar"),
      actions: <Widget>[
        new IconButton(
            icon: new Icon(choices[0].icon),
            onPressed: () => setChoice(choices[0])),
        new IconButton(
            icon: new Icon(choices[1].icon),
            onPressed: () => setChoice(choices[1])),
        new PopupMenuButton<AppBarChoice>(
          onSelected: setChoice,
          itemBuilder: (BuildContext context) {
            return choices.skip(2).map((AppBarChoice choice) {
              return new PopupMenuItem<AppBarChoice>(
                child: new Text(choice.title),
                value: choice,
              );
            }).toList();
          },
        )
      ],
    );

    var basicMaterial = new MaterialApp(
      home: new Scaffold(
          body: new Padding(
            padding: const EdgeInsets.all(16.0),
            child: new ChoiceCard(choice: _choice),
          ),
          appBar: basicAppBar),
    );

    var tabbedAppBar = new AppBar(
        title: const Text('Tabbed AppBar'),
        bottom: new TabBar(
          tabs: choices.map((AppBarChoice choice) {
            return new Tab(
              text: choice.title,
              icon: new Icon(choice.icon),
            );
          }).toList(),
          isScrollable: true,
        ));

    var tabbedMaterial = new MaterialApp(
        home: new DefaultTabController(
            length: choices.length,
            child: new Scaffold(
              appBar: tabbedAppBar,
              body: new TabBarView(
                children: choices.map((AppBarChoice choice) {
                  return new Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: new ChoiceCard(choice: choice),
                  );
                }).toList(),
              ),
              drawer: Drawer(),
              bottomNavigationBar: new BottomNavigationBar(
                items: const <BottomNavigationBarItem>[
                  BottomNavigationBarItem(
                      icon: Icon(Icons.home), title: Text('Home')),
                  BottomNavigationBarItem(
                    icon: Icon(Icons.business),
                    title: Text('Business'),
                  ),
                  BottomNavigationBarItem(
                    icon: Icon(Icons.school),
                    title: Text('School'),
                  )
                ],
                currentIndex: _selectedIndex,
                selectedItemColor: Colors.amber[800],
                onTap: _onItemTapped,
              ),
            )));

    return tabbedMaterial;
  }
}

class ChoiceCard extends StatelessWidget {
  final AppBarChoice choice;

  const ChoiceCard({Key key, this.choice}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final TextStyle textStyle = Theme.of(context).textTheme.display1;
    return new Card(
      color: Colors.white,
      child: new Center(
        child: new Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            new Icon(choice.icon, size: 128.0, color: textStyle.color),
            new Text(choice.title, style: textStyle),
          ],
        ),
      ),
    );
  }
}
