package com.davidmarian_buzatu.benchme.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.util.Log;
import android.widget.TextView;

import com.davidmarian_buzatu.benchme.R;
import com.davidmarian_buzatu.benchme.model.Device;
import com.davidmarian_buzatu.benchme.services.DialogShow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

import static com.davidmarian_buzatu.benchme.model.Device.ATKIN;
import static com.davidmarian_buzatu.benchme.model.Device.CPU;
import static com.davidmarian_buzatu.benchme.model.Device.CPUCORES;
import static com.davidmarian_buzatu.benchme.model.Device.CPUSPEED;
import static com.davidmarian_buzatu.benchme.model.Device.HASH;
import static com.davidmarian_buzatu.benchme.model.Device.HDD;
import static com.davidmarian_buzatu.benchme.model.Device.HDDT;
import static com.davidmarian_buzatu.benchme.model.Device.MERSENNE;
import static com.davidmarian_buzatu.benchme.model.Device.MODEL;
import static com.davidmarian_buzatu.benchme.model.Device.OS;
import static com.davidmarian_buzatu.benchme.model.Device.RAM;
import static com.davidmarian_buzatu.benchme.model.Device.RAMT;
import static com.davidmarian_buzatu.benchme.model.Device.ROOTS;

public class ResultsActivity extends AppCompatActivity {
    private int mNrCores;
    private Map<String, String> infoMap = new HashMap<>();
    private Bundle bundle;
    private Device device = new Device();
    private String infoCPU, infoCPUCores, infoCPUSpeed, infoRAM, infoHDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        final ProgressDialog dialog = DialogShow.getInstance().getDisplayDialog(this, R.string.act_result_dialog_message);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            device.setScoreMersenne((Double) bundle.get(MERSENNE));
            device.setScoreAtkin((Double) bundle.get(ATKIN));
            device.setScoreRoots((Double) bundle.get(ROOTS));
            device.setScoreHDD((Double) bundle.get(HDDT));
            device.setScoreRAM((Double) bundle.get(RAMT));
            device.setScoreHASH((Double) bundle.get(HASH));
        }
        setInfoAboutPhone(dialog);
    }

    private void setInfoAboutPhone(ProgressDialog dialog) {
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
            infoCPU = getCPUInfo();
            deviceCPUTV.setText(new StringBuilder().append("CPU: ").append(infoCPU));
        } catch (IOException e) {
            e.printStackTrace();
        }
        infoCPUCores = getNrCores();
        deviceCPUCoresTV.setText(new StringBuilder().append("CPU type: ").append(infoCPUCores));

        infoCPUSpeed = getMaxCPUFreqMHz() + "GHz";
        deviceCPUSpeed.setText(new StringBuilder().append("CPU Speed: ").append(infoCPUSpeed));
        infoRAM = getMemorySizeHumanized();
        deviceRAM.setText(new StringBuilder().append("RAM: ").append(infoRAM));
        infoHDD = getHDDSize() + " GB";
        deviceHDD.setText(new StringBuilder().append("HDD Size: ").append(infoHDD));

        setDeviceMap(infoCPU, infoCPUCores, infoCPUSpeed, infoRAM, infoHDD);
        saveToFirebase(dialog);
    }

    private void setDeviceMap(String infoCPU, String infoCPUCores, String infoCPUSpeed, String infoRAM, String infoHDD) {
        infoMap.put(MODEL, Build.DEVICE);
        infoMap.put(OS, Build.VERSION.RELEASE);
        infoMap.put(CPU, infoCPU);
        infoMap.put(CPUCORES, infoCPUCores);

        infoMap.put(CPUSPEED, infoCPUSpeed);
        infoMap.put(RAM, infoRAM);
        infoMap.put(HDD, infoHDD);
    }

    private String getHDDSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long bytesAvailable = stat.getBlockSizeLong() * stat.getBlockCountLong();
        double gbAvailable = bytesAvailable / 1000000000.0;
        if (gbAvailable > 0 && gbAvailable < 64) {
            gbAvailable = 64.0;
        } else if (gbAvailable > 64 && gbAvailable < 128) {
            gbAvailable = 128.0;
        } else if (gbAvailable > 128 && gbAvailable < 256) {
            gbAvailable = 256.0;
        } else if (gbAvailable > 256 && gbAvailable < 512) {
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
        switch (mNrCores) {
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
                if (data[0].contains("Processor")) {
                    cpuInfo.append(data[1]);
                } else if (data[0].contains("processor")) {
                    ++nrCPUs;
                } else if (data[0].contains("Hardware")) {
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

            RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            String line = reader.readLine();
            NumberFormat fmt = NumberFormat.getNumberInstance();
            fmt.setMaximumFractionDigits(1);
            fmt.setRoundingMode(RoundingMode.CEILING);

            return Double.parseDouble(fmt.format(Double.parseDouble(line) / 1000000));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    private void saveToFirebase(ProgressDialog dialog) {
        setDeviceInfo();
        FirebaseFirestore.getInstance().collection("devices").add(device).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                dialog.dismiss();
            }
        });
    }

    private void setDeviceInfo() {
        device.setDeviceModel(infoMap.get(MODEL));
        device.setDeviceOS(infoMap.get(OS));
        device.setDeviceCPU(infoMap.get(CPU));
        device.setDeviceCPUCores(infoMap.get(CPUCORES));
        device.setDeviceCPUSpeed(infoMap.get(CPUSPEED));
        device.setDeviceRAM(infoMap.get(RAM));
        device.setDeviceHDD(infoMap.get(HDD));
    }
}
