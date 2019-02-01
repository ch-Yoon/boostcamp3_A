package com.aone.menurandomchoice.views.ownersignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.databinding.ActivityOwnerSignUpBinding;
import com.aone.menurandomchoice.views.base.BaseActivity;
import com.aone.menurandomchoice.views.ownerdetail.OwnerDetailActivity;
import com.aone.menurandomchoice.views.ownerlogin.OwnerLoginActivity;

public class OwnerSignUpActivity
        extends BaseActivity<ActivityOwnerSignUpBinding, OwnerSignUpContract.View, OwnerSignUpContract.Presenter>
        implements OwnerSignUpContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpBackArrow();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void setUpBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    @Override
    protected ActivityOwnerSignUpBinding setUpDataBinding() {
        ActivityOwnerSignUpBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_owner_sign_up);
        dataBinding.setActivity(this);

        return dataBinding;
    }

    @NonNull
    @Override
    protected OwnerSignUpContract.Presenter setUpPresenter() {
        return new OwnerSignUpPresenter();
    }

    @NonNull
    @Override
    protected OwnerSignUpContract.View getView() {
        return this;
    }

    @Override
    protected void onSaveInstanceStateToBundle(@NonNull Bundle outState) {
    }

    public void onSignUpRequestClick(View view) {
        long userId = getIntent().getLongExtra(OwnerLoginActivity.EXTRA_USER_ID, -1);
        if(userId != -1) {
            String accessKey = getDataBinding().activityOwnerSignUpEtAccessKey.getText().toString();
            getPresenter().requestSignUp(userId, accessKey);
        }
    }

    @Override
    public void moveToOwnerDetailActivity(long userId) {
        Intent ownerDetailIntent = new Intent(OwnerSignUpActivity.this, OwnerDetailActivity.class);
        startActivity(ownerDetailIntent);
        finish();
    }

    @Override
    public void showToastMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
