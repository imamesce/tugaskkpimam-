package id.imam.cobakkp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import id.imam.cobakkp.activity.LoginActivity;
import id.imam.cobakkp.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class PengaturanFragment extends Fragment {
    private FirebaseAuth mAuth;
Button buttonLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_pengaturan,container,false);
        }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLogout = (Button) view.findViewById(R.id.btn_logout);
        mAuth=FirebaseAuth.getInstance();


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                //     Intent intent = new Intent(getActivity(), MainActivity.class);
                //   startActivity(intent);
             //   SharedPreferences sharedPreferences= requireActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
               // sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });



    }
}