package com.example.bloodlinks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;

public class  MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2900;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HashMap<String, String[]>p=
//                new HashMap<String, String[]>(){{
//                    put("Akurdi", new String[]{"18.6486417", "73.7647081"}); put("Ambegaon", new String[]{"19.1318288", "73.7318062"}); put("Aundh", new String[]{"18.5618834", "73.8101957"}); put("Balewadi", new String[]{"18.5820266", "73.7689831"}); put("Baner", new String[]{"18.5642431", "73.7768573"}); put("Bavdhan", new String[]{"18.503663", "73.766679"}); put("Bhavani Peth", new String[]{"18.510753", "73.870265"}); put("Bhosari", new String[]{"18.6210093", "73.8501298"}); put("Bibvewadi", new String[]{"18.472614", "73.867191"}); put("Budhwar Peth", new String[]{"18.5175599", "73.858037"}); put("Chakan", new String[]{"18.7623111", "73.8625448"}); put("Charholi Budruk", new String[]{"18.651442", "73.904604"}); put("Chikhli", new String[]{"18.6784229", "73.8164871"}); put("Chinchwad", new String[]{"18.6315806", "73.7854601"}); put("Dapodi", new String[]{"18.5808457", "73.8327748"}); put("Dehu Road", new String[]{"18.680047", "73.7343306"}); put("Dhankawadi", new String[]{"18.464590", "73.850474"}); put("Dhanori", new String[]{"18.5906707", "73.8913189"}); put("Dhayari", new String[]{"18.4373984", "73.8190432"}); put("Dighi", new String[]{"18.616875", "73.878876"}); put("Dudulgaon", new String[]{"18.677188", "73.881960"}); put("Erandwane", new String[]{"18.507307", "73.831590"}); put("Fursungi", new String[]{"18.4727247", "73.9589417"}); put("Ganesh Peth", new String[]{"18.5154218", "73.8647366"}); put("Ganesh khind", new String[]{"18.554747", "73.824184"}); put("Ghorpade Peth", new String[]{"18.5257134", "73.8606351"}); put("Ghorpadi", new String[]{"18.5268544", "73.9000956"}); put("Guruwar Peth", new String[]{"18.5113589", "73.8576197"}); put("Hadapsar", new String[]{"18.5198046", "73.9344461"}); put("Hinjwadi", new String[]{"18.590315", "73.733011"}); put("Kalas", new String[]{"18.588604", "73.876221"}); put("Kalewadi", new String[]{"18.6039604", "73.777045"}); put("Kasarwadi", new String[]{"18.6012765", "73.8236984"}); put("Kasba Peth", new String[]{"18.5219052", "73.8582903"}); put("Katraj", new String[]{"18.4536792", "73.8563196"}); put("Khadki", new String[]{"18.5681753", "73.8507787"}); put("Kharadi", new String[]{"18.5505175", "73.9424945"}); put("Kondhwa", new String[]{"18.4777463", "73.8941378"}); put("Koregaon Park", new String[]{"18.5375532", "73.8939252"}); put("Kothrud", new String[]{"18.5038889", "73.807673"}); put("Mahatma Phule Peth", new String[]{"18.5089336", "73.8733395"}); put("Mangalwar Peth", new String[]{"18.5243388", "73.8592081"}); put("Manjri", new String[]{"18.523232", "73.985460"}); put("Markal", new String[]{"18.671511", "73.965863"}); put("Mohammedwadi", new String[]{"18.470321", "73.913362"}); put("Moshi", new String[]{"18.6523154", "73.8442032"}); put("Mundhwa", new String[]{"18.5336703", "73.9317831"}); put("Nana Peth", new String[]{"18.5183568", "73.8563098"}); put("Narayan Peth",
//                        new String[]{"18.5155793", "73.8510765"}); put("Navi Peth", new String[]{"18.50927", "73.84415"}); put("Panmala", new String[]{"18.500140", "73.839517"}); put("Parvati", new String[]{"18.497561", "73.8470954"}); put("Pashan", new String[]{"18.5386704", "73.7952542"}); put("Phugewadi", new String[]{"18.5887843", "73.8306707"}); put("Pimple Gurav", new String[]{"18.589395", "73.811475"}); put("Pimple Nilakh", new String[]{"18.5696793", "73.7940824"}); put("Pimple Saudagar", new String[]{"18.5981553", "73.7977661"}); put("Pimpri", new String[]{"18.624090", "73.798579"}); put("Pirangut", new String[]{"18.5112818", "73.6790069"}); put("Rahatani", new String[]{"18.6070438", "73.7958009"}); put("Rasta Peth", new String[]{"18.5219151", "73.8665761"}); put("Ravet", new String[]{"18.643269", "73.7450557"}); put("Raviwar Peth", new String[]{"18.515377", "73.860640"}); put("Sadashiv Peth", new String[]{"18.5107764", "73.8502149"}); put("Sangvi", new String[]{"18.5895213", "73.831923"}); put("Saswad", new String[]{"18.3444347", "74.0295234"}); put("Shaniwar Peth", new String[]{"18.5192839", "73.8524863"}); put("Shivajinagar ", new String[]{"18.5294586", "73.8478247"}); put("Shukrawar Peth", new String[]{"18.5113537", "73.8540109"}); put("Somwar Peth", new String[]{"18.5217663", "73.8669305"}); put("Talawade", new String[]{"18.7396582", "73.8068571"}); put("Tathawade", new String[]{"18.4933085", "73.8273183"}); put("Thergaon", new String[]{"18.609275", "73.7729374"}); put("Undri", new String[]{"18.4581575", "73.9150286"}); put("Vadgaon Budruk", new String[]{"18.4674971", "73.825365"}); put("Vishrantwadi", new String[]{"18.57263", "73.87825"}); put("Vitthalwadi", new String[]{"18.481749", "73.829208"}); put("Wadgaon Sheri", new String[]{"18.551754", "73.920050"}); put("Wagholi", new String[]{"18.5806299", "73.9833099"}); put("Wakad", new String[]{"18.599803", "73.762438"}); put("Wanwadi", new String[]{"18.493390", "73.899205"}); put("Warje", new String[]{"18.4820444", "73.8001705"}); put("Yerwada", new String[]{"18.557992", "73.882885"});
//        }};
//
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference pr = db.collection("Places");
//
//        for(Map.Entry<String, String[]> entry: p.entrySet()) {
//            HashMap<String,Object>temp=new HashMap<>();
//            temp.put("location", entry.getKey());
//            temp.put("latitude", entry.getValue()[0]);
//            temp.put("longitude", entry.getValue()[1]);
//            pr.document().set(temp);
//        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(MainActivity.this, ActivityHome.class);
                startActivity(homeintent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }

}
