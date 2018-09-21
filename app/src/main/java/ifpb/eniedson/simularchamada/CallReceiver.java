package ifpb.eniedson.simularchamada;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

public class CallReceiver extends PhoneCallReceiver {

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx, "Ligação recebida encerrada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx, "Ligação iniciada", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx, "Ligação recebida", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx, "Ligação encerrada", Toast.LENGTH_SHORT).show();
    }
}
