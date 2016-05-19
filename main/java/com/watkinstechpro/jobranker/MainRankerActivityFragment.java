package com.watkinstechpro.jobranker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.watkinstechpro.jobranker.globals.Globals;
import com.watkinstechpro.jobranker.globals.LinearEquation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainRankerActivityFragment extends Fragment {

    public MainRankerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_ranker, container, false);
        FragmentActivity activity = getActivity();
        final int NUM_JOB_RANKING_TYPES = 3;
        final Map<Double, String> jobRanksingMap = getJobRanking();
        int numJobs = jobRanksingMap.size();
        int worstJobEndIndex = numJobs / NUM_JOB_RANKING_TYPES;
        int bestJobBeginIndex = numJobs - numJobs / worstJobEndIndex; // worstJobEndIndex is the same as the fraction of number of jobs

//        String[] worstJobs = new String[worstJobEndIndex + 1];
//        String[] bestJobs = new String[numJobs - bestJobBeginIndex];
//        String[] avgJobs = new String[(bestJobBeginIndex - 1)-(worstJobEndIndex + 1) + 1];

//        ListView avgJobsListView = (ListView) activity.findViewById(R.id.listViewAvgRankings);
//        final ArrayAdapter adapter = new ArrayAdapter(activity,
//                android.R.layout.simple_list_item_1, toAryList(avgJobs));
//        avgJobsListView.setAdapter(adapter);

        EditText editText = (EditText) view.findViewById(R.id.edtTxtJobs);
        String text ="****** !!JOB RANKER!! ******";
                text += "\n\n*** The Worst Areas to Work ***".toUpperCase(Locale.US);

        int index = 0;

            for(Double value:jobRanksingMap.keySet()) {
                if (index == worstJobEndIndex + 1) {
                    text += "\n\n*** The So-So AREAS TO WORK ***".toUpperCase(Locale.US);
                } else if (index == bestJobBeginIndex) {
                    text += "\n\n*** The GOOD AREAS TO WORK ***".toUpperCase(Locale.US);
                }

                text += "\n" + (numJobs - index) + ". " + Globals.getPhrase(jobRanksingMap.get(value));
                index++;
            }

        editText.setText(text);
        return view;
    }



    private ArrayList<String> toAryList(String[] ary){
        if(ary == null || ary.length == 0) return null;
        ArrayList<String> aryLst = new ArrayList<>(ary.length);
        for(String item:ary){
            aryLst.add(item);
        }
        return aryLst;
    }

    private Map<Double, String> getJobRanking(){
        final Globals globalVariable = (Globals) getActivity().getApplicationContext();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Globals.BLS_PARAMS[] blsParams = Globals.BLS_PARAMS.values();

        SortedMap<Double, String> jobsRankingsMap = new TreeMap<>();
        double blsJobGrowth = getFactor(Globals.BLS_JOB_GROWTH_BY_ED_MAP, "bs", year, false);
        Log.d("bs"+"_blsJobGrowth", Double.toString(blsJobGrowth));

        for(Globals.BLS_PARAMS blsParam: blsParams){
            if(blsParam.equals(Globals.BLS_PARAMS.total)) continue;
            String blsParamName = blsParam.name();

            double blsEmployment = getFactor(Globals.BLS_EMPLOYMENT_MAP, blsParamName, year, true);
            Log.d(blsParamName+"_blsEmployment", Double.toString(blsEmployment));
            double blsInundation = getFactor(Globals.BLS_JOB_INUNDATION_MAP, blsParamName);
            Log.d(blsParamName+"_blsInundation", Double.toString(blsInundation));
             double blsUnemploymentFactor = 1 - Globals.BLS_UNEMPLOYMENT_RATE_MAP.get(blsParamName);
            Log.d(blsParamName+"_blsUnEmploymenRate", Double.toString(blsUnemploymentFactor));

            double result = blsEmployment * blsInundation * blsJobGrowth * blsUnemploymentFactor;
            Log.d(blsParamName+"_result"+blsParamName, Double.toString(blsEmployment));
            jobsRankingsMap.put(result, blsParamName);
        }

        Log.d("jobRankings",jobsRankingsMap.toString());
        return jobsRankingsMap;
    }

    private double getFactor(Map<String, Double> map, String param){
        double total = Globals.BLS_JOB_INUNDATION_MAP.get(Globals.BLS_PARAMS.total.name());
        double value = map.get(param);
        return total / value;
    }

    private double getFactor(Map<String, LinearEquation> map, String param, double year, boolean invert){
        double total = map.get(param).getResult(Globals.END_YEAR);
        double value = map.get(param).getResult(year);
        double result = value / total;
        return(invert?Math.pow(result, -1):result);
    }
}
