/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package org.maptalks.poi.animation;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.ShapeType;
import org.apache.poi.xslf.usermodel.*;
import org.junit.Test;
import org.maptalks.poi.shape.Table;
import org.maptalks.poi.shape.symbol.TextBoxSymbol;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by wangjun on 16/2/18.
 */
public class TestTableWithCustomSymbol {

    @Test
    public void testCustomTable() throws Exception {
        XMLSlideShow pptx = new XMLSlideShow();
        String imagePath = this.getClass().getResource("/images/text").getPath();
        XSLFSlide slide = pptx.createSlide();
        //add tile
        String tilePath = imagePath+"/tile.png";
        File tileFile = new File(tilePath);
        BufferedImage bufferedImage = ImageIO.read(tileFile);
        double width = bufferedImage.getWidth(), height = bufferedImage.getHeight();
        Dimension screenSize = new Dimension(1024, 768);
        screenSize.setSize(width, height);
        pptx.setPageSize(screenSize);

        BufferedInputStream in = new  BufferedInputStream(new FileInputStream(tileFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        int size=0;
        byte[] temp=new byte[1024];
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] content = out.toByteArray();
        XSLFPictureData pictureData = pptx.addPicture(content, PictureData.PictureType.PNG);
        XSLFPictureShape picShape = slide.createPicture(pictureData);
        picShape.setLineWidth(0);
        picShape.setAnchor(new Rectangle2D.Double(0, 0, width, height));
        out.close();

        //add table
        String[][] rows = {{"序号","名称","面积","说明"},
                {
                    "1",
                    "柳州万达",
                    "88000",
                    "购物中心商业面积8.8万M2,主力店：冠超市、万达影院、大玩家、宝贝王等，配套停车位1500个。在万象城未开业前为柳州最高端的购物中心。"
                },
                {
                    "2",
                    "万象城",
                    "113600",
                    "购物中心商业总建面11.36万M2，配套车位1500个。主力店：沃尔玛、金逸影院、Meland儿童成长乐园、反斗城、星际传奇等。将打造成为柳州最高端的购物中心。"
                }
        };
        TextBoxSymbol symbol = new TextBoxSymbol();
        symbol.setWordWrap(true);
        symbol.setLineColor(Color.black);
        symbol.setLineOpacity(0.0);
        symbol.setLineWidth(1.0);
        symbol.setFillColor(Color.WHITE);
        symbol.setFillOpacity(0.0);

        symbol.setTextWeight("normal");
        symbol.setTextStyle("normal");
        symbol.setHorizontal("middle");
        symbol.setVertical("middle");
        symbol.setFontColor(Color.BLUE);
        symbol.setTextOpacity(1.0);
        symbol.setFontSize(14.0);
        Double[] padding = {0.0, 0.0};
        symbol.setPadding(padding);
        symbol.setLineSpacing(8.0);
//        symbol.setBoxType(ShapeType.RECT);

        TextBoxSymbol[][] symbols = {{symbol,symbol,symbol,symbol},
                {symbol,symbol,symbol,symbol},
                {symbol,symbol,symbol,symbol}
        };
        Double[] rowHeights = {22.0, 79.0, 100.0};
        Double[] colWidths = {44.0, 78.0, 117.0, 328.0};
        XSLFTable table = new Table(6.0, 5.0, 567.0, 201.0, rows, symbols, rowHeights, colWidths).convertTo(slide.createTable());
        String savePath = this.getClass().getResource("/ppt").getPath();
        FileOutputStream output = new FileOutputStream(savePath+"/custom_table.pptx");
        pptx.write(output);
        output.close();
    }
   
}