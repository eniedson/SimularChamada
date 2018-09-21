package ifpb.eniedson.simularchamada;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView text;
    private SharedPreferences sp;
    private int hora;
    private int minuto;
    private Button salvar;
    private Button iniciar;
    private boolean executando;
    private Set<String> diass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AskPermission.Builder(this).setPermissions(Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.PROCESS_OUTGOING_CALLS).setCallback(new PermissionCallback() {
                @Override
                public void onPermissionsGranted(int requestCode) {
                    Toast.makeText(MainActivity.this, "Permissions Received.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionsDenied(int requestCode) {
                    Toast.makeText(MainActivity.this, "Permissions Denied.", Toast.LENGTH_LONG).show();
                }
        }).setErrorCallback(new ErrorCallback() {
            @Override
            public void onShowRationalDialog(final PermissionInterface permissionInterface, int requestCode) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("We need permissions for this app.");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionInterface.onDialogShown();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.show();
            }

            @Override
            public void onShowSettings(final PermissionInterface permissionInterface, int requestCode) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("We need permissions for this app. Open setting screen?");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionInterface.onSettingsShown();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.show();
            }
        }).request(100);
        sp = getSharedPreferences("banco", Context.MODE_PRIVATE);
        text = findViewById(R.id.text);
        salvar = findViewById(R.id.salvar);
        iniciar = findViewById(R.id.iniciar);

        hora = sp.getInt("horas", 0);
        minuto = sp.getInt("minutos", 0);
        executando = sp.getBoolean("exe", false);
        diass = sp.getStringSet("lista",new ArraySet<String>());

        if(executando){
            iniciar.setText("PARAR");
        }

        ArrayList<ToggleButton> btns = new ArrayList<>();
        btns.add((ToggleButton) findViewById(R.id.sunday_toggle));
        btns.add((ToggleButton) findViewById(R.id.monday_toggle));
        btns.add((ToggleButton) findViewById(R.id.tuesday_toggle));
        btns.add((ToggleButton) findViewById(R.id.wednesday_toggle));
        btns.add((ToggleButton) findViewById(R.id.thursday_toggle));
        btns.add((ToggleButton) findViewById(R.id.friday_toggle));
        btns.add((ToggleButton) findViewById(R.id.saturday_toggle));
        for(String d: diass){
            btns.get(Integer.parseInt(d)-1).setChecked(true);
        }

        StringBuilder sb = new StringBuilder();
        if (hora < 10) {
            sb.append("0");
        }
        sb.append(hora);
        sb.append(":");
        if (minuto < 10) {
            sb.append("0");
        }
        sb.append(minuto);
        text.setText(sb.toString());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog tdp = new TimePickerDialog(MainActivity.this, MainActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                tdp.show();
            }
        });
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diass = new ArraySet<>();
                ToggleButton btnSunday = findViewById(R.id.sunday_toggle);
                ToggleButton btnMonday = findViewById(R.id.monday_toggle);
                ToggleButton btnTuesday = findViewById(R.id.tuesday_toggle);
                ToggleButton btnWednsday = findViewById(R.id.wednesday_toggle);
                ToggleButton btnThursday = findViewById(R.id.thursday_toggle);
                ToggleButton btnFriday = findViewById(R.id.friday_toggle);
                ToggleButton btnSaturday = findViewById(R.id.saturday_toggle);

                if (btnSunday.isChecked()) {
                    diass.add("1");
                }
                if (btnMonday.isChecked()) {
                    diass.add("2");
                }
                if (btnTuesday.isChecked()) {
                    diass.add("3");
                }
                if (btnWednsday.isChecked()) {
                    diass.add("4");
                }
                if (btnThursday.isChecked()) {
                    diass.add("5");
                }
                if (btnFriday.isChecked()) {
                    diass.add("6");
                }
                if (btnSaturday.isChecked()) {
                    diass.add("7");
                }
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("horas", hora);
                editor.putInt("minutos", minuto);
                editor.putStringSet("lista",diass);
                editor.apply();
                Toast.makeText(MainActivity.this, "Informações salvas", Toast.LENGTH_SHORT).show();
            }
        });
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executando = !executando;
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("exe", executando);
                editor.apply();
                if(executando){
                    Date date = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
//                    c.add(Calendar.DATE,1);
                    c.set(Calendar.HOUR_OF_DAY,hora);
                    c.set(Calendar.MINUTE,minuto);
                    c.set(Calendar.SECOND,0);

                    AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Intent it = new Intent(MainActivity.this , Ligacao.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,100,it,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                    iniciar.setText("PARAR");
                }else{
                    AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Intent it = new Intent(MainActivity.this , Ligacao.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,100,it,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    iniciar.setText("INICIAR");
                }
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hora = i;
        minuto = i1;
        StringBuilder sb = new StringBuilder();
        if (i < 10) {
            sb.append("0");
        }
        sb.append(i);
        sb.append(":");
        if (i1 < 10) {
            sb.append("0");
        }
        sb.append(i1);
        text.setText(sb.toString());
    }
}
