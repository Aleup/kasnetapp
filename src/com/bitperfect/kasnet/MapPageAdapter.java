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

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MapPageAdapter extends FragmentStatePagerAdapter {
  Context ctxt=null;
  List<Fragment> fragments;

    public MapPageAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
    }

  public void addFragment(Fragment fragment) {
    this.fragments.add(fragment);
  }

  @Override
  public int getCount() {
    return 2;
  }

  @Override
  public Fragment getItem(int position) {
      switch(position) {
          case 0: return LoginFragment.newInstance("FirstFragment, Instance 1");
          case 1: return PageMapFragment.newInstance("SecondFragment, Instance 1");
          default: return PageMapFragment.newInstance("ThirdFragment, Default");
      }
  }

  @Override
  public String getPageTitle(int position) {
      switch(position) {
          case 0: return "Ingresar";
          case 1: return "Mi Ubicación";
          default: return "";
      }
  }
}