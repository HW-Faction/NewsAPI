package myapp.newsapp.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class AuthRepository {

    private static final String TAG = "AuthRepository.java";

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<AuthResult> userMutableLiveData;

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<AuthResult> login(Activity activity, String username) {

        Log.d(TAG, "login():: username: " + username);
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
        provider.addCustomParameter("login", username);
        List<String> scopes =
                new ArrayList<String>() {
                    {
                        add("user:email");
                    }
                };
        provider.setScopes(scopes);

        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            Log.d(TAG, "pendingResultTask != null");
            pendingResultTask
                    .addOnSuccessListener(
                            authResult -> {
                                Log.d(TAG, "pendingResultTask successful: ");
                                userMutableLiveData.setValue(authResult);
                            })
                    .addOnFailureListener(
                            e -> {
                                // Handle failure.
                                Log.d(TAG, "pendingResultTask failed: " + e.getMessage());
                            });
        } else {
            firebaseAuth
                    .startActivityForSignInWithProvider(/* activity= */ activity, provider.build())
                    .addOnSuccessListener(
                            authResult -> {
                                Log.d(TAG, "authResult:: name: " + authResult.getUser().getDisplayName());
                                userMutableLiveData.setValue(authResult);
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Log.d(TAG, "authResult:: failure: " + e.getMessage());
                                }
                            });
        }

        return userMutableLiveData;
    }

}
