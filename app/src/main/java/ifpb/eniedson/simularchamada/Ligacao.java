package ifpb.eniedson.simularchamada;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import java.util.Calendar;
import java.util.Date;

public class Ligacao extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 83998917437"));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(it2);
//        context.startActivity(new Intent(context, LigacaoActivity.class));
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,1);
        c.set(Calendar.HOUR_OF_DAY,15);
        c.set(Calendar.MINUTE,30);
        c.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent it = new Intent(context , Ligacao.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,it,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

    }
}
