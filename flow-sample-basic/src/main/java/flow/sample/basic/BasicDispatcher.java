/*
 * Copyright 2016 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flow.sample.basic;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import flow.Flow;

final class BasicDispatcher implements Flow.Dispatcher {

  private final Activity activity;

  BasicDispatcher(Activity activity) {
    this.activity = activity;
  }

  @Override public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
    Log.d("BasicDispatcher", "dispatching " + traversal);
    Object dest = traversal.destination.top();

    ViewGroup frame = (ViewGroup) activity.findViewById(R.id.basic_activity_frame);

    if (traversal.origin != null) {
      if (frame.getChildCount() > 0) {
        traversal.origin.topSaveState().save(frame.getChildAt(0));
        frame.removeAllViews();
      }
    }

    @LayoutRes final int layout;
    if (dest instanceof HelloScreen) {
      layout = R.layout.hello_screen;
    } else if (dest instanceof WelcomeScreen) {
      layout = R.layout.welcome_screen;
    } else {
      throw new AssertionError("Unrecognized screen " + dest);
    }

    View incomingView = LayoutInflater.from(traversal.createContext(dest, activity)) //
        .inflate(layout, frame, false);

    frame.addView(incomingView);
    traversal.destination.topSaveState().restore(incomingView);

    callback.onTraversalCompleted();
  }
}
