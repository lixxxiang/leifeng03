package com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.DetectFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.Button;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelImpl.DetectFragmentWithMapModelImpl;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.models.modelInterface.OnSendArrayListener;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.BasePresenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.MyOrientationListener;
import com.yinglan.scrolllayout.ScrollLayout;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.detectFragmentNormal;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.mainActivitypresenter.MainActivityPresenter.detectFragmentWithMap;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.baiduMap;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.bitmapDescriptor;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.mapView;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.locationMode;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.mlocationClient;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.mIconLocation;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.myOrientationListener;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.context;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Utils.icon_format;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms.DetectFragmentWithMap.scrollLayout;

/**
 * Created by yyfwptz on 2017/3/27.
 */

public class DetectFragmentWithMapPresenter extends BasePresenter<DetectFragmentWithMap, DetectFragmentWithMapModelImpl> {

    private FragmentManager fragmentManager;
    private MapStatusUpdate mapStatusUpdate;
    private Button hideButton;
    private Marker markerA;
    private InfoWindow infoWindow;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LocationClient mLocClient;
    private DetectFragmentWithMapPresenter.MyLocationListenner mlistener;
    private LatLng currentPt;
    private float mCurrentX;
    public static BitmapDescriptor setLocationIcon;
    @Override
    protected DetectFragmentWithMapModelImpl getModel() {
        return new DetectFragmentWithMapModelImpl();
    }

    /**
     * 切换视图
     */
    public void changeFragment() {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        fTransaction.hide(detectFragmentWithMap);
        fTransaction.show(detectFragmentNormal);
        fTransaction.commit();
    }

    /**
     *  添加地标  测试
     * @param icons
     */
    public void setIcon(Icon[] icons) {
        for (Icon i : icons) {
            bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            setIcon(i.getLatitude(), i.getLangitude());
            initOverlay(i.getLatitude(), i.getLangitude(),bitmapDescriptor);
        }
    }

    private void setIcon(double latitude, double langitude){
        Log.e("TAGG", ""+latitude+" "+langitude);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(latitude, langitude);
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(17.0f);

        baiduMap.setMapStatus(mapStatusUpdate);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(latLng).zoom(17).build()));
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                hideButton = new Button(MainActivity.mainActivity.getApplicationContext());
                InfoWindow.OnInfoWindowClickListener listener = null;
                if (marker == markerA) {
                    hideButton.setBackgroundColor(0x000000);
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                        }
                    };
                    LatLng ll = marker.getPosition();
                    infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(hideButton), ll, -47, listener);
                    baiduMap.showInfoWindow(infoWindow);
                }
                return true;
            }
        });
    }
    /**
     * 添加标志物 子方法
     * @param latitude
     * @param langitude
     * @param bitmapDescriptor
     */
    public void initOverlay(double latitude, double langitude, BitmapDescriptor bitmapDescriptor) {
        LatLng latLng = new LatLng(latitude, langitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptor)
                .zIndex(9)
                .draggable(true);
        markerA = (Marker) (baiduMap.addOverlay(markerOptions));
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(17)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }


    /**
     * 获取图标信息
     */
    public void getIcons() { //start from here
        model.getIcons(new OnSendArrayListener() {

            @Override
            public void sendArray(Object[] objects) {
                getView().getIcons(objects);
            }

        });
    }

    /**
     *
     */
    public class MyLocationListenner implements BDLocationListener {
        private boolean isFirstIn = true;

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()
                    .direction(mCurrentX)
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(data);
            MyLocationConfiguration configuration
                    = new MyLocationConfiguration(locationMode, true, mIconLocation);
            baiduMap.setMyLocationConfigeration(configuration);

            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.setMapStatus(msu);
                baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(latLng).zoom(17).build()));
                isFirstIn = false;
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    /**
     * 初始化定位
     * @param res
     */
    public void initLocation(Resources res) {
        locationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mlocationClient = new LocationClient(MainActivity.mainActivity);
        mlistener = new MyLocationListenner();
        mlocationClient.registerLocationListener(mlistener);
        LocationClientOption mOption = new LocationClientOption();
        mOption.setCoorType("bd09ll");
        mOption.setIsNeedAddress(true);
        mOption.setOpenGps(true);
        int span = 1000;
        mOption.setScanSpan(span);
        mlocationClient.setLocOption(mOption);
        mlocationClient.start();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.location_marker, options);
        bitmap = rotateBitmap(icon_format(bitmap, 100, 100), 330);
        mIconLocation = BitmapDescriptorFactory.fromBitmap(bitmap);
        myOrientationListener = new MyOrientationListener(context);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }


    /**
     * R.drawable.location_marker 图标有问题 对图标的旋转操作
     * @param origin
     * @param alpha
     * @return
     */
    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin))
            return newBM;
        origin.recycle();
        return newBM;
    }


    /**
     *  长按添加当前点图标
     * @param res
     */
    public void setLocation(final Resources res)
    {
        final int[] flag = {0};
        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(flag[0] == 0){
                    showIcon(res, latLng);
                    scrollLayout.setToOpen();
                    flag[0] = 1;
                }else{
                    removeIcon();
                    scrollLayout.setToOpen();
                    showIcon(res, latLng);
                }
            }
        });
    }

    /**
     * 添加图标
     * @param res
     * @param latLng
     */
    private void showIcon(Resources res, LatLng latLng){
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.icon_focus_marka, options);
        setLocationIcon = BitmapDescriptorFactory.fromBitmap(icon_format(bitmap, 75, 120));
        currentPt = latLng;
        setPosition(currentPt.latitude, currentPt.longitude, setLocationIcon);
    }

    /**
     *  点击新位置 删除前一个位置的icon
     */
    public void removeIcon(){
        markerA.remove();
    }


    /**
     *  长按添加图标 子方法
     * @param latitude
     * @param langitude
     * @param bitmapDescriptor
     */
    public void setPosition(double latitude, double langitude, BitmapDescriptor bitmapDescriptor){
        setIcon(latitude, langitude);
        initOverlay(latitude,langitude, bitmapDescriptor);
    }

    /**
     * 上拉菜单
     * @param scrollLayout
     */
    public void initialScrollLayout(ScrollLayout scrollLayout){
        scrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        scrollLayout.getBackground().setAlpha(0);
    }

    /**
     * 上拉菜单
     */
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                scrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {

            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

}
