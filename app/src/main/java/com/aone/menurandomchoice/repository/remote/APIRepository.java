package com.aone.menurandomchoice.repository.remote;

import android.util.Log;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.DataRepository;
import com.aone.menurandomchoice.repository.local.db.SQLiteDatabaseHelper;
import com.aone.menurandomchoice.repository.local.db.SQLiteDatabaseRepository;
import com.aone.menurandomchoice.repository.model.EmptyObject;
import com.aone.menurandomchoice.repository.model.UpdateTime;
import com.aone.menurandomchoice.repository.model.KakaoAddress;
import com.aone.menurandomchoice.repository.model.KakaoAddressResult;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.MenuLocation;
import com.aone.menurandomchoice.repository.model.MenuSearchRequest;
import com.aone.menurandomchoice.repository.model.OwnerInfo;
import com.aone.menurandomchoice.repository.model.SignUpData;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.remote.mapper.MenuMapper;
import com.aone.menurandomchoice.repository.remote.response.JMTCallback;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.repository.remote.response.JMTResponseBody;
import com.aone.menurandomchoice.repository.remote.response.KakaoCallback;
import com.aone.menurandomchoice.utils.NetworkUtil;
import com.google.gson.Gson;
import com.kakao.usermgmt.response.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.internal.EverythingIsNonNull;

public class APIRepository implements APIHelper {

    private static APIRepository apiRepositoryInstance;
    private APICreator apiCreator;

    public static APIRepository getInstance() {
        if(apiRepositoryInstance == null) {
            apiRepositoryInstance = new APIRepository();
        }
        return apiRepositoryInstance;
    }

    private APIRepository() {
        setUp();
    }

    private void setUp() {
        apiCreator = new APICreator();
    }

    @Override
    public void executeLocationSearch(@NonNull String query,
                                      @NonNull NetworkResponseListener<KakaoAddressResult> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            String REST_API_KEY = GlobalApplication.getGlobalApplicationContext().getString(R.string.KAKAO_REST_API_KEY);
            apiCreator.getApiInstance()
                    .getAddress(REST_API_KEY, query)
                    .enqueue(new KakaoCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    @EverythingIsNonNull
    public void requestMenuLocation(@NonNull Map<String, String> queryMap,
                                    @NonNull final NetworkResponseListener<List<MenuLocation>> listener) {

        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getMenuLocation(queryMap)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestStoreDetail(UserAccessInfo userAccessInfo,
                                   @NonNull NetworkResponseListener<StoreDetail> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getStoreDetail(userAccessInfo.getAccessStoreIndex())
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestUpdateTimeFromServer(int storeIdx,
                                  @NonNull NetworkResponseListener<UpdateTime> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .checkStoreUpdated(storeIdx)
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestSignedUpCheck(long userId,
                                     @NonNull NetworkResponseListener<LoginData> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getSignedUpCheckRequest(new OwnerInfo(String.valueOf(userId)))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestSignUp(long userId,
                              @NonNull String accessKey,
                              @NonNull NetworkResponseListener<LoginData> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .getSignUpRequest(new SignUpData(String.valueOf(userId), accessKey))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestMenuList(@NonNull MenuSearchRequest menuSearchRequest,
                                                 @NonNull NetworkResponseListener<List<MenuDetail>> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .createMenuListRequestCall(MenuMapper.createMenuListSearchQueryMap(menuSearchRequest))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

    @Override
    public void requestSaveStoreDetail(@NonNull StoreDetail storeInfo,
                                       @NonNull NetworkResponseListener<EmptyObject> listener) {
        if(NetworkUtil.isNetworkConnecting()) {
            apiCreator.getApiInstance()
                    .createStoreDetailSaveRequestCall(storeInfo, MenuMapper.createRegisteredImageList(storeInfo))
                    .enqueue(new JMTCallback<>(listener));
        } else {
            listener.onError(JMTErrorCode.NETWORK_NOT_CONNECT_ERROR);
        }
    }

}
