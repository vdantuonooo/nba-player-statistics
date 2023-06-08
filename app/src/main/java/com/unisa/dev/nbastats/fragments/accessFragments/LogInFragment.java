package com.unisa.dev.nbastats.fragments.accessFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.activities.MainActivity;
import com.unisa.dev.nbastats.api.RetrofitNBAStats;
import com.unisa.dev.nbastats.models.LoginModel;


public class LogInFragment extends Fragment implements RetrofitNBAStats.OnPostLogin {

    private View view;

    private ImageView backarrow;
    private NavController navController;
    private RetrofitNBAStats retrofitNBAStats;

    private EditText email, password;

    private RelativeLayout logInButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log_in, container, false);


        backarrow = view.findViewById(R.id.backarrow);
        email = view.findViewById(R.id.editTextEmail);
        password = view.findViewById(R.id.editTextPassword);
        logInButton = view.findViewById(R.id.logInButton);


        retrofitNBAStats = new RetrofitNBAStats();
        retrofitNBAStats.setOnPostLoginListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                retrofitNBAStats.postLogin(emailText, passwordText);
            }
        });

    }

    @Override
    public void onError(Throwable error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostLogin(LoginModel loginModel) {
        Toast.makeText(getContext(), loginModel.getMessage(), Toast.LENGTH_SHORT).show();

        if(loginModel.isSuccess()){
            if(getActivity()!=null) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }else{
            Toast.makeText(getContext(), loginModel.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}