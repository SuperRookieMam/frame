package com.yhl.util;

import com.yhl.constpackage.InterceputConst;

public class InterceptUtil {

    public static  boolean isInInterceptMethod(String statementId){
        for (int i = 0; i < InterceputConst.METHOD_NAME.length; i++) {
            if (statementId.contains(InterceputConst.METHOD_NAME[i])){
                return true;
            }
        }
        return  false;
    }

}
