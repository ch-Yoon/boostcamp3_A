package com.aone.menurandomchoice.views.ownerlogin;

import android.app.Activity;
import android.content.Intent;

import com.aone.menurandomchoice.views.base.BaseContract;

import androidx.annotation.Nullable;

public interface OwnerLoginContract {

    interface View extends BaseContract.View {

        Activity getActivity();

    }

    interface Presenter extends BaseContract.Presenter<OwnerLoginContract.View> {

        void handlingLoggedInAccount();

        void handlingDeviceKaKaoAccountLogin();

        void handlingOtherKaKaoAccountLogin();

        boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data);
    }

}
