package com.unisa.dev.nbastats.fragments.accessFragments;

import android.content.Intent;
import android.media.Image;
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
import com.unisa.dev.nbastats.models.MessageModel;

import java.util.Objects;

public class SignUpFragment extends Fragment implements RetrofitNBAStats.OnAccountSigned {

    private View view;
    private ImageView backarrow;
    private NavController navController;
    private RetrofitNBAStats retrofitNBAStats;
    private EditText email, password;
    private RelativeLayout signUpButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        backarrow = view.findViewById(R.id.backarrow);
        email = view.findViewById(R.id.editTextEmail);
        password = view.findViewById(R.id.editTextPassword);
        signUpButton = view.findViewById(R.id.signUpButton);

        retrofitNBAStats = new RetrofitNBAStats();

        retrofitNBAStats.setOnAccountSigned(this);

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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                retrofitNBAStats.postCreateAccount(emailText, passwordText);
            }
        });

    }

    @Override
    public void onError(Throwable error) {
        if(getContext()!=null) {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAccountSigned(MessageModel messageModel) {
        if(Objects.equals(messageModel.getMessage(), "Esiste gia un utente con questa email.")
                || Objects.equals(messageModel.getMessage(), "Errore durante la creazione dell'account.")){
            Toast.makeText(getContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getContext(), messageModel.getMessage() , Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);

            if(getActivity()!=null) {
                getActivity().finish();
            }
        }

    }
}