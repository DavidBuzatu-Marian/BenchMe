package com.davidmarian_buzatu.benchme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.TextView;

import com.davidmarian_buzatu.benchme.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultsActivity extends AppCompatActivity {
    private int mNrCores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setInfoAboutPhone();
        //TODO: get extras and show final result
    }

    private void setInfoAboutPhone() {
        TextView deviceModelTV = findViewById(R.id.act_results_TV_device_model);
        TextView deviceOSTV = findViewById(R.id.act_results_TV_device_OS);
        TextView deviceCPUTV = findViewById(R.id.act_results_TV_device_CPU);
        TextView deviceCPUCoresTV = findViewById(R.id.act_results_TV_device_CPU_cores);
        TextView deviceCPUSpeed = findViewById(R.id.act_results_TV_device_CPU_speed);
        TextView deviceRAM = findViewById(R.id.act_results_TV_device_RAM);
        TextView deviceHDD = findViewById(R.id.act_results_TV_device_storage);

        deviceModelTV.setText(new StringBuilder().append("Model: ").append(Build.DEVICE).toString());
        deviceOSTV.setText(new StringBuilder().append("OS: Android ").append(Build.VERSION.RELEASE).toString());
        try {
            deviceCPUTV.setText(new StringBuilder().append("CPU: ").append(getCPUInfo()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        deviceCPUCoresTV.setText(new StringBuilder().append("CPU type: ").append(getNrCores()));
        deviceCPUSpeed.setText(new StringBuilder().append("CPU Speed: ").append(getMaxCPUFreqMHz()).append(" GHz"));
        deviceRAM.setText(new StringBuilder().append("RAM: ").append(getMemorySizeHumanized()));
        deviceHDD.setText(new StringBuilder().append("HDD Size: ").append(getHDDSize()).append(" GB"));
    }

    private String getHDDSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long bytesAvailable = stat.getBlockSizeLong() * stat.getBlockCountLong();
        double gbAvailable = bytesAvailable / 1000000000.0;
        if(gbAvailable > 0 && gbAvailable < 64) {
            gbAvailable = 64.0;
        } else if(gbAvailable > 64 && gbAvailable < 128) {
            gbAvailable = 128.0;
        } else if(gbAvailable > 128 && gbAvailable < 256) {
            gbAvailable = 256.0;
        } else if(gbAvailable > 256 && gbAvailable < 512) {
            gbAvailable = 512.0;
        }
        return new DecimalFormat("#.##").format(gbAvailable);
    }

    /*
        SOURCE GOT FROM https://ourcodeworld.com/articles/read/900/how-to-retrieve-the-available-ram-on-your-android-device-with-java
     */

    public String getMemorySizeHumanized() {
        RandomAccessFile reader;
        String load;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam;
        String lastValue = "";

        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }

            reader.close();

            totRam = Double.parseDouble(value);

            double mb = Math.ceil(totRam / 1024.0);
            double gb = Math.ceil(totRam / 1048576.0);
            double tb = Math.ceil(totRam / 1073741824.0);

            if (tb > 1) {
                lastValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = twoDecimalForm.format(mb).concat(" MB");
            } else {
                lastValue = twoDecimalForm.format(totRam).concat(" KB");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return lastValue;
    }

    private String getNrCores() {
        StringBuilder result = new StringBuilder();
        switch(mNrCores) {
            case 2:
                result.append("Dual core");
                break;
            case 4:
                result.append("Quad core");
                break;
            case 8:
                result.append("Octa core");
                break;
            default:
                result.append(mNrCores).append(" cores");
        }
        return result.toString();
    }

    private String getCPUInfo() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
        String str;
        int nrCPUs = 0;
        StringBuilder cpuInfo = new StringBuilder();

        while ((str = br.readLine()) != null) {
            String[] data = str.trim().split(":");
            if (data.length > 1) {
                if(data[0].contains("Processor")) {
                    cpuInfo.append(data[1]);
                } else if(data[0].contains("processor")) {
                    ++nrCPUs;
                } else if(data[0].contains("Hardware")) {
                    cpuInfo.append(" (").append(data[1]).append(")");
                }
            }

        }
        mNrCores = nrCPUs;
        br.close();

        return cpuInfo.toString();
    }


    private double getMaxCPUFreqMHz() {
        try {

            RandomAccessFile reader = new RandomAccessFile( "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r" );
            String line = reader.readLine();
            NumberFormat fmt = NumberFormat.getNumberInstance();
            fmt.setMaximumFractionDigits(1);
            fmt.setRoundingMode(RoundingMode.CEILING);

            return Double.parseDouble(fmt.format(Double.parseDouble(line) / 1000000));
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }

        return 0;
    }
}
