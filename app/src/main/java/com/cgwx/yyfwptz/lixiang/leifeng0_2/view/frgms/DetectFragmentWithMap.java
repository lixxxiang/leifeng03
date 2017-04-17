package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment.DetectFragmentWithMapPresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.CheckPermission.checkPermission;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class DetectFragmentWithMap extends BaseFragment<DetectFragmentWithMapPresenter, DetectFragmentWithMap> implements BaseViewInterface {


    @BindView(R.id.changeView)
    Button changeView;

    private View view;
    public static TextureMapView mapView;
    public static BaiduMap baiduMap;
    private FragmentManager fragmentManager;
    private LocationClient mLocClient;
    public static Button requestLocButton;
    public static BitmapDescriptor bitmapDescriptor;
    private Icon[] icons;
    private MapStatusUpdate mapStatusUpdate;
    private Button hideButton;
    private Marker markerA;
    private InfoWindow infoWindow;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private DetectFragmentWithMap.MyLocationListenner myListener = new DetectFragmentWithMap.MyLocationListenner();




    public DetectFragmentWithMap() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.detect_fragment_with_map, container, false);
        ButterKnife.bind(this, view);
        mapView = (TextureMapView) view.findViewById(R.id.bmapView);
        requestLocButton = (Button) view.findViewById(R.id.button1);

        /**
         * to presenter
         */
        checkPermission();
        /**
         * 传参 定位点坐标
         */
        fpresenter.setLocationMode();
        fpresenter.getIcons();
        fpresenter.setIcon(icons);
        fragmentManager = getFragmentManager();
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }

    @Override
    protected DetectFragmentWithMapPresenter getPresenter() {
        return new DetectFragmentWithMapPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        bitmapDescriptor.recycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    public void getIcons(Object[] objects) {
        icons = (Icon[]) objects;
    }

    private void setIcon(Icon[] icons) {
        for (Icon i : icons) {
            Log.e("---", String.valueOf(i.getLatitude()));

            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            baiduMap = mapView.getMap();
            baiduMap.setMyLocationEnabled(true);
            /**
             * 缩放等级
             */
            mapStatusUpdate = MapStatusUpdateFactory.zoomTo(14.0f);
            baiduMap.setMapStatus(mapStatusUpdate);

            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                public boolean onMarkerClick(final Marker marker) {
                    hideButton = new Button(MainActivity.mainActivity.getApplicationContext());
                    InfoWindow.OnInfoWindowClickListener listener = null;
                    if (marker == markerA) {
                        /**
                         * 透明
                         */
                        hideButton.setBackgroundColor(0x000000);
                        listener = new InfoWindow.OnInfoWindowClickListener() {
                            public void onInfoWindowClick() {
//                            Toast.makeText(MainActivity.mainActivity, "dd", Toast.LENGTH_SHORT).show();
                            }
                        };
                        LatLng ll = marker.getPosition();
                        infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(hideButton), ll, -47, listener);
                        baiduMap.showInfoWindow(infoWindow);
                    }
                    return true;
                }
            });
            initOverlay(i.getLatitude(), i.getLangitude());
        }
    }

    private void initOverlay(double latitude, double langitude) {
        LatLng latLng = new LatLng(latitude, langitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptor)
                .zIndex(9)
                .draggable(true);
        markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
        markerA = (Marker) (baiduMap.addOverlay(markerOptions));
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }

    private void setLocationMode() {
        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, bitmapDescriptor));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, bitmapDescriptor));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, bitmapDescriptor));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        mLocClient = new LocationClient(MainActivity.mainActivity);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private class MyLocationListenner implements BDLocationListener {
        boolean isFirstLoc = true;


        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mapView == null)
                return;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
