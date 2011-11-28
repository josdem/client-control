package com.all.client.model;

public enum RepeatType {
	OFF(0, "repeatButton", "tooltip.repeatOff"), ALL(1, "repeatAllButton", "tooltip.repeatAll"), ONE(2, "repeatOneButton",
			"tooltip.repeatOne");
	private final int value;
	private final String synth;
	private final String tooltipSynth;

	private RepeatType(int value, String synth, String tooltipSynth) {
		this.value = value;
		this.synth = synth;
		this.tooltipSynth = tooltipSynth;
	}

	public int value() {
		return value;  
	}

	public String synth() {
		return synth;
	}

	public String getTooltipSynth() {
		return tooltipSynth;
	}

	public static RepeatType valueOf(int repeat) {
		for (RepeatType type : values()) {
			if (type.value == repeat) {
				return type;
			}
		}
		return null;
	}

}
