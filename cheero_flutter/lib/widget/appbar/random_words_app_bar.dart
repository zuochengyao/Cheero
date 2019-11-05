import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

class RandomWordsAppBar extends AppBar {
  final BuildContext mContext;
  final String mTitle;
  final bool mCenterTitle;
  final Set<WordPair> mSaved;

  final _biggerFont = const TextStyle(fontSize: 18.0);

  RandomWordsAppBar({this.mContext, this.mTitle, this.mCenterTitle, this.mSaved});

  @override
  Widget get title => new Text(mTitle, textDirection: TextDirection.rtl);

  @override
  bool get centerTitle => this.mCenterTitle;

  @override
  Widget get leading {
    return new IconButton(
      icon: new Icon(Icons.menu),
      tooltip: 'Navigation menu',
      onPressed: _pushSaved,
    );
  }

  @override
  List<Widget> get actions {
    return <Widget>[
      new IconButton(
          icon: new Icon(Icons.search), tooltip: 'Search', onPressed: null)
    ];
  }

  void _pushSaved() {
    Navigator.of(mContext).push(new MaterialPageRoute(builder: (context) {
      final tiles = mSaved.map((pair) {
        return new ListTile(
          title: new Text(
            pair.asPascalCase,
            style: _biggerFont,
          ),
        );
      });
      final divided =
          ListTile.divideTiles(context: context, tiles: tiles).toList();
      return new Scaffold(
        appBar: new AppBar(
          title: new Text('Saved Suggestions'),
          centerTitle: true,
        ),
        body: new ListView(children: divided),
      );
    }));
  }
}
