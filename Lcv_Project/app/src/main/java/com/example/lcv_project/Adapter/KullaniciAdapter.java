package com.example.lcv_project.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcv_project.Models.User;
import com.example.lcv_project.R;
import com.example.lcv_project.Models.Invitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.KullaniciHolder> {

    private ArrayList<User>mKullaniciList;
    private Context mContext;
    private View v;
    private int kPos;
    private User mKullanici;

    private Dialog MesajDialog;
    private ImageButton ImgIptal;
    private ImageButton ImgSend;
    private CircleImageView ImgProfil;
    private EditText EditMesaj;
    private TextView txtIsim;

    private String txtMesaj;
    private Window MsjWindow;

    private FirebaseFirestore mFireStore;
    private DocumentReference mRef;
    private String mUID, kanalId, InnovationDocId;
    private Invitation newInnovation;
    private HashMap<String,Object> mData;
    public KullaniciAdapter(ArrayList<User> mKullaniciList, Context mContext, String mUID){
        this.mKullaniciList=mKullaniciList;
        this.mContext=mContext;
        mFireStore=FirebaseFirestore.getInstance();
        this.mUID=mUID;
    }

    @NonNull
    @Override
    public KullaniciHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        v= LayoutInflater.from( mContext ).inflate( R.layout.user_item,parent,false );
    return new KullaniciHolder( v );

    }

    @Override
    public void onBindViewHolder(@NonNull KullaniciHolder holder, int position) {
        mKullanici=mKullaniciList.get( position );
        holder.kullaniciIsmi.setText( mKullanici.getUserName() );
        if(mKullanici.getAccountType().equals( "default" ))
            holder.kullaniciProfil.setImageResource( R.mipmap.ic_launcher );
        else
            Picasso.get().load( mKullanici.getAccountType() ).resize( 66,66 ).into( holder.kullaniciProfil );
   holder.itemView.setOnClickListener( new View.OnClickListener() {
       @Override
       public void onClick(View view) {
            kPos=holder.getAdapterPosition();
            if (kPos!=RecyclerView.NO_POSITION){
                mRef=mFireStore.collection( "Kullanicilar" ).document(mUID).collection( "Kanal" ).document(mKullaniciList.get( kPos ).getUserId());
                mRef.get()
                        .addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    // new innovation
                                }else
                                    mesajGonderDialog( mKullaniciList.get( kPos ) );
                            }
                        } );
            }

       }
   } );
    }

    private void mesajGonderDialog(final User kullanici) {
        MesajDialog=new Dialog( mContext );
        MesajDialog.getWindow().setBackgroundDrawableResource( android.R.color.transparent );
        MsjWindow=MesajDialog.getWindow();
        MsjWindow.setGravity( Gravity.CENTER );
        MesajDialog.setContentView( R.layout.new_invitation_send);

        ImgIptal=MesajDialog.findViewById( R.id.innovation_iptal );
        ImgSend=MesajDialog.findViewById( R.id.innovation_send);
        ImgProfil=MesajDialog.findViewById( R.id.innovation_dialog_imgKullaniciProfil );
        EditMesaj=MesajDialog.findViewById( R.id.innovation_editmsg );
        txtIsim=MesajDialog.findViewById( R.id.innovation_dialog_kullanici_name );
        txtIsim.setText( kullanici.getUserName() );
    if(kullanici.getAccountType().equals("default"))
            ImgProfil.setImageResource( R.mipmap.ic_launcher );

        else
            Picasso.get().load( kullanici.getAccountType() ).resize( 126,126 ).into( ImgProfil );
         ImgIptal.setOnClickListener( new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 MesajDialog.dismiss();
             }
         } );
        ImgSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtMesaj=EditMesaj.getText().toString();
                if(!TextUtils.isEmpty( txtMesaj )){
                    kanalId= UUID.randomUUID().toString();
                    newInnovation=new Invitation(kanalId,mUID);
                    mFireStore.collection( "NewNotification" ).document(kullanici.getUserId()).collection( "Notifications" ).document(mUID)
                            .set( newInnovation )
                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //innovation
                                        InnovationDocId=UUID.randomUUID().toString();
                                        mData=new HashMap<>();
                                        mData.put( "innovationDetail" ,txtMesaj);
                                        mData.put( "Sender",mUID );
                                        mData.put( "Receiver",kullanici.getUserId() );
                                        mData.put( "InnovationType","text" );
                                        mData.put( "SendDate", FieldValue.serverTimestamp() );
                                        mData.put( "InnovationId",InnovationDocId );
                                        mFireStore.collection( "NewInnovation" ).document(kanalId).collection( "Innovations" ).document(InnovationDocId)
                                                .set( mData )
                                                .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText( mContext,"Your invitation has been sent",Toast.LENGTH_SHORT ).show();
                                                           if(MesajDialog.isShowing())
                                                               MesajDialog.dismiss();
                                                        }else
                                                            Toast.makeText( mContext,task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                                                    }
                                                } );

                                    } else
                                        Toast.makeText( mContext, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();

                                }  } );

                }else
                    Toast.makeText( mContext, "write your innovation",Toast.LENGTH_SHORT ).show();
            }
        } );
        MsjWindow.setLayout( ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT );
        MesajDialog.show();
    }


    @Override
    public int getItemCount() {
        return mKullaniciList.size();
    }

    class KullaniciHolder extends RecyclerView.ViewHolder{
        TextView kullaniciIsmi;
        CircleImageView kullaniciProfil;
        public KullaniciHolder(@NonNull View itemView){
            super(itemView);
            kullaniciIsmi=itemView.findViewById( R.id.KayitKullaniciadi);
            kullaniciProfil=itemView.findViewById( R.id.kullanici_item_imgKullaniciProfili);
        }
    }
}
