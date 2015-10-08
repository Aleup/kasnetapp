/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    https://commonsware.com/Android
 */

package com.bitperfect.kasnet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AbstractMapActivity {

  SlidingTabLayout mSlidingTabLayout;
  public ViewPager pager;
  public ProgressDialog prgDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (readyToGo()) {
      setContentView(R.layout.activity_main);
      pager=(ViewPager)findViewById(R.id.pager);
      pager.setAdapter(buildAdapter());
      mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
      mSlidingTabLayout.setViewPager(pager);
    }
  }

  private PagerAdapter buildAdapter() {
    MapPageAdapter adapter = new MapPageAdapter(getSupportFragmentManager());
    return adapter;
  }

  public ViewPager getPager()
  {
    return pager;
  }

}


