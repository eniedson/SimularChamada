package ifpb.eniedson.simularchamada;

import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LigacaoActivity extends AppCompatActivity {

    private FloatingActionButton cancelar;
    private Thread thread;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligacao);

        cancelar = findViewById(R.id.calcelar);
        mp = MediaPlayer.create(this,R.raw.zeldadubstep);
        mp.start();
        thread = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i<20;i++){
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    long milliseconds = 1000;
                    vibrator.vibrate(milliseconds);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.stop();
        mp.stop();
    }
}
