package com.example.jimappv2a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimappv2a.Callback.INombreCallbackListener;
import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.Model.AlumnoModel;
import com.example.jimappv2a.Preferences.SaveSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity /*implements INombreCallbackListener*/ {


    DatabaseReference dniref, claveref;
    private Unbinder unbinder;
    String dni;
    String clave;
    private Intent HomeActivity;
    int i = 0;
    int bandera = -1;

    //private INombreCallbackListener nombreCallbackListener;

/*
    public LoginActivity() {
        nombreCallbackListener = this;
    }

 */

    @BindView(R.id.loginForm)
    ConstraintLayout loginForm;
    @BindView(R.id.login_dni)
    TextView txt_dni;
    @BindView(R.id.login_password)
    TextView txt_password;
    @BindView(R.id.loginBtn)
    Button btn_login;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        HomeActivity = new Intent(this, com.example.jimappv2a.HomeActivity.class);

        dniref = FirebaseDatabase.getInstance().getReference(Common.CURSOS_REF);

        claveref = FirebaseDatabase.getInstance().getReference(Common.CLAVE_REF);

        unbinder = ButterKnife.bind(this);

        loginProgress.setVisibility(View.INVISIBLE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.INVISIBLE);

                dni = txt_dni.getText().toString();
                clave = txt_password.getText().toString();

                if (dni.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    btn_login.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                } else {
                    RevisarDNIyClave(dni, clave);
                }

            }
        });


    }


    private void RevisarDNIyClave(final String numdni, final String password) {


        Query query = dniref.child("1A").child(Common.ALUMNOS_REF).orderByChild("dni")
                .equalTo(numdni).limitToLast(1);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //bandera += 1;
                    for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                        Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                        Common.selectedAlumno.setKey(itemSnapShot.getKey());
                        Common.currentNombre = Common.selectedAlumno.getNombre();
                    }
                    //nombreCallbackListener.onCallback(Common.selectedAlumno.getNombre());


                    Query tarea = claveref.orderByChild("claveapp").equalTo(password);

                    tarea.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(LoginActivity.this, "Bienvenido " + Common.currentNombre, Toast.LENGTH_SHORT).show();
                                loginProgress.setVisibility(View.INVISIBLE);
                                btn_login.setVisibility(View.VISIBLE);


                                ////////////////////////////////////////////////////////////////////
                                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                        "Datos", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("numdni", Common.selectedAlumno.getDni());
                                editor.putString("curso_id", "1A");
                                editor.apply();
                                Common.currentCurso = "1A";
                                ////////////////////////////////////////////////////////////////////


                                startActivity(HomeActivity);
                                finish();


                            } else {

                                Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                btn_login.setVisibility(View.VISIBLE);
                                loginProgress.setVisibility(View.INVISIBLE);
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            btn_login.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.INVISIBLE);

                        }
                    });

                } else {


                    Query query = dniref.child("1B").child(Common.ALUMNOS_REF).orderByChild("dni")
                            .equalTo(numdni).limitToLast(1);


                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                //bandera += 1;
                                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                                    Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                                    Common.selectedAlumno.setKey(itemSnapShot.getKey());
                                    Common.currentNombre = Common.selectedAlumno.getNombre();
                                }
                                //nombreCallbackListener.onCallback(Common.selectedAlumno.getNombre());


                                Query tarea = claveref.orderByChild("claveapp").equalTo(password);

                                tarea.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(LoginActivity.this, "Bienvenido " + Common.currentNombre, Toast.LENGTH_SHORT).show();
                                            loginProgress.setVisibility(View.INVISIBLE);
                                            btn_login.setVisibility(View.VISIBLE);


                                            ////////////////////////////////////////////////////////////////////
                                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                                    "Datos", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("numdni", Common.selectedAlumno.getDni());
                                            editor.putString("curso_id", "1B");
                                            editor.apply();
                                            Common.currentCurso = "1B";
                                            ////////////////////////////////////////////////////////////////////


                                            startActivity(HomeActivity);
                                            finish();


                                        } else {

                                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                            btn_login.setVisibility(View.VISIBLE);
                                            loginProgress.setVisibility(View.INVISIBLE);
                                        }

                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        btn_login.setVisibility(View.VISIBLE);
                                        loginProgress.setVisibility(View.INVISIBLE);

                                    }
                                });

                            } else {

                                Query query = dniref.child("2A").child(Common.ALUMNOS_REF).orderByChild("dni")
                                        .equalTo(numdni).limitToLast(1);


                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            //bandera += 1;
                                            for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                                                Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                                                Common.selectedAlumno.setKey(itemSnapShot.getKey());
                                                Common.currentNombre = Common.selectedAlumno.getNombre();
                                            }
                                            //nombreCallbackListener.onCallback(Common.selectedAlumno.getNombre());


                                            Query tarea = claveref.orderByChild("claveapp").equalTo(password);

                                            tarea.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        Toast.makeText(LoginActivity.this, "Bienvenido " + Common.currentNombre, Toast.LENGTH_SHORT).show();
                                                        loginProgress.setVisibility(View.INVISIBLE);
                                                        btn_login.setVisibility(View.VISIBLE);


                                                        ////////////////////////////////////////////////////////////////////
                                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                                                "Datos", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("numdni", Common.selectedAlumno.getDni());
                                                        editor.putString("curso_id", "2A");
                                                        editor.apply();
                                                        Common.currentCurso = "2A";
                                                        ////////////////////////////////////////////////////////////////////


                                                        startActivity(HomeActivity);
                                                        finish();


                                                    } else {

                                                        Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                                        btn_login.setVisibility(View.VISIBLE);
                                                        loginProgress.setVisibility(View.INVISIBLE);
                                                    }

                                                }


                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                    btn_login.setVisibility(View.VISIBLE);
                                                    loginProgress.setVisibility(View.INVISIBLE);

                                                }
                                            });

                                        } else {

                                            Query query = dniref.child("2B").child(Common.ALUMNOS_REF).orderByChild("dni")
                                                    .equalTo(numdni).limitToLast(1);


                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        //bandera += 1;
                                                        for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                                                            Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                                                            Common.selectedAlumno.setKey(itemSnapShot.getKey());
                                                            Common.currentNombre = Common.selectedAlumno.getNombre();
                                                        }
                                                        //nombreCallbackListener.onCallback(Common.selectedAlumno.getNombre());


                                                        Query tarea = claveref.orderByChild("claveapp").equalTo(password);

                                                        tarea.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    Toast.makeText(LoginActivity.this, "Bienvenido " + Common.currentNombre, Toast.LENGTH_SHORT).show();
                                                                    loginProgress.setVisibility(View.INVISIBLE);
                                                                    btn_login.setVisibility(View.VISIBLE);


                                                                    ////////////////////////////////////////////////////////////////////
                                                                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                                                            "Datos", MODE_PRIVATE);
                                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                    editor.putString("numdni", Common.selectedAlumno.getDni());
                                                                    editor.putString("curso_id", "2B");
                                                                    editor.apply();
                                                                    Common.currentCurso = "2B";
                                                                    ////////////////////////////////////////////////////////////////////


                                                                    startActivity(HomeActivity);
                                                                    finish();


                                                                } else {

                                                                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                                                    btn_login.setVisibility(View.VISIBLE);
                                                                    loginProgress.setVisibility(View.INVISIBLE);
                                                                }

                                                            }


                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                btn_login.setVisibility(View.VISIBLE);
                                                                loginProgress.setVisibility(View.INVISIBLE);

                                                            }
                                                        });

                                                    } else {


                                                        Query query = dniref.child("3A").child(Common.ALUMNOS_REF).orderByChild("dni")
                                                                .equalTo(numdni).limitToLast(1);


                                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    //bandera += 1;
                                                                    for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                                                                        Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                                                                        Common.selectedAlumno.setKey(itemSnapShot.getKey());
                                                                        Common.currentNombre = Common.selectedAlumno.getNombre();
                                                                    }
                                                                    //nombreCallbackListener.onCallback(Common.selectedAlumno.getNombre());


                                                                    Query tarea = claveref.orderByChild("claveapp").equalTo(password);

                                                                    tarea.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                Toast.makeText(LoginActivity.this, "Bienvenido " + Common.currentNombre, Toast.LENGTH_SHORT).show();
                                                                                loginProgress.setVisibility(View.INVISIBLE);
                                                                                btn_login.setVisibility(View.VISIBLE);


                                                                                ////////////////////////////////////////////////////////////////////
                                                                                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                                                                        "Datos", MODE_PRIVATE);
                                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                                editor.putString("numdni", Common.selectedAlumno.getDni());
                                                                                editor.putString("curso_id", "3A");
                                                                                editor.apply();
                                                                                Common.currentCurso = "3A";
                                                                                ////////////////////////////////////////////////////////////////////


                                                                                startActivity(HomeActivity);
                                                                                finish();


                                                                            } else {

                                                                                Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                                                                btn_login.setVisibility(View.VISIBLE);
                                                                                loginProgress.setVisibility(View.INVISIBLE);
                                                                            }

                                                                        }


                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                            Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            btn_login.setVisibility(View.VISIBLE);
                                                                            loginProgress.setVisibility(View.INVISIBLE);

                                                                        }
                                                                    });

                                                                } else {


                                                                    Query query = dniref.child("3B").child(Common.ALUMNOS_REF).orderByChild("dni")
                                                                            .equalTo(numdni).limitToLast(1);


                                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                //bandera += 1;
                                                                                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                                                                                    Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                                                                                    Common.selectedAlumno.setKey(itemSnapShot.getKey());
                                                                                    Common.currentNombre = Common.selectedAlumno.getNombre();
                                                                                }
                                                                                //nombreCallbackListener.onCallback(Common.selectedAlumno.getNombre());


                                                                                Query tarea = claveref.orderByChild("claveapp").equalTo(password);

                                                                                tarea.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                        if (dataSnapshot.exists()) {
                                                                                            Toast.makeText(LoginActivity.this, "Bienvenido " + Common.currentNombre, Toast.LENGTH_SHORT).show();
                                                                                            loginProgress.setVisibility(View.INVISIBLE);
                                                                                            btn_login.setVisibility(View.VISIBLE);


                                                                                            ////////////////////////////////////////////////////////////////////
                                                                                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                                                                                                    "Datos", MODE_PRIVATE);
                                                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                                            editor.putString("numdni", Common.selectedAlumno.getDni());
                                                                                            editor.putString("curso_id", "3B");
                                                                                            editor.apply();
                                                                                            Common.currentCurso = "3B";
                                                                                            ////////////////////////////////////////////////////////////////////


                                                                                            startActivity(HomeActivity);
                                                                                            finish();


                                                                                        } else {

                                                                                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                                                                            btn_login.setVisibility(View.VISIBLE);
                                                                                            loginProgress.setVisibility(View.INVISIBLE);
                                                                                        }

                                                                                    }


                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                                        Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                        btn_login.setVisibility(View.VISIBLE);
                                                                                        loginProgress.setVisibility(View.INVISIBLE);

                                                                                    }
                                                                                });

                                                                            } else {
                                                                                Toast.makeText(LoginActivity.this, "El alumno no existe", Toast.LENGTH_SHORT).show();
                                                                                btn_login.setVisibility(View.VISIBLE);
                                                                                loginProgress.setVisibility(View.INVISIBLE);

                                                                            }


                                                                        }


                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                            Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            btn_login.setVisibility(View.VISIBLE);
                                                                            loginProgress.setVisibility(View.INVISIBLE);

                                                                        }

                                                                    });


                                                                }


                                                            }


                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                                btn_login.setVisibility(View.VISIBLE);
                                                                loginProgress.setVisibility(View.INVISIBLE);

                                                            }

                                                        });


                                                    }


                                                }


                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                    btn_login.setVisibility(View.VISIBLE);
                                                    loginProgress.setVisibility(View.INVISIBLE);

                                                }

                                            });


                                        }


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        btn_login.setVisibility(View.VISIBLE);
                                        loginProgress.setVisibility(View.INVISIBLE);

                                    }

                                });


                            }


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            btn_login.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.INVISIBLE);

                        }

                    });


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                btn_login.setVisibility(View.VISIBLE);
                loginProgress.setVisibility(View.INVISIBLE);

            }

        });


    }


    private void Preferencias(String numdni, String curso_id) {

        Common.currentCurso = curso_id;
        Query querydos = dniref.child(Common.currentCurso).child(Common.ALUMNOS_REF).orderByChild("dni")
                .equalTo(numdni).limitToLast(1);

        querydos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                        Common.selectedAlumno = itemSnapShot.getValue(AlumnoModel.class);
                        Common.selectedAlumno.setKey(itemSnapShot.getKey());
                    }
                    Intent intent = HomeActivity;
                    intent.putExtra(Common.IS_OPEN_ACTIVITY_NEW_AVISO,getIntent().getBooleanExtra(Common.IS_OPEN_ACTIVITY_NEW_AVISO,false));
                    startActivity(intent);
                    finish();
                    ////////////////////////////////////
                    //startActivity(HomeActivity);
                    //finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(
                "Datos", MODE_PRIVATE);
        String numdni = sharedPreferences.getString("numdni", "");
        String curso_id = sharedPreferences.getString("curso_id", "");


        if (!numdni.isEmpty() && !curso_id.isEmpty()) {

            // call Login Activity
            Preferencias(numdni, curso_id);

        } else {
            loginForm.setVisibility(View.VISIBLE);
        }
    }

    /*
    @Override
    public void onCallback(String nombre) {

        Common.Nombre = nombre;
        Log.d("Emanuel","value: " + nombre);
    }

     */
}
