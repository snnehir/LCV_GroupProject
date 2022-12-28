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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnKayit;
    private FirebaseAuth mAuth;
    private EditText Kullaniciadi,Parola;
    private String txtKullaniciadi, txtParola;
    private FirebaseUser mUser;
    private void init(){
        mAuth=FirebaseAuth.getInstance();
        Kullaniciadi=(EditText) findViewById(R.id.Kullaniciadi);
        Parola=(EditText) findViewById(R.id.Parola);
        mUser=mAuth.getCurrentUser();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        tanimlama();
       /* if(mUser !=null){
            finish();
            startActivity(new Intent(MainActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }*/


        btnKayit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent KayitOl = new Intent(getApplicationContext(), MainRegister.class);
                startActivity(KayitOl);
            }
        });
    }

    public void tanimlama() {
        btnKayit = findViewById(R.id.btnKayit);
    }
    ///Giriş yap
    public void btnGirisYap(View v) {

        txtKullaniciadi=Kullaniciadi.getText().toString();
        txtParola=Parola.getText().toString();

        if(!TextUtils.isEmpty(txtKullaniciadi)){
            if(!TextUtils.isEmpty(txtParola)){
                mAuth.signInWithEmailAndPassword(txtKullaniciadi, txtParola)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    if((txtKullaniciadi.equals("admin@gmail.com")) && (txtParola.equals("admin123"))){
                                        startActivity(new Intent(MainActivity.this, AdminMain.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                    }else {
                                        startActivity( new Intent( MainActivity.this, MainWelcome.class ).setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
                                    }
                                    Toast.makeText(MainActivity.this, "Başarıyla giriş yaptınız", Toast.LENGTH_SHORT).show();
                                    finish();

                                }else Toast.makeText(MainActivity.this, "Giriş Başarılı değil", Toast.LENGTH_SHORT).show();
                            }
                        });

            }else Toast.makeText(MainActivity.this, "Lütfen geçerli bir şifre giriniz", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(MainActivity.this, "Lütfen geçerli bir mail adresi giriniz", Toast.LENGTH_SHORT).show();

    }
}

