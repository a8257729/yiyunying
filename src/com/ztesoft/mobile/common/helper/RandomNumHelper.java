package com.ztesoft.mobile.common.helper;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 复杂随机数生成类
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */

public class RandomNumHelper {

    private Long randomnum = null;
    private Float randomfloat = null;
    private boolean floatvalue = false;
    private long upper = 100;
    private long lower = 0;
    private String algorithm = null;
    private String provider = null;
    private boolean secure = false;
    private Random random = null;
    private SecureRandom secrandom = null;

    private final float getFloat() {
        if (ValidateHelper.validateNotNull(random)) {
            return random.nextFloat();

        } else {
            return secrandom.nextFloat();
        }
    }


    public final void generateRandomObject() throws Exception {

        if (secure) {
            try {
                if (ValidateHelper.validateNotNull(provider)) {
                    secrandom = SecureRandom.getInstance(algorithm, provider);
                } else {
                    secrandom = SecureRandom.getInstance(algorithm);
                }
            } catch (NoSuchAlgorithmException ne) {
                throw new Exception(ne.getMessage());
            } catch (NoSuchProviderException pe) {
                throw new Exception(pe.getMessage());
            }
        } else {
            random = new Random();
        }
    }

    /**
     * generate the random number
     *
     */
    private final void generaterandom() {

        if (floatvalue) {
            randomfloat = new Float(getFloat());
        } else {
            randomnum = new Long(lower + (long) ((getFloat() * (upper - lower))));
        }
    }

    public final Number getRandom() {
        generaterandom();
        if (floatvalue) {
            return randomfloat;
        } else {
            return randomnum;
        }
    }

    public final void setRange(long low, long up) {

        lower = low;
        upper = up;

        if ((lower == 0) && (upper == 1)) {
            floatvalue = true;
        }
    }


    public final void setAlgorithm(String value) {
        algorithm = value;
        secure = true;
    }

    public final void setProvider(String value) {
        provider = value;
    }

    public final void setRange(String value) throws Exception {
        try {
            upper = new Integer(value.substring(value.indexOf('-') + 1)).
                    longValue();
        } catch (Exception ex) {
            throw new Exception("upper attribute could not be" +
                                " turned into an Integer default value was used");
        }

        try {
            lower = new Integer(value.substring(0, value.indexOf('-'))).
                    longValue();
        } catch (Exception ex) {
            throw new Exception("lower attribute could not be" +
                                " turned into an Integer default value was used");
        }

        if ((lower == 0) && (upper == 1)) {
            floatvalue = true;
        }

        if (upper < lower) {
            throw new Exception("You can't have a range where the lowerbound" +
                                " is higher than the upperbound.");
        }

    }

    public static Double getRandom(Long min, Long max) throws Exception {
        RandomNumHelper rNum = new RandomNumHelper();
        rNum.setRange(Math.round(min.longValue()), Math.round(max.longValue()));
        rNum.generateRandomObject();
        return new Double(rNum.getRandom().doubleValue());
    }


    public static Double[] getRandomDouble(double min, double max, int count) throws
            Exception {
        RandomNumHelper rNum = new RandomNumHelper();
        rNum.setRange(java.lang.Math.round(min), java.lang.Math.round(max));
        rNum.generateRandomObject();
        Double[] ds = new Double[count];
        for (int i = 0; i < count; i++) {
            ds[i] = new Double(rNum.getRandom().doubleValue());
        }
        return ds;
    }

    public static boolean[] getRandomBoolean(int count) {
        java.util.Random random = new java.util.Random();
        boolean[] booles = new boolean[count];
        for (int i = 0; i < booles.length; i++) {
            booles[i] = random.nextBoolean();
        }
        return booles;
    }

    public static void main(String[] args) throws Exception {
        RandomNumHelper RNUM = new RandomNumHelper();
        RNUM.setRange(java.lang.Math.round(1), java.lang.Math.round(20000));
        RNUM.generateRandomObject();
        for (int i = 0; i < 2000; i++) {
            //System.out.println(RNUM.getRandom().doubleValue());
        }
    }

}
