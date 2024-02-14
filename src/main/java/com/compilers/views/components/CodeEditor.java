package com.compilers.views.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledEditorKit;
import java.awt.*;

public class CodeEditor extends JTextPane {
    public CodeEditor() {
        super();
        var title = new TitledBorder("Editor");
        setBorder(title);
        setEditorKit(new StyledEditorKit());
        setDocument(new DefaultStyledDocument());
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    }
}
