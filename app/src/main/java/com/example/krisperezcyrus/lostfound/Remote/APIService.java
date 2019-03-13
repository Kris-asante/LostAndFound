package com.example.krisperezcyrus.lostfound.Remote;


import com.example.krisperezcyrus.lostfound.Model.MyResponse;
import com.example.krisperezcyrus.lostfound.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAxxVisgo:APA91bFT5PZDucRSNtJoHwHTR9pwkvakCKJnB0Wp6kwFuBxwYBbVC_pjzrXzsqJ2mKqVdr0ZSrUAAysOln43MCSOeqf06oD172BsVoqQ6oMKHwycgeeJH-YjIoMYMKTk9hZekgXwxskf"



    })
   @POST("fsm/send")

   Call<MyResponse> sendNotification(@Body Sender body);




}
