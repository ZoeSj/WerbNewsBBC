package com.example.jh.flabbybird;

import android.content.Context;



/** 封装Ui相关的操作 */
public class UiUtils {

	/** 把dp单位的值转换为px单位的值 */
	public static int dp2px(Context con,int dp) {
		float density = con.getResources().getDisplayMetrics().density;
		int px = (int) (dp * density + 0.5f);
		return px;
	}



}














