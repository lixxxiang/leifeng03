package com.cgwx.yyfwptz.lixiang.leifeng0_2.view.frgms;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.R;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.entities.Icon;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.presenters.HomeFragment.HomeFragmentWithMap2Presenter;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.BaseViewInterface;
import com.cgwx.yyfwptz.lixiang.leifeng0_2.view.activity.MainActivity;
import com.yinglan.scrolllayout.ScrollLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Utils.checkPermission;
import static com.cgwx.yyfwptz.lixiang.leifeng0_2.utils.Utils.icon_format;


/**
 * Created by Jay on 2015/8/28 0028.
 */

public class HomeFragmentWithMap2 extends BaseFragment<HomeFragmentWithMap2Presenter, HomeFragmentWithMap2> implements BaseViewInterface {
    public static TextureMapView mapView = null;
    public static BaiduMap baiduMap;
    private LocationClient mlocationClient;
    private MylocationListener mlistener;
    private Context context;
    private View view;
    private double mLatitude;
    private double mLongitude;
    private float mCurrentX;
    @BindView(R.id.changeView)
    Button changeView;
    private FragmentManager fragmentManager;
    private Icon[] icons;
    private MapStatusUpdate mapStatusUpdate;
    private LatLng currentPt;
    private BitmapDescriptor mIconLocation;
    public static BitmapDescriptor bitmapDescriptor;
    public static BitmapDescriptor setLocationIcon;
    private MyOrientationListener myOrientationListener;
    private MyLocationConfiguration.LocationMode locationMode;
    private ScrollLayout mScrollLayout;


    private MainPagerAdapter.OnClickItemListenerImpl mOnClickItemListener = new MainPagerAdapter.OnClickItemListenerImpl() {
        @Override
        public void onClickItem(View item, int position) {
            if (mScrollLayout.getCurrentStatus() == ScrollLayout.Status.OPENED) {
                mScrollLayout.scrollToClose();
            }
        }
    };


    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if(currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
//                getActivity().finish();
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };



    public HomeFragmentWithMap2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.home_fragment_with_map2test, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();


        mScrollLayout = (ScrollLayout) view.findViewById(R.id.scroll_down_layout);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.getBackground().setAlpha(0);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getActivity());
        mainPagerAdapter.setOnClickItemListener(mOnClickItemListener);

        mapView = (TextureMapView) view.findViewById(R.id.bmapView);
//        requestLocButton = (Button) view.findViewById(R.id.button1);
        /**
         * to presenter
         */
        checkPermission();
        /**
         * 传参 定位点坐标
         */
//        fpresenter.setLocationMode();
        initLocation();
        fpresenter.getIcons();
        fpresenter.setIcon(icons);

//        myOrientationListener.start();
        fragmentManager = getFragmentManager();
        final int[] flag = {0};
        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(flag[0] == 0){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_focus_marka, options);
                    setLocationIcon = BitmapDescriptorFactory.fromBitmap(icon_format(bitmap, 75, 120));
                    currentPt = latLng;
                    fpresenter.setPosition(currentPt.latitude, currentPt.longitude, setLocationIcon);
                    flag[0] = 1;
                }else{
                    fpresenter.removeIcon();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_focus_marka, options);
                    setLocationIcon = BitmapDescriptorFactory.fromBitmap(icon_format(bitmap, 75, 120));
                    currentPt = latLng;
                    fpresenter.setPosition(currentPt.latitude, currentPt.longitude, setLocationIcon);
                }

            }
        });
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpresenter.changeFragment();
            }
        });
        return view;
    }

    @Override
    protected HomeFragmentWithMap2Presenter getPresenter() {
        return new HomeFragmentWithMap2Presenter();
    }


    private void initLocation() {
        locationMode = MyLocationConfiguration.LocationMode.NORMAL;

        mlocationClient = new LocationClient(MainActivity.mainActivity);
        mlistener = new MylocationListener();
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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location_marker, options);
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


    @Override
    public void onResume() {
        super.onResume();
        mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.setVisibility(View.INVISIBLE);
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mlocationClient.stop();
        mapView.onDestroy();
        myOrientationListener.stop();
    }

    public class MylocationListener implements BDLocationListener {
        private boolean isFirstIn = true;

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();
            MyLocationData data = new MyLocationData.Builder()
                    .direction(mCurrentX)
                    .accuracy(bdLocation.getRadius())
                    .latitude(mLatitude)
                    .longitude(mLongitude)
                    .build();
            baiduMap.setMyLocationData(data);
            MyLocationConfiguration configuration
                    = new MyLocationConfiguration(locationMode, true, mIconLocation);
            baiduMap.setMyLocationConfigeration(configuration);

            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.setMapStatus(msu);
                baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
                isFirstIn = false;
            }
        }
    }

    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    @Override
    public void onStart() {
        super.onStart();
        myOrientationListener.start();
    }

    public void getIcons(Object[] objects) {
        icons = (Icon[]) objects;
    }

}