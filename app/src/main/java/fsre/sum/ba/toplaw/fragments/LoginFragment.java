package fsre.sum.ba.toplaw.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import fsre.sum.ba.toplaw.HomeActivity;
import fsre.sum.ba.toplaw.R;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailInput, passwordInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Correct way to access TextInputLayout and TextInputEditText
        emailLayout = view.findViewById(R.id.emailTxt);
        passwordLayout = view.findViewById(R.id.passwordTxt);

        // Accessing the nested TextInputEditText inside the layouts
        emailInput = (TextInputEditText) emailLayout.getEditText();
        passwordInput = (TextInputEditText) passwordLayout.getEditText();

        MaterialButton loginButton = view.findViewById(R.id.loginButton);

        // Set click listener for login button
        loginButton.setOnClickListener(v -> loginUser());

        return view;
    }

    private void loginUser() {
        // Ensure proper null-check to avoid potential crashes
        if (emailInput == null || passwordInput == null) {
            Toast.makeText(getContext(), "Error loading input fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            requireActivity().finish(); // Close LoginFragment after login success
                        } else {
                            Toast.makeText(getContext(), "Login failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
        }
    }
}
