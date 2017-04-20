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


    public void changeFragment() {
        fragmentManager = MainActivity.mainActivity.getFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
//        fTransaction.hide(homeFragmentWithMap);
        fTransaction.hide(detectFragmentWithMap);
        fTransaction.show(detectFragmentNormal);
        fTransaction.commit();
    }

    public void setIcon(Icon[] icons) {
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
            initOverlay(i.getLatitude(), i.getLangitude(),bitmapDescriptor);
        }
    }

    public void initOverlay(double latitude, double langitude, BitmapDescriptor bitmapDescriptor) {
        LatLng latLng = new LatLng(latitude, langitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptor)
                .zIndex(9)
                .draggable(true);
//        markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
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

    public void setLocationMode() {
        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
//        requestLocButton.setText("普通");
//        View.OnClickListener btnClickListener = new View.OnClickListener() {
//            public void onClick(View v) {
//                switch (mCurrentMode) {
//                    case NORMAL:
//                        requestLocButton.setText("跟随");
//                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
//                        baiduMap
//                                .setMyLocationConfigeration(new MyLocationConfiguration(
//                                        mCurrentMode, true, bitmapDescriptor));
//                        break;
//                    case COMPASS:
//                        requestLocButton.setText("普通");
//                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//                        baiduMap
//                                .setMyLocationConfigeration(new MyLocationConfiguration(
//                                        mCurrentMode, true, bitmapDescriptor));
//                        break;
//                    case FOLLOWING:
//                        requestLocButton.setText("罗盘");
//                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
//                        baiduMap
//                                .setMyLocationConfigeration(new MyLocationConfiguration(
//                                        mCurrentMode, true, bitmapDescriptor));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        requestLocButton.setOnClickListener(btnClickListener);

        mLocClient = new LocationClient(MainActivity.mainActivity);
        mLocClient.registerLocationListener(mlistener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public void getIcons() { //start from here

        model.getIcons(new OnSendArrayListener() {

            @Override
            public void sendArray(Object[] objects) {
                getView().getIcons(objects);
            }

        });
    }

    //所有的定位信息都通过接口回调来实现
    public class MyLocationListenner implements BDLocationListener {
        //定位请求回调接口
        private boolean isFirstIn = true;

        //定位请求回调函数,这里面会得到定位信息
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
            //MyLocationData 定位数据,定位数据建造器
            /*
            * 可以通过BDLocation配置如下参数
            * 1.accuracy 定位精度
            * 2.latitude 百度纬度坐标
            * 3.longitude 百度经度坐标
            * 4.satellitesNum GPS定位时卫星数目 getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数
            * 5.speed GPS定位时速度 getSpeed()获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
            * 6.direction GPS定位时方向角度
            *
            *
            * */
            MyLocationData data = new MyLocationData.Builder()
                    .direction(mCurrentX)//设定图标方向
                    .accuracy(bdLocation.getRadius())//getRadius 获取定位精度,默认值0.0f
                    .latitude(bdLocation.getLatitude())//百度纬度坐标
                    .longitude(bdLocation.getLongitude())//百度经度坐标
                    .build();
            //设置定位数据, 只有先允许定位图层后设置数据才会生效，参见 setMyLocationEnabled(boolean)
            baiduMap.setMyLocationData(data);
            //配置定位图层显示方式,三个参数的构造器
            /*
            * 1.定位图层显示模式
            * 2.是否允许显示方向信息
            * 3.用户自定义定位图标
            *
            * */
            MyLocationConfiguration configuration
                    = new MyLocationConfiguration(locationMode, true, mIconLocation);
            //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效，参见 setMyLocationEnabled(boolean)
            baiduMap.setMyLocationConfigeration(configuration);

            //判断是否为第一次定位,是的话需要定位到用户当前位置
            if (isFirstIn) {
                //地理坐标基本数据结构
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.setMapStatus(msu);
                baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
                isFirstIn = false;
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    public void initLocation(Resources res) {
        locationMode = MyLocationConfiguration.LocationMode.NORMAL;

        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        mlocationClient = new LocationClient(MainActivity.mainActivity);
        mlistener = new MyLocationListenner();
        //注册监听器
        mlocationClient.registerLocationListener(mlistener);
        //配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
        LocationClientOption mOption = new LocationClientOption();
        //设置坐标类型
        mOption.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        mOption.setIsNeedAddress(true);
        //设置是否打开gps进行定位
        mOption.setOpenGps(true);
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        int span = 1000;
        mOption.setScanSpan(span);
        //设置 LocationClientOption
        mlocationClient.setLocOption(mOption);
        mlocationClient.start();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.location_marker, options);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 100;
        int newHeight = 100;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap = rotateBitmap(bitmap, 330);
        mIconLocation = BitmapDescriptorFactory.fromBitmap(bitmap);
        myOrientationListener = new MyOrientationListener(context);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }
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
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
    public void a(final Resources res)
    {
        final int[] flag = {0};
        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(flag[0] == 0){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.icon_focus_marka, options);
                    setLocationIcon = BitmapDescriptorFactory.fromBitmap(icon_format(bitmap, 75, 120));
                    currentPt = latLng;
                    setPosition(currentPt.latitude, currentPt.longitude, setLocationIcon);
                    flag[0] = 1;
                }else{
                    removeIcon();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.icon_focus_marka, options);
                    setLocationIcon = BitmapDescriptorFactory.fromBitmap(icon_format(bitmap, 75, 120));
                    currentPt = latLng;
                    setPosition(currentPt.latitude, currentPt.longitude, setLocationIcon);
                }

            }
        });
    }
    public void removeIcon(){
        markerA.remove();
    }

    public void setPosition(double latitude, double langitude, BitmapDescriptor bitmapDescriptor){
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        /**
         * 缩放等级
         */
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(17.0f);
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
        initOverlay(latitude,langitude, bitmapDescriptor);
    }

}
