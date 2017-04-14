function load_data(){
    var url = "http://10.10.160.69:8888/";
     $.get(url + new Date().getTime(), function(data){
         $("#feeds").html(data);
         var dataObj=eval('('+data+')');
         $("#username").text(dataObj.username);
         $("#location_info").text(dataObj.location_info);
         $("#release_time").text(dataObj.release_time);
         $("#video_info").text(dataObj.video_info);
         $("#repost_num").text(dataObj.repost_num);
         $("#commit_num").text(dataObj.commit_num);
         $("#like_num").text(dataObj.like_num);
         $("#satellite").text(dataObj.satellite);
         document.getElementById('find_icon').src = dataObj.find_icon;
         document.getElementById('avatar').src = dataObj.avatar;
         document.getElementById('member_icon').src = dataObj.member_icon;
         document.getElementById('video_url').src = dataObj.video_url;
     });
}