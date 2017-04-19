package com.bloodtrackerplus.bloodtracker;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker mCurrLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);

        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        mGoogleMap.setMyLocationEnabled(true);

        buildGoogleApiClient();

        mGoogleApiClient.connect();

    }




    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mCurrLocation = mGoogleMap.addMarker(markerOptions);

            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(37.7750, 122.4183))
                    .title("San Francisco")
                    .snippet("Population: 776733"));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

            marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(26.934495, 75.794250))
                    .title("UMMED SINGH SUSHILA DEVI MEMORIAL BLOOD BANK")
                    .snippet(" A-6, Shastri Nagar, Near Tagore Public School," +
                            " Opposite State Bank Of Indore, Jaipur - 302016\n"));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(26.917892, 75.801538))
                    .title("ZANANA HOSPITAL BLOOD BANK")
                    .snippet(" 14, Station Road, Chand Pole Darwaja, Joshi Colony, Chand Pole Bazar, Jaipur - 302001" ));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(26.922227, 75.815721))
                    .title("GLOBAL BLOOD DONAR ORGANIZATION SANSATHAN")
                    .snippet(" 2240, Near Siwam Mandir, Kishanpole Bazar, Jaipur - 302001" ));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(26.897880, 75.808316))
                    .title("Santokba Durlabhji Blood Bank")
                    .snippet(" SDMH, 302015, Bhawani Singh Rd, Rambagh, Jaipur, Rajasthan 302005" ));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(26.905444, 75.778020))
                    .title("LifeCell International Pvt. Ltd\n")
                    .snippet(" 20, Kiran Path, Suraj Nagar (West), Civil Lines., Jaipur, Rajasthan 302006"));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
 }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }



}