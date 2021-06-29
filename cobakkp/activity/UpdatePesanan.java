package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;
import id.imam.cobakkp.model.ModelPesan;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdatePesanan extends AppCompatActivity {
    Display mDisplay;
    String imagesUri;
    String path;
    Bitmap bitmap;

    int totalHeight;
    int totalWidth;
    public static final int READ_PHONE = 110;
    String file_name = "Screenshot";
    File myPath;

    //----------------------
private TextView id_order,statuspesan;

Button btnupdatepesanan,btnPrint;
ImageView uploadgambarpesanan;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
FirebaseStorage firebaseStorage;
FirebaseUser firebaseUser;
ModelPesan modelPesan;
 StorageReference storageReference;
 Uri filePath;
 Uri url;
    private String TAG;

    //Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pesanan);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        statuspesan = findViewById(R.id.textView9);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference =FirebaseStorage.getInstance().getReference();
        uploadgambarpesanan = findViewById(R.id.updateimagepesanan);
            btnupdatepesanan = findViewById(R.id.butonupdatepesanan);
            id_order = findViewById(R.id.namaupdatepesanan);
            //coba print dri sini
            btnPrint =  findViewById(R.id.btnstruk);
        //----------------------coba print

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();

        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
            }else{
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PHONE);
            }
        }



//-----------------------------------------coba print diatas-----------------------------------------------------------
        Intent intentambildata = getIntent();
        if (intentambildata.hasExtra("id_order")) {
            String id_orderr = intentambildata.getStringExtra("id_order");
            String hargaa = intentambildata.getStringExtra("harga_wisata");

           // statuspesan.setText(Integer.parseInt(String.valueOf(harga)));
            statuspesan.setText(hargaa);
            id_order.setText(id_orderr);
        }


       // id_order.setText(getIntent().getStringExtra("id_order"));
        //id_order.getText().toString();






        //String harga =getIntent().getStringExtra("harga_wisata");

       // String namaa =  getIntent().getStringExtra("id_order");
       //CollectionReference neww = firebaseFirestore.document("order").collection(namaa);
      //  Query query = firebaseFirestore.document("order").collection(namaa).orderBy("status");








        uploadgambarpesanan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent,221);
        }

    });
        btnupdatepesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath != null ){
                    uploadfilekefirestore(filePath);

                }else {
                    Toast.makeText(UpdatePesanan.this,"silahkan pilih gambar",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void uploadfilekefirestore(Uri uri) {
   // final StorageReference filereff = FirebaseStorage.getInstance().getReference(System.currentTimeMillis()+ "."+ getFileExtension(uri));
        final StorageReference filereff = storageReference.child("bukti_bayar/"+System.currentTimeMillis()+"."+getFileExtension(uri));

        filereff.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            filereff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    url = uri;
                    Map<String, Object> user = new HashMap<>();
                    user.put("Image", url.toString());
                    user.put("id_order",id_order.getText().toString());
                    String namaa = getIntent().getStringExtra("id_order");

                    firebaseFirestore.collection("order").document(namaa).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            onBackPressed();

                        }
                    });
                    /**
                    firebaseFirestore.collection("order").document(namaa).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            onBackPressed();

                        }
                    });
                     */
                }
            });
        }
    });
    }

    public String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 221 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            uploadgambarpesanan.setImageURI(filePath);

            }
    }
}