package com.example.leo.gofish;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karlo on 11/15/2016.
 */

public class ListFragment extends Fragment {
    List<Station> stations = new ArrayList<Station>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_fragment, container, false);
        ListView lv = (ListView) root.findViewById(R.id.station_list);
        init();
        ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(getActivity(), R.layout.custom_list_textview, stations);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Station s = stations.get(i);
                DownloadFile df = new DownloadFile(getActivity());
                if(!(df.getStatus() == AsyncTask.Status.RUNNING)) {
                    df.execute(s);
                    DetailFragment frag = new DetailFragment();
                    df.delegate = frag;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Station", s);
                    frag.setArguments(bundle);

                    Fragment fr = frag;
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fr);
                    ft.commit();
                }
            }
        });
        return root;
    }

    public void init() {
        InputStream inputstream = getResources().openRawResource(R.raw.stations);
        CSVFile csv = new CSVFile(inputstream);
        stations = csv.read();
    }

}
