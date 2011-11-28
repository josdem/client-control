package com.all.login.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import com.all.core.common.view.util.CopyPasteKeyAdapterForMac;
import com.all.core.common.view.util.SelectedTextForeground;

public class EditorWithCustomizeSelectedForeground extends BasicComboBoxEditor {

	private static final Color COLOR_SELECTION_TEXTFIELDS = SelectedTextForeground.SELECTED_FOREGROUND_COLOR;

	private JTextField editor = null;

	public EditorWithCustomizeSelectedForeground() {
		super();
		editor = new JTextField();
		editor.addKeyListener(new CopyPasteKeyAdapterForMac());
		editor.setSelectionColor(COLOR_SELECTION_TEXTFIELDS);
	}

	@Override
	public Component getEditorComponent() {
		return editor;
	}

}