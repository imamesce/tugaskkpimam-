package id.imam.cobakkp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import id.imam.cobakkp.activity.DetailActivity;
import id.imam.cobakkp.activity.PesananAnda;
import id.imam.cobakkp.activity.ProfileActivity;
import id.imam.cobakkp.R;
import id.imam.cobakkp.adapter.AdapterPesanan;
import id.imam.cobakkp.adapter.Adapterwisata;
import id.imam.cobakkp.apiclient.ApiInterface;
import id.imam.cobakkp.apiclient.RetrofitInstance;
import id.imam.cobakkp.model.ModelPesan;
import id.imam.cobakkp.model.ModelWisata;
import id.imam.cobakkp.model.ModelWisatabaru;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
CircleImageView circleImageView;
TextView pesananAnda;
RecyclerView recyclerViewFragmentHome;
List<ModelWisata> wisataList;
ApiInterface apiInterface;
Adapterwisata adapterwisata;
    TextView display;
     FirebaseAuth mauth;
     FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
     StorageReference storageReference;
     Adapterwisata adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_home, container, false);


        //   private void openWhatsap() {
        //     try {
        //   String mobile = "6287872017197";
        //     String msg = "Haloo,,saya ingin bertanya...";
        //       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mobile + "&text=" + msg)));
        //     }catch (Exception e){
        //whatsapp app not install
        //       }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mauth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        pesananAnda = view.findViewById(R.id.lihatpesanan);
        circleImageView = (CircleImageView) view.findViewById(R.id.imageView3);
        display = (TextView) view.findViewById(R.id.textView5);
        recyclerViewFragmentHome = (RecyclerView) view.findViewById(R.id.recyclerfragmenthome);
       //       String emaill = firebaseAuth.getCurrentUser().getEmail();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        chekgambarprofil();

      pesananAnda.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent= new Intent(getActivity(), PesananAnda.class);
              startActivity(intent);
          }
      });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofile();
            }
        });


        Query query2 = firebaseFirestore.collection("produk").whereEqualTo("keterangan","tersedia");

        FirestoreRecyclerOptions<ModelWisatabaru> options = new FirestoreRecyclerOptions.Builder<ModelWisatabaru>()
                .setQuery(query2,ModelWisatabaru.class)
                .build();


        adapter = new Adapterwisata(options);
        recyclerViewFragmentHome.setAdapter(adapter);
        recyclerViewFragmentHome.setHasFixedSize(true);
        recyclerViewFragmentHome.setNestedScrollingEnabled(false);
        recyclerViewFragmentHome.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter.setOnItemClickListener(new Adapterwisata.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ModelWisatabaru modelWisata = documentSnapshot.toObject(ModelWisatabaru.class);

                String id = documentSnapshot.getId();

                Toast.makeText(getActivity(), "id" + id, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id_wisata",id);
                intent.putExtra("nama_wisata",modelWisata.getNama_wisata());
                intent.putExtra("tempat_wisata",modelWisata.getTempat_wisata());
                intent.putExtra("gambar_wisata",modelWisata.getGambar_wisata());
                intent.putExtra("deskripsi_wisata",modelWisata.getDekripsi_wisata());
                intent.putExtra("keterangan_wisata",modelWisata.getKeterangan_wisata());
                intent.putExtra("harga_wisata",modelWisata.getHarga_wisata());

                startActivity(intent);
            }
        });
    }
    private void updateprofile() {
        Intent intent= new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }


    private void chekgambarprofil() {
        StorageReference profileRef = storageReference.child("users/"+ Objects.requireNonNull(mauth.getCurrentUser()).getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(circleImageView);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}



