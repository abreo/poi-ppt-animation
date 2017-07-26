package org.maptalks.poi.shape;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.maptalks.poi.shape.symbol.TextBoxSymbol;

import java.awt.geom.Rectangle2D;

/**
 * Created by wangjun on 2017/7/20.
 */
public class TextBox {

    private String text;

    private double left = 0.0;

    private double top = 0.0;

    private double width = 0.0;

    private double height = 0.0;

    private TextBoxSymbol textBoxSymbol = new TextBoxSymbol();

    public TextBox(String text) {
        this.text = text;
        this.width = 100;
        this.height = 16;
        this.textBoxSymbol = TextBoxSymbol.DEFAULT_SYMBOL;
    }

    public TextBox(String text, double left, double top, double width, double height, TextBoxSymbol symbol) {
        this.text = text;
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.textBoxSymbol = symbol;
    }

    public XSLFTextBox addTo(XSLFSlide slide) {
        if(slide == null) return null;
        XSLFTextBox textBox = slide.createTextBox();
        Rectangle2D textAnchor = new Rectangle2D.Double(this.left, this.top, this.width, this.height);
        textBox.setAnchor(textAnchor);

        textBox.setLineColor(this.textBoxSymbol.getLineColor());
        textBox.setFillColor(this.textBoxSymbol.getFillColor());
        textBox.setVerticalAlignment(this.textBoxSymbol.getVerticalAlignment());
        textBox.setWordWrap(this.textBoxSymbol.isWordWrap());

        XSLFTextRun text = textBox.setText(this.text);

        text.setFontColor(this.textBoxSymbol.getFontColor());
        text.setFontSize(this.textBoxSymbol.getFontSize());
        text.setFontFamily(this.textBoxSymbol.getFontFamily());
        return textBox;
    }
}
