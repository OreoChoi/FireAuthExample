package com.kcejjh.fireauthexample.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kcejjh.fireauthexample.R;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 1009;
    private String TAG = this.getClass().getName();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Button btnLogin;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * jhChoi - 20200918
     * Login 여부를 확인 후 Ui를 변경합니다.
     */
    private void updateUI(FirebaseUser mUser) {
        if (mUser != null) {
            btnLogin.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                toast("로그인 성공");
            } else {
                toast("로그인 실패");
            }
        }
    }

    /**
     * jhChoi - 20200918
     * 뷰의 id를 find 합니다.
     */
    private void findView() {
        btnLogin = findViewById(R.id.btn_login);
        btnLogout = findViewById(R.id.btn_logout);
    }

    /**
     * jhChoi - 20200918
     * 이벤트를 셋팅합니다.
     */
    private void setEvent() {
        btnLogin.setOnClickListener((v) -> {
            showLoginUI();
        });

        btnLogout.setOnClickListener((v) -> {
            AuthUI.getInstance().signOut(this).addOnCompleteListener((task) -> {
                toast("로그아웃 되었습니다.");
                updateUI(null);
            });
        });
    }

    /**
     * jhChoi - 20200918
     * Login을 위한 UI를 표시합니다.
     */
    private void showLoginUI() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }
}
