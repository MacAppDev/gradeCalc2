package com.weight.generator;

import android.text.InputFilter;
import android.text.Spanned;

public class InputPercentFilter implements InputFilter {
	private double min, max;

	public InputPercentFilter(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public InputPercentFilter(String min, String max) {
		this.min = Integer.parseInt(min);
		this.max = Integer.parseInt(max);
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		try {
			
			double input = Integer.parseInt(dest.toString() + source.toString());
			if (isInRange(min, max, input))
				return null;
		} catch (NumberFormatException nfe) {
		}
		return "";
	}

	private boolean isInRange(double a, double b, double c) {
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}

}