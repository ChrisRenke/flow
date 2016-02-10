# Flow

_"Name-giving will be the foundation of our science."_ - Linnaeus

_"The winds and waves are always on the side of the ablest navigators."_ - Gibbon

_"Memory is the treasury and guardian of all things._" - Cicero

**Flow gives names to your Activity's UI states, navigates between them, and remembers where it's been.**

## Features

Navigate between UI states. Support the back button easily without confusing your users with surprising results.

Remember the UI state, and its history, across configuration changes and process death.

Manage resources with set-up/tear-down hooks invoked for each UI state. UI states can easily share resources, and they'll be disposed when no longer needed.

Manage all types of UIs-- complex master-detail views, multiple layers, and window-based dialogs are all simple to manage.


## Using Flow

Gradle:

```groovy
compile 'com.squareup.flow:flow:1.0-alpha'
```

Install Flow into your Activity:

```java
public class MainActivity {
  @Override protected void attachBaseContext(Context baseContext) {
    baseContext = Flow.configure(baseContext, this).install();
    super.attachBaseContext(baseContext);
  }
}
```

By default, Flow will take over your Activity's content view. When you start your Activity, you should see a "Hello world" screen. Of course you'll want to change this-- that's covered under [Controlling UI](#Controlling-UI) below.

### Defining UI states with key objects

Your Activity's UI states are represented in Flow by Objects, which Flow refers to as "keys". Keys are typically [value objects][valueobject] with just enough information to identify a discrete UI state.

Flow relies on a key's [equals][equals] and [hashCode][hashcode] methods for its identity. Keys should be immutable-- that is, their `equals` and `hashCode` methods should always behave the same.

To give an idea of what keys might look like, here are some examples:

```java
public enum TabKey {
  TIMELINE,
  NOTIFICATIONS,
  PROFILE
}

public final class HomeKey extends flow.ClassKey {
}

public final class ArticleKey {
  public final String articleId;

  public ArticleKey(String articleId) {
    this.articleId = articleId;
  }

  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof ArticleKey) &&
           articleId.equals(((ArticleKey)o).articleId);
  }
  
  public int hashCode() {
    return articleId.hashCode();
  }
}
```

See the [Sample Projects](#sample-projects) below for more example keys.

For help with defining good keys, see [Guidelines for Keys](#TODO). 

### Navigation
Flow offers two simple commands for navigating your app.

`Flow#goBack()` -- Goes back to the previous state,. Think "back button".
`Flow#set(key)` -- Goes to the requested state. Updates History appropriately.

Flow also lets you rewrite history safely and easily.

`Flow#setHistory(history, direction)` -- Change history to whatever you want.

### Controlling UI
Navigation only counts if it changes UI state. Because every app has different needs, Flow lets you plug in your own logic for updating your UI.

**TODO**

### Managing resources

### Surviving process death


## Sample Projects

* [Hello World](flow-sample-helloworld) - A starting point for integration.
* [Basic Sample](flow-sample-basic)- Fully configured Flow.
* [Tree Sample](flow-sample-tree) - Uses TreeKeys to define scopes and share state.
* [MultiKey Sample](flow-sample-multikey) - Uses MultiKeys to represent screens with dialogs as discrete states.

## License

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



## TODO

* write a "Guidelines for keys" wiki doc
 

[equals]: http://developer.android.com/reference/java/lang/Object.html#equals(java.lang.Object)
[hashcode]: http://developer.android.com/reference/java/lang/Object.html#hashCode()
[valueobject]: https://en.wikipedia.org/wiki/Value_object
