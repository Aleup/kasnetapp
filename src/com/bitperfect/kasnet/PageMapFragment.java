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

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class PageMapFragment extends SupportMapFragment implements
    OnMapReadyCallback {

    private boolean needsInit=false;
    private GoogleMap map;
    private Context ctx;
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (savedInstanceState == null) {
      needsInit=true;
    }

    getMapAsync(this);
  }

  @Override
  public void onMapReady(final GoogleMap map) {
      this.map = map;
      this.ctx = getContext();
      if (needsInit) {
          if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
                  == PackageManager.PERMISSION_GRANTED) {
              map.setMyLocationEnabled(true);
          } else {
              Toast.makeText(getActivity(), "No se dispone de permisos de localización para la aplicación.", Toast.LENGTH_LONG).show();
          }
          CameraUpdate center=
                  CameraUpdateFactory.newLatLngZoom(new LatLng(-12.1182223, -76.9888869), 14);
          map.moveCamera(center);
      }
      (new AsyncCargaKML()).doInBackground();


    addMarker(map, -12.1182223, -76.9888869, R.string.GloboKas,
              R.string.GloboKas_desc);


  }

    private void retrieveFileFromResource(GoogleMap map, Context context) {

    }

  private void addMarker(GoogleMap map, double lat, double lon,
                         int title, int snippet) {
    map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
            .title(getString(title))
            .snippet(getString(snippet))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.globo)));
  }

    public static PageMapFragment newInstance(String text) {
        PageMapFragment f = new PageMapFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    private class AsyncCargaKML extends AsyncTask<String, Void, String> {
        private ProgressDialog prgDialog;
        private KmlLayer layer;

        public AsyncCargaKML() {
        }

        @Override
        protected String doInBackground(final String... params) {
            try {
                layer = new KmlLayer(map, R.raw.kasnet, ctx);
                layer.addLayerToMap();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            prgDialog = new ProgressDialog(getActivity());
            prgDialog.setMessage("Downloading Mp3 file. Please wait...");
            prgDialog.setIndeterminate(false);
            prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            prgDialog.setCancelable(false);
            prgDialog.show();
            prgDialog = ProgressDialog.show(getActivity(), "Verificando Marcadores",
                    "Cargando datos, espere...", true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (prgDialog.isShowing()) {
                prgDialog.dismiss();
            }
            if (s.equals("success")) {
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            prgDialog.show();
        }
    }

}