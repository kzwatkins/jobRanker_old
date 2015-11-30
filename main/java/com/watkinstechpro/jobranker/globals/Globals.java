package com.watkinstechpro.jobranker.globals;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kwatkins on 11/22/2015.
 */
public class Globals extends Application {
    private static Globals instance;
    public static int BEGIN_YEAR = 2012;
    public static int END_YEAR = 2022;

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
    }

    public static Globals getInstance(){
        return instance;
    }

    public enum BLS_PARAMS{
        total,
        miningLogging,
        construction,
        manufacturing,
        tradeUtilitiesTransport,
        information,
        profBusinessServices,
        educationAndHealth,
        leisureHosp,
        otherServices,
        government
    }

    public enum EDUCATION_LEVELS{
        total,
        phd,
        ms,
        bs,
        as,
        bacc,
        someCollege,
        hsDiploma,
        lessThanHs,
    }

   // public static final Map<String, String> BLS_NATL_EMPLOYMENT_MAP;
    public static final Map<String, Double> BLS_JOB_INUNDATION_MAP;
    public static final Map<String, Double> BLS_UNEMPLOYMENT_RATE_MAP;
    public static final Map<String, LinearEquation> BLS_JOB_GROWTH_BY_ED_MAP;
    public static final Map<String, LinearEquation> BLS_EMPLOYMENT_MAP;
    public static final Map<String, Integer> BLS_SALARY_MAP;
    public static final String[] BLS_FACTS;

    static {
        //BLS_NATL_EMPLOYMENT_MAP = Collections.unmodifiableMap(getBlsNatlEmploymentMap());
        BLS_JOB_INUNDATION_MAP = Collections.unmodifiableMap(getBlsJobInundationMap());
        BLS_UNEMPLOYMENT_RATE_MAP = Collections.unmodifiableMap(getBlsUnemploymentRateMap());
        BLS_JOB_GROWTH_BY_ED_MAP = Collections.unmodifiableMap(getBlsJobGrowthByEdMap());
        BLS_EMPLOYMENT_MAP = Collections.unmodifiableMap(getBlsEmploymentMap());
        BLS_SALARY_MAP = Collections.unmodifiableMap(getBlsSalaryMap());
        BLS_FACTS = getBlsFacts();
    }

    private static String[] getBlsFacts(){
        ArrayList<String> blsFacts = new ArrayList<>();
        blsFacts.add("According to average national data, people can make *double* the salary each year if they finish their college degree instead of dropping out of college!");
        blsFacts.add("According to average national data, people who drop out of college make almost 20% less money each year than others who only complete their high school diplomas!");
        blsFacts.add("According to average national data, the highest percentage growth of those employed by year 2022 will have a Master's degree!");
        String[] blsFactsAry = new String[blsFacts.size()];
        blsFacts.toArray(blsFactsAry);

        return blsFactsAry;
    }
//    private static Map<String, String> getBlsNatlEmploymentMap() {
//        Map<String, String> map = new HashMap<>(10);
//        map.put(BLS_PARAMS.total.name(), "CEU0000000001");
//        map.put(BLS_PARAMS.miningLogging.name(), "CEU1000000001");
//        map.put(BLS_PARAMS.construction.name(), "CEU2000000001");
//        map.put(BLS_PARAMS.manufacturing.name(), "CEU3000000001");
//        map.put(BLS_PARAMS.tradeUtilitiesTransport.name(), "CEU4000000001");
//        map.put(BLS_PARAMS.information.name(), "CEU5000000001");
//        map.put(BLS_PARAMS.profBusinessServices.name(), "CEU6000000001");
//        map.put(BLS_PARAMS.leisureHosp.name(), "CEU7000000001");
//        map.put(BLS_PARAMS.otherServices.name(), "CEU8000000001");
//        map.put(BLS_PARAMS.government.name(), "CEU9000000001");
//
//        return map;
//    }

    private static Map<String, Double> getBlsJobInundationMap() {
        Map<String, Double> map = new HashMap<>(11);
        map.put(BLS_PARAMS.total.name(), 0.9459);
        map.put(BLS_PARAMS.miningLogging.name(), 0.975); // included with construction
        map.put(BLS_PARAMS.construction.name(), 0.975);
        map.put(BLS_PARAMS.manufacturing.name(), 0.92);
        map.put(BLS_PARAMS.tradeUtilitiesTransport.name(), 1.0541);
        map.put(BLS_PARAMS.information.name(), 0.9455);// included with professional business services
        map.put(BLS_PARAMS.profBusinessServices.name(), 0.9455);
        map.put(BLS_PARAMS.educationAndHealth.name(), 0.6);
        map.put(BLS_PARAMS.leisureHosp.name(), 1.4318);
        map.put(BLS_PARAMS.otherServices.name(), 0.9455); // included with professional business services
        map.put(BLS_PARAMS.government.name(), 0.6818);

        return map;
    }

    private static Map<String, Double> getBlsUnemploymentRateMap() {
        Map<String, Double> map = new HashMap<>(11);
        map.put(BLS_PARAMS.total.name(), 0.048);
        map.put(BLS_PARAMS.miningLogging.name(), 0.094);
        map.put(BLS_PARAMS.construction.name(), 0.062);
        map.put(BLS_PARAMS.manufacturing.name(), 0.04);
        map.put(BLS_PARAMS.tradeUtilitiesTransport.name(), 0.41);
        map.put(BLS_PARAMS.information.name(), 0.03);
        map.put(BLS_PARAMS.profBusinessServices.name(), 0.054);
        map.put(BLS_PARAMS.educationAndHealth.name(), 0.034);
        map.put(BLS_PARAMS.leisureHosp.name(), 0.08);
        map.put(BLS_PARAMS.otherServices.name(), 0.042);
        map.put(BLS_PARAMS.government.name(), 0.024);

        return map;
    }

    private static Map<String, Integer> getBlsSalaryMap() {
        Map<String, Integer> map = new HashMap<>(8);
        map.put(EDUCATION_LEVELS.total.name(), 34750);
        map.put(EDUCATION_LEVELS.phd.name(), 96420);
        map.put(EDUCATION_LEVELS.ms.name(), 63400);
        map.put(EDUCATION_LEVELS.bs.name(), 67140);
        map.put(EDUCATION_LEVELS.as.name(), 57590);
        map.put(EDUCATION_LEVELS.bacc.name(), 34760);
        map.put(EDUCATION_LEVELS.someCollege.name(), 28730);
        map.put(EDUCATION_LEVELS.hsDiploma.name(), 35170);
        map.put(EDUCATION_LEVELS.lessThanHs.name(), 20110);

        return map;
    }

    private static Map<String, LinearEquation> getBlsJobGrowthByEdMap() {
        Map<String, LinearEquation> map = new HashMap<>(9);

        map.put("total", new LinearEquation(1953.4875, -3785061.05));
        map.put("phd", new LinearEquation(179.8, -156555.2));
        map.put("ms", new LinearEquation(156.0625, -110365.55));
        map.put("bs", new LinearEquation(1392.9625, -764607.55));
        map.put("as", new LinearEquation(1130.75, -257114.1));
        map.put("nonDegree", new LinearEquation(1167.125, -327701.3));
        map.put("someCollege", new LinearEquation(128.125, -54600.3));
        map.put("diploma", new LinearEquation(1578.85, -1106381.8));
        map.put("lessThanHS", new LinearEquation(1519.8, -1007710.0));

        return map;
    }

    private static Map<String, LinearEquation> getBlsEmploymentMap(){
        // http://www.bls.gov/news.release/ecopro.t03.htm
        Map<String, LinearEquation> map = new HashMap<>(10);


        map.put(BLS_PARAMS.total.name(), new LinearEquation(BEGIN_YEAR, 145355.8, END_YEAR, 160983.7));
        map.put(BLS_PARAMS.miningLogging.name(), new LinearEquation(BEGIN_YEAR, 800.5, END_YEAR, 921.7));
        map.put(BLS_PARAMS.construction.name(), new LinearEquation(BEGIN_YEAR, 5640.9, END_YEAR, 7263.0));
        map.put(BLS_PARAMS.manufacturing.name(), new LinearEquation(BEGIN_YEAR,  11918.9, END_YEAR, 11369.4));
        map.put(BLS_PARAMS.tradeUtilitiesTransport.name(), new LinearEquation(BEGIN_YEAR, 25517.0, END_YEAR, 27349.2));
        map.put(BLS_PARAMS.information.name(), new LinearEquation(BEGIN_YEAR, 2677.6, END_YEAR, 2612.4));
        map.put(BLS_PARAMS.profBusinessServices.name(), new LinearEquation(BEGIN_YEAR, 17930.2, END_YEAR, 21413.0));
        map.put(BLS_PARAMS.educationAndHealth.name(), new LinearEquation(BEGIN_YEAR, 20318.7, END_YEAR, 25988.1));
        map.put(BLS_PARAMS.leisureHosp.name(), new LinearEquation(BEGIN_YEAR, 13745.8, END_YEAR, 15035.0));
        map.put(BLS_PARAMS.otherServices.name(), new LinearEquation(BEGIN_YEAR, 6174.5, END_YEAR, 6823.4));
        map.put(BLS_PARAMS.government.name(), new LinearEquation(BEGIN_YEAR, 21972.2, END_YEAR, 22438.7));

        return map;
    }

    public static boolean isOnline() {
        Context context = getInstance().getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Log.d("netInfo", netInfo.toString());
        Log.d("activeNetworkInfo", cm.getActiveNetworkInfo().toString());
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
