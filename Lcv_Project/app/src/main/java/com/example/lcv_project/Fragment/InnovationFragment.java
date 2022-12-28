package com.example.lcv_project.Fragment;

//import android.graphics.Rect;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.lcv_project.Adapter.KullaniciAdapter;
import com.example.lcv_project.Models.User;
import com.example.lcv_project.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.Query;


        import java.util.ArrayList;


public class InnovationFragment extends Fragment {
    private View v;
    private FirebaseFirestore mFirestore;
    private RecyclerView mRecyclerView;
    private KullaniciAdapter mAdapter;
    private FirebaseUser mUser;
    private Query mQuery;
    private ArrayList<User> mKullaniciList;
    private User mKullanici;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate( R.layout.fragment_invitation, container, false );


      mUser=FirebaseAuth.getInstance().getCurrentUser();
      mFirestore=FirebaseFirestore.getInstance();

       mKullaniciList= new ArrayList<>();
       mRecyclerView=v.findViewById( R.id.innovation_fragment_recyclerView);
       mRecyclerView.setHasFixedSize( true );
       mRecyclerView.setLayoutManager( new LinearLayoutManager(v.getContext(),LinearLayoutManager.VERTICAL, false ) );
      mQuery=mFirestore.collection( "Kullanicilar" );
         mQuery.addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Toast.makeText( v.getContext(), error.getMessage(), Toast.LENGTH_SHORT ).show();
                        return;
                    }
                    if (value != null) {
                        mKullaniciList.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            mKullanici = snapshot.toObject( User.class );
                            System.out.println( "---------------------: " + mKullanici.getUserId() );
                            assert mKullanici != null;
                            System.out.println( "---------------------: " + mUser.getUid() );
                            if (!mKullanici.getUserId().equals( mUser.getUid() ))
                                mKullaniciList.add( mKullanici );
                        }
                        mRecyclerView.addItemDecoration( new LinearDecoration( 20, mKullaniciList.size() ) );
                        mAdapter = new KullaniciAdapter( mKullaniciList, v.getContext(),mUser.getUid() );
                        mRecyclerView.setAdapter( mAdapter );
                    }
                }
        );
        return v;
    }



    class LinearDecoration extends RecyclerView.ItemDecoration {
        private int boslukMiktari;
        private int veriSayisi;

        public LinearDecoration(int boslukMiktari,int veriSayisi){
            this.boslukMiktari=boslukMiktari;
            this.veriSayisi=veriSayisi;
        }
      @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state){
            int pos=parent.getChildAdapterPosition( view );
            if (pos!=(veriSayisi-1))
                outRect.bottom=boslukMiktari;
        }

    }}