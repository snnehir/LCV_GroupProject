package com.example.lcv_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lcv_project.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainRegister extends AppCompatActivity {
    Button btnGiriseDon;

    private FirebaseUser mUser;
    private User mKulanici;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private EditText KayitKullaniciadi;
    private EditText KayitParola;
    private String txtKullaniciAdi, txtKullaniciParola;

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        KayitKullaniciadi = (EditText) findViewById(R.id.KayitKullaniciadi);
        KayitParola = (EditText) findViewById(R.id.KayitParola);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        init();

        // Girişe Dön butonu yönlendirme

        tanimlama();

        btnGiriseDon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent GiriseDon = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(GiriseDon);

            }
        });
    }

    public void tanimlama() {
        btnGiriseDon = findViewById(R.id.btnGiriseDon);
    }

    public void onClick(View view) {
        startActivity(new Intent("com.example.w101.third"));
    }

    // Kayıt ol butonu
    public void btnKaydet(View v) {
        txtKullaniciAdi = KayitKullaniciadi.getText().toString();
        txtKullaniciParola = KayitParola.getText().toString();

        if (!TextUtils.isEmpty(txtKullaniciAdi)) {
            if (!TextUtils.isEmpty(txtKullaniciParola)) {
                mAuth.createUserWithEmailAndPassword(txtKullaniciAdi, txtKullaniciParola)
                        .addOnCompleteListener(MainRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mUser = mAuth.getCurrentUser();
                                    if (mUser != null) {
                                        mKulanici = new User(txtKullaniciAdi, txtKullaniciParola, mUser.getUid(), "default");
                                        mFirestore.collection("Kullanicilar").document(mUser.getUid())
                                                .set(mKulanici)
                                                .addOnCompleteListener(MainRegister.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(MainRegister.this, "Başarıyla kayıt oldunuz", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                            startActivity(new Intent(MainRegister.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                        } else
                                                            Toast.makeText(MainRegister.this, "Başarılıyla Kayıt", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }

                                } else
                                    Toast.makeText(MainRegister.this, "Geçerli e-mail ve 8 karakterli parola girmelisiniz", Toast.LENGTH_SHORT).show();

                            }
                        });
            } else
                Toast.makeText(MainRegister.this, "Lütfen 8 karakterli bir parola oluşturunuz", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(MainRegister.this, "Lütfen geçerli mail adresi giriniz", Toast.LENGTH_SHORT).show();
    }

}

