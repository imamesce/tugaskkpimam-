package id.imam.cobakkp.activity;

import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;
import id.imam.cobakkp.fragment.HomeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
   private TextView Namawisata,Harga,Keteranganwisata,TempatWisata;
    private TextView totalbayar;
    private ImageView imageVieworder;
    private Button btnorder;
   private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
     private FirebaseUser firebaseUser;
     private RelativeLayout relativeLayoutt;
  //  private int taskId;
    //  String st;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Harga = findViewById(R.id.textviewharga);
        relativeLayoutt = findViewById(R.id.relative_layout_progress_order);
        imageVieworder =  findViewById(R.id.imageorder);
        TempatWisata =  findViewById(R.id.tempat_wisata);
        totalbayar=findViewById(R.id.texttotalbayar);
        Namawisata = findViewById(R.id.idnamawisata);
        Keteranganwisata = findViewById(R.id.idketeranganwisata);
        btnorder=findViewById(R.id.btn_order);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();


        String getemail= firebaseAuth.getCurrentUser().getEmail();


        Intent intentambildata = getIntent();
        if (intentambildata.hasExtra("harga_wisata")) {
            String hargawisata = intentambildata.getStringExtra("harga_wisata");
            String gambarwisata =  intentambildata.getStringExtra("gambar_wisata");
            String namawisata = intentambildata.getStringExtra("nama_wisata");
            String tempatwisata = intentambildata.getStringExtra("tempat_wisata");
            String keteranganwisata = intentambildata.getStringExtra("keterangan_wisata");
           // String Image_url ="http://192.168.52.105:1337";
            Glide.with(this)
                    .load(gambarwisata)
                    .placeholder(R.drawable.gradient1)
                    .into(imageVieworder);


            //String idorder = intentambildata.getStringExtra("id_order");
            Namawisata.setText(namawisata);
            TempatWisata.setText(tempatwisata);
            Keteranganwisata.setText(keteranganwisata);
            Harga.setText(hargawisata);


        }else {
            Toast.makeText(this,"tidak ada data",Toast.LENGTH_LONG).show();

        }
       // st= getIntent().getExtras().getString("Value");

       // Nama.setText(st);




        btnorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showprogres();


                // String idku = firebaseFirestore.collection("order").getId();

               // String status = totalbayar.getText().toString();
               String harga= Harga.getText().toString();
               //convert ke integer int hargaWisata = Integer.parseInt(harga);
                //
                String namawisataa =  Namawisata.getText().toString();
                String keteranganwisataa = Keteranganwisata.getText().toString();
                String tempatwisataa = TempatWisata.getText().toString();
                Map<String, Object>ordermap = new HashMap<>();
                ordermap.put("dibuat", FieldValue.serverTimestamp());
                ordermap.put("status","false");
                ordermap.put("keterangan","menunggu pembayaran");


                ordermap.put("nama_wisata",namawisataa);
                ordermap.put("keterangan_wisata",keteranganwisataa);
                ordermap.put("tempat_wisata",tempatwisataa);


                // ordermap.put("harga");
                ordermap.put("harga_wisata",harga);
               // ordermap.put("alamat",);
                ordermap.put("alamat_email",getemail);




                    firebaseFirestore.collection("order").add(ordermap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            toast();
                            hideprogres();
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                            finish();
                           // onBackPressed();
                        }
                    });
            }
        });






    }

    private void toast() {
        Toast.makeText(this,"Silahkan lihat Pesanan Anda",Toast.LENGTH_LONG).show();

    }

    private void hideprogres() {
        relativeLayoutt.setVisibility(View.GONE);
    }

    private void showprogres() {

        relativeLayoutt.setVisibility(View.VISIBLE);
    }
}